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
        // 如果是第一次同步，创建Ctb实体和Bind实体
        if (cbsImportEntity.getBindEntity() == null) {
            // 创建Ctb实体
            BeanUtils.copyProperties(cbsImportEntity, ctbImportEntity);
            ctbImportEntity.setFkServiceCompanyId(bindCbsCustomsBrokerCtbCompanyEntity.getFkCtbServiceCompanyId());
            ctbImportEntity.setCtbTenantId(bindCbsCustomsBrokerCtbCompanyEntity.getFkCtbCompanyId());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            ctbImportEntity.setTitle(cbsImportEntity.getContractEntity().getTitle() + " 进口批次：" + dateFormat.format(cbsImportEntity.getCreateTime()));
            ctbImportEntity.setCurrencyCode(cbsImportEntity.getContractEntity().getCurrencyCode());
            ctbImportDao.insertWithoutTenant(ctbImportEntity);
            // 插入可能插入不全，补全所有信息
            Long ctbImportId = ctbImportEntity.getId();
            BeanUtils.copyProperties(cbsImportEntity, ctbImportEntity);
            ctbImportEntity.setId(ctbImportId);
            ctbImportDao.updateByIdWithoutTenant(ctbImportEntity);
            // 创建statusStream
            ctbImportStatusStreamDao.insert(CtbImportStatusStreamEntity.builder()
                    .fkImportId(ctbImportEntity.getId())
                    .lastStatus(0)
                    .status(1)
                    .remark("企业委托创建")
                    .build());
            // 创建Bind对实体
            bindCbsCtbImportEntity = BindCbsCtbImportEntity.builder().
                    fkCbsCompanyId(bindCbsCustomsBrokerCtbCompanyEntity.getFkCbsCompanyId()).
                    fkCtbCompanyId(bindCbsCustomsBrokerCtbCompanyEntity.getFkCtbCompanyId()).
                    fkCbsImportId(cbsImportEntity.getId()).
                    fkCtbImportId(ctbImportEntity.getId()).
                    isWorkByCtb(true).
                    build();
            bindCbsCtbImportDao.insert(bindCbsCtbImportEntity);
            // 合并到CbsImport中
            cbsImportEntity = cbsImportDao.detail(cbsImportId);
        } else {
            // 更新Bind
            bindCbsCtbImportEntity = cbsImportEntity.getBindEntity();
            bindCbsCtbImportEntity.setIsWorkByCtb(true);
            bindCbsCtbImportDao.updateById(bindCbsCtbImportEntity);
            // 更新Ctb实体
            BeanUtils.copyProperties(cbsImportEntity, ctbImportEntity);
            ctbImportEntity.setFkServiceCompanyId(bindCbsCustomsBrokerCtbCompanyEntity.getFkCtbServiceCompanyId());
            ctbImportEntity.setCurrencyCode(cbsImportEntity.getContractEntity().getCurrencyCode());
            ctbImportEntity.setId(bindCbsCtbImportEntity.getFkCtbImportId());
            ctbImportEntity.setStatus(1);
            ctbImportDao.updateByIdWithoutTenant(ctbImportEntity);
            // 创建statusStream
            ctbImportStatusStreamDao.insert(CtbImportStatusStreamEntity.builder()
                    .fkImportId(ctbImportEntity.getId())
                    .lastStatus(ctbImportEntity.getStatus())
                    .status(1)
                    .remark("企业同步数据")
                    .build());
        }
        // 更新CBS报关行属性为绑定报关行
        cbsImportEntity.setFkDirectoryCustomsBrokerId(bindCbsCustomsBrokerCtbCompanyEntity.getFkCbsCustomBrokerId());
        cbsImportDao.updateById(cbsImportEntity);
        // 获取最新import实体
        ctbImportEntity = ctbImportDao.detailWithoutBindAndTenant(ctbImportEntity.getId());

        // 删除原有Ctb交易方（交易方）
        for (CtbImportMemberEntity ctbImportMemberEntity : ctbImportEntity.getMemberEntityList()) {
            // 删除member
            ctbImportMemberDao.deleteById(ctbImportMemberEntity.getId());
        }
        // 同步Ctb交易方（名录和交易方）
        for (CbsContractMemberEntity cbsContractMemberEntity : cbsImportEntity.getContractEntity().getMemberEntityList()) {
            // 名录 检查是否有相同partner，有则复用，没有则创建
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
            CtbImportMemberEntity ctbImportMemberEntity = CtbImportMemberEntity.builder().
                    fkImportId(ctbImportEntity.getId()).
                    fkPartnerId(ctbPartnerEntity.getId()).
                    type(cbsContractMemberEntity.getType()).
                    build();
            ctbImportMemberDao.insert(ctbImportMemberEntity);
        }

        // 删除原有Ctb进口商品
        for (CtbImportGoodsEntity ctbImportGoodsEntity : ctbImportEntity.getImportGoodsList()) {
            ctbImportGoodsDao.deleteById(ctbImportGoodsEntity.getId());
        }
        // 同步进口商品
        for (CbsImportGoodsEntity cbsImportGoodsEntity : cbsImportEntity.getImportGoodsList()) {
            CbsGoodsPartNoEntity cbsGoodsPartNoEntity = cbsImportGoodsEntity.getPartNoEntity();
            // 判断是否有绑定关系
            // 1、如果没有绑定关系，创建绑定关系
            // 2、如果有绑定关系，无需处理，只处理importGoods
            CtbGoodsPartNoEntity ctbGoodsPartNoEntity = CtbGoodsPartNoEntity.builder().build();
            if (cbsGoodsPartNoEntity.getBindEntity() == null) {
                CtbDirectoryGoodsEntity ctbDirectoryGoodsEntity = CtbDirectoryGoodsEntity.builder().build();
                BeanUtils.copyProperties(cbsGoodsPartNoEntity.getGoodsEntity(), ctbDirectoryGoodsEntity);
                ctbDirectoryGoodsEntity.setCtbTenantId(bindCbsCustomsBrokerCtbCompanyEntity.getFkCtbCompanyId());
                ctbDirectoryGoodsDao.insertWithoutTenant(ctbDirectoryGoodsEntity);
                // 创建加贸关联商品
                CtbOrderProcessingTradeGoodsEntity ctbOrderProcessingTradeGoodsEntity = CtbOrderProcessingTradeGoodsEntity.builder().
                        fkOrderProcessingTradeId(-1L).
                        type(1).
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
            // 创建新importGoods
            CtbImportGoodsEntity ctbImportGoodsEntity = CtbImportGoodsEntity.builder().build();
            BeanUtils.copyProperties(cbsImportGoodsEntity, ctbImportGoodsEntity);
            ctbImportGoodsEntity.setFkImportId(ctbImportEntity.getId());
            ctbImportGoodsEntity.setGoodsPartNo(ctbGoodsPartNoEntity.getGoodsPartNo());
            ctbImportGoodsDao.insert(ctbImportGoodsEntity);
        }

        // 删除原有航次添加新航次
        updateCtbVoyageChange(ctbImportEntity.getId(), bindCbsCtbImportEntity.getFkCtbCompanyId(), cbsImportEntity.getVoyageList());

        // 附件
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
        // 创建Cbs状态流
        cbsImportStatusStreamDao.insert(CbsImportStatusStreamEntity.builder()
                .fkImportId(bindCbsCtbImportEntity.getFkCbsImportId())
                .lastStatus(4)
                .status(11)
                .remark("报关行同步反馈")
                .build());
        // 更新Cbs实体
        BeanUtils.copyProperties(ctbImportEntity, cbsImportEntity, "bindEntity");
        cbsImportEntity.setId(bindCbsCtbImportEntity.getFkCbsImportId());
        cbsImportEntity.setStatus(11);
        cbsImportDao.updateByIdWithoutTenant(cbsImportEntity);
        // 更新绑定实体
        bindCbsCtbImportEntity.setIsWorkByCtb(false);
        bindCbsCtbImportDao.updateById(bindCbsCtbImportEntity);

        // 删除原有航次添加新航次
        updateCbsVoyageChange(cbsImportEntity.getId(), bindCbsCtbImportEntity.getFkCbsCompanyId(), ctbImportEntity.getVoyageList());

        // 附件
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
     * 变更Cbs航次
     */
    private void updateCbsVoyageChange(Long cbsImportId, Long cbsCompanyId, List<CtbImportVoyageEntity> ctbImportVoyageEntityList) {
        // 删除原航次
        cbsImportVoyageDao.delete(new QueryWrapper<CbsImportVoyageEntity>().eq("fk_import_id", cbsImportId));
        for (CtbImportVoyageEntity ctbImportVoyageEntity : ctbImportVoyageEntityList) {
            // 判断是否有相同船务公司名录，有则使用，没有则插入名录
            CtbDirectoryShipCompanyEntity ctbDirectoryShipCompanyEntity = ctbImportVoyageEntity.getShipCompanyEntity();
            CbsDirectoryShipCompanyEntity cbsDirectoryShipCompanyEntity = cbsDirectoryShipCompanyDao.findSameToCtb(ctbDirectoryShipCompanyEntity, cbsCompanyId);
            if (cbsDirectoryShipCompanyEntity == null && ctbDirectoryShipCompanyEntity != null) {
                cbsDirectoryShipCompanyEntity = CbsDirectoryShipCompanyEntity.builder().build();
                BeanUtils.copyProperties(ctbDirectoryShipCompanyEntity, cbsDirectoryShipCompanyEntity);
                cbsDirectoryShipCompanyEntity.setTenantId(cbsCompanyId);
                cbsDirectoryShipCompanyDao.insertWithoutTenant(cbsDirectoryShipCompanyEntity);
            }
            // 插入新航次
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
     * 变更Ctb航次
     */
    private void updateCtbVoyageChange(Long ctbImportId, Long ctbCompanyId, List<CbsImportVoyageEntity> cbsImportVoyageEntityList) {
        // 删除原航次
        ctbImportVoyageDao.delete(new QueryWrapper<CtbImportVoyageEntity>().eq("fk_import_id", ctbImportId));
        for (CbsImportVoyageEntity cbsImportVoyageEntity : cbsImportVoyageEntityList) {
            // 判断是否有相同船务公司名录，有则使用，没有则插入名录
            CbsDirectoryShipCompanyEntity cbsDirectoryShipCompanyEntity = cbsImportVoyageEntity.getShipCompanyEntity();
            CtbDirectoryShipCompanyEntity ctbDirectoryShipCompanyEntity = ctbDirectoryShipCompanyDao.findSameToCbs(cbsDirectoryShipCompanyEntity, ctbCompanyId);
            if (ctbDirectoryShipCompanyEntity == null && cbsDirectoryShipCompanyEntity != null) {
                ctbDirectoryShipCompanyEntity = CtbDirectoryShipCompanyEntity.builder().build();
                BeanUtils.copyProperties(cbsDirectoryShipCompanyEntity, ctbDirectoryShipCompanyEntity);
                ctbDirectoryShipCompanyEntity.setCtbTenantId(ctbCompanyId);
                ctbDirectoryShipCompanyDao.insertWithoutTenant(ctbDirectoryShipCompanyEntity);
            }
            // 插入新航次
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
            resultMap.put("msg", "请先编辑出口商品，报关行不会对出口单商品内容进行操作，请知悉。");
        } else {
            for (CbsImportGoodsEntity cbsImportGoodsEntity : cbsImportEntity.getImportGoodsList()) {
                if (!checkCbsImportGoods(cbsImportGoodsEntity)) {
                    resultMap.put("code", 500);
                    resultMap.put("msg", "请先编辑出口商品，商品数量必须大于0，报关行不会对出口单商品内容进行操作，请知悉。");
                }
            }
        }
        return resultMap;
    }

    private boolean checkCbsImportGoods(CbsImportGoodsEntity cbsImportGoodsEntity) {
        return cbsImportGoodsEntity.getCount().compareTo(BigDecimal.ZERO) > 0;
    }
}