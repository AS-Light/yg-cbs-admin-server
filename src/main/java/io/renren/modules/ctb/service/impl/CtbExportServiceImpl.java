package io.renren.modules.ctb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.enumeration.CtbDocumentControlEnum;
import io.renren.common.enumeration.InOutEnum;
import io.renren.common.utils.CommonFunction;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.ctb.dao.*;
import io.renren.modules.ctb.entity.*;
import io.renren.modules.ctb.service.CtbExportService;
import io.renren.modules.ctb.vo.PriceItemVo;
import io.renren.modules.ctb.vo.UnifiedUploadingVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;


@Service("ctbExportService")
public class CtbExportServiceImpl extends ServiceImpl<CtbExportDao, CtbExportEntity> implements CtbExportService {

    private CtbExportDao exportDao;
    private CtbExportMemberDao memberDao;
    private CtbExportGoodsDao exportGoodsDao;
    private CtbOrderProcessingTradeGoodsDao orderProcessingTradeGoodsDao;
    private CtbGoodsPartNoDao goodsPartNoDao;
    private CtbExportStatusStreamDao exportStatusStreamDao;
    private CtbExportManagerChangeStreamDao exportManagerChangeStreamDao;
    private CtbExportVoyageDao exportVoyageDao;
    private CtbMoneyInDao moneyInDao;
    private CtbMoneyOutDao moneyOutDao;
    private CommonFunction commonFunction;

    public CtbExportServiceImpl(CtbExportDao exportDao,
                                CtbExportMemberDao memberDao,
                                CtbExportGoodsDao exportGoodsDao,
                                CtbOrderProcessingTradeGoodsDao orderProcessingTradeGoodsDao,
                                CtbGoodsPartNoDao goodsPartNoDao,
                                CtbExportStatusStreamDao exportStatusStreamDao,
                                CtbExportManagerChangeStreamDao exportManagerChangeStreamDao,
                                CtbExportVoyageDao exportVoyageDao,
                                CtbMoneyInDao moneyInDao,
                                CtbMoneyOutDao moneyOutDao,
                                CommonFunction commonFunction) {
        this.exportDao = exportDao;
        this.memberDao = memberDao;
        this.exportGoodsDao = exportGoodsDao;
        this.orderProcessingTradeGoodsDao = orderProcessingTradeGoodsDao;
        this.goodsPartNoDao = goodsPartNoDao;
        this.exportStatusStreamDao = exportStatusStreamDao;
        this.exportManagerChangeStreamDao = exportManagerChangeStreamDao;
        this.exportVoyageDao = exportVoyageDao;
        this.moneyInDao = moneyInDao;
        this.moneyOutDao = moneyOutDao;
        this.commonFunction = commonFunction;
    }

    @Override
    public PageUtils queryIndex(CtbExportEntity entity) {
        IPage<CtbExportEntity> page = exportDao.queryIndex(new QueryPage<CtbExportEntity>().getPage(entity), entity);
        return new PageUtils(page);
    }

    @Override
    public CtbExportEntity detail(Long id) {
        return exportDao.detail(id);
    }

    @Override
    @Transactional
    public HashMap<String, Object> saveReturnId(CtbExportEntity entity) {
        HashMap<String, Object> resultMap = new HashMap<>();
        if (entity.getMemberEntityList() == null || entity.getMemberEntityList().isEmpty() || entity.getMemberEntityList().size() < 2) {
            resultMap.put("code", 500);
            resultMap.put("msg", "请选择交易双方");
        }
        exportDao.insert(entity);
        for (CtbExportMemberEntity memberEntity : entity.getMemberEntityList()) {
            memberEntity.setFkExportId(entity.getId());
            memberDao.insert(memberEntity);
        }
        // 为出口单创建第一个状态流
        CtbExportStatusStreamEntity statusStreamEntity = new CtbExportStatusStreamEntity();
        statusStreamEntity.setFkExportId(entity.getId());
        statusStreamEntity.setRemark("创建");
        statusStreamEntity.setLastStatus(0);
        statusStreamEntity.setStatus(1);
        statusStreamEntity.setOperator(entity.getOperator());
        exportStatusStreamDao.insert(statusStreamEntity);
        // 初始化负责人
        distributionManager(entity.getId(), entity.getOperator(), entity.getOperator());
        resultMap.put("code", 200);
        resultMap.put("id", entity.getId());
        return resultMap;
    }

    @Override
    @Transactional
    public Integer updateAllById(CtbExportEntity entity) {
        exportDao.updateById(entity);
        // 非同步数据才可更新商品
        if (entity.getBindEntity() == null) {
            // 更新商品
            List<CtbExportGoodsEntity> oldGoodsList = exportGoodsDao.listByExportId(entity.getId());
            for (CtbExportGoodsEntity oldGoods : oldGoodsList) {
                // 删除旧商品
                exportGoodsDao.deleteById(oldGoods);
                // 删除相应料号
                if (oldGoods.getPartNoEntity() != null) {
                    orderProcessingTradeGoodsDao.deleteById(oldGoods.getGoodsPartNo());
                    goodsPartNoDao.deleteById(oldGoods.getPartNoEntity().getId());
                }
            }
            List<CtbExportGoodsEntity> newGoodsList = entity.getExportGoodsList();
            for (CtbExportGoodsEntity newGoods : newGoodsList) {
                // 创建相应合同商品
                CtbOrderProcessingTradeGoodsEntity orderProcessingTradeGoodsEntity = CtbOrderProcessingTradeGoodsEntity.builder().type(2).build();
                orderProcessingTradeGoodsDao.insert(orderProcessingTradeGoodsEntity);
                // 创建相应料号
                CtbGoodsPartNoEntity goodsPartNoEntity = newGoods.getPartNoEntity();
                goodsPartNoEntity.setGoodsPartNo(orderProcessingTradeGoodsEntity.getId());
                goodsPartNoDao.insert(goodsPartNoEntity);
                // 创建新进口商品
                newGoods.setGoodsPartNo(orderProcessingTradeGoodsEntity.getId());
                exportGoodsDao.insert(newGoods);
            }
        }
        // 更新单内航次
        if (entity.getVoyageList() != null && !entity.getVoyageList().isEmpty()) {
            CtbExportVoyageEntity voyageEntity = entity.getVoyageList().get(0);
            voyageEntity.setCreateTime(new Date());
            voyageEntity.setFkExportId(entity.getId());
            if (voyageEntity.getId() == null) {
                exportVoyageDao.insert(voyageEntity);
            } else {
                exportVoyageDao.updateById(voyageEntity);
            }
        }
        if (entity.getImgExportContractList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CtbImgExportContractEntity imgContractShipEntity : entity.getImgExportContractList()) {
                imgList.add(imgContractShipEntity.getImgUrl());
            }
            commonFunction.ctbUnifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(CtbDocumentControlEnum.TYPE_EXPORT_CONTRACT.getCode())
                    .field("fk_export_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        if (entity.getImgExportContractShipList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CtbImgExportContractShipEntity imgContractShipEntity : entity.getImgExportContractShipList()) {
                imgList.add(imgContractShipEntity.getImgUrl());
            }
            commonFunction.ctbUnifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(CtbDocumentControlEnum.TYPE_EXPORT_CONTRACT_SHIP.getCode())
                    .field("fk_export_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        if (entity.getImgExportPackingListList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CtbImgExportPackingListEntity imgPackingListEntity : entity.getImgExportPackingListList()) {
                imgList.add(imgPackingListEntity.getImgUrl());
            }
            commonFunction.ctbUnifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(CtbDocumentControlEnum.TYPE_EXPORT_PACKING_LIST.getCode())
                    .field("fk_export_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        if (entity.getImgExportInvoiceList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CtbImgExportInvoiceEntity imgExportInvoiceEntity : entity.getImgExportInvoiceList()) {
                imgList.add(imgExportInvoiceEntity.getImgUrl());
            }
            commonFunction.ctbUnifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(CtbDocumentControlEnum.TYPE_EXPORT_INVOICE.getCode())
                    .field("fk_export_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        if (entity.getImgExportLadingBillList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CtbImgExportLadingBillEntity imgExportLadingBillEntity : entity.getImgExportLadingBillList()) {
                imgList.add(imgExportLadingBillEntity.getImgUrl());
            }
            commonFunction.ctbUnifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(CtbDocumentControlEnum.TYPE_EXPORT_LADING_BILL.getCode())
                    .field("fk_export_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        if (entity.getImgExportDeliveryOrderList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CtbImgExportDeliveryOrderEntity imgExportDeliveryOrderEntity : entity.getImgExportDeliveryOrderList()) {
                imgList.add(imgExportDeliveryOrderEntity.getImgUrl());
            }
            commonFunction.ctbUnifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(CtbDocumentControlEnum.TYPE_EXPORT_DELIVERY_ORDER.getCode())
                    .field("fk_export_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        if (entity.getImgExportOthersList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CtbImgExportOthersEntity imgExportOthersEntity : entity.getImgExportOthersList()) {
                imgList.add(imgExportOthersEntity.getImgUrl());
            }
            commonFunction.ctbUnifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(CtbDocumentControlEnum.TYPE_EXPORT_OTHERS.getCode())
                    .field("fk_export_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        if (entity.getImgExportEntryBillList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CtbImgExportEntryBillEntity imgExportEntryBillEntity : entity.getImgExportEntryBillList()) {
                imgList.add(imgExportEntryBillEntity.getImgUrl());
            }
            commonFunction.ctbUnifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(CtbDocumentControlEnum.TYPE_EXPORT_ENTRY_BILL.getCode())
                    .field("fk_export_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }

        if (entity.getImgExportPayInAdvanceList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CtbImgExportPayInAdvanceEntity imgExportPayInAdvanceEntity : entity.getImgExportPayInAdvanceList()) {
                imgList.add(imgExportPayInAdvanceEntity.getImgUrl());
            }
            commonFunction.ctbUnifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(CtbDocumentControlEnum.TYPE_EXPORT_PAY_IN_ADVANCE.getCode())
                    .field("fk_export_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        if (entity.getImgExportPayTailList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CtbImgExportPayTailEntity imgExportPayTailEntity : entity.getImgExportPayTailList()) {
                imgList.add(imgExportPayTailEntity.getImgUrl());
            }
            commonFunction.ctbUnifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(CtbDocumentControlEnum.TYPE_EXPORT_PAY_TAIL.getCode())
                    .field("fk_export_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        if (entity.getImgExportLicenseAgreementList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CtbImgExportLicenseAgreementEntity imgExportLicenseAgreementEntity : entity.getImgExportLicenseAgreementList()) {
                imgList.add(imgExportLicenseAgreementEntity.getImgUrl());
            }
            commonFunction.ctbUnifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(CtbDocumentControlEnum.TYPE_EXPORT_LICENSE_AGREEMENT.getCode())
                    .field("fk_export_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        if (entity.getImgExportPowerOfAttorneyList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CtbImgExportPowerOfAttorneyEntity imgExportPowerOfAttorneyEntity : entity.getImgExportPowerOfAttorneyList()) {
                imgList.add(imgExportPowerOfAttorneyEntity.getImgUrl());
            }
            commonFunction.ctbUnifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(CtbDocumentControlEnum.TYPE_EXPORT_POWER_OF_ATTORNEY.getCode())
                    .field("fk_export_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        return 1;
    }

    @Override
    @Transactional
    public Object updateVoyageChange(CtbExportVoyageEntity entity) {
        CtbExportEntity exportEntity = exportDao.selectById(entity.getFkExportId());
        exportEntity.setPassPortCode(entity.getPassPortCode());
        exportDao.updateById(exportEntity);
        if (entity.getId() == null) {
            entity.setCreateTime(new Date());
            return exportVoyageDao.insert(entity);
        } else {
            return exportVoyageDao.updateById(entity);
        }
    }

    /**
     * 提审
     */
    @Override
    @Transactional
    public void submit(CtbExportStatusStreamEntity statusStreamEntity) throws RuntimeException {
        CtbExportEntity entity = exportDao.detail(statusStreamEntity.getFkExportId());
        String errMsg = checkExportComplete(entity);
        if (errMsg != null) throw new RuntimeException(errMsg);

        CtbExportStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkExportId());
        if (lastStatusStreamEntity != null) {
            if (lastStatusStreamEntity.getStatus() == 1 || lastStatusStreamEntity.getStatus() == 3) {
                entity.setStatus(2);
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());
                statusStreamEntity.setStatus(2);

                exportDao.updateById(entity);
                exportStatusStreamDao.insert(statusStreamEntity);
            } else {
                throw new RuntimeException("出口单状态不合法，编辑中和不通过状态才能提交审核");
            }
        } else {
            entity.setStatus(2);
            statusStreamEntity.setLastStatus(-1);
            statusStreamEntity.setStatus(2);

            exportDao.updateById(entity);
            exportStatusStreamDao.insert(statusStreamEntity);
        }
    }

    /**
     * @Description: 提审
     * 可以提审的状态：1、编辑中，3、不通过
     */
    @Override
    @Transactional
    public Integer submitBack(CtbExportStatusStreamEntity statusStreamEntity) {
        CtbExportEntity entity = exportDao.selectById(statusStreamEntity.getFkExportId());
        CtbExportStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkExportId());
        if (lastStatusStreamEntity != null) {
            if (lastStatusStreamEntity.getStatus() == 2) {
                entity.setStatus(lastStatusStreamEntity.getLastStatus());
                statusStreamEntity.setStatus(lastStatusStreamEntity.getLastStatus());
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());

                exportDao.updateById(entity);
                exportStatusStreamDao.insert(statusStreamEntity);
                return 1;
            } else {
                return -1;
            }
        } else {
            return -3;
        }
    }

    /**
     * @Description: 审核
     * 可以审核的状态：2、提审
     */
    @Override
    @Transactional
    public void check(CtbExportStatusStreamEntity statusStreamEntity) throws RuntimeException {
        CtbExportEntity entity = exportDao.detail(statusStreamEntity.getFkExportId());
        CtbExportStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkExportId());
        if (lastStatusStreamEntity != null) {
            if (InOutEnum.STATUS_2.getCode() == entity.getStatus()) {
                entity.setStatus(statusStreamEntity.getStatus());
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());
                exportDao.updateById(entity);
                exportStatusStreamDao.insert(statusStreamEntity);
                if (InOutEnum.STATUS_4.getCode() == statusStreamEntity.getStatus()) {
                    // 更新报关费用属性
                    entity.setCustomsFeeMoney(sumPriceItem(entity));
                    exportDao.updateById(entity);
                    // 如果非同步来的出口单，最终审核结果通过，插入收支流水
                    if (entity.getBindEntity() == null) {
                        processPriceItemList(entity, statusStreamEntity.getOperator());
                    }
                }
            } else {
                throw new RuntimeException("出口单状态不合法，请先提交审核");
            }
        } else {
            throw new RuntimeException("没有找到这个出口单,请联系管理员");
        }
    }

    @Override
    @Transactional
    public void deleteByIds(Long[] ids) {
        List<Long> goodsIds = exportGoodsDao.listIdsByExportIds(ids);
        if (goodsIds != null && !goodsIds.isEmpty()) {
            exportGoodsDao.deleteBatchIds(goodsIds);
        }
        removeByIds(Arrays.asList(ids));
    }

    private void insertMoneyIO(CtbExportEntity entity, Long operator) {
    }

    @Override
    @Transactional
    public void distributionManager(Long exportId, Long nowManager, Long operator) {
        // 更新export表
        CtbExportEntity exportEntity = exportDao.selectById(exportId);
        exportEntity.setOperator(operator);
        exportEntity.setManager(nowManager);
        exportDao.updateById(exportEntity);
        // 插入负责人变更
        CtbExportManagerChangeStreamEntity lastManagerChangeStreamEntity = getLastManagerChangeStreamEntity(exportId);
        CtbExportManagerChangeStreamEntity managerChangeStreamEntity = CtbExportManagerChangeStreamEntity.builder().
                fkExportId(exportId).
                manager(nowManager).
                operator(operator).
                createTime(new Date()).
                build();
        if (lastManagerChangeStreamEntity != null) {
            managerChangeStreamEntity.setLastManager(lastManagerChangeStreamEntity.getManager());
        }
        exportManagerChangeStreamDao.insert(managerChangeStreamEntity);
    }

    private CtbExportStatusStreamEntity getLastStatusStreamEntity(Long exportId) {
        List<CtbExportStatusStreamEntity> statusStreamEntityList = exportStatusStreamDao.selectList(new QueryWrapper<CtbExportStatusStreamEntity>().eq("fk_export_id", exportId).orderByAsc("id"));
        if (statusStreamEntityList != null && !statusStreamEntityList.isEmpty()) {
            return statusStreamEntityList.get(statusStreamEntityList.size() - 1);
        } else {
            return null;
        }
    }

    private CtbExportManagerChangeStreamEntity getLastManagerChangeStreamEntity(Long exportId) {
        List<CtbExportManagerChangeStreamEntity> managerChangeStreamEntityList = exportManagerChangeStreamDao.selectList(new QueryWrapper<CtbExportManagerChangeStreamEntity>().eq("fk_export_id", exportId).orderByAsc("id"));
        if (managerChangeStreamEntityList != null && !managerChangeStreamEntityList.isEmpty()) {
            return managerChangeStreamEntityList.get(managerChangeStreamEntityList.size() - 1);
        } else {
            return null;
        }
    }

    private void processPriceItemList(CtbExportEntity entity, Long operator) {
        commonFunction.processCtbPriceItemList(PriceItemVo.builder()
                .tableName("ctb_export_price_item")
                .parameterField("fk_export_id")
                .serviceCompanyId(entity.getFkServiceCompanyId())
                .orderType(3)
                .parameterId(entity.getId())
                .name(entity.getTitle())
                .operator(operator)
                .build());
    }

    private BigDecimal sumPriceItem(CtbExportEntity entity) {
        if (entity.getExportPriceItemEntityList() != null) {
            BigDecimal sum = BigDecimal.ZERO;
            for (CtbExportPriceItemEntity priceItemEntity : entity.getExportPriceItemEntityList()) {
                BigDecimal money = priceItemEntity.getMoney();
                BigDecimal exchangeRate = priceItemEntity.getCurrencyEntity().getExchangeRate();
                sum = sum.add(money.multiply(exchangeRate));
            }
            return sum;
        } else {
            return BigDecimal.ZERO;
        }
    }

    /**
     * 判断出口单完整性
     */
    private String checkExportComplete(CtbExportEntity entity) {
        if (entity.getExportGoodsList().size() <= 0) {
            return "出口单必须包含出口商品";
        } else {
            for (CtbExportGoodsEntity exportGoods : entity.getExportGoodsList()) {
                if (exportGoods.getPartNoEntity().getContractCount().compareTo(BigDecimal.ZERO) <= 0 || exportGoods.getPartNoEntity().getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
                    return "出口商品数量和价格必须大于0";
                }
            }
        }
        // 进出口岸
        // if (entity.getFromCountry() == null) return "启运国不能为空";
        // if (entity.getFromPort() == null) return "启运港不能为空";
        // if (entity.getToCountry() == null) return "贸易国";
        // if (entity.getToPort() == null) return "入境口岸不能为空";
        // 船务公司
        // if (entity.getVoyageList() == null || entity.getVoyageList().isEmpty()) {
        //     return "至少有一条航次信息";
        // } else {
        //     CtbExportVoyageEntity voyageEntity = entity.getVoyageList().get(entity.getVoyageList().size() - 1);
        //     if (voyageEntity == null) return "最近一次修改的航次信息为空";
        //     else if (voyageEntity.getShipCompanyEntity() == null) return "船务公司不能为空";
        //     else if (StringUtils.isEmpty(voyageEntity.getTransportName())) return "运输工具名称不能为空";
        //     else if (StringUtils.isEmpty(voyageEntity.getVoyageNo())) return "航次号不能为空";
        //     else if (StringUtils.isEmpty(voyageEntity.getBLNo())) return "提单号不能为空";
        // }
        // 费用
        // if (StringUtils.isEmpty(entity.getInvoiceCode())) return "出口发票编号不能为空";
        // if (entity.getInvoiceMoney() == null || entity.getInvoiceMoney().compareTo(BigDecimal.ZERO) <= 0)
        //    return "出口发票金额必须大于零";

        // 附件
        // if (entity.getImgExportLadingBillList() == null || entity.getImgExportLadingBillList().isEmpty())
        //    return "提单附件不能为空";
        // if (entity.getImgExportPackingListList() == null || entity.getImgExportPackingListList().isEmpty())
        //     return "箱单附件不能为空";
        // if (entity.getImgExportInvoiceList() == null || entity.getImgExportInvoiceList().isEmpty())
        //     return "出口发票附件不能为空";

        return null;
    }
}