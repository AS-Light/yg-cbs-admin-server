package io.renren.modules.bind.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.CommonFunction;
import io.renren.common.utils.PageUtils;
import io.renren.modules.bind.dao.BindCbsCtbExportDao;
import io.renren.modules.bind.dao.BindCbsCtbGoodsPartNoDao;
import io.renren.modules.bind.dao.BindCbsCustomsBrokerCtbCompanyDao;
import io.renren.modules.bind.entity.BindCbsCtbExportEntity;
import io.renren.modules.bind.entity.BindCbsCtbGoodsPartNoEntity;
import io.renren.modules.bind.entity.BindCbsCustomsBrokerCtbCompanyEntity;
import io.renren.modules.bind.service.BindCbsCtbExportService;
import io.renren.modules.cbs.dao.CbsDirectoryShipCompanyDao;
import io.renren.modules.cbs.dao.CbsExportDao;
import io.renren.modules.cbs.dao.CbsExportStatusStreamDao;
import io.renren.modules.cbs.dao.CbsExportVoyageDao;
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

@Service("bindCbsCtbExportService")
public class BindCbsCtbExportServiceImpl extends ServiceImpl<BindCbsCtbExportDao, BindCbsCtbExportEntity> implements BindCbsCtbExportService {

    private BindCbsCtbExportDao bindCbsCtbExportDao;
    private BindCbsCustomsBrokerCtbCompanyDao bindCbsCustomsBrokerCtbCompanyDao;
    private BindCbsCtbGoodsPartNoDao bindCbsCtbGoodsPartNoDao;
    private CbsExportDao cbsExportDao;
    private CbsExportStatusStreamDao cbsExportStatusStreamDao;
    private CbsExportVoyageDao cbsExportVoyageDao;
    private CbsDirectoryShipCompanyDao cbsDirectoryShipCompanyDao;
    private CtbExportDao ctbExportDao;
    private CtbExportStatusStreamDao ctbExportStatusStreamDao;
    private CtbDirectoryGoodsDao ctbDirectoryGoodsDao;
    private CtbOrderProcessingTradeGoodsDao ctbOrderProcessingTradeGoodsDao;
    private CtbGoodsPartNoDao ctbGoodsPartNoDao;
    private CtbExportGoodsDao ctbExportGoodsDao;
    private CtbExportMemberDao ctbExportMemberDao;
    private CtbPartnerDao ctbPartnerDao;
    private CtbPartnerTypeDao ctbPartnerTypeDao;
    private CtbExportVoyageDao ctbExportVoyageDao;
    private CtbDirectoryShipCompanyDao ctbDirectoryShipCompanyDao;
    private CommonFunction commonFunction;

    public BindCbsCtbExportServiceImpl(
            BindCbsCtbExportDao bindCbsCtbExportDao,
            BindCbsCustomsBrokerCtbCompanyDao bindCbsCustomsBrokerCtbCompanyDao,
            BindCbsCtbGoodsPartNoDao bindCbsCtbGoodsPartNoDao,
            CbsExportDao cbsExportDao,
            CbsExportStatusStreamDao cbsExportStatusStreamDao,
            CbsExportVoyageDao cbsExportVoyageDao,
            CbsDirectoryShipCompanyDao cbsDirectoryShipCompanyDao,
            CtbExportDao ctbExportDao,
            CtbExportStatusStreamDao ctbExportStatusStreamDao,
            CtbDirectoryGoodsDao ctbDirectoryGoodsDao,
            CtbOrderProcessingTradeGoodsDao ctbOrderProcessingTradeGoodsDao,
            CtbGoodsPartNoDao ctbGoodsPartNoDao,
            CtbExportGoodsDao ctbExportGoodsDao,
            CtbExportMemberDao ctbExportMemberDao,
            CtbPartnerDao ctbPartnerDao,
            CtbPartnerTypeDao ctbPartnerTypeDao,
            CtbExportVoyageDao ctbExportVoyageDao,
            CtbDirectoryShipCompanyDao ctbDirectoryShipCompanyDao,
            CommonFunction commonFunction
    ) {
        this.bindCbsCtbExportDao = bindCbsCtbExportDao;
        this.bindCbsCustomsBrokerCtbCompanyDao = bindCbsCustomsBrokerCtbCompanyDao;
        this.bindCbsCtbGoodsPartNoDao = bindCbsCtbGoodsPartNoDao;
        this.cbsExportDao = cbsExportDao;
        this.cbsExportStatusStreamDao = cbsExportStatusStreamDao;
        this.cbsExportVoyageDao = cbsExportVoyageDao;
        this.cbsDirectoryShipCompanyDao = cbsDirectoryShipCompanyDao;
        this.ctbExportDao = ctbExportDao;
        this.ctbExportStatusStreamDao = ctbExportStatusStreamDao;
        this.ctbDirectoryGoodsDao = ctbDirectoryGoodsDao;
        this.ctbOrderProcessingTradeGoodsDao = ctbOrderProcessingTradeGoodsDao;
        this.ctbGoodsPartNoDao = ctbGoodsPartNoDao;
        this.ctbExportGoodsDao = ctbExportGoodsDao;
        this.ctbExportMemberDao = ctbExportMemberDao;
        this.ctbPartnerDao = ctbPartnerDao;
        this.ctbPartnerTypeDao = ctbPartnerTypeDao;
        this.ctbExportVoyageDao = ctbExportVoyageDao;
        this.ctbDirectoryShipCompanyDao = ctbDirectoryShipCompanyDao;
        this.commonFunction = commonFunction;
    }

    @Override
    public PageUtils queryIndex(BindCbsCtbExportEntity entity) {
        return null;
    }

    @Override
    @Transactional
    public HashMap<String, Object> cbsSynToCtb(Long cbsExportId, Long cbsCompanyId, Long ctbCompanyId) {
        CbsExportEntity cbsExportEntity = cbsExportDao.detail(cbsExportId);
        HashMap<String, Object> resultMap = checkCbsExportGoods(cbsExportEntity);
        if (resultMap.containsKey("code")) {
            return resultMap;
        }

        BindCbsCustomsBrokerCtbCompanyEntity bindCbsCustomsBrokerCtbCompanyEntity = bindCbsCustomsBrokerCtbCompanyDao.detailByBothId(cbsCompanyId, ctbCompanyId);
        CtbExportEntity ctbExportEntity = CtbExportEntity.builder().build();
        BindCbsCtbExportEntity bindCbsCtbExportEntity;
        // 如果是第一次同步，创建Ctb实体和Bind实体
        if (cbsExportEntity.getBindEntity() == null) {
            // 创建Ctb实体
            BeanUtils.copyProperties(cbsExportEntity, ctbExportEntity);
            ctbExportEntity.setFkServiceCompanyId(bindCbsCustomsBrokerCtbCompanyEntity.getFkCtbServiceCompanyId());
            ctbExportEntity.setCtbTenantId(bindCbsCustomsBrokerCtbCompanyEntity.getFkCtbCompanyId());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            ctbExportEntity.setTitle(cbsExportEntity.getContractEntity().getTitle() + " 出口批次：" + dateFormat.format(cbsExportEntity.getCreateTime()));
            ctbExportEntity.setCurrencyCode(cbsExportEntity.getContractEntity().getCurrencyCode());
            ctbExportDao.insertWithoutTenant(ctbExportEntity);
            // 插入可能插入不全，补全所有信息
            Long ctbExportId = ctbExportEntity.getId();
            BeanUtils.copyProperties(cbsExportEntity, ctbExportEntity);
            ctbExportEntity.setId(ctbExportId);
            ctbExportDao.updateByIdWithoutTenant(ctbExportEntity);
            // 创建statusStream
            ctbExportStatusStreamDao.insert(CtbExportStatusStreamEntity.builder()
                    .fkExportId(ctbExportEntity.getId())
                    .lastStatus(0)
                    .status(1)
                    .remark("企业委托创建")
                    .build());
            // 创建Bind对实体
            bindCbsCtbExportEntity = BindCbsCtbExportEntity.builder().
                    fkCbsCompanyId(bindCbsCustomsBrokerCtbCompanyEntity.getFkCbsCompanyId()).
                    fkCtbCompanyId(bindCbsCustomsBrokerCtbCompanyEntity.getFkCtbCompanyId()).
                    fkCbsExportId(cbsExportEntity.getId()).
                    fkCtbExportId(ctbExportEntity.getId()).
                    isWorkByCtb(true).
                    build();
            bindCbsCtbExportDao.insert(bindCbsCtbExportEntity);
            // 合并到CbsExport中
            cbsExportEntity = cbsExportDao.detail(cbsExportId);
        } else {
            // 获取Bind
            bindCbsCtbExportEntity = cbsExportEntity.getBindEntity();
            bindCbsCtbExportEntity.setIsWorkByCtb(true);
            bindCbsCtbExportDao.updateById(bindCbsCtbExportEntity);
            // 更新Ctb实体
            BeanUtils.copyProperties(cbsExportEntity, ctbExportEntity);
            ctbExportEntity.setFkServiceCompanyId(bindCbsCustomsBrokerCtbCompanyEntity.getFkCtbServiceCompanyId());
            ctbExportEntity.setCurrencyCode(cbsExportEntity.getContractEntity().getCurrencyCode());
            ctbExportEntity.setId(bindCbsCtbExportEntity.getFkCtbExportId());
            ctbExportEntity.setStatus(1);
            ctbExportDao.updateByIdWithoutTenant(ctbExportEntity);
            // 创建statusStream
            ctbExportStatusStreamDao.insert(CtbExportStatusStreamEntity.builder()
                    .fkExportId(ctbExportEntity.getId())
                    .lastStatus(ctbExportEntity.getStatus())
                    .status(1)
                    .remark("企业同步数据")
                    .build());
        }
        // 更新CBS报关行属性为绑定报关行
        cbsExportEntity.setFkDirectoryCustomsBrokerId(bindCbsCustomsBrokerCtbCompanyEntity.getFkCbsCustomBrokerId());
        cbsExportDao.updateById(cbsExportEntity);
        // 获取最新export实体
        ctbExportEntity = ctbExportDao.detailWithoutBindAndTenant(ctbExportEntity.getId());

        // 删除原有Ctb交易方（名录和交易方）
        for (CtbExportMemberEntity ctbExportMemberEntity : ctbExportEntity.getMemberEntityList()) {
            // 删除member
            ctbExportMemberDao.deleteById(ctbExportMemberEntity.getId());
        }
        // 同步Ctb交易方（名录和交易方）
        for (CbsContractMemberEntity cbsContractMemberEntity : cbsExportEntity.getContractEntity().getMemberEntityList()) {
            // 名录
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

            // 交易方
            CtbExportMemberEntity ctbExportMemberEntity = CtbExportMemberEntity.builder().
                    fkExportId(ctbExportEntity.getId()).
                    fkPartnerId(ctbPartnerEntity.getId()).
                    type(cbsContractMemberEntity.getType()).
                    build();
            ctbExportMemberDao.insert(ctbExportMemberEntity);
        }

        // 删除原有Ctb出口商品
        for (CtbExportGoodsEntity ctbExportGoodsEntity : ctbExportEntity.getExportGoodsList()) {
            ctbExportGoodsDao.deleteById(ctbExportGoodsEntity.getId());
        }
        // 同步出口商品
        for (CbsExportGoodsEntity cbsExportGoodsEntity : cbsExportEntity.getExportGoodsList()) {
            CbsGoodsPartNoEntity cbsGoodsPartNoEntity = cbsExportGoodsEntity.getPartNoEntity();
            // 判断是否有绑定关系
            // 1、如果没有绑定关系，创建绑定关系
            // 2、如果有绑定关系，无需处理，只处理exportGoods
            CtbGoodsPartNoEntity ctbGoodsPartNoEntity = CtbGoodsPartNoEntity.builder().build();
            if (cbsGoodsPartNoEntity.getBindEntity() == null) {
                CtbDirectoryGoodsEntity ctbDirectoryGoodsEntity = CtbDirectoryGoodsEntity.builder().build();
                BeanUtils.copyProperties(cbsGoodsPartNoEntity.getGoodsEntity(), ctbDirectoryGoodsEntity);
                ctbDirectoryGoodsEntity.setCtbTenantId(bindCbsCustomsBrokerCtbCompanyEntity.getFkCtbCompanyId());
                ctbDirectoryGoodsDao.insertWithoutTenant(ctbDirectoryGoodsEntity);
                // 创建加贸关联商品
                CtbOrderProcessingTradeGoodsEntity ctbOrderProcessingTradeGoodsEntity = CtbOrderProcessingTradeGoodsEntity.builder().
                        fkOrderProcessingTradeId(-1L).
                        type(2).
                        build();
                ctbOrderProcessingTradeGoodsDao.insertWithoutTenant(ctbOrderProcessingTradeGoodsEntity);
                // 创建新料号
                BeanUtils.copyProperties(cbsGoodsPartNoEntity, ctbGoodsPartNoEntity);
                ctbGoodsPartNoEntity.setFkGoodsId(ctbDirectoryGoodsEntity.getId());
                ctbGoodsPartNoEntity.setGoodsPartNo(ctbOrderProcessingTradeGoodsEntity.getId());
                ctbGoodsPartNoDao.insertWithoutTenant(ctbGoodsPartNoEntity);
                // 创建绑定关系
                BindCbsCtbGoodsPartNoEntity bindCbsCtbGoodsPartNoEntity = BindCbsCtbGoodsPartNoEntity.builder().
                        fkCbsGoodsPartNo(cbsGoodsPartNoEntity.getGoodsPartNo()).
                        fkCtbGoodsPartNo(ctbGoodsPartNoEntity.getGoodsPartNo()).
                        build();
                bindCbsCtbGoodsPartNoDao.insert(bindCbsCtbGoodsPartNoEntity);
            } else {
                ctbGoodsPartNoEntity = cbsGoodsPartNoEntity.getBindEntity().getCtbGoodsPartNoEntity();
            }
            // 创建新exportGoods
            CtbExportGoodsEntity ctbExportGoodsEntity = CtbExportGoodsEntity.builder().build();
            BeanUtils.copyProperties(cbsExportGoodsEntity, ctbExportGoodsEntity);
            ctbExportGoodsEntity.setFkExportId(ctbExportEntity.getId());
            ctbExportGoodsEntity.setGoodsPartNo(ctbGoodsPartNoEntity.getGoodsPartNo());
            ctbExportGoodsDao.insert(ctbExportGoodsEntity);
        }

        // 删除原有航次添加新航次
        updateCtbVoyageChange(ctbExportEntity.getId(), bindCbsCtbExportEntity.getFkCtbCompanyId(), cbsExportEntity.getVoyageList());

        // 附件
        Map<String, String> map = new HashMap<>();
        map.put("cbs_img_export_contract_ship", "ctb_img_export_contract_ship");
        map.put("cbs_img_export_delivery_order", "ctb_img_export_delivery_order");
        map.put("cbs_img_export_entry_bill", "ctb_img_export_entry_bill");
        map.put("cbs_img_export_invoice", "ctb_img_export_invoice");
        map.put("cbs_img_export_lading_bill", "ctb_img_export_lading_bill");
        map.put("cbs_img_export_license_agreement", "ctb_img_export_license_agreement");
        map.put("cbs_img_export_others", "ctb_img_export_others");
        map.put("cbs_img_export_packing_list", "ctb_img_export_packing_list");
        map.put("cbs_img_export_pay_in_advance", "ctb_img_export_pay_in_advance");
        map.put("cbs_img_export_pay_tail", "ctb_img_export_pay_tail");
        map.put("cbs_img_export_power_of_attorney", "ctb_img_export_power_of_attorney");
        for (Map.Entry<String, String> vo : map.entrySet()) {
            commonFunction.synCbsCtbImportAndExportImg(SynImportAndExportImgVo.builder()
                    .originalTable(vo.getKey())
                    .targetTable(vo.getValue())
                    .originalFkId(cbsExportEntity.getId())
                    .targetFkId(ctbExportEntity.getId())
                    .fkField("fk_export_id")
                    .build());
        }

        resultMap.put("code", 200);
        resultMap.put("bindId", bindCbsCtbExportEntity.getId());
        return resultMap;
    }


    @Override
    @Transactional
    public Long ctbSynToCbs(Long ctbExportId) {
        CtbExportEntity ctbExportEntity = ctbExportDao.detail(ctbExportId);
        BindCbsCtbExportEntity bindCbsCtbExportEntity = ctbExportEntity.getBindEntity();
        CbsExportEntity cbsExportEntity = bindCbsCtbExportEntity.getCbsExportEntity();
        // 创建Cbs状态流
        cbsExportStatusStreamDao.insert(CbsExportStatusStreamEntity.builder()
                .fkExportId(bindCbsCtbExportEntity.getFkCbsExportId())
                .lastStatus(4)
                .status(11)
                .remark("报关行同步反馈")
                .build());
        // 更新Cbs实体
        BeanUtils.copyProperties(ctbExportEntity, cbsExportEntity);
        cbsExportEntity.setId(bindCbsCtbExportEntity.getFkCbsExportId());
        cbsExportEntity.setStatus(11);
        cbsExportDao.updateByIdWithoutTenant(cbsExportEntity);
        // 更新绑定实体
        bindCbsCtbExportEntity.setIsWorkByCtb(false);
        bindCbsCtbExportDao.updateById(bindCbsCtbExportEntity);

        // 删除原有航次添加新航次
        updateCbsVoyageChange(cbsExportEntity.getId(), bindCbsCtbExportEntity.getFkCbsCompanyId(), ctbExportEntity.getVoyageList());

        // 附件
        Map<String, String> map = new HashMap<>();
        map.put("ctb_img_export_contract_ship", "cbs_img_export_contract_ship");
        map.put("ctb_img_export_delivery_order", "cbs_img_export_delivery_order");
        map.put("ctb_img_export_entry_bill", "cbs_img_export_entry_bill");
        map.put("ctb_img_export_invoice", "cbs_img_export_invoice");
        map.put("ctb_img_export_lading_bill", "cbs_img_export_lading_bill");
        map.put("ctb_img_export_license_agreement", "cbs_img_export_license_agreement");
        map.put("ctb_img_export_others", "cbs_img_export_others");
        map.put("ctb_img_export_packing_list", "cbs_img_export_packing_list");
        map.put("ctb_img_export_pay_in_advance", "cbs_img_export_pay_in_advance");
        map.put("ctb_img_export_pay_tail", "cbs_img_export_pay_tail");
        map.put("ctb_img_export_power_of_attorney", "cbs_img_export_power_of_attorney");
        for (Map.Entry<String, String> vo : map.entrySet()) {
            commonFunction.synCbsCtbImportAndExportImg(SynImportAndExportImgVo.builder()
                    .originalTable(vo.getKey())
                    .targetTable(vo.getValue())
                    .originalFkId(ctbExportEntity.getId())
                    .targetFkId(cbsExportEntity.getId())
                    .fkField("fk_export_id")
                    .build());
        }

        return bindCbsCtbExportEntity.getId();
    }

    /**
     * 变更Cbs航次
     */
    private void updateCbsVoyageChange(Long cbsExportId, Long cbsCompanyId, List<CtbExportVoyageEntity> ctbExportVoyageEntityList) {
        // 删除原航次
        cbsExportVoyageDao.delete(new QueryWrapper<CbsExportVoyageEntity>().eq("fk_export_id", cbsExportId));
        for (CtbExportVoyageEntity ctbExportVoyageEntity : ctbExportVoyageEntityList) {
            // 判断是否有相同船务公司名录，有则使用，没有则插入名录
            CtbDirectoryShipCompanyEntity ctbDirectoryShipCompanyEntity = ctbExportVoyageEntity.getShipCompanyEntity();
            CbsDirectoryShipCompanyEntity cbsDirectoryShipCompanyEntity = cbsDirectoryShipCompanyDao.findSameToCtb(ctbDirectoryShipCompanyEntity, cbsCompanyId);
            if (cbsDirectoryShipCompanyEntity == null && ctbDirectoryShipCompanyEntity != null) {
                cbsDirectoryShipCompanyEntity = CbsDirectoryShipCompanyEntity.builder().build();
                BeanUtils.copyProperties(ctbDirectoryShipCompanyEntity, cbsDirectoryShipCompanyEntity);
                cbsDirectoryShipCompanyEntity.setTenantId(cbsCompanyId);
                cbsDirectoryShipCompanyDao.insertWithoutTenant(cbsDirectoryShipCompanyEntity);
            }
            // 插入新航次
            CbsExportVoyageEntity cbsExportVoyageEntity = CbsExportVoyageEntity.builder().build();
            BeanUtils.copyProperties(ctbExportVoyageEntity, cbsExportVoyageEntity);
            if (cbsDirectoryShipCompanyEntity != null) {
                cbsExportVoyageEntity.setFkShipCompanyId(cbsDirectoryShipCompanyEntity.getId());
            }
            cbsExportVoyageEntity.setFkExportId(cbsExportId);
            cbsExportVoyageDao.insert(cbsExportVoyageEntity);
        }
    }

    /**
     * 变更Ctb航次
     */
    private void updateCtbVoyageChange(Long ctbExportId, Long ctbCompanyId, List<CbsExportVoyageEntity> cbsExportVoyageEntityList) {
        // 删除原航次
        ctbExportVoyageDao.delete(new QueryWrapper<CtbExportVoyageEntity>().eq("fk_export_id", ctbExportId));
        for (CbsExportVoyageEntity cbsExportVoyageEntity : cbsExportVoyageEntityList) {
            // 判断是否有相同船务公司名录，有则使用，没有则插入名录
            CbsDirectoryShipCompanyEntity cbsDirectoryShipCompanyEntity = cbsExportVoyageEntity.getShipCompanyEntity();
            CtbDirectoryShipCompanyEntity ctbDirectoryShipCompanyEntity = ctbDirectoryShipCompanyDao.findSameToCbs(cbsDirectoryShipCompanyEntity, ctbCompanyId);
            if (ctbDirectoryShipCompanyEntity == null && cbsDirectoryShipCompanyEntity != null) {
                ctbDirectoryShipCompanyEntity = CtbDirectoryShipCompanyEntity.builder().build();
                BeanUtils.copyProperties(cbsDirectoryShipCompanyEntity, ctbDirectoryShipCompanyEntity);
                ctbDirectoryShipCompanyEntity.setCtbTenantId(ctbCompanyId);
                ctbDirectoryShipCompanyDao.insertWithoutTenant(ctbDirectoryShipCompanyEntity);
            }
            // 插入新航次
            CtbExportVoyageEntity ctbExportVoyageEntity = CtbExportVoyageEntity.builder().build();
            BeanUtils.copyProperties(cbsExportVoyageEntity, ctbExportVoyageEntity);
            if (ctbDirectoryShipCompanyEntity != null) {
                ctbExportVoyageEntity.setFkShipCompanyId(ctbDirectoryShipCompanyEntity.getId());
            }
            ctbExportVoyageEntity.setFkExportId(ctbExportId);
            ctbExportVoyageDao.insert(ctbExportVoyageEntity);
        }
    }

    @Override
    public BindCbsCtbExportEntity detail(Long id) {
        return null;
    }

    private HashMap<String, Object> checkCbsExportGoods(CbsExportEntity cbsExportEntity) {
        HashMap<String, Object> resultMap = new HashMap<>();
        if ((cbsExportEntity.getExportGoodsList() == null || cbsExportEntity.getExportGoodsList().isEmpty())) {
            resultMap.put("code", 500);
            resultMap.put("msg", "请先编辑进口商品，报关行不会对进口单商品内容进行操作，请知悉。");
        } else {
            for (CbsExportGoodsEntity cbsExportGoodsEntity : cbsExportEntity.getExportGoodsList()) {
                if (!checkCbsExportGoods(cbsExportGoodsEntity)) {
                    resultMap.put("code", 500);
                    resultMap.put("msg", "请先编辑进口商品，商品数量必须大于0，报关行不会对进口单商品内容进行操作，请知悉。");
                }
            }
        }
        return resultMap;
    }

    private boolean checkCbsExportGoods(CbsExportGoodsEntity cbsExportGoodsEntity) {
        return cbsExportGoodsEntity.getCount().compareTo(BigDecimal.ZERO) > 0;
    }

}