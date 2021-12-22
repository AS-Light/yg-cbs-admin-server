package io.renren.modules.cst.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.jaxb.FileUtil;
import io.renren.common.jaxb.XmlUtil;
import io.renren.common.jaxb.ZipUtil;
import io.renren.common.jaxb.invt.ImportInfo;
import io.renren.common.jaxb.invt.InvtHeadType;
import io.renren.common.jaxb.invt.InvtListType;
import io.renren.common.jaxb.invt.InvtMessage;
import io.renren.modules.cst.dao.CstInvtDao;
import io.renren.modules.cst.dao.CstInvtHeaderDao;
import io.renren.modules.cst.dao.CstInvtListDao;
import io.renren.modules.cst.entity.CstInvtEntity;
import io.renren.modules.cst.entity.CstInvtHeaderEntity;
import io.renren.modules.cst.entity.CstInvtListEntity;
import io.renren.modules.cst.service.CstInvtService;
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
 * 海关加工贸易手册保税核注清单表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-12-13 19:21:36
 */
@Service("cstInvtService")
public class CstInvtServiceImpl implements CstInvtService {
    private CstInvtDao invtDao;
    private CstInvtHeaderDao headerDao;
    private CstInvtListDao goodsDao;

    @Autowired
    public void setInvtDao(CstInvtDao invtDao) {
        this.invtDao = invtDao;
    }

    @Autowired
    public void setHeaderDao(CstInvtHeaderDao headerDao) {
        this.headerDao = headerDao;
    }

    @Autowired
    public void setGoodsDao(CstInvtListDao goodsDao) {
        this.goodsDao = goodsDao;
    }

    @Override
    public CstInvtEntity detail(Long id) {
        return invtDao.detail(id);
    }

    @Override
    public CstInvtEntity detailByImportId(Long importId) {
        return invtDao.detailByImportId(importId);
    }

    @Override
    public CstInvtEntity detailByExportId(Long exportId) {
        return invtDao.detailByExportId(exportId);
    }

    @Override
    public List<CstInvtEntity> listByContractId(Long contractId) {
        return invtDao.queryByContractId(contractId);
    }

    @Override
    @Transactional
    public Long save(CstInvtEntity entity) throws RuntimeException {
        CstInvtHeaderEntity headerEntity = entity.getInvtHeaderEntity();
        if (headerEntity != null) {
            // 存储表头信息, 没有Id就创建，有Id就更新
            if (headerEntity.getId() == null) {
                headerDao.insert(headerEntity);
            } else {
                headerDao.updateById(headerEntity);
            }
            // 存储料件和成品表体
            List<CstInvtListEntity> oldInvtGoodsList = goodsDao.selectList(new QueryWrapper<CstInvtListEntity>().eq("fk_invt_header_id", headerEntity.getId()));
            List<CstInvtListEntity> newInvtGoodsList = entity.getInvtGoodsList();
            if (oldInvtGoodsList != null) {
                for (CstInvtListEntity oldInvtGoods : oldInvtGoodsList) {
                    goodsDao.deleteById(oldInvtGoods.getId());
                }
            }
            if (newInvtGoodsList != null) {
                for (CstInvtListEntity newInvtGoods : newInvtGoodsList) {
                    newInvtGoods.setFkInvtHeaderId(headerEntity.getId());
                    goodsDao.insert(newInvtGoods);
                }
            }
            return headerEntity.getId();
        } else {
            throw new RuntimeException("表头不能为空");
        }
    }

    @Override
    public String generateInvtXml(HttpServletResponse response, CstInvtEntity entity) {
        CstInvtEntity cstInvtEntity;
        String name;
        if (1 == entity.getType()) {
            cstInvtEntity = detailByImportId(entity.getImportId());
            name = "invt_import_";
        } else {
            cstInvtEntity = detailByExportId(entity.getExportId());
            name = "invt_export_";
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
        InvtHeadType invtHeadType = InvtHeadType.builder().build();
        BeanUtils.copyProperties(cstInvtEntity.getInvtHeaderEntity(), invtHeadType);
        // 时间转换
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        if (invtHeadType.getInputTime() != null) {
            invtHeadType.setInputTimeString(dtf.format(LocalDateTime.ofInstant(invtHeadType.getInputTime().toInstant(), ZoneId.systemDefault())));
            invtHeadType.setInputTime(null);
        }
        if (invtHeadType.getPrevdTime() != null) {
            invtHeadType.setPrevdTimeString(dtf.format(LocalDateTime.ofInstant(invtHeadType.getPrevdTime().toInstant(), ZoneId.systemDefault())));
            invtHeadType.setPrevdTime(null);
        }
        if (invtHeadType.getEntryDclTime() != null) {
            invtHeadType.setEntryDclTimeString(dtf.format(LocalDateTime.ofInstant(invtHeadType.getEntryDclTime().toInstant(), ZoneId.systemDefault())));
            invtHeadType.setEntryDclTime(null);
        }
        if (invtHeadType.getFormalVrfdedTime() != null) {
            invtHeadType.setFormalVrfdedTimeString(dtf.format(LocalDateTime.ofInstant(invtHeadType.getFormalVrfdedTime().toInstant(), ZoneId.systemDefault())));
            invtHeadType.setFormalVrfdedTime(null);
        }
        if (invtHeadType.getInvtDclTime() != null) {
            invtHeadType.setInvtDclTimeString(dtf.format(LocalDateTime.ofInstant(invtHeadType.getInvtDclTime().toInstant(), ZoneId.systemDefault())));
            invtHeadType.setInvtDclTime(null);
        }
        // ImportInfo 附件
        // ImportInfo importInfo = ImportInfo.builder().MessageType("NPTS001").OpType("S").HostId("").FileSize("0").Sign("").build();
        // NptsEmlImg 料件
        // List<NptsEmlImg> nptsEmlImgList = new ArrayList<>();
        // NptsEmlExg 成品
        // List<NptsEmlExg> nptsEmlExgList = new ArrayList<>();
//        for (CstNptsEmlImgExgEntity cstNptsEmlImgExgEntity : cstInvtEntity.getInvtGoodsList()) {
//            if (ManualEnum.Manual_TYPE_1.getMsg().equals(cstNptsEmlImgExgEntity.getMtpckEndprdTypecd())) {
//                // 料件
//                NptsEmlImg nptsEmlImg = NptsEmlImg.builder().build();
//                BeanUtils.copyProperties(cstNptsEmlImgExgEntity, nptsEmlImg);
//                nptsEmlImgList.add(nptsEmlImg);
//            } else {
//                // 成品
//                NptsEmlExg nptsEmlExg = NptsEmlExg.builder().build();
//                BeanUtils.copyProperties(cstNptsEmlImgExgEntity, nptsEmlExg);
//                nptsEmlExgList.add(nptsEmlExg);
//            }
//        }
        // InvtListType 保税核注清单
        List<InvtListType> invtListTypeList = new ArrayList<>();
        for (CstInvtListEntity cstInvtListEntity : cstInvtEntity.getInvtGoodsList()) {
            InvtListType invtListType = InvtListType.builder().build();
            BeanUtils.copyProperties(cstInvtListEntity, invtListType);
            invtListTypeList.add(invtListType);
        }
        // XML
        String xml = XmlUtil.convertToXml(InvtMessage.builder()
                .SysId("B1")
                .OperCusRegCode(invtHeadType.getInputCode())
                .InvtHeadType(invtHeadType)
                .InvtListType(invtListTypeList)
                .ImportInfo(ImportInfo.builder().MessageType("NPTS301").OpType("S").FileSize("0").build())
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

