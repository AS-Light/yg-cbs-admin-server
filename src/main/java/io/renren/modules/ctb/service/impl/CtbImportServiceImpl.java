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
import io.renren.modules.ctb.service.CtbImportService;
import io.renren.modules.ctb.vo.PriceItemVo;
import io.renren.modules.ctb.vo.UnifiedUploadingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;


@Service("ctbImportService")
public class CtbImportServiceImpl extends ServiceImpl<CtbImportDao, CtbImportEntity> implements CtbImportService {

    private CtbImportDao importDao;
    private CtbImportMemberDao memberDao;
    private CtbImportStatusStreamDao importStatusStreamDao;
    private CtbImportManagerChangeStreamDao importManagerChangeStreamDao;
    private CtbImportGoodsDao importGoodsDao;
    private CtbImportVoyageDao importVoyageDao;
    private CtbOrderProcessingTradeGoodsDao orderProcessingTradeGoodsDao;
    private CtbGoodsPartNoDao goodsPartNoDao;
    private CtbMoneyInDao moneyInDao;
    private CtbMoneyOutDao moneyOutDao;
    private CommonFunction commonFunction;

    @Autowired
    public CtbImportServiceImpl(CtbImportDao importDao,
                                CtbImportMemberDao memberDao,
                                CtbImportStatusStreamDao importStatusStreamDao,
                                CtbImportManagerChangeStreamDao importManagerChangeStreamDao,
                                CtbImportGoodsDao importGoodsDao,
                                CtbImportVoyageDao importVoyageDao,
                                CtbOrderProcessingTradeGoodsDao orderProcessingTradeGoodsDao,
                                CtbGoodsPartNoDao goodsPartNoDao,
                                CtbMoneyInDao moneyInDao,
                                CtbMoneyOutDao moneyOutDao,
                                CommonFunction commonFunction) {
        this.importDao = importDao;
        this.memberDao = memberDao;
        this.importStatusStreamDao = importStatusStreamDao;
        this.importManagerChangeStreamDao = importManagerChangeStreamDao;
        this.importGoodsDao = importGoodsDao;
        this.importVoyageDao = importVoyageDao;
        this.orderProcessingTradeGoodsDao = orderProcessingTradeGoodsDao;
        this.goodsPartNoDao = goodsPartNoDao;
        this.moneyInDao = moneyInDao;
        this.moneyOutDao = moneyOutDao;
        this.commonFunction = commonFunction;
    }

    @Override
    public PageUtils queryIndex(CtbImportEntity entity) {
        IPage<CtbImportEntity> page = importDao.queryIndex(new QueryPage<CtbImportEntity>().getPage(entity), entity);
        return new PageUtils(page);
    }

    @Override
    public CtbImportEntity detail(Long id) {
        return importDao.detail(id);
    }

    @Override
    @Transactional
    public HashMap<String, Object> saveReturnId(CtbImportEntity entity) {
        HashMap<String, Object> resultMap = new HashMap<>();
        if (entity.getMemberEntityList() == null || entity.getMemberEntityList().isEmpty() || entity.getMemberEntityList().size() < 2) {
            resultMap.put("code", 500);
            resultMap.put("msg", "请选择交易双方");
        }
        importDao.insert(entity);
        for (CtbImportMemberEntity memberEntity : entity.getMemberEntityList()) {
            memberEntity.setFkImportId(entity.getId());
            memberDao.insert(memberEntity);
        }
        // 为出口单创建第一个状态流
        CtbImportStatusStreamEntity statusStreamEntity = new CtbImportStatusStreamEntity();
        statusStreamEntity.setFkImportId(entity.getId());
        statusStreamEntity.setRemark("创建");
        statusStreamEntity.setLastStatus(0);
        statusStreamEntity.setStatus(1);
        statusStreamEntity.setOperator(entity.getOperator());
        importStatusStreamDao.insert(statusStreamEntity);
        // 初始化负责人
        distributionManager(entity.getId(), entity.getOperator(), entity.getOperator());
        resultMap.put("code", 200);
        resultMap.put("id", entity.getId());
        return resultMap;
    }

    @Override
    @Transactional
    public Integer updateAllById(CtbImportEntity entity) {
        importDao.updateById(entity);
        // 非同步数据才可更新商品
        if (entity.getBindEntity() == null) {
            // 更新商品
            List<CtbImportGoodsEntity> oldGoodsList = importGoodsDao.listByImportId(entity.getId());
            for (CtbImportGoodsEntity oldGoods : oldGoodsList) {
                // 删除旧商品
                importGoodsDao.deleteById(oldGoods);
                // 删除相应料号
                if (oldGoods.getPartNoEntity() != null) {
                    orderProcessingTradeGoodsDao.deleteById(oldGoods.getGoodsPartNo());
                    goodsPartNoDao.deleteById(oldGoods.getPartNoEntity().getId());
                }
            }
            List<CtbImportGoodsEntity> newGoodsList = entity.getImportGoodsList();
            for (CtbImportGoodsEntity newGoods : newGoodsList) {
                // 创建相应合同商品
                CtbOrderProcessingTradeGoodsEntity orderProcessingTradeGoodsEntity = CtbOrderProcessingTradeGoodsEntity.builder().type(1).build();
                orderProcessingTradeGoodsDao.insert(orderProcessingTradeGoodsEntity);
                // 创建相应料号
                CtbGoodsPartNoEntity goodsPartNoEntity = newGoods.getPartNoEntity();
                goodsPartNoEntity.setGoodsPartNo(orderProcessingTradeGoodsEntity.getId());
                goodsPartNoDao.insert(goodsPartNoEntity);
                // 创建新进口商品
                newGoods.setGoodsPartNo(orderProcessingTradeGoodsEntity.getId());
                importGoodsDao.insert(newGoods);
            }
        }
        // 更新进出国和进出口岸
        if (entity.getFromCountry() != null) entity.setFromCountryCode(entity.getFromCountry().getCode());
        if (entity.getFromPort() != null) entity.setFromPortCode(entity.getFromPort().getPortCode());
        if (entity.getToCountry() != null) entity.setToCountryCode(entity.getToCountry().getCode());
        if (entity.getToPort() != null) entity.setToPortCode(entity.getToPort().getDeclPort());
        // 更新单内航次
        if (entity.getVoyageList() != null && !entity.getVoyageList().isEmpty()) {
            CtbImportVoyageEntity voyageEntity = entity.getVoyageList().get(0);
            voyageEntity.setCreateTime(new Date());
            voyageEntity.setFkImportId(entity.getId());
            if (voyageEntity.getId() == null) {
                importVoyageDao.insert(voyageEntity);
            } else {
                importVoyageDao.updateById(voyageEntity);
            }
        }
        // 更新进口合同
        if (entity.getImgImportContractList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CtbImgImportContractEntity imgContractShipEntity : entity.getImgImportContractList()) {
                imgList.add(imgContractShipEntity.getImgUrl());
            }
            commonFunction.ctbUnifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(CtbDocumentControlEnum.TYPE_IMPORT_CONTRACT.getCode())
                    .field("fk_import_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        // 更新船务合同
        if (entity.getImgImportContractShipList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CtbImgImportContractShipEntity imgContractShipEntity : entity.getImgImportContractShipList()) {
                imgList.add(imgContractShipEntity.getImgUrl());
            }
            commonFunction.ctbUnifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(CtbDocumentControlEnum.TYPE_IMPORT_CONTRACT_SHIP.getCode())
                    .field("fk_import_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        // 更新箱单
        if (entity.getImgImportPackingListList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CtbImgImportPackingListEntity imgPackingListEntity : entity.getImgImportPackingListList()) {
                imgList.add(imgPackingListEntity.getImgUrl());
            }
            commonFunction.ctbUnifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(CtbDocumentControlEnum.TYPE_IMPORT_PACKING_LIST.getCode())
                    .field("fk_import_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        // 更新发票附件
        if (entity.getImgImportInvoiceList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CtbImgImportInvoiceEntity imgImportInvoiceEntity : entity.getImgImportInvoiceList()) {
                imgList.add(imgImportInvoiceEntity.getImgUrl());
            }
            commonFunction.ctbUnifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(CtbDocumentControlEnum.TYPE_IMPORT_INVOICE.getCode())
                    .field("fk_import_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        if (entity.getImgImportLadingBillList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CtbImgImportLadingBillEntity imgImportLadingBillEntity : entity.getImgImportLadingBillList()) {
                imgList.add(imgImportLadingBillEntity.getImgUrl());
            }
            commonFunction.ctbUnifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(CtbDocumentControlEnum.TYPE_IMPORT_LADING_BILL.getCode())
                    .field("fk_import_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        // 更新提货单附件
        if (entity.getImgImportDeliveryOrderList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CtbImgImportDeliveryOrderEntity imgImportDeliveryOrderEntity : entity.getImgImportDeliveryOrderList()) {
                imgList.add(imgImportDeliveryOrderEntity.getImgUrl());
            }
            commonFunction.ctbUnifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(CtbDocumentControlEnum.TYPE_IMPORT_DELIVERY_ORDER.getCode())
                    .field("fk_import_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        // 更新其他单据附件
        if (entity.getImgImportOthersList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CtbImgImportOthersEntity imgImportOthersEntity : entity.getImgImportOthersList()) {
                imgList.add(imgImportOthersEntity.getImgUrl());
            }
            commonFunction.ctbUnifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(CtbDocumentControlEnum.TYPE_IMPORT_OTHERS.getCode())
                    .field("fk_import_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        // 更新报关单附件
        if (entity.getImgImportEntryBillList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CtbImgImportEntryBillEntity imgImportEntryBillEntity : entity.getImgImportEntryBillList()) {
                imgList.add(imgImportEntryBillEntity.getImgUrl());
            }
            commonFunction.ctbUnifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(CtbDocumentControlEnum.TYPE_IMPORT_ENTRY_BILL.getCode())
                    .field("fk_import_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        // 更新首付款附件
        if (entity.getImgImportPayInAdvanceList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CtbImgImportPayInAdvanceEntity imgImportPayInAdvanceEntity : entity.getImgImportPayInAdvanceList()) {
                imgList.add(imgImportPayInAdvanceEntity.getImgUrl());
            }
            commonFunction.ctbUnifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(CtbDocumentControlEnum.TYPE_IMPORT_PAY_IN_ADVANCE.getCode())
                    .field("fk_import_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        // 更新尾款附件
        if (entity.getImgImportPayTailList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CtbImgImportPayTailEntity imgImportPayTailEntity : entity.getImgImportPayTailList()) {
                imgList.add(imgImportPayTailEntity.getImgUrl());
            }
            commonFunction.ctbUnifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(CtbDocumentControlEnum.TYPE_IMPORT_PAY_TAIL.getCode())
                    .field("fk_import_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        // 更新授权书附件
        if (entity.getImgImportPowerOfAttorneyList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CtbImgImportPowerOfAttorneyEntity imgImportPowerOfAttorneyEntity : entity.getImgImportPowerOfAttorneyList()) {
                imgList.add(imgImportPowerOfAttorneyEntity.getImgUrl());
            }
            commonFunction.ctbUnifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(CtbDocumentControlEnum.TYPE_IMPORT_POWER_OF_ATTORNEY.getCode())

                    .field("fk_import_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        return 1;
    }

    /**
     * 变更航次
     */
    @Override
    @Transactional
    public Object updateVoyageChange(CtbImportVoyageEntity entity) {
        CtbImportEntity importEntity = importDao.selectById(entity.getFkImportId());
        importEntity.setPassPortCode(entity.getPassPortCode());
        importDao.updateById(importEntity);
        if (entity.getId() == null) {
            entity.setCreateTime(new Date());
            return importVoyageDao.insert(entity);
        } else {
            return importVoyageDao.updateById(entity);
        }
    }

    /**
     * 提审
     */
    @Override
    @Transactional
    public void submit(CtbImportStatusStreamEntity statusStreamEntity) throws RuntimeException {
        CtbImportEntity entity = importDao.detail(statusStreamEntity.getFkImportId());
        String errMsg = checkImportComplete(entity);
        if (errMsg != null) throw new RuntimeException(errMsg);

        CtbImportStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkImportId());
        if (lastStatusStreamEntity != null) {
            if (lastStatusStreamEntity.getStatus() == 1 || lastStatusStreamEntity.getStatus() == 3) {
                entity.setStatus(2);
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());
                statusStreamEntity.setStatus(2);

                importDao.updateById(entity);
                importStatusStreamDao.insert(statusStreamEntity);
            } else {
                throw new RuntimeException("进口单状态不合法，编辑中和不通过状态才能提交审核");
            }
        } else {
            entity.setStatus(2);
            statusStreamEntity.setLastStatus(-1);
            statusStreamEntity.setStatus(2);

            importDao.updateById(entity);
            importStatusStreamDao.insert(statusStreamEntity);
        }
    }

    /**
     * @Description: 提审
     * 可以提审的状态：1、编辑中，3、不通过
     */
    @Override
    @Transactional
    public Integer submitBack(CtbImportStatusStreamEntity statusStreamEntity) {
        CtbImportEntity entity = importDao.selectById(statusStreamEntity.getFkImportId());
        CtbImportStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkImportId());
        if (lastStatusStreamEntity != null) {
            if (lastStatusStreamEntity.getStatus() == 2) {
                entity.setStatus(lastStatusStreamEntity.getLastStatus());
                statusStreamEntity.setStatus(lastStatusStreamEntity.getLastStatus());
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());

                importDao.updateById(entity);
                importStatusStreamDao.insert(statusStreamEntity);
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
    public void check(CtbImportStatusStreamEntity statusStreamEntity) throws RuntimeException {
        CtbImportEntity entity = importDao.detail(statusStreamEntity.getFkImportId());
        CtbImportStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkImportId());
        if (lastStatusStreamEntity != null) {
            if (InOutEnum.STATUS_2.getCode() == entity.getStatus()) {
                entity.setStatus(statusStreamEntity.getStatus());
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());
                importDao.updateById(entity);
                importStatusStreamDao.insert(statusStreamEntity);
                // 如果最终审核结果通过，插入收支流水
                if (InOutEnum.STATUS_4.getCode() == statusStreamEntity.getStatus()) {
                    // 更新报关费用属性
                    entity.setCustomsFeeMoney(sumPriceItem(entity));
                    importDao.updateById(entity);
                    // 如果非同步来的进口单，最终审核结果通过，插入收支流水
                    if (entity.getBindEntity() == null) {
                        processPriceItemList(entity, statusStreamEntity.getOperator());
                    }
                }
            } else {
                throw new RuntimeException("进口单状态不合法，请先提交审核");
            }
        } else {
            throw new RuntimeException("没有找到这个进口单,请联系管理员");
        }
    }

    @Override
    @Transactional
    public void deleteByIds(Long[] ids) {
        List<Long> goodsIds = importGoodsDao.listIdsByImportIds(ids);
        if (goodsIds != null && !goodsIds.isEmpty()) {
            importGoodsDao.deleteBatchIds(goodsIds);
        }
        removeByIds(Arrays.asList(ids));
    }

    @Override
    @Transactional
    public void distributionManager(Long importId, Long nowManager, Long operator) {
        // 更新import表
        CtbImportEntity importEntity = importDao.selectById(importId);
        importEntity.setOperator(operator);
        importEntity.setManager(nowManager);
        importDao.updateById(importEntity);
        // 插入负责人变更
        CtbImportManagerChangeStreamEntity lastManagerChangeStreamEntity = getLastManagerChangeStreamEntity(importId);
        CtbImportManagerChangeStreamEntity managerChangeStreamEntity = CtbImportManagerChangeStreamEntity.builder().
                fkImportId(importId).
                manager(nowManager).
                operator(operator).
                createTime(new Date()).
                build();
        if (lastManagerChangeStreamEntity != null) {
            managerChangeStreamEntity.setLastManager(lastManagerChangeStreamEntity.getManager());
        }
        importManagerChangeStreamDao.insert(managerChangeStreamEntity);
    }

    private void insertMoneyIO(CtbImportEntity entity, Long operator) {
    }

    /**
     * 获取上一个状态
     */
    private CtbImportStatusStreamEntity getLastStatusStreamEntity(Long importId) {
        List<CtbImportStatusStreamEntity> statusStreamEntityList = importStatusStreamDao.selectList(new QueryWrapper<CtbImportStatusStreamEntity>().eq("fk_import_id", importId).orderByAsc("id"));
        if (statusStreamEntityList != null && !statusStreamEntityList.isEmpty()) {
            return statusStreamEntityList.get(statusStreamEntityList.size() - 1);
        } else {
            return null;
        }
    }

    private CtbImportManagerChangeStreamEntity getLastManagerChangeStreamEntity(Long importId) {
        List<CtbImportManagerChangeStreamEntity> managerChangeStreamEntityList = importManagerChangeStreamDao.selectList(new QueryWrapper<CtbImportManagerChangeStreamEntity>().eq("fk_import_id", importId).orderByAsc("id"));
        if (managerChangeStreamEntityList != null && !managerChangeStreamEntityList.isEmpty()) {
            return managerChangeStreamEntityList.get(managerChangeStreamEntityList.size() - 1);
        } else {
            return null;
        }
    }

    private void processPriceItemList(CtbImportEntity entity, Long operator) {
        commonFunction.processCtbPriceItemList(PriceItemVo.builder()
                .tableName("ctb_import_price_item")
                .parameterField("fk_import_id")
                .parameterId(entity.getId())
                .serviceCompanyId(entity.getFkServiceCompanyId())
                .orderType(2)
                .name(entity.getTitle())
                .operator(operator)
                .build());
    }

    private BigDecimal sumPriceItem(CtbImportEntity entity) {
        if (entity.getImportPriceItemEntityList() != null) {
            BigDecimal sum = BigDecimal.ZERO;
            for (CtbImportPriceItemEntity priceItemEntity : entity.getImportPriceItemEntityList()) {
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
     * 判断进口单完整性
     */
    private String checkImportComplete(CtbImportEntity entity) {
        if (entity.getImportGoodsList().size() <= 0) {
            return "进口单必须包含进口商品";
        } else {
            for (CtbImportGoodsEntity importGoods : entity.getImportGoodsList()) {
                if (importGoods.getPartNoEntity().getContractCount().compareTo(BigDecimal.ZERO) <= 0 || importGoods.getPartNoEntity().getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
                    return "进口商品数量和价格必须大于0";
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
        //     CtbImportVoyageEntity voyageEntity = entity.getVoyageList().get(entity.getVoyageList().size() - 1);
        //     if (voyageEntity == null) return "最近一次修改的航次信息为空";
        //     else if (voyageEntity.getShipCompanyEntity() == null) return "船务公司不能为空";
        //     else if (StringUtils.isEmpty(voyageEntity.getTransportName())) return "运输工具名称不能为空";
        //     else if (StringUtils.isEmpty(voyageEntity.getVoyageNo())) return "航次号不能为空";
        //     else if (StringUtils.isEmpty(voyageEntity.getBLNo())) return "提单号不能为空";
        // }
        // 费用
        // if (StringUtils.isEmpty(entity.getInvoiceCode())) return "进口发票编号不能为空";
        // if (entity.getInvoiceMoney() == null || entity.getInvoiceMoney().compareTo(BigDecimal.ZERO) <= 0)
        //     return "进口发票金额必须大于零";

        // 附件
        // if (entity.getImgImportLadingBillList() == null || entity.getImgImportLadingBillList().isEmpty())
        //     return "提单附件不能为空";
        // if (entity.getImgImportPackingListList() == null || entity.getImgImportPackingListList().isEmpty())
        //     return "箱单附件不能为空";
        // if (entity.getImgImportInvoiceList() == null || entity.getImgImportInvoiceList().isEmpty()) return "进口发票附件不能为空";

        return null;
    }
}