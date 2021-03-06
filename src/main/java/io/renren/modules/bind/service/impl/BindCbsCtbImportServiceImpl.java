package io.renren.modules.bind.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.CommonFunction;
import io.renren.common.utils.PageUtils;
import io.renren.modules.bind.dao.BindCbsCtbGoodsPartNoDao;
import io.renren.modules.bind.dao.BindCbsCtbImportDao;
import io.renren.modules.bind.dao.BindCbsCustomsBrokerCtbCompanyDao;
import io.renren.modules.bind.entity.BindCbsCtbGoodsPartNoEntity;
import io.renren.modules.bind.entity.BindCbsCtbImportEntity;
import io.renren.modules.bind.entity.BindCbsCustomsBrokerCtbCompanyEntity;
import io.renren.modules.bind.service.BindCbsCtbImportService;
import io.renren.modules.cbs.dao.CbsDirectoryShipCompanyDao;
import io.renren.modules.cbs.dao.CbsImportDao;
import io.renren.modules.cbs.dao.CbsImportStatusStreamDao;
import io.renren.modules.cbs.dao.CbsImportVoyageDao;
import io.renren.modules.cbs.entity.*;
import io.renren.modules.cbs.vo.SynImportAndExportImgVo;
import io.renren.modules.ctb.dao.*;
import io.renren.modules.ctb.entity.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("bindCbsCtbImportService")
public class BindCbsCtbImportServiceImpl extends ServiceImpl<BindCbsCtbImportDao, BindCbsCtbImportEntity> implements BindCbsCtbImportService {

    private BindCbsCtbImportDao bindCbsCtbImportDao;
    private BindCbsCustomsBrokerCtbCompanyDao bindCbsCustomsBrokerCtbCompanyDao;
    private BindCbsCtbGoodsPartNoDao bindCbsCtbGoodsPartNoDao;
    private CbsImportDao cbsImportDao;
    private CbsImportStatusStreamDao cbsImportStatusStreamDao;
    private CbsImportVoyageDao cbsImportVoyageDao;
    private CbsDirectoryShipCompanyDao cbsDirectoryShipCompanyDao;
    private CtbImportDao ctbImportDao;
    private CtbImportStatusStreamDao ctbImportStatusStreamDao;
    private CtbDirectoryGoodsDao ctbDirectoryGoodsDao;
    private CtbOrderProcessingTradeGoodsDao ctbOrderProcessingTradeGoodsDao;
    private CtbGoodsPartNoDao ctbGoodsPartNoDao;
    private CtbImportGoodsDao ctbImportGoodsDao;
    private CtbImportMemberDao ctbImportMemberDao;
    private CtbPartnerDao ctbPartnerDao;
    private CtbPartnerTypeDao ctbPartnerTypeDao;
    private CtbImportVoyageDao ctbImportVoyageDao;
    private CtbDirectoryShipCompanyDao ctbDirectoryShipCompanyDao;
    private CommonFunction commonFunction;

    public BindCbsCtbImportServiceImpl(
            BindCbsCtbImportDao bindCbsCtbImportDao,
            BindCbsCustomsBrokerCtbCompanyDao bindCbsCustomsBrokerCtbCompanyDao,
            CbsImportDao cbsImportDao,
            CbsImportStatusStreamDao cbsImportStatusStreamDao,
            CbsImportVoyageDao cbsImportVoyageDao,
            CtbImportDao ctbImportDao,
            CtbDirectoryGoodsDao ctbDirectoryGoodsDao,
            CtbOrderProcessingTradeGoodsDao ctbOrderProcessingTradeGoodsDao,
            BindCbsCtbGoodsPartNoDao bindCbsCtbGoodsPartNoDao,
            CbsDirectoryShipCompanyDao cbsDirectoryShipCompanyDao,
            CtbImportStatusStreamDao ctbImportStatusStreamDao,
            CtbGoodsPartNoDao ctbGoodsPartNoDao,
            CtbImportGoodsDao ctbImportGoodsDao,
            CtbImportMemberDao ctbImportMemberDao,
            CtbPartnerDao ctbPartnerDao,
            CtbPartnerTypeDao ctbPartnerTypeDao,
            CtbImportVoyageDao ctbImportVoyageDao,
            CtbDirectoryShipCompanyDao ctbDirectoryShipCompanyDao,
            CommonFunction commonFunction
    ) {
        this.bindCbsCtbImportDao = bindCbsCtbImportDao;
        this.bindCbsCustomsBrokerCtbCompanyDao = bindCbsCustomsBrokerCtbCompanyDao;
        this.cbsImportDao = cbsImportDao;
        this.cbsImportStatusStreamDao = cbsImportStatusStreamDao;
        this.cbsImportVoyageDao = cbsImportVoyageDao;
        this.ctbImportDao = ctbImportDao;
        this.ctbDirectoryGoodsDao = ctbDirectoryGoodsDao;
        this.ctbOrderProcessingTradeGoodsDao = ctbOrderProcessingTradeGoodsDao;
        this.bindCbsCtbGoodsPartNoDao = bindCbsCtbGoodsPartNoDao;
        this.cbsDirectoryShipCompanyDao = cbsDirectoryShipCompanyDao;
        this.ctbImportStatusStreamDao = ctbImportStatusStreamDao;
        this.ctbGoodsPartNoDao = ctbGoodsPartNoDao;
        this.ctbImportGoodsDao = ctbImportGoodsDao;
        this.ctbImportMemberDao = ctbImportMemberDao;
        this.ctbPartnerDao = ctbPartnerDao;
        this.ctbPartnerTypeDao = ctbPartnerTypeDao;
        this.ctbImportVoyageDao = ctbImportVoyageDao;
        this.ctbDirectoryShipCompanyDao = ctbDirectoryShipCompanyDao;
        this.commonFunction = commonFunction;
    }

    @Override
    public PageUtils queryIndex(BindCbsCtbImportEntity entity) {
        return null;
    }

    @Override
    @Transactional
    public HashMap<String, Object> cbsSynToCtb(Long cbsImportId, Long cbsCompanyId, Long ctbCompanyId) {
        CbsImportEntity cbsImportEntity = cbsImportDao.detail(cbsImportId);
        HashMap<String, Object> resultMap = checkCbsImportGoods(cbsImportEntity);
        if (resultMap.containsKey("code")) {
            return resultMap;
        }

        BindCbsCustomsBrokerCtbCompanyEntity bindCbsCustomsBrokerCtbCompanyEntity = bindCbsCustomsBrokerCtbCompanyDao.detailByBothId(cbsCompanyId, ctbCompanyId);
        CtbImportEntity ctbImportEntity = CtbImportEntity.builder().build();
        BindCbsCtbImportEntity bindCbsCtbImportEntity;
        // ?????????????????????????????????Ctb?????????Bind??????
        if (cbsImportEntity.getBindEntity() == null) {
            // ??????Ctb??????
            BeanUtils.copyProperties(cbsImportEntity, ctbImportEntity);
            ctbImportEntity.setFkServiceCompanyId(bindCbsCustomsBrokerCtbCompanyEntity.getFkCtbServiceCompanyId());
            ctbImportEntity.setCtbTenantId(bindCbsCustomsBrokerCtbCompanyEntity.getFkCtbCompanyId());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            ctbImportEntity.setTitle(cbsImportEntity.getContractEntity().getTitle() + " ???????????????" + dateFormat.format(cbsImportEntity.getCreateTime()));
            ctbImportEntity.setCurrencyCode(cbsImportEntity.getContractEntity().getCurrencyCode());
            ctbImportDao.insertWithoutTenant(ctbImportEntity);
            // ?????????????????????????????????????????????
            Long ctbImportId = ctbImportEntity.getId();
            BeanUtils.copyProperties(cbsImportEntity, ctbImportEntity);
            ctbImportEntity.setId(ctbImportId);
            ctbImportDao.updateByIdWithoutTenant(ctbImportEntity);
            // ??????statusStream
            ctbImportStatusStreamDao.insert(CtbImportStatusStreamEntity.builder()
                    .fkImportId(ctbImportEntity.getId())
                    .lastStatus(0)
                    .status(1)
                    .remark("??????????????????")
                    .build());
            // ??????Bind?????????
            bindCbsCtbImportEntity = BindCbsCtbImportEntity.builder().
                    fkCbsCompanyId(bindCbsCustomsBrokerCtbCompanyEntity.getFkCbsCompanyId()).
                    fkCtbCompanyId(bindCbsCustomsBrokerCtbCompanyEntity.getFkCtbCompanyId()).
                    fkCbsImportId(cbsImportEntity.getId()).
                    fkCtbImportId(ctbImportEntity.getId()).
                    isWorkByCtb(true).
                    build();
            bindCbsCtbImportDao.insert(bindCbsCtbImportEntity);
            // ?????????CbsImport???
            cbsImportEntity = cbsImportDao.detail(cbsImportId);
        } else {
            // ??????Bind
            bindCbsCtbImportEntity = cbsImportEntity.getBindEntity();
            bindCbsCtbImportEntity.setIsWorkByCtb(true);
            bindCbsCtbImportDao.updateById(bindCbsCtbImportEntity);
            // ??????Ctb??????
            BeanUtils.copyProperties(cbsImportEntity, ctbImportEntity);
            ctbImportEntity.setFkServiceCompanyId(bindCbsCustomsBrokerCtbCompanyEntity.getFkCtbServiceCompanyId());
            ctbImportEntity.setCurrencyCode(cbsImportEntity.getContractEntity().getCurrencyCode());
            ctbImportEntity.setId(bindCbsCtbImportEntity.getFkCtbImportId());
            ctbImportEntity.setStatus(1);
            ctbImportDao.updateByIdWithoutTenant(ctbImportEntity);
            // ??????statusStream
            ctbImportStatusStreamDao.insert(CtbImportStatusStreamEntity.builder()
                    .fkImportId(ctbImportEntity.getId())
                    .lastStatus(ctbImportEntity.getStatus())
                    .status(1)
                    .remark("??????????????????")
                    .build());
        }
        // ??????CBS?????????????????????????????????
        cbsImportEntity.setFkDirectoryCustomsBrokerId(bindCbsCustomsBrokerCtbCompanyEntity.getFkCbsCustomBrokerId());
        cbsImportDao.updateById(cbsImportEntity);
        // ????????????import??????
        ctbImportEntity = ctbImportDao.detailWithoutBindAndTenant(ctbImportEntity.getId());

        // ????????????Ctb????????????????????????
        for (CtbImportMemberEntity ctbImportMemberEntity : ctbImportEntity.getMemberEntityList()) {
            // ??????member
            ctbImportMemberDao.deleteById(ctbImportMemberEntity.getId());
        }
        // ??????Ctb?????????????????????????????????
        for (CbsContractMemberEntity cbsContractMemberEntity : cbsImportEntity.getContractEntity().getMemberEntityList()) {
            // ?????? ?????????????????????partner?????????????????????????????????
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
            CtbImportMemberEntity ctbImportMemberEntity = CtbImportMemberEntity.builder().
                    fkImportId(ctbImportEntity.getId()).
                    fkPartnerId(ctbPartnerEntity.getId()).
                    type(cbsContractMemberEntity.getType()).
                    build();
            ctbImportMemberDao.insert(ctbImportMemberEntity);
        }

        // ????????????Ctb????????????
        for (CtbImportGoodsEntity ctbImportGoodsEntity : ctbImportEntity.getImportGoodsList()) {
            ctbImportGoodsDao.deleteById(ctbImportGoodsEntity.getId());
        }
        // ??????????????????
        for (CbsImportGoodsEntity cbsImportGoodsEntity : cbsImportEntity.getImportGoodsList()) {
            CbsGoodsPartNoEntity cbsGoodsPartNoEntity = cbsImportGoodsEntity.getPartNoEntity();
            // ???????????????????????????
            // 1????????????????????????????????????????????????
            // 2???????????????????????????????????????????????????importGoods
            CtbGoodsPartNoEntity ctbGoodsPartNoEntity = CtbGoodsPartNoEntity.builder().build();
            if (cbsGoodsPartNoEntity.getBindEntity() == null) {
                CtbDirectoryGoodsEntity ctbDirectoryGoodsEntity = CtbDirectoryGoodsEntity.builder().build();
                BeanUtils.copyProperties(cbsGoodsPartNoEntity.getGoodsEntity(), ctbDirectoryGoodsEntity);
                ctbDirectoryGoodsEntity.setCtbTenantId(bindCbsCustomsBrokerCtbCompanyEntity.getFkCtbCompanyId());
                ctbDirectoryGoodsDao.insertWithoutTenant(ctbDirectoryGoodsEntity);
                // ????????????????????????
                CtbOrderProcessingTradeGoodsEntity ctbOrderProcessingTradeGoodsEntity = CtbOrderProcessingTradeGoodsEntity.builder().
                        fkOrderProcessingTradeId(-1L).
                        type(1).
                        build();
                ctbOrderProcessingTradeGoodsDao.insertWithoutTenant(ctbOrderProcessingTradeGoodsEntity);
                // ???????????????
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
            } else {
                ctbGoodsPartNoEntity = cbsGoodsPartNoEntity.getBindEntity().getCtbGoodsPartNoEntity();
            }
            // ?????????importGoods
            CtbImportGoodsEntity ctbImportGoodsEntity = CtbImportGoodsEntity.builder().build();
            BeanUtils.copyProperties(cbsImportGoodsEntity, ctbImportGoodsEntity);
            ctbImportGoodsEntity.setFkImportId(ctbImportEntity.getId());
            ctbImportGoodsEntity.setGoodsPartNo(ctbGoodsPartNoEntity.getGoodsPartNo());
            ctbImportGoodsDao.insert(ctbImportGoodsEntity);
        }

        // ?????????????????????????????????
        updateCtbVoyageChange(ctbImportEntity.getId(), bindCbsCtbImportEntity.getFkCtbCompanyId(), cbsImportEntity.getVoyageList());

        // ??????
        Map<String, String> map = new HashMap<>();
        map.put("cbs_img_import_contract_ship", "ctb_img_import_contract_ship");
        map.put("cbs_img_import_delivery_order", "ctb_img_import_delivery_order");
        map.put("cbs_img_import_entry_bill", "ctb_img_import_entry_bill");
        map.put("cbs_img_import_invoice", "ctb_img_import_invoice");
        map.put("cbs_img_import_lading_bill", "ctb_img_import_lading_bill");
        map.put("cbs_img_import_others", "ctb_img_import_others");
        map.put("cbs_img_import_packing_list", "ctb_img_import_packing_list");
        map.put("cbs_img_import_pay_in_advance", "ctb_img_import_pay_in_advance");
        map.put("cbs_img_import_pay_tail", "ctb_img_import_pay_tail");
        map.put("cbs_img_import_power_of_attorney", "ctb_img_import_power_of_attorney");
        for (Map.Entry<String, String> vo : map.entrySet()) {
            commonFunction.synCbsCtbImportAndExportImg(SynImportAndExportImgVo.builder()
                    .originalTable(vo.getKey())
                    .targetTable(vo.getValue())
                    .originalFkId(cbsImportEntity.getId())
                    .targetFkId(ctbImportEntity.getId())
                    .fkField("fk_import_id")
                    .build());
        }

        resultMap.put("code", 200);
        resultMap.put("bindId", bindCbsCtbImportEntity.getId());
        return resultMap;
    }

    @Override
    @Transactional
    public Long ctbSynToCbs(Long ctbImportId) {
        CtbImportEntity ctbImportEntity = ctbImportDao.detail(ctbImportId);
        BindCbsCtbImportEntity bindCbsCtbImportEntity = ctbImportEntity.getBindEntity();
        CbsImportEntity cbsImportEntity = bindCbsCtbImportEntity.getCbsImportEntity();
        // ??????Cbs?????????
        cbsImportStatusStreamDao.insert(CbsImportStatusStreamEntity.builder()
                .fkImportId(bindCbsCtbImportEntity.getFkCbsImportId())
                .lastStatus(4)
                .status(11)
                .remark("?????????????????????")
                .build());
        // ??????Cbs??????
        BeanUtils.copyProperties(ctbImportEntity, cbsImportEntity, "bindEntity");
        cbsImportEntity.setId(bindCbsCtbImportEntity.getFkCbsImportId());
        cbsImportEntity.setStatus(11);
        cbsImportDao.updateByIdWithoutTenant(cbsImportEntity);
        // ??????????????????
        bindCbsCtbImportEntity.setIsWorkByCtb(false);
        bindCbsCtbImportDao.updateById(bindCbsCtbImportEntity);

        // ?????????????????????????????????
        updateCbsVoyageChange(cbsImportEntity.getId(), bindCbsCtbImportEntity.getFkCbsCompanyId(), ctbImportEntity.getVoyageList());

        // ??????
        Map<String, String> map = new HashMap<>();
        map.put("ctb_img_import_contract_ship", "cbs_img_import_contract_ship");
        map.put("ctb_img_import_delivery_order", "cbs_img_import_delivery_order");
        map.put("ctb_img_import_entry_bill", "cbs_img_import_entry_bill");
        map.put("ctb_img_import_invoice", "cbs_img_import_invoice");
        map.put("ctb_img_import_lading_bill", "cbs_img_import_lading_bill");
        map.put("ctb_img_import_others", "cbs_img_import_others");
        map.put("ctb_img_import_packing_list", "cbs_img_import_packing_list");
        map.put("ctb_img_import_pay_in_advance", "cbs_img_import_pay_in_advance");
        map.put("ctb_img_import_pay_tail", "cbs_img_import_pay_tail");
        map.put("ctb_img_import_power_of_attorney", "cbs_img_import_power_of_attorney");
        for (Map.Entry<String, String> vo : map.entrySet()) {
            commonFunction.synCbsCtbImportAndExportImg(SynImportAndExportImgVo.builder()
                    .originalTable(vo.getKey())
                    .targetTable(vo.getValue())
                    .originalFkId(ctbImportEntity.getId())
                    .targetFkId(cbsImportEntity.getId())
                    .fkField("fk_import_id")
                    .build());
        }

        return bindCbsCtbImportEntity.getId();
    }

    /**
     * ??????Cbs??????
     */
    private void updateCbsVoyageChange(Long cbsImportId, Long cbsCompanyId, List<CtbImportVoyageEntity> ctbImportVoyageEntityList) {
        // ???????????????
        cbsImportVoyageDao.delete(new QueryWrapper<CbsImportVoyageEntity>().eq("fk_import_id", cbsImportId));
        for (CtbImportVoyageEntity ctbImportVoyageEntity : ctbImportVoyageEntityList) {
            // ??????????????????????????????????????????????????????????????????????????????
            CtbDirectoryShipCompanyEntity ctbDirectoryShipCompanyEntity = ctbImportVoyageEntity.getShipCompanyEntity();
            CbsDirectoryShipCompanyEntity cbsDirectoryShipCompanyEntity = cbsDirectoryShipCompanyDao.findSameToCtb(ctbDirectoryShipCompanyEntity, cbsCompanyId);
            if (cbsDirectoryShipCompanyEntity == null && ctbDirectoryShipCompanyEntity != null) {
                cbsDirectoryShipCompanyEntity = CbsDirectoryShipCompanyEntity.builder().build();
                BeanUtils.copyProperties(ctbDirectoryShipCompanyEntity, cbsDirectoryShipCompanyEntity);
                cbsDirectoryShipCompanyEntity.setTenantId(cbsCompanyId);
                cbsDirectoryShipCompanyDao.insertWithoutTenant(cbsDirectoryShipCompanyEntity);
            }
            // ???????????????
            CbsImportVoyageEntity cbsImportVoyageEntity = CbsImportVoyageEntity.builder().build();
            BeanUtils.copyProperties(ctbImportVoyageEntity, cbsImportVoyageEntity);
            if (cbsDirectoryShipCompanyEntity != null) {
                cbsImportVoyageEntity.setFkShipCompanyId(cbsDirectoryShipCompanyEntity.getId());
            }
            cbsImportVoyageEntity.setFkImportId(cbsImportId);
            cbsImportVoyageDao.insert(cbsImportVoyageEntity);
        }
    }

    /**
     * ??????Ctb??????
     */
    private void updateCtbVoyageChange(Long ctbImportId, Long ctbCompanyId, List<CbsImportVoyageEntity> cbsImportVoyageEntityList) {
        // ???????????????
        ctbImportVoyageDao.delete(new QueryWrapper<CtbImportVoyageEntity>().eq("fk_import_id", ctbImportId));
        for (CbsImportVoyageEntity cbsImportVoyageEntity : cbsImportVoyageEntityList) {
            // ??????????????????????????????????????????????????????????????????????????????
            CbsDirectoryShipCompanyEntity cbsDirectoryShipCompanyEntity = cbsImportVoyageEntity.getShipCompanyEntity();
            CtbDirectoryShipCompanyEntity ctbDirectoryShipCompanyEntity = ctbDirectoryShipCompanyDao.findSameToCbs(cbsDirectoryShipCompanyEntity, ctbCompanyId);
            if (ctbDirectoryShipCompanyEntity == null && cbsDirectoryShipCompanyEntity != null) {
                ctbDirectoryShipCompanyEntity = CtbDirectoryShipCompanyEntity.builder().build();
                BeanUtils.copyProperties(cbsDirectoryShipCompanyEntity, ctbDirectoryShipCompanyEntity);
                ctbDirectoryShipCompanyEntity.setCtbTenantId(ctbCompanyId);
                ctbDirectoryShipCompanyDao.insertWithoutTenant(ctbDirectoryShipCompanyEntity);
            }
            // ???????????????
            CtbImportVoyageEntity ctbImportVoyageEntity = CtbImportVoyageEntity.builder().build();
            BeanUtils.copyProperties(cbsImportVoyageEntity, ctbImportVoyageEntity);
            if (ctbDirectoryShipCompanyEntity != null) {
                ctbImportVoyageEntity.setFkShipCompanyId(ctbDirectoryShipCompanyEntity.getId());
            }
            ctbImportVoyageEntity.setFkImportId(ctbImportId);
            ctbImportVoyageDao.insert(ctbImportVoyageEntity);
        }
    }

    @Override
    public BindCbsCtbImportEntity detail(Long id) {
        return null;
    }

    private HashMap<String, Object> checkCbsImportGoods(CbsImportEntity cbsImportEntity) {
        HashMap<String, Object> resultMap = new HashMap<>();
        if ((cbsImportEntity.getImportGoodsList() == null || cbsImportEntity.getImportGoodsList().isEmpty())) {
            resultMap.put("code", 500);
            resultMap.put("msg", "?????????????????????????????????????????????????????????????????????????????????????????????");
        } else {
            for (CbsImportGoodsEntity cbsImportGoodsEntity : cbsImportEntity.getImportGoodsList()) {
                if (!checkCbsImportGoods(cbsImportGoodsEntity)) {
                    resultMap.put("code", 500);
                    resultMap.put("msg", "???????????????????????????????????????????????????0?????????????????????????????????????????????????????????????????????");
                }
            }
        }
        return resultMap;
    }

    private boolean checkCbsImportGoods(CbsImportGoodsEntity cbsImportGoodsEntity) {
        return cbsImportGoodsEntity.getCount().compareTo(BigDecimal.ZERO) > 0;
    }
}