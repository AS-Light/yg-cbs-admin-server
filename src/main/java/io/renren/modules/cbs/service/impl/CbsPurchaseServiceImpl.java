package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import io.renren.common.enumeration.DocumentControlEnum;
import io.renren.common.enumeration.MoneyEnum;
import io.renren.common.utils.CommonFunction;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.cbs.dao.*;
import io.renren.modules.cbs.entity.*;
import io.renren.modules.cbs.service.CbsPurchaseService;
import io.renren.modules.cbs.service.CbsStoreGoodsInService;
import io.renren.modules.cbs.vo.UnifiedUploadingVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;


@Service("cbsPurchaseService")
public class CbsPurchaseServiceImpl extends ServiceImpl<CbsPurchaseDao, CbsPurchaseEntity> implements CbsPurchaseService {

    private CbsPurchaseDao purchaseDao;
    private CbsContractDao contractDao;
    private CbsPurchaseStatusStreamDao purchaseStatusStreamDao;
    private CbsPurchaseGoodsDao purchaseGoodsDao;
    private CbsGoodsPartNoDao goodsPartNoDao;
    private CbsImgPurchaseInvoiceDao imgPurchaseInvoiceDao;
    private CbsImgPurchaseDeliveryOrderDao imgPurchaseDeliveryOrderDao;
    private CbsImgPurchaseReceiptDao imgPurchaseReceiptDao;
    private CbsMoneyOutDao moneyOutDao;

    private CbsStoreGoodsInService storeGoodsInService;
    private CommonFunction commonFunction;

    @Autowired
    public CbsPurchaseServiceImpl(CbsPurchaseDao purchaseDao,
                                  CbsContractDao contractDao,
                                  CbsPurchaseStatusStreamDao purchaseStatusStreamDao,
                                  CbsPurchaseGoodsDao purchaseGoodsDao,
                                  CbsGoodsPartNoDao goodsPartNoDao,
                                  CbsImgPurchaseInvoiceDao imgPurchaseInvoiceDao,
                                  CbsImgPurchaseDeliveryOrderDao imgPurchaseDeliveryOrderDao,
                                  CbsImgPurchaseReceiptDao imgPurchaseReceiptDao,
                                  CbsMoneyOutDao moneyOutDao,
                                  CommonFunction commonFunction) {
        this.purchaseDao = purchaseDao;
        this.contractDao = contractDao;
        this.purchaseStatusStreamDao = purchaseStatusStreamDao;
        this.purchaseGoodsDao = purchaseGoodsDao;
        this.goodsPartNoDao = goodsPartNoDao;
        this.imgPurchaseInvoiceDao = imgPurchaseInvoiceDao;
        this.imgPurchaseDeliveryOrderDao = imgPurchaseDeliveryOrderDao;
        this.imgPurchaseReceiptDao = imgPurchaseReceiptDao;
        this.moneyOutDao = moneyOutDao;
        this.commonFunction = commonFunction;
    }

    @Autowired
    public void setStoreGoodsInService(CbsStoreGoodsInService storeGoodsInService) {
        this.storeGoodsInService = storeGoodsInService;
    }

    @Override
    public PageUtils queryIndex(CbsPurchaseEntity entity) {
        IPage<CbsPurchaseEntity> page = purchaseDao.queryIndex(new QueryPage<CbsPurchaseEntity>().getPage(entity), entity);
        return new PageUtils(page);
    }

    @Override
    public List<CbsPurchaseEntity> queryForStoreIn() {
        return purchaseDao.queryForStoreIn();
    }

    @Override
    public CbsPurchaseEntity detail(Long id) {
        return purchaseDao.detail(id);
    }

    @Override
    @Transactional
    public Long saveReturnId(CbsPurchaseEntity entity) {
        // 1???????????????????????????????????????????????????
        // 2????????????????????????????????????
        // ??? ???????????????????????????????????????????????????1???3????????????????????????????????????????????????????????????
        // ??? ???????????????????????????????????????????????????????????????2???????????????????????????????????????????????????????????????????????????????????????
        // ??? ????????????????????????????????????????????????>3???????????????????????????????????????????????? >= ??????????????????????????????????????????????????????????????????????????????
        CbsContractEntity contract = contractDao.simpleDetailWithGoods(entity.getFkContractId());
        List<CbsPurchaseEntity> contractPurchaseList = purchaseDao.queryByContractIdWithGoods(entity.getFkContractId());
        if (contractPurchaseList == null || contractPurchaseList.isEmpty()) {
            return createPurchase(entity);
        } else {
            for (CbsPurchaseEntity contractPurchase : contractPurchaseList) {
                if (Arrays.asList(1, 3).contains(contractPurchase.getStatus())) {
                    return contractPurchase.getId();
                } else if (contractPurchase.getStatus() == 3) {
                    throw new RuntimeException("?????????????????????????????????????????????????????????????????????");
                }
            }
            boolean allGoodsCountComplete = true;
            for (CbsContractGoodsEntity contractGoods : contract.getContractGoodsList()) {
                CbsGoodsPartNoEntity goodsPartNoEntity = contractGoods.getPartNoEntity();
                if (goodsPartNoEntity.getContractCount().compareTo(goodsPartNoEntity.getImportCount()) > 0) {
                    allGoodsCountComplete = false;
                }
            }
            if (!allGoodsCountComplete || (entity.getForceToCreate() != null && entity.getForceToCreate())) {
                return createPurchase(entity);
            } else {
                Map<String, Object> errMsgMap = new HashMap<>();
                errMsgMap.put("code", 300);
                errMsgMap.put("msg", "??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
                throw new RuntimeException(new Gson().toJson(errMsgMap));
            }
        }
    }

    /**
     * ?????????????????????????????????
     */
    private Long createPurchase(CbsPurchaseEntity entity) {
        purchaseDao.insert(entity);
        // ???????????????
        CbsContractEntity contractEntity = contractDao.detail(entity.getFkContractId());
        for (CbsContractGoodsEntity contractGoodsEntity : contractEntity.getContractGoodsList()) {
            CbsPurchaseGoodsEntity purchaseGoodsEntity = new CbsPurchaseGoodsEntity();
            purchaseGoodsEntity.setFkPurchaseId(entity.getId());
            purchaseGoodsEntity.setCount(BigDecimal.ZERO);
            purchaseGoodsEntity.setCreateTime(new Date());
            purchaseGoodsEntity.setGoodsPartNo(contractGoodsEntity.getId());
            purchaseGoodsEntity.setUpdateTime(new Date());
            purchaseGoodsDao.insert(purchaseGoodsEntity);
        }
        // ??????????????????????????????????????????
        CbsPurchaseStatusStreamEntity statusStreamEntity = new CbsPurchaseStatusStreamEntity();
        statusStreamEntity.setFkPurchaseId(entity.getId());
        statusStreamEntity.setRemark("??????");
        statusStreamEntity.setLastStatus(0);
        statusStreamEntity.setStatus(1);
        statusStreamEntity.setOperator(entity.getOperator());
        purchaseStatusStreamDao.insert(statusStreamEntity);
        return entity.getId();
    }

    @Override
    @Transactional
    public Integer updateAllById(CbsPurchaseEntity entity) {
        purchaseDao.updateById(entity);
        // ????????????
        if (entity.getPurchaseGoodsList() != null) {
            List<CbsPurchaseGoodsEntity> oldGoodsList = purchaseGoodsDao.listByPurchaseId(entity.getId());
            for (CbsPurchaseGoodsEntity oldGoods : oldGoodsList) {
                // ???????????????
                purchaseGoodsDao.deleteById(oldGoods);
            }
            List<CbsPurchaseGoodsEntity> newGoodsList = entity.getPurchaseGoodsList();
            for (CbsPurchaseGoodsEntity newGoods : newGoodsList) {
                // ???????????????
                purchaseGoodsDao.insert(newGoods);
            }
        }
        // ??????????????????
        if (entity.getImgPurchaseInvoiceList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CbsImgPurchaseInvoiceEntity imgImportInvoiceEntity : entity.getImgPurchaseInvoiceList()) {
                imgList.add(imgImportInvoiceEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_PURCHASE_INVOICE.getCode())
                    .contractId(entity.getFkContractId())
                    .field("fk_purchase_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        // ?????????????????????
        if (entity.getImgPurchaseDeliveryOrderList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CbsImgPurchaseDeliveryOrderEntity imgPurchaseDeliveryOrderEntity : entity.getImgPurchaseDeliveryOrderList()) {
                imgList.add(imgPurchaseDeliveryOrderEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_PURCHASE_DELIVERY_ORDER.getCode())
                    .contractId(entity.getFkContractId())
                    .field("fk_purchase_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        // ?????????????????????
        if (entity.getImgPurchaseReceiptList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CbsImgPurchaseReceiptEntity imgPurchaseReceiptEntity : entity.getImgPurchaseReceiptList()) {
                imgList.add(imgPurchaseReceiptEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_PURCHASE_RECEIPT.getCode())
                    .contractId(entity.getFkContractId())
                    .field("fk_purchase_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        return 1;
    }

    /**
     * ??????
     */
    @Override
    @Transactional
    public void submit(CbsPurchaseStatusStreamEntity statusStreamEntity) throws RuntimeException {
        CbsPurchaseEntity entity = purchaseDao.detail(statusStreamEntity.getFkPurchaseId());
        String errMsg = checkPurchaseComplete(entity);
        if (errMsg != null) throw new RuntimeException(errMsg);

        CbsPurchaseStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkPurchaseId());
        if (lastStatusStreamEntity != null) {
            if (lastStatusStreamEntity.getStatus() == 1 || lastStatusStreamEntity.getStatus() == 3) {
                entity.setStatus(2);
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());
                statusStreamEntity.setStatus(2);

                purchaseDao.updateById(entity);
                purchaseStatusStreamDao.insert(statusStreamEntity);
            } else {
                throw new RuntimeException("??????????????????????????????????????????????????????????????????????????????");
            }
        } else {
            throw new RuntimeException("?????????????????????????????????,??????????????????");
        }
    }

    /**
     * @Description: ??????
     * ????????????????????????1???????????????3????????????
     */
    @Override
    @Transactional
    public Integer submitBack(CbsPurchaseStatusStreamEntity statusStreamEntity) {
        CbsPurchaseEntity entity = purchaseDao.selectById(statusStreamEntity.getFkPurchaseId());
        CbsPurchaseStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkPurchaseId());
        if (lastStatusStreamEntity != null) {
            if (lastStatusStreamEntity.getStatus() == 2) {
                entity.setStatus(lastStatusStreamEntity.getLastStatus());
                statusStreamEntity.setStatus(lastStatusStreamEntity.getLastStatus());
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());

                purchaseDao.updateById(entity);
                purchaseStatusStreamDao.insert(statusStreamEntity);
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
    public HashMap<String, Object> check(CbsPurchaseStatusStreamEntity statusStreamEntity, Long operator) {
        HashMap<String, Object> resultMap = new HashMap<>();
        CbsPurchaseEntity entity = purchaseDao.detail(statusStreamEntity.getFkPurchaseId());
        CbsPurchaseStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkPurchaseId());
        if (lastStatusStreamEntity != null) {
            if (entity.getStatus() == 2) {
                entity.setStatus(statusStreamEntity.getStatus());
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());

                purchaseDao.updateById(entity);
                purchaseStatusStreamDao.insert(statusStreamEntity);

                // ?????????????????????????????????????????????????????????????????????
                if (statusStreamEntity.getStatus() == 4) {
                    for (CbsPurchaseGoodsEntity purchaseGoodsEntity : entity.getPurchaseGoodsList()) {
                        // ????????????????????????
                        CbsGoodsPartNoEntity partNoEntity = purchaseGoodsEntity.getPartNoEntity();
                        partNoEntity.setImportCount(partNoEntity.getImportCount().add(purchaseGoodsEntity.getCount()));
                        goodsPartNoDao.update(partNoEntity, new UpdateWrapper<CbsGoodsPartNoEntity>().eq("goods_part_no", partNoEntity.getGoodsPartNo()));
                    }
                    insertMoneyIO(entity, operator);
                    // ??????????????????????????????????????????????????????????????????????????????????????????????????????
                    if (entity.getSynStoreIn()) {
                        // ??????
                        CbsStoreGoodsInEntity storeGoodsInEntity = new CbsStoreGoodsInEntity();
                        storeGoodsInEntity.setFkPurchaseId(entity.getId());
                        storeGoodsInEntity.setType(3);
                        resultMap = storeGoodsInService.saveReturnId(storeGoodsInEntity);
                        if (resultMap.containsKey("code") && resultMap.containsKey("msg")) {
                            return resultMap;
                        }
                        if (!resultMap.containsKey("id")) {
                            resultMap = new HashMap<>();
                            resultMap.put("code", 500);
                            resultMap.put("msg", "????????????????????????????????????????????????????????????????????????????????????????????????");
                        }
                        Long storeGoodsInId = (long) resultMap.get("id");
                        // ??????
                        storeGoodsInEntity = storeGoodsInService.detail(storeGoodsInId);
                        storeGoodsInEntity.setStartAddress(entity.getStartAddress());
                        storeGoodsInEntity.setStartContact(entity.getStartContact());
                        storeGoodsInEntity.setStartPhone(entity.getStartPhone());
                        storeGoodsInEntity.setEndAddress(entity.getEndAddress());
                        storeGoodsInEntity.setEndContact(entity.getEndContact());
                        storeGoodsInEntity.setEndPhone(entity.getEndPhone());
                        storeGoodsInEntity.setFkTransitCompanyId(entity.getFkTransitCompanyId());
                        storeGoodsInEntity.setWagonNumber(entity.getWagonNumber());
                        storeGoodsInEntity.setTransFeeMoney(BigDecimal.ZERO);
                        storeGoodsInEntity.setFkStoreId(entity.getFkStoreId());
                        storeGoodsInEntity.setDepartTime(entity.getDepartTime());
                        storeGoodsInEntity.setArrivalTime(entity.getArrivalTime());
                        storeGoodsInEntity.setStoreGoodsInItemEntityList(new ArrayList<>());
                        storeGoodsInEntity.setImgStoreGoodsInReceiptEntityList(new ArrayList<>());
                        storeGoodsInEntity.setOperator(operator);
                        for (CbsPurchaseGoodsEntity purchaseGoodsEntity : entity.getPurchaseGoodsList()) {
                            CbsStoreGoodsInItemEntity storeGoodsInItemEntity = new CbsStoreGoodsInItemEntity();
                            storeGoodsInItemEntity.setFkStoreGoodsInId(storeGoodsInId);
                            storeGoodsInItemEntity.setCreateTime(new Date());
                            storeGoodsInItemEntity.setGoodsInCount(purchaseGoodsEntity.getCount());
                            storeGoodsInItemEntity.setGoodsPartNo(purchaseGoodsEntity.getGoodsPartNo());
                            storeGoodsInEntity.getStoreGoodsInItemEntityList().add(storeGoodsInItemEntity);
                        }
                        for (CbsImgPurchaseReceiptEntity imgPurchaseReceiptEntity : entity.getImgPurchaseReceiptList()) {
                            CbsImgStoreGoodsInReceiptEntity imgStoreGoodsInReceiptEntity = new CbsImgStoreGoodsInReceiptEntity();
                            imgStoreGoodsInReceiptEntity.setFkStoreGoodsInId(storeGoodsInId);
                            imgStoreGoodsInReceiptEntity.setCreateTime(new Date());
                            imgStoreGoodsInReceiptEntity.setImgUrl(imgPurchaseReceiptEntity.getImgUrl());
                            storeGoodsInEntity.getImgStoreGoodsInReceiptEntityList().add(imgStoreGoodsInReceiptEntity);
                        }
                        storeGoodsInService.updateAllById(storeGoodsInEntity);
                        // ??????
                        CbsStoreGoodsInStatusStreamEntity storeGoodsInStatusStreamEntity = new CbsStoreGoodsInStatusStreamEntity();
                        storeGoodsInStatusStreamEntity.setFkStoreGoodsInId(storeGoodsInId);
                        storeGoodsInStatusStreamEntity.setCreateTime(new Date());
                        storeGoodsInStatusStreamEntity.setLastStatus(1);
                        storeGoodsInStatusStreamEntity.setStatus(2);
                        storeGoodsInStatusStreamEntity.setOperator(operator);
                        storeGoodsInStatusStreamEntity.setRemark("????????????????????????????????????");
                        storeGoodsInService.submit(storeGoodsInStatusStreamEntity);
                        // ??????
                        storeGoodsInStatusStreamEntity.setLastStatus(2);
                        storeGoodsInStatusStreamEntity.setStatus(4);
                        storeGoodsInStatusStreamEntity.setRemark("????????????????????????????????????");
                        storeGoodsInService.check(storeGoodsInStatusStreamEntity);
                    }
                }
                resultMap.put("code", 200);
                return resultMap;
            } else {
                resultMap.put("code", 500);
                resultMap.put("msg", "??????????????????????????????????????????????????????");
                return resultMap;
            }
        } else {
            resultMap.put("code", 500);
            resultMap.put("msg", "?????????????????????????????????,?????????????????????");
            return resultMap;
        }
    }

    @Override
    @Transactional
    public void deleteByIds(Long[] ids) {
        List<Long> goodsIds = purchaseGoodsDao.listIdsByPurchaseIds(ids);
        if (goodsIds != null && !goodsIds.isEmpty()) {
            purchaseGoodsDao.deleteBatchIds(goodsIds);
        }
        removeByIds(Arrays.asList(ids));
    }

    private void insertMoneyIO(CbsPurchaseEntity entity, Long operator) {
        // ???????????? - ??????
        CbsMoneyOutEntity moneyOutEntity = new CbsMoneyOutEntity();
        moneyOutEntity.setTitle("?????????????????? - " + entity.getId() + " ??????????????????");
        moneyOutEntity.setFkContractId(entity.getFkContractId());
        moneyOutEntity.setCurrencyCode("142");
        moneyOutEntity.setType(MoneyEnum.MONEY_TYPE_1001.getCode());
        moneyOutEntity.setStatus(1);
        moneyOutEntity.setOperator(operator);
        BigDecimal money = BigDecimal.ZERO;
        for (CbsPurchaseGoodsEntity purchaseGoodsEntity : entity.getPurchaseGoodsList()) {
            money = money.add(purchaseGoodsEntity.getCount().multiply(purchaseGoodsEntity.getPartNoEntity().getUnitPrice()));
        }
        moneyOutEntity.setMoney(money);
        moneyOutDao.insert(moneyOutEntity);
        // ?????????
        moneyOutEntity = new CbsMoneyOutEntity();
        moneyOutEntity.setTitle("?????????????????? - " + entity.getId() + " ????????????");
        moneyOutEntity.setFkContractId(entity.getFkContractId());
        moneyOutEntity.setCurrencyCode("142");
        moneyOutEntity.setType(MoneyEnum.MONEY_TYPE_21.getCode());
        moneyOutEntity.setStatus(1);
        moneyOutEntity.setOperator(operator);
        money = entity.getTransFeeMoney();
        moneyOutEntity.setMoney(money);
        moneyOutDao.insert(moneyOutEntity);
    }

    /**
     * ?????????????????????
     */
    private CbsPurchaseStatusStreamEntity getLastStatusStreamEntity(Long purchaseId) {
        List<CbsPurchaseStatusStreamEntity> statusStreamEntityList = purchaseStatusStreamDao.selectList(new QueryWrapper<CbsPurchaseStatusStreamEntity>().eq("fk_purchase_id", purchaseId).orderByAsc("id"));
        if (statusStreamEntityList != null && !statusStreamEntityList.isEmpty()) {
            return statusStreamEntityList.get(statusStreamEntityList.size() - 1);
        } else {
            return null;
        }
    }

    /**
     * ??????????????????????????????
     */
    private String checkPurchaseComplete(CbsPurchaseEntity entity) {
        if (entity.getPurchaseGoodsList().size() <= 0) {
            return "??????????????????????????????";
        } else {
            for (CbsPurchaseGoodsEntity purchaseGoods : entity.getPurchaseGoodsList()) {
                if (purchaseGoods.getPartNoEntity().getContractCount().compareTo(BigDecimal.ZERO) <= 0 || purchaseGoods.getPartNoEntity().getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
                    return "?????????????????????????????????0";
                }
            }
        }
        // ????????????
        if (entity.getStartAddress() == null || entity.getStartContact() == null || entity.getStartPhone() == null) {
            return "??????????????????????????????????????????????????????";
        }
        if (entity.getEndAddress() == null || entity.getEndContact() == null || entity.getEndPhone() == null) {
            return "??????????????????????????????????????????????????????";
        }
        // ??????
        if (StringUtils.isEmpty(entity.getInvoiceCode())) {
            return "????????????????????????";
        }
        if (entity.getInvoiceMoney() == null || entity.getInvoiceMoney().compareTo(BigDecimal.ZERO) <= 0) {
            return "???????????????????????????";
        }
        // ??????
        if (entity.getImgPurchaseInvoiceList() == null || entity.getImgPurchaseInvoiceList().isEmpty()) {
            return "????????????????????????";
        }
        if (entity.getImgPurchaseDeliveryOrderList() == null || entity.getImgPurchaseDeliveryOrderList().isEmpty()) {
            return "?????????????????????";
        }
        // ?????????????????????????????????????????????????????????
        if (entity.getSynStoreIn()) {
            if (entity.getFkStoreId() == null) {
                return "?????????????????????????????????????????????";
            }
            if (entity.getImgPurchaseReceiptList() == null || entity.getImgPurchaseReceiptList().isEmpty()) {
                return "?????????????????????????????????????????????";
            }
        }
        return null;
    }
}