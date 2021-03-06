package io.renren.modules.bind.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.modules.bind.dao.BindCbsContractCtbProcessingTradeDao;
import io.renren.modules.bind.dao.BindCbsCtbGoodsPartNoDao;
import io.renren.modules.bind.dao.BindCbsCustomsBrokerCtbCompanyDao;
import io.renren.modules.bind.entity.BindCbsContractCtbProcessingTradeEntity;
import io.renren.modules.bind.entity.BindCbsCtbGoodsPartNoEntity;
import io.renren.modules.bind.entity.BindCbsCustomsBrokerCtbCompanyEntity;
import io.renren.modules.bind.service.BindCbsContractCtbProcessingTradeService;
import io.renren.modules.cbs.dao.CbsContractDao;
import io.renren.modules.cbs.dao.CbsContractStatusStreamDao;
import io.renren.modules.cbs.dao.CbsDirectoryGoodsDao;
import io.renren.modules.cbs.dao.CbsGoodsPartNoDao;
import io.renren.modules.cbs.entity.*;
import io.renren.modules.ctb.dao.*;
import io.renren.modules.ctb.entity.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Service("bindCbsContractCtbProcessingTradeService")
public class BindCbsContractCtbProcessingTradeServiceImpl extends ServiceImpl<BindCbsContractCtbProcessingTradeDao, BindCbsContractCtbProcessingTradeEntity> implements BindCbsContractCtbProcessingTradeService {

    private BindCbsContractCtbProcessingTradeDao bindCbsContractCtbProcessingTradeDao;
    private BindCbsCustomsBrokerCtbCompanyDao bindCbsCustomsBrokerCtbCompanyDao;
    private BindCbsCtbGoodsPartNoDao bindCbsCtbGoodsPartNoDao;
    private CbsContractDao cbsContractDao;
    private CbsContractStatusStreamDao cbsContractStatusStreamDao;
    private CtbOrderProcessingTradeDao ctbOrderProcessingTradeDao;
    private CtbOrderProcessingTradeStatusStreamDao ctbOrderProcessingTradeStatusStreamDao;
    private CtbOrderProcessingTradeGoodsDao ctbOrderProcessingTradeGoodsDao;
    private CbsGoodsPartNoDao cbsGoodsPartNoDao;
    private CtbGoodsPartNoDao ctbGoodsPartNoDao;
    private CbsDirectoryGoodsDao cbsDirectoryGoodsDao;
    private CtbDirectoryGoodsDao ctbDirectoryGoodsDao;
    private CtbImgOrderProcessingTradeDao ctbImgOrderProcessingTradeDao;
    private CtbDocumentControlDao ctbDocumentControlDao;
    private CtbOrderProcessingTradeMemberDao ctbOrderProcessingTradeMemberDao;
    private CtbPartnerDao ctbPartnerDao;
    private CtbPartnerTypeDao ctbPartnerTypeDao;

    public BindCbsContractCtbProcessingTradeServiceImpl(
            BindCbsContractCtbProcessingTradeDao bindCbsContractCtbProcessingTradeDao,
            BindCbsCustomsBrokerCtbCompanyDao bindCbsCustomsBrokerCtbCompanyDao,
            BindCbsCtbGoodsPartNoDao bindCbsCtbGoodsPartNoDao,
            CbsContractDao cbsContractDao,
            CbsContractStatusStreamDao cbsContractStatusStreamDao,
            CtbOrderProcessingTradeDao ctbOrderProcessingTradeDao,
            CtbOrderProcessingTradeStatusStreamDao ctbOrderProcessingTradeStatusStreamDao,
            CtbOrderProcessingTradeGoodsDao ctbOrderProcessingTradeGoodsDao,
            CbsGoodsPartNoDao cbsGoodsPartNoDao,
            CtbGoodsPartNoDao ctbGoodsPartNoDao,
            CbsDirectoryGoodsDao cbsDirectoryGoodsDao,
            CtbDirectoryGoodsDao ctbDirectoryGoodsDao,
            CtbImgOrderProcessingTradeDao ctbImgOrderProcessingTradeDao,
            CtbDocumentControlDao ctbDocumentControlDao,
            CtbOrderProcessingTradeMemberDao ctbOrderProcessingTradeMemberDao,
            CtbPartnerDao ctbPartnerDao,
            CtbPartnerTypeDao ctbPartnerTypeDao
    ) {
        this.bindCbsContractCtbProcessingTradeDao = bindCbsContractCtbProcessingTradeDao;
        this.bindCbsCustomsBrokerCtbCompanyDao = bindCbsCustomsBrokerCtbCompanyDao;
        this.bindCbsCtbGoodsPartNoDao = bindCbsCtbGoodsPartNoDao;
        this.cbsContractDao = cbsContractDao;
        this.cbsContractStatusStreamDao = cbsContractStatusStreamDao;
        this.ctbOrderProcessingTradeDao = ctbOrderProcessingTradeDao;
        this.ctbOrderProcessingTradeStatusStreamDao = ctbOrderProcessingTradeStatusStreamDao;
        this.ctbOrderProcessingTradeGoodsDao = ctbOrderProcessingTradeGoodsDao;
        this.cbsGoodsPartNoDao = cbsGoodsPartNoDao;
        this.ctbGoodsPartNoDao = ctbGoodsPartNoDao;
        this.cbsDirectoryGoodsDao = cbsDirectoryGoodsDao;
        this.ctbDirectoryGoodsDao = ctbDirectoryGoodsDao;
        this.ctbImgOrderProcessingTradeDao = ctbImgOrderProcessingTradeDao;
        this.ctbDocumentControlDao = ctbDocumentControlDao;
        this.ctbOrderProcessingTradeMemberDao = ctbOrderProcessingTradeMemberDao;
        this.ctbPartnerDao = ctbPartnerDao;
        this.ctbPartnerTypeDao = ctbPartnerTypeDao;
    }

    @Override
    public PageUtils queryIndex(BindCbsContractCtbProcessingTradeEntity entity) {
        return null;
    }

    @Override
    @Transactional
    public HashMap<String, Object> cbsSynToCtb(Long cbsContractId, Long cbsCompanyId, Long ctbCompanyId, Long operator) {
        CbsContractEntity cbsContractEntity = cbsContractDao.detail(cbsContractId);
        HashMap<String, Object> resultMap = checkCbsContractGoods(cbsContractEntity);
        if (resultMap.containsKey("code")) {
            return resultMap;
        }

        BindCbsCustomsBrokerCtbCompanyEntity bindCbsCustomsBrokerCtbCompanyEntity = bindCbsCustomsBrokerCtbCompanyDao.detailByBothId(cbsCompanyId, ctbCompanyId);
        CtbOrderProcessingTradeEntity ctbOrderProcessingTradeEntity = null;
        BindCbsContractCtbProcessingTradeEntity bindCbsContractCtbProcessingTradeEntity = null;
        // ?????????????????????????????????Ctb?????????Bind??????
        if (cbsContractEntity.getBindEntity() == null) {
            // ??????Ctb??????
            ctbOrderProcessingTradeEntity = CtbOrderProcessingTradeEntity.builder().
                    title(cbsContractEntity.getTitle()).
                    type(cbsContractEntity.getType()).
                    currencyCode(cbsContractEntity.getCurrencyCode()).
                    fkServiceCompanyId(bindCbsCustomsBrokerCtbCompanyEntity.getFkCtbServiceCompanyId()).
                    ctbTenantId(bindCbsCustomsBrokerCtbCompanyEntity.getFkCtbCompanyId()).
                    imgOrderProcessingTradeEntityList(new ArrayList<>()).
                    orderProcessingTradeGoodsList(new ArrayList<>()).
                    memberEntityList(new ArrayList<>()).
                    status(1).
                    operator(operator).
                    createTime(new Date()).
                    build();
            ctbOrderProcessingTradeDao.insertWithoutTenant(ctbOrderProcessingTradeEntity);
            // ??????statusStream
            ctbOrderProcessingTradeStatusStreamDao.insert(CtbOrderProcessingTradeStatusStreamEntity.builder()
                    .fkOrderProcessingTradeId(ctbOrderProcessingTradeEntity.getId())
                    .remark("??????????????????")
                    .lastStatus(0)
                    .status(1)
                    .createTime(new Date())
                    .build());
            // ??????Bind??????
            bindCbsContractCtbProcessingTradeEntity = BindCbsContractCtbProcessingTradeEntity.builder().
                    fkCbsCompanyId(bindCbsCustomsBrokerCtbCompanyEntity.getFkCbsCompanyId()).
                    fkCtbCompanyId(bindCbsCustomsBrokerCtbCompanyEntity.getFkCtbCompanyId()).
                    fkCbsContractId(cbsContractEntity.getId()).
                    fkCtbProcessingTradeId(ctbOrderProcessingTradeEntity.getId()).
                    isWorkByCtb(true).
                    build();
            bindCbsContractCtbProcessingTradeDao.insert(bindCbsContractCtbProcessingTradeEntity);
            // ?????????CbsContract???
            cbsContractEntity = cbsContractDao.detail(cbsContractId);
        } else {
            ctbOrderProcessingTradeEntity = cbsContractEntity.getBindEntity().getCtbOrderProcessingTradeEntity();
            // ??????statusStream
            ctbOrderProcessingTradeStatusStreamDao.insert(CtbOrderProcessingTradeStatusStreamEntity.builder()
                    .fkOrderProcessingTradeId(ctbOrderProcessingTradeEntity.getId())
                    .remark("??????????????????")
                    .lastStatus(ctbOrderProcessingTradeEntity.getStatus())
                    .status(1)
                    .createTime(new Date())
                    .build());
            // ???????????????????????????????????????
            ctbOrderProcessingTradeEntity.setTitle(cbsContractEntity.getTitle());
            ctbOrderProcessingTradeEntity.setFkServiceCompanyId(bindCbsCustomsBrokerCtbCompanyEntity.getFkCtbServiceCompanyId());
            ctbOrderProcessingTradeEntity.setType(cbsContractEntity.getType());
            ctbOrderProcessingTradeEntity.setCurrencyCode(cbsContractEntity.getCurrencyCode());
            ctbOrderProcessingTradeEntity.setStatus(1);
            ctbOrderProcessingTradeDao.updateByIdWithoutTenant(ctbOrderProcessingTradeEntity);
            // ??????Bind
            bindCbsContractCtbProcessingTradeEntity = cbsContractEntity.getBindEntity();
            bindCbsContractCtbProcessingTradeEntity.setIsWorkByCtb(true);
            bindCbsContractCtbProcessingTradeDao.updateById(bindCbsContractCtbProcessingTradeEntity);
        }

        // ????????????Ctb?????????????????????????????????
        for (CtbOrderProcessingTradeMemberEntity ctbOrderProcessingTradeMemberEntity : ctbOrderProcessingTradeEntity.getMemberEntityList()) {
            // ??????member
            ctbOrderProcessingTradeMemberDao.deleteById(ctbOrderProcessingTradeMemberEntity.getId());
        }
        // ??????Ctb?????????????????????????????????
        for (CbsContractMemberEntity cbsContractMemberEntity : cbsContractEntity.getMemberEntityList()) {
            // ??????
            CbsPartnerEntity cbsPartnerEntity = cbsContractMemberEntity.getPartnerEntity();
            CtbPartnerEntity ctbPartnerEntity = ctbPartnerDao.findSameToCbs(cbsPartnerEntity, bindCbsCustomsBrokerCtbCompanyEntity.getFkCtbCompanyId());
            if (ctbPartnerEntity == null) {
                ctbPartnerEntity = new CtbPartnerEntity(cbsPartnerEntity);
                ctbPartnerEntity.setCtbTenantId(bindCbsCustomsBrokerCtbCompanyEntity.getFkCtbCompanyId());
                ctbPartnerDao.insertWithoutTenant(ctbPartnerEntity);
                for (CbsPartnerTypeEntity cbsPartnerTypeEntity : cbsPartnerEntity.getTypeList()) {
                    CtbPartnerTypeEntity ctbPartnerTypeEntity = new CtbPartnerTypeEntity(cbsPartnerTypeEntity, ctbPartnerEntity.getId());
                    ctbPartnerTypeDao.insert(ctbPartnerTypeEntity);
                }
            }

            // ?????????
            CtbOrderProcessingTradeMemberEntity ctbOrderProcessingTradeMemberEntity = CtbOrderProcessingTradeMemberEntity.builder().
                    fkOrderProcessingTradeId(ctbOrderProcessingTradeEntity.getId()).
                    fkPartnerId(ctbPartnerEntity.getId()).
                    type(cbsContractMemberEntity.getType()).
                    ctbTenantId(bindCbsCustomsBrokerCtbCompanyEntity.getFkCtbCompanyId()).
                    build();
            ctbOrderProcessingTradeMemberDao.insertWithoutTenant(ctbOrderProcessingTradeMemberEntity);
        }

        // ??????????????????
        List<CbsContractGoodsEntity> cbsContractGoodsEntityList = new ArrayList<>();
        for (CbsContractEntity cbsContractChildEntity : cbsContractEntity.getChildren()) {
            for (CbsContractGoodsEntity cbsContractGoodsEntity : cbsContractChildEntity.getContractGoodsList()) {
                if (cbsContractChildEntity.getType() == 11) {
                    cbsContractGoodsEntity.setType(1);
                } else if (cbsContractChildEntity.getType() == 12) {
                    cbsContractGoodsEntity.setType(2);
                } else {
                    cbsContractGoodsEntity.setType(3);
                }
                cbsContractGoodsEntityList.add(cbsContractGoodsEntity);
            }
        }
        // 1???????????????????????????
        for (CtbOrderProcessingTradeGoodsEntity ctbOrderProcessingTradeGoodsEntity : ctbOrderProcessingTradeEntity.getOrderProcessingTradeGoodsList()) {
            // ??????????????????
            ctbOrderProcessingTradeGoodsDao.deleteById(ctbOrderProcessingTradeGoodsEntity.getId());
            // ????????????
            ctbGoodsPartNoDao.deleteById(ctbOrderProcessingTradeGoodsEntity.getPartNoEntity().getId());
            if (ctbOrderProcessingTradeGoodsEntity.getPartNoEntity().getBindEntity() != null) {
                // ??????????????????
                bindCbsCtbGoodsPartNoDao.deleteById(ctbOrderProcessingTradeGoodsEntity.getPartNoEntity().getBindEntity().getId());
            }
        }
        for (CbsContractGoodsEntity cbsGoodsEntity : cbsContractGoodsEntityList) {
            CbsGoodsPartNoEntity cbsGoodsPartNoEntity = cbsGoodsEntity.getPartNoEntity();
            // 2????????????????????????
            // ??????????????????
            CtbDirectoryGoodsEntity ctbDirectoryGoodsEntity = CtbDirectoryGoodsEntity.builder().build();
            BeanUtils.copyProperties(cbsGoodsPartNoEntity.getGoodsEntity(), ctbDirectoryGoodsEntity);
            ctbDirectoryGoodsEntity.setCtbTenantId(bindCbsCustomsBrokerCtbCompanyEntity.getFkCtbCompanyId());
            ctbDirectoryGoodsDao.insertWithoutTenant(ctbDirectoryGoodsEntity);
            // ????????????????????????
            CtbOrderProcessingTradeGoodsEntity ctbOrderProcessingTradeGoodsEntity = CtbOrderProcessingTradeGoodsEntity.builder().
                    fkOrderProcessingTradeId(ctbOrderProcessingTradeEntity.getId()).
                    type(cbsGoodsEntity.getType()).
                    build();
            ctbOrderProcessingTradeGoodsDao.insertWithoutTenant(ctbOrderProcessingTradeGoodsEntity);
            // ???????????????
            CtbGoodsPartNoEntity ctbGoodsPartNoEntity = CtbGoodsPartNoEntity.builder().build();
            BeanUtils.copyProperties(cbsGoodsPartNoEntity, ctbGoodsPartNoEntity);
            ctbGoodsPartNoEntity.setFkGoodsId(ctbDirectoryGoodsEntity.getId());
            ctbGoodsPartNoEntity.setGoodsPartNo(ctbOrderProcessingTradeGoodsEntity.getId());
            ctbGoodsPartNoDao.insertWithoutTenant(ctbGoodsPartNoEntity);
            // ??????????????????
            BindCbsCtbGoodsPartNoEntity bindCbsCtbGoodsPartNoEntity = BindCbsCtbGoodsPartNoEntity.builder().
                    fkCbsGoodsPartNo(cbsGoodsPartNoEntity.getGoodsPartNo()).
                    fkCtbGoodsPartNo(ctbGoodsPartNoEntity.getGoodsPartNo()).
                    build();
            bindCbsCtbGoodsPartNoDao.insert(bindCbsCtbGoodsPartNoEntity);
        }

        // ??????????????????
        ctbImgOrderProcessingTradeDao.deleteOrderByIdWithoutTenant(ctbOrderProcessingTradeEntity.getId());
        ctbDocumentControlDao.deleteWithoutTenantId(CtbDocumentControlEntity.builder().
                businessTable("ctb_img_order_processing_trade").
                businessId(ctbOrderProcessingTradeEntity.getId()).
                build());
        // ??????????????????
        List<CbsImgContractEntity> cbsImgContractEntityList = new ArrayList<>();
        for (CbsContractEntity cbsContractChildEntity : cbsContractEntity.getChildren()) {
            for (CbsImgContractEntity cbsImgContractEntity : cbsContractChildEntity.getImgContractEntityList()) {
                if (cbsContractChildEntity.getType() == 11) {
                    cbsImgContractEntity.setType(1);
                } else if (cbsContractChildEntity.getType() == 12) {
                    cbsImgContractEntity.setType(2);
                } else {
                    cbsImgContractEntity.setType(3);
                }
                cbsImgContractEntityList.add(cbsImgContractEntity);
            }
        }
        for (CbsImgContractEntity cbsImgContractEntity : cbsImgContractEntityList) {
            CtbImgOrderProcessingTradeEntity ctbImgOrderProcessingTradeEntity = CtbImgOrderProcessingTradeEntity.builder().
                    imgUrl(cbsImgContractEntity.getImgUrl()).
                    type(cbsImgContractEntity.getType()).
                    build();
            ctbImgOrderProcessingTradeEntity.setFkOrderProcessingTradeId(ctbOrderProcessingTradeEntity.getId());
            ctbImgOrderProcessingTradeDao.insert(ctbImgOrderProcessingTradeEntity);
            ctbDocumentControlDao.insert(CtbDocumentControlEntity.builder().
                    businessTable("ctb_img_order_processing_trade").
                    businessId(ctbOrderProcessingTradeEntity.getId()).
                    businessFile(ctbImgOrderProcessingTradeEntity.getImgUrl()).
                    build());
        }
        resultMap = new HashMap<>();
        resultMap.put("code", 200);
        resultMap.put("bindId", bindCbsContractCtbProcessingTradeEntity.getId());
        return resultMap;
    }

    @Override
    @Transactional
    public Long ctbSynToCbs(Long ctbOrderProcessingTradeId) {
        CtbOrderProcessingTradeEntity ctbOrderProcessingTradeEntity = ctbOrderProcessingTradeDao.detail(ctbOrderProcessingTradeId);
        BindCbsContractCtbProcessingTradeEntity bindCbsContractCtbProcessingTradeEntity = ctbOrderProcessingTradeEntity.getBindEntity();
        // ????????????????????????????????????????????????????????????
        CbsContractEntity cbsContractEntity = bindCbsContractCtbProcessingTradeEntity.getCbsContractEntity();
        cbsContractStatusStreamDao.insert(CbsContractStatusStreamEntity.builder()
                .fkContractId(cbsContractEntity.getId())
                .lastStatus(cbsContractEntity.getStatus())
                .status(2)
                .createTime(new Date())
                .remark("?????????????????????")
                .build());
        cbsContractEntity.setEmlNo(ctbOrderProcessingTradeEntity.getEmlNo());
        cbsContractEntity.setSeqNo(ctbOrderProcessingTradeEntity.getSeqNo());
        cbsContractEntity.setStatus(2);
        cbsContractDao.updateByIdWithoutTenant(cbsContractEntity);
        // ????????????????????????
        // 1???Ctb??????????????????????????????????????????Cbs???????????????
        // 2???Ctb?????????Cbs???????????????Cbs?????????????????????????????????
        // 3???Ctb?????????Cbs????????????Cbs???????????????
        for (CtbOrderProcessingTradeGoodsEntity ctbGoodsEntity : ctbOrderProcessingTradeEntity.getOrderProcessingTradeGoodsList()) {
            CtbGoodsPartNoEntity ctbGoodsPartNoEntity = ctbGoodsEntity.getPartNoEntity();
            CbsGoodsPartNoEntity cbsGoodsPartNoEntity = ctbGoodsPartNoEntity.getBindEntity().getCbsGoodsPartNoEntity();
            if (!ctbGoodsPartNoEntity.getGoodsEntity().getHsCode().equals(cbsGoodsPartNoEntity.getGoodsEntity().getHsCode())) {
                // ??????????????????????????????????????????CbsDirectoryGoods
                CbsDirectoryGoodsEntity newCbsDirectoryGoodsEntity = CbsDirectoryGoodsEntity.builder().build();
                BeanUtils.copyProperties(ctbGoodsPartNoEntity.getGoodsEntity(), newCbsDirectoryGoodsEntity);
                newCbsDirectoryGoodsEntity.setTenantId(bindCbsContractCtbProcessingTradeEntity.getFkCbsCompanyId());
                cbsDirectoryGoodsDao.insertWithoutTenant(newCbsDirectoryGoodsEntity);
                // ?????????????????????????????????
                cbsGoodsPartNoEntity.setFkGoodsId(newCbsDirectoryGoodsEntity.getId());
                cbsGoodsPartNoDao.updateById(cbsGoodsPartNoEntity);
            }
        }
        bindCbsContractCtbProcessingTradeEntity.setIsWorkByCtb(false);
        bindCbsContractCtbProcessingTradeDao.updateById(bindCbsContractCtbProcessingTradeEntity);
        // ????????????????????????????????????????????????Bind??????
        return bindCbsContractCtbProcessingTradeEntity.getId();
    }

    @Override
    public BindCbsContractCtbProcessingTradeEntity detail(Long id) {
        return null;
    }

    private HashMap<String, Object> checkCbsContractGoods(CbsContractEntity cbsContractEntity) {
        HashMap<String, Object> resultMap = new HashMap<>();
        for (CbsContractEntity cbsChildContractEntity : cbsContractEntity.getChildren()) {
            if (cbsChildContractEntity.getType() == 11 || cbsChildContractEntity.getType() == 12) {
                if ((cbsChildContractEntity.getContractGoodsList() == null || cbsChildContractEntity.getContractGoodsList().isEmpty())) {
                    resultMap.put("code", 500);
                    resultMap.put("msg", "?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
                } else {
                    for (CbsContractGoodsEntity cbsContractGoodsEntity : cbsChildContractEntity.getContractGoodsList()) {
                        if (!checkCbsContractGoods(cbsContractGoodsEntity)) {
                            resultMap.put("code", 500);
                            resultMap.put("msg", "?????????????????????????????????????????????????????????????????????????????????0????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
                        }
                    }
                }
            }
        }
        return resultMap;
    }

    private boolean checkCbsContractGoods(CbsContractGoodsEntity cbsContractGoodsEntity) {
        return cbsContractGoodsEntity.getPartNoEntity().getContractCount().compareTo(BigDecimal.ZERO) > 0;
    }
}