package io.renren.modules.cst.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.enumeration.ManualEnum;
import io.renren.common.jaxb.FileUtil;
import io.renren.common.jaxb.XmlUtil;
import io.renren.common.jaxb.ZipUtil;
import io.renren.common.jaxb.manual.*;
import io.renren.modules.cst.dao.CstNptsEmlConsumeDao;
import io.renren.modules.cst.dao.CstNptsEmlDao;
import io.renren.modules.cst.dao.CstNptsEmlHeaderDao;
import io.renren.modules.cst.dao.CstNptsEmlImgExgDao;
import io.renren.modules.cst.entity.CstNptsEmlConsumeEntity;
import io.renren.modules.cst.entity.CstNptsEmlEntity;
import io.renren.modules.cst.entity.CstNptsEmlHeaderEntity;
import io.renren.modules.cst.entity.CstNptsEmlImgExgEntity;
import io.renren.modules.cst.service.CstNptsEmlService;
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
 * 海关加工贸易手册备案表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-12-13 19:21:36
 */
@Service("cstNptsEmlService")
public class CstNptsEmlServiceImpl implements CstNptsEmlService {
    private CstNptsEmlDao nptsEmlDao;
    private CstNptsEmlHeaderDao headerDao;
    private CstNptsEmlImgExgDao imgExgDao;
    private CstNptsEmlConsumeDao consumeDao;

    @Autowired
    public void setNptsEmlDao(CstNptsEmlDao nptsEmlDao) {
        this.nptsEmlDao = nptsEmlDao;
    }

    @Autowired
    public void setHeaderDao(CstNptsEmlHeaderDao headerDao) {
        this.headerDao = headerDao;
    }

    @Autowired
    public void setImgExgDao(CstNptsEmlImgExgDao imgExgDao) {
        this.imgExgDao = imgExgDao;
    }

    @Autowired
    public void setConsumeDao(CstNptsEmlConsumeDao consumeDao) {
        this.consumeDao = consumeDao;
    }

    public CstNptsEmlEntity detail(Long id) {
        return nptsEmlDao.detail(id);
    }

    public CstNptsEmlEntity detailByContractId(Long contractId) {
        if (contractId != null) {
            return nptsEmlDao.detailByContractId(contractId);
        } else {
            return null;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Long save(CstNptsEmlEntity entity) throws RuntimeException {
        CstNptsEmlHeaderEntity headerEntity = entity.getEmlHeader();
        if (headerEntity != null) {
            // 存储表头信息, 没有Id就创建，有Id就更新
            if (headerEntity.getId() == null) {
                headerDao.insert(headerEntity);
            } else {
                headerDao.updateById(headerEntity);
            }
            // 存储料件和成品表体
            /*List<CstNptsEmlImgExgEntity> oldImgExgEntityList = imgExgDao.selectList(new QueryWrapper<CstNptsEmlImgExgEntity>().eq("fk_eml_header_id", headerEntity.getId()));
            List<CstNptsEmlImgExgEntity> imgExgEntityList = entity.getEmlImgList();
            if (oldImgExgEntityList != null) {
                for (CstNptsEmlImgExgEntity oldImgExgEntity : oldImgExgEntityList) {
                    imgExgDao.deleteById(oldImgExgEntity.getId());
                }
            }
            if (imgExgEntityList != null) {
                for (CstNptsEmlImgExgEntity imgExgEntity : imgExgEntityList) {
                    imgExgEntity.setFkEmlHeaderId(headerEntity.getId());
                    imgExgDao.insert(imgExgEntity);
                }
            }*/
            // 存储损耗表体
            /*List<CstNptsEmlConsumeEntity> oldConsumeEntityList = consumeDao.selectList(new QueryWrapper<CstNptsEmlConsumeEntity>().eq("fk_eml_header_id", headerEntity.getId()));
            List<CstNptsEmlConsumeEntity> consumeEntityList = entity.getEmlConsumeList();
            if (oldConsumeEntityList != null) {
                for (CstNptsEmlConsumeEntity oldConsumeEntity : oldConsumeEntityList) {
                    consumeDao.deleteById(oldConsumeEntity.getId());
                }
            }
            if (consumeEntityList != null) {
                for (CstNptsEmlConsumeEntity consumeEntity : consumeEntityList) {
                    consumeEntity.setFkEmlHeaderId(headerEntity.getId());
                    consumeDao.insert(consumeEntity);
                }
            }*/
            return headerEntity.getId();
        } else {
            throw new RuntimeException("表头不能为空");
        }
    }

    @Override
    public CstNptsEmlImgExgEntity selectGoodsByPartNo(Long partNo) {
        QueryWrapper<CstNptsEmlImgExgEntity> qw = new QueryWrapper<>();
        qw.eq("gds_mtno", partNo);
        return imgExgDao.selectOne(qw);
    }

    @Override
    public String generateNptsEmlXml(HttpServletResponse response, CstNptsEmlEntity entity) {
        CstNptsEmlEntity cstNptsEmlEntity = detailByContractId(entity.getFkContractId());
        // : 2020/2/27 根据以上信息创建文件名称，手册-公司-录入单位编码-时间戳(manual-entity.getId()-cstNptsEmlEntity.getEmlHeader().getInputEtpsTypecd()-new Date().xml)
        StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder.append("manual_");
        fileNameBuilder.append(entity.getId());
        /*fileNameBuilder.append("_");
        fileNameBuilder.append(cstNptsEmlEntity.getEmlHeader().getInputEtpsTypecd());*/
        fileNameBuilder.append("_");
        fileNameBuilder.append(new Date().getTime());
        fileNameBuilder.append(".xml");
        String xmlName = fileNameBuilder.toString();
        // NptsEmlHead 表头
        NptsEmlHead nptsEmlHead = NptsEmlHead.builder().build();
        BeanUtils.copyProperties(cstNptsEmlEntity.getEmlHeader(), nptsEmlHead);
        // 时间转换
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        if (nptsEmlHead.getDclTime() != null) {
            nptsEmlHead.setDclTimeString(dtf.format(LocalDateTime.ofInstant(nptsEmlHead.getDclTime().toInstant(), ZoneId.systemDefault())));
            nptsEmlHead.setDclTime(null);
        }
        if (nptsEmlHead.getValidDate() != null) {
            nptsEmlHead.setValidDateString(dtf.format(LocalDateTime.ofInstant(nptsEmlHead.getValidDate().toInstant(), ZoneId.systemDefault())));
            nptsEmlHead.setValidDate(null);
        }
        if (nptsEmlHead.getInputTime() != null) {
            nptsEmlHead.setInputTimeString(dtf.format(LocalDateTime.ofInstant(nptsEmlHead.getInputTime().toInstant(), ZoneId.systemDefault())));
            nptsEmlHead.setInputTime(null);
        }
        // ImportInfo 附件
        ImportInfo importInfo = ImportInfo.builder().MessageType("NPTS001").OpType("S").HostId("").FileSize("0").Sign("").build();
        // NptsEmlImg 料件
        List<NptsEmlImg> nptsEmlImgList = new ArrayList<>();
        // NptsEmlExg 成品
        List<NptsEmlExg> nptsEmlExgList = new ArrayList<>();
        for (CstNptsEmlImgExgEntity cstNptsEmlImgExgEntity : cstNptsEmlEntity.getEmlImgList()) {
            if (ManualEnum.Manual_TYPE_1.getMsg().equals(cstNptsEmlImgExgEntity.getMtpckEndprdTypecd())) {
                // 料件
                NptsEmlImg nptsEmlImg = NptsEmlImg.builder().build();
                BeanUtils.copyProperties(cstNptsEmlImgExgEntity, nptsEmlImg);
                nptsEmlImgList.add(nptsEmlImg);
            } else {
                // 成品
                NptsEmlExg nptsEmlExg = NptsEmlExg.builder().build();
                BeanUtils.copyProperties(cstNptsEmlImgExgEntity, nptsEmlExg);
                nptsEmlExgList.add(nptsEmlExg);
            }
        }
        // NptsEmlConsume 损耗
        List<NptsEmlConsume> nptsEmlConsumeList = new ArrayList<>();
        for (CstNptsEmlConsumeEntity cstNptsEmlConsumeEntity : cstNptsEmlEntity.getEmlConsumeList()) {
            NptsEmlConsume nptsEmlConsume = NptsEmlConsume.builder().build();
            BeanUtils.copyProperties(cstNptsEmlConsumeEntity, nptsEmlConsume);
            nptsEmlConsumeList.add(nptsEmlConsume);
        }
        // XML
        String xml = XmlUtil.convertToXml(EmlMessage.builder()
                .NptsSign("")
                .OperCusRegCode(nptsEmlHead.getInputEtpsTypecd())
                .NptsEmlHead(nptsEmlHead)
                .ImportInfo(importInfo)
                .NptsEmlImg(nptsEmlImgList)
                .NptsEmlExg(nptsEmlExgList)
                .NptsEmlConsume(nptsEmlConsumeList)
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

