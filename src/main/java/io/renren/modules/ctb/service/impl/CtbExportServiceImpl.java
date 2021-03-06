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
            resultMap.put("msg", "?????????????????????");
        }
        exportDao.insert(entity);
        for (CtbExportMemberEntity memberEntity : entity.getMemberEntityList()) {
            memberEntity.setFkExportId(entity.getId());
            memberDao.insert(memberEntity);
        }
        // ????????????????????????????????????
        CtbExportStatusStreamEntity statusStreamEntity = new CtbExportStatusStreamEntity();
        statusStreamEntity.setFkExportId(entity.getId());
        statusStreamEntity.setRemark("??????");
        statusStreamEntity.setLastStatus(0);
        statusStreamEntity.setStatus(1);
        statusStreamEntity.setOperator(entity.getOperator());
        exportStatusStreamDao.insert(statusStreamEntity);
        // ??????????????????
        distributionManager(entity.getId(), entity.getOperator(), entity.getOperator());
        resultMap.put("code", 200);
        resultMap.put("id", entity.getId());
        return resultMap;
    }

    @Override
    @Transactional
    public Integer updateAllById(CtbExportEntity entity) {
        exportDao.updateById(entity);
        // ?????????????????????????????????
        if (entity.getBindEntity() == null) {
            // ????????????
            List<CtbExportGoodsEntity> oldGoodsList = exportGoodsDao.listByExportId(entity.getId());
            for (CtbExportGoodsEntity oldGoods : oldGoodsList) {
                // ???????????????
                exportGoodsDao.deleteById(oldGoods);
                // ??????????????????
                if (oldGoods.getPartNoEntity() != null) {
                    orderProcessingTradeGoodsDao.deleteById(oldGoods.getGoodsPartNo());
                    goodsPartNoDao.deleteById(oldGoods.getPartNoEntity().getId());
                }
            }
            List<CtbExportGoodsEntity> newGoodsList = entity.getExportGoodsList();
            for (CtbExportGoodsEntity newGoods : newGoodsList) {
                // ????????????????????????
                CtbOrderProcessingTradeGoodsEntity orderProcessingTradeGoodsEntity = CtbOrderProcessingTradeGoodsEntity.builder().type(2).build();
                orderProcessingTradeGoodsDao.insert(orderProcessingTradeGoodsEntity);
                // ??????????????????
                CtbGoodsPartNoEntity goodsPartNoEntity = newGoods.getPartNoEntity();
                goodsPartNoEntity.setGoodsPartNo(orderProcessingTradeGoodsEntity.getId());
                goodsPartNoDao.insert(goodsPartNoEntity);
                // ?????????????????????
                newGoods.setGoodsPartNo(orderProcessingTradeGoodsEntity.getId());
                exportGoodsDao.insert(newGoods);
            }
        }
        // ??????????????????
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
     * ??????
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
                throw new RuntimeException("????????????????????????????????????????????????????????????????????????");
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
     * @Description: ??????
     * ????????????????????????1???????????????3????????????
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
     * @Description: ??????
     * ????????????????????????2?????????
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
                    // ????????????????????????
                    entity.setCustomsFeeMoney(sumPriceItem(entity));
                    exportDao.updateById(entity);
                    // ??????????????????????????????????????????????????????????????????????????????
                    if (entity.getBindEntity() == null) {
                        processPriceItemList(entity, statusStreamEntity.getOperator());
                    }
                }
            } else {
                throw new RuntimeException("?????????????????????????????????????????????");
            }
        } else {
            throw new RuntimeException("???????????????????????????,??????????????????");
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
        // ??????export???
        CtbExportEntity exportEntity = exportDao.selectById(exportId);
        exportEntity.setOperator(operator);
        exportEntity.setManager(nowManager);
        exportDao.updateById(exportEntity);
        // ?????????????????????
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
     * ????????????????????????
     */
    private String checkExportComplete(CtbExportEntity entity) {
        if (entity.getExportGoodsList().size() <= 0) {
            return "?????????????????????????????????";
        } else {
            for (CtbExportGoodsEntity exportGoods : entity.getExportGoodsList()) {
                if (exportGoods.getPartNoEntity().getContractCount().compareTo(BigDecimal.ZERO) <= 0 || exportGoods.getPartNoEntity().getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
                    return "???????????????????????????????????????0";
                }
            }
        }
        // ????????????
        // if (entity.getFromCountry() == null) return "?????????????????????";
        // if (entity.getFromPort() == null) return "?????????????????????";
        // if (entity.getToCountry() == null) return "?????????";
        // if (entity.getToPort() == null) return "????????????????????????";
        // ????????????
        // if (entity.getVoyageList() == null || entity.getVoyageList().isEmpty()) {
        //     return "???????????????????????????";
        // } else {
        //     CtbExportVoyageEntity voyageEntity = entity.getVoyageList().get(entity.getVoyageList().size() - 1);
        //     if (voyageEntity == null) return "???????????????????????????????????????";
        //     else if (voyageEntity.getShipCompanyEntity() == null) return "????????????????????????";
        //     else if (StringUtils.isEmpty(voyageEntity.getTransportName())) return "??????????????????????????????";
        //     else if (StringUtils.isEmpty(voyageEntity.getVoyageNo())) return "?????????????????????";
        //     else if (StringUtils.isEmpty(voyageEntity.getBLNo())) return "?????????????????????";
        // }
        // ??????
        // if (StringUtils.isEmpty(entity.getInvoiceCode())) return "??????????????????????????????";
        // if (entity.getInvoiceMoney() == null || entity.getInvoiceMoney().compareTo(BigDecimal.ZERO) <= 0)
        //    return "?????????????????????????????????";

        // ??????
        // if (entity.getImgExportLadingBillList() == null || entity.getImgExportLadingBillList().isEmpty())
        //    return "????????????????????????";
        // if (entity.getImgExportPackingListList() == null || entity.getImgExportPackingListList().isEmpty())
        //     return "????????????????????????";
        // if (entity.getImgExportInvoiceList() == null || entity.getImgExportInvoiceList().isEmpty())
        //     return "??????????????????????????????";

        return null;
    }
}