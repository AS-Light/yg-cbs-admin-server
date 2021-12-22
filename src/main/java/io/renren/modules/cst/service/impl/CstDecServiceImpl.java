package io.renren.modules.cst.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.jaxb.FileUtil;
import io.renren.common.jaxb.XmlUtil;
import io.renren.common.jaxb.ZipUtil;
import io.renren.common.jaxb.dec.Container;
import io.renren.common.jaxb.dec.DecHead;
import io.renren.common.jaxb.dec.DecList;
import io.renren.common.jaxb.dec.DecMessage;
import io.renren.modules.cst.dao.CstDecContainerDao;
import io.renren.modules.cst.dao.CstDecDao;
import io.renren.modules.cst.dao.CstDecHeaderDao;
import io.renren.modules.cst.dao.CstDecListDao;
import io.renren.modules.cst.entity.CstDecContainerEntity;
import io.renren.modules.cst.entity.CstDecEntity;
import io.renren.modules.cst.entity.CstDecHeaderEntity;
import io.renren.modules.cst.entity.CstDecListEntity;
import io.renren.modules.cst.service.CstDecService;
import io.renren.modules.oss.cloud.OSSFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 海关报关单表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-12-13 19:21:36
 */
@Service("cstDecService")
public class CstDecServiceImpl implements CstDecService {
    private CstDecDao decDao;
    private CstDecHeaderDao headerDao;
    private CstDecListDao goodsDao;
    private CstDecContainerDao containerDao;

    @Autowired
    public void setDecDao(CstDecDao decDao) {
        this.decDao = decDao;
    }

    @Autowired
    public void setHeaderDao(CstDecHeaderDao headerDao) {
        this.headerDao = headerDao;
    }

    @Autowired
    public void setGoodsDao(CstDecListDao goodsDao) {
        this.goodsDao = goodsDao;
    }

    @Autowired
    public void setContainerDao(CstDecContainerDao containerDao) {
        this.containerDao = containerDao;
    }

    @Override
    public CstDecEntity detail(Long id) {
        return decDao.detail(id);
    }

    @Override
    public CstDecEntity detailByImportId(Long importId) {
        return decDao.detailByImportId(importId);
    }

    @Override
    public CstDecEntity detailByExportId(Long exportId) {
        return decDao.detailByExportId(exportId);
    }

    @Override
    public List<CstDecEntity> listByContractId(Long contractId) {
        return decDao.queryByContractId(contractId);
    }

    @Override
    @Transactional
    public Long save(CstDecEntity entity) throws RuntimeException {
        CstDecHeaderEntity headerEntity = entity.getDecHeaderEntity();
        if (headerEntity != null) {
            // 存储表头信息, 没有Id就创建，有Id就更新
            if (headerEntity.getId() == null) {
                headerDao.insert(headerEntity);
            } else {
                headerDao.updateById(headerEntity);
            }
            // 存储料件和成品表体
            List<CstDecListEntity> oldDecGoodsList = goodsDao.selectList(new QueryWrapper<CstDecListEntity>().eq("fk_dec_header_id", headerEntity.getId()));
            List<CstDecListEntity> newDecGoodsList = entity.getDecGoodsList();
            if (oldDecGoodsList != null) {
                for (CstDecListEntity oldDecGoods : oldDecGoodsList) {
                    goodsDao.deleteById(oldDecGoods.getId());
                }
            }
            if (newDecGoodsList != null) {
                for (CstDecListEntity newDecGoods : newDecGoodsList) {
                    newDecGoods.setFkDecHeaderId(headerEntity.getId());
                    goodsDao.insert(newDecGoods);
                }
            }
            // 存储料件和成品表体
            List<CstDecContainerEntity> oldDecContainerList = containerDao.selectList(new QueryWrapper<CstDecContainerEntity>().eq("fk_dec_header_id", headerEntity.getId()));
            List<CstDecContainerEntity> newDecContainerList = entity.getDecContainerList();
            if (oldDecContainerList != null) {
                for (CstDecContainerEntity oldDecContainer : oldDecContainerList) {
                    containerDao.deleteById(oldDecContainer.getId());
                }
            }
            if (newDecGoodsList != null) {
                for (CstDecContainerEntity newDecContainer : newDecContainerList) {
                    newDecContainer.setFkDecHeaderId(headerEntity.getId());
                    containerDao.insert(newDecContainer);
                }
            }
            return headerEntity.getId();
        } else {
            throw new RuntimeException("表头不能为空");
        }
    }

    @Override
    public String generateDecXml(HttpServletResponse response, CstDecEntity entity) {
        CstDecEntity cstDecEntity;
        String name;
        if (1 == entity.getType()) {
            cstDecEntity = detailByImportId(entity.getImportId());
            name = "dec_import_";
        } else {
            cstDecEntity = detailByExportId(entity.getExportId());
            name = "dec_export_";
        }
        // : 2020/2/27 根据以上信息创建文件名称，手册-公司-录入单位编码-时间戳(manual-entity.getId()-cstNptsEmlEntity.getEmlHeader().getInputEtpsTypecd()-new Date().xml)
        StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder.append(name);
        fileNameBuilder.append(entity.getId());
        /*fileNameBuilder.append("_");
        fileNameBuilder.append(cstNptsEmlEntity.getEmlHeader().getInputEtpsTypecd());*/
        fileNameBuilder.append("_");
        fileNameBuilder.append(new Date().getTime());
        fileNameBuilder.append(".xml");
        String xmlName = fileNameBuilder.toString();
        // NptsEmlHead 表头
        DecHead decHead = DecHead.builder().build();
        BeanUtils.copyProperties(cstDecEntity.getDecHeaderEntity(), decHead);
        // 时间转换
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        if (decHead.getCmplDschrgDt() != null) {
            decHead.setCmplDschrgDtString(dtf.format(LocalDateTime.ofInstant(decHead.getCmplDschrgDt().toInstant(), ZoneId.systemDefault())));
            decHead.setCmplDschrgDt(null);
        }
        if (decHead.getDespDate() != null) {
            decHead.setDespDateString(dtf.format(LocalDateTime.ofInstant(decHead.getDespDate().toInstant(), ZoneId.systemDefault())));
            decHead.setDespDate(null);
        }
        if (decHead.getIEDate() != null) {
            decHead.setIEDateString(dtf.format(LocalDateTime.ofInstant(decHead.getIEDate().toInstant(), ZoneId.systemDefault())));
            decHead.setIEDate(null);
        }
        if (decHead.getPDate() != null) {
            decHead.setPDateString(dtf.format(LocalDateTime.ofInstant(decHead.getPDate().toInstant(), ZoneId.systemDefault())));
            decHead.setPDate(null);
        }
        // DecList
        List<DecList> decListList = new ArrayList<>();
        for (CstDecListEntity cstDecListEntity : cstDecEntity.getDecGoodsList()) {
            DecList decList = DecList.builder().build();
            BeanUtils.copyProperties(cstDecListEntity, decList);
            decListList.add(decList);
        }
        // Container
        List<Container> containerList = new ArrayList<>();
        for (CstDecContainerEntity cstDecContainerEntity : cstDecEntity.getDecContainerList()) {
            Container container = Container.builder().build();
            BeanUtils.copyProperties(cstDecContainerEntity, container);
            containerList.add(container);
        }
        // XML
        String xml = XmlUtil.convertToXml(DecMessage.builder()
                .DecHead(decHead)
                .DecList(decListList)
                .Container(containerList)
                .build());
        // : 2020/2/27 生成XML文件,返回XML路径
        String xmlUrl = FileUtil.createFile(xmlName, xml);
        // 下载XML文件
        // FileUtil.downloadFile(response, fileName);
        // : 2020/2/28 压缩成zip,上传到阿里云,删除刚创建的XML和ZIP,返回URL给前端
        File zipFile = null;
        FileOutputStream zipOS = null;
        FileInputStream zipIS = null;
        try {
            String zipUrl = xmlUrl.replace(".xml", ".zip");
            String zipName = xmlName.replace(".xml", ".zip");
            zipFile = new File(zipUrl);
            zipOS = new FileOutputStream(zipFile);
            ZipUtil.toZip(xmlUrl, zipOS, true);
            zipIS = new FileInputStream(zipFile);
            String url = OSSFactory.build().uploadSuffix(zipIS, zipName);
            return url;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (zipIS != null) {
                    zipIS.close();
                }
                if (zipOS != null) {
                    zipOS.close();
                }
                if (zipFile != null) {
                    zipFile.delete();
                }
                new File(xmlUrl).delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}

