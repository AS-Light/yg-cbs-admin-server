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
import io.renren.modules.cbs.service.CbsSellService;
import io.renren.modules.cbs.service.CbsStoreGoodsOutSellService;
import io.renren.modules.cbs.service.CbsStoreGoodsOutService;
import io.renren.modules.cbs.vo.UnifiedUploadingVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;


@Service("cbsSellService")
public class CbsSellServiceImpl extends ServiceImpl<CbsSellDao, CbsSellEntity> implements CbsSellService {

    private CbsSellDao sellDao;
    private CbsContractDao contractDao;
    private CbsSellStatusStreamDao sellStatusStreamDao;
    private CbsSellGoodsDao sellGoodsDao;
    private CbsGoodsPartNoDao goodsPartNoDao;
    private CbsImgSellInvoiceDao imgSellInvoiceDao;
    private CbsImgSellDeliveryOrderDao imgSellDeliveryOrderDao;
    private CbsImgSellReceiptDao imgSellReceiptDao;
    private CbsImgStoreGoodsOutReceiptDao imgStoreGoodsOutReceiptDao;
    private CbsMoneyInDao moneyInDao;
    private CbsMoneyOutDao moneyOutDao;

    private CbsStoreGoodsOutService storeGoodsOutService;
    private CbsStoreGoodsOutSellService storeGoodsOutSellService;
    private CommonFunction commonFunction;

    public CbsSellServiceImpl(CbsSellDao sellDao,
                              CbsContractDao contractDao,
                              CbsSellStatusStreamDao sellStatusStreamDao,
                              CbsSellGoodsDao sellGoodsDao,
                              CbsGoodsPartNoDao goodsPartNoDao,
                              CbsImgSellInvoiceDao imgSellInvoiceDao,
                              CbsImgSellDeliveryOrderDao imgSellDeliveryOrderDao,
                              CbsImgSellReceiptDao imgSellReceiptDao,
                              CbsImgStoreGoodsOutReceiptDao imgStoreGoodsOutReceiptDao,
                              CbsMoneyInDao moneyInDao,
                              CbsMoneyOutDao moneyOutDao,
                              CommonFunction commonFunction) {
        this.sellDao = sellDao;
        this.contractDao = contractDao;
        this.sellStatusStreamDao = sellStatusStreamDao;
        this.sellGoodsDao = sellGoodsDao;
        this.goodsPartNoDao = goodsPartNoDao;
        this.imgSellInvoiceDao = imgSellInvoiceDao;
        this.imgSellDeliveryOrderDao = imgSellDeliveryOrderDao;
        this.imgSellReceiptDao = imgSellReceiptDao;
        this.imgStoreGoodsOutReceiptDao = imgStoreGoodsOutReceiptDao;
        this.moneyInDao = moneyInDao;
        this.moneyOutDao = moneyOutDao;
        this.commonFunction = commonFunction;
    }

    @Autowired
    public void setStoreGoodsOutService(CbsStoreGoodsOutService storeGoodsOutService) {
        this.storeGoodsOutService = storeGoodsOutService;
    }

    @Autowired
    public void setStoreGoodsOutSellService(CbsStoreGoodsOutSellService storeGoodsOutSellService) {
        this.storeGoodsOutSellService = storeGoodsOutSellService;
    }

    @Override
    public PageUtils queryIndex(CbsSellEntity entity) {
        IPage<CbsSellEntity> page = sellDao.queryIndex(new QueryPage<CbsSellEntity>().getPage(entity), entity);
        return new PageUtils(page);
    }

    @Override
    public List<CbsSellEntity> queryForStoreOut() {
        return sellDao.queryForStoreOut();
    }

    @Override
    public CbsSellEntity detail(Long id) {
        return sellDao.detail(id);
    }

    @Override
    public Long saveReturnId(CbsSellEntity entity) {
        // 1???????????????????????????????????????????????????
        // 2????????????????????????????????????
        // ??? ???????????????????????????????????????????????????1???3????????????????????????????????????????????????????????????
        // ??? ???????????????????????????????????????????????????4???12????????????????????????????????????????????????????????????
        // ??? ???????????????????????????????????????????????????????????????2???11???????????????????????????????????????????????????????????????????????????????????????
        // ??? ????????????????????????????????????????????????>13???????????????????????????????????????????????? >= ??????????????????????????????????????????????????????????????????????????????
        CbsContractEntity contract = contractDao.simpleDetailWithGoods(entity.getFkContractId());
        List<CbsSellEntity> contractSellList = sellDao.queryByContractIdWithGoods(entity.getFkContractId());
        if (contractSellList == null || contractSellList.isEmpty()) {
            return createSell(entity);
        } else {
            for (CbsSellEntity contractSell : contractSellList) {
                if (Arrays.asList(1, 3).contains(contractSell.getStatus())) {
                    return contractSell.getId();
                } else if (contractSell.getStatus() == 2) {
                    throw new RuntimeException("?????????????????????????????????????????????????????????????????????");
                }
            }
            boolean allGoodsCountComplete = true;
            for (CbsContractGoodsEntity contractGoods : contract.getContractGoodsList()) {
                CbsGoodsPartNoEntity goodsPartNoEntity = contractGoods.getPartNoEntity();
                if (goodsPartNoEntity.getContractCount().compareTo(goodsPartNoEntity.getExportCount()) > 0) {
                    allGoodsCountComplete = false;
                }
            }
            if (!allGoodsCountComplete || (entity.getForceToCreate() != null && entity.getForceToCreate())) {
                return createSell(entity);
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
    private Long createSell(CbsSellEntity entity) {
        sellDao.insert(entity);
        // ??????????????????????????????????????????
        CbsSellStatusStreamEntity statusStreamEntity = new CbsSellStatusStreamEntity();
        statusStreamEntity.setFkSellId(entity.getId());
        statusStreamEntity.setRemark("??????");
        statusStreamEntity.setLastStatus(0);
        statusStreamEntity.setStatus(1);
        statusStreamEntity.setOperator(entity.getOperator());
        sellStatusStreamDao.insert(statusStreamEntity);
        return entity.getId();
    }

    @Override
    @Transactional
    public Integer updateAllById(CbsSellEntity entity) {
        sellDao.updateById(entity);
        // ????????????
        if (entity.getSellGoodsList() != null) {
            List<CbsSellGoodsEntity> oldGoodsList = sellGoodsDao.listBySellId(entity.getId());
            for (CbsSellGoodsEntity oldGoods : oldGoodsList) {
                // ???????????????
                sellGoodsDao.deleteById(oldGoods);
            }
            List<CbsSellGoodsEntity> newGoodsList = entity.getSellGoodsList();
            for (CbsSellGoodsEntity newGoods : newGoodsList) {
                // ???????????????
                sellGoodsDao.insert(newGoods);
            }
        }
        if (entity.getImgSellInvoiceList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CbsImgSellInvoiceEntity imgSellInvoiceEntity : entity.getImgSellInvoiceList()) {
                imgList.add(imgSellInvoiceEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_SELL_INVOICE.getCode())
                    .contractId(entity.getFkContractId())
                    .field("fk_sell_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        if (entity.getImgSellDeliveryOrderList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CbsImgSellDeliveryOrderEntity imgSellDeliveryOrderEntity : entity.getImgSellDeliveryOrderList()) {
                imgList.add(imgSellDeliveryOrderEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_SELL_DELIVERY_ORDER.getCode())
                    .contractId(entity.getFkContractId())
                    .field("fk_sell_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        if (entity.getImgSellReceiptList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CbsImgSellReceiptEntity imgSellReceiptEntity : entity.getImgSellReceiptList()) {
                imgList.add(imgSellReceiptEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_SELL_RECEIPT.getCode())
                    .contractId(entity.getFkContractId())
                    .field("fk_sell_id")
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
    public void submit(CbsSellStatusStreamEntity statusStreamEntity) throws RuntimeException {
        CbsSellEntity entity = sellDao.detail(statusStreamEntity.getFkSellId());
        String errMsg = checkSellComplete(entity);
        if (errMsg != null) throw new RuntimeException(errMsg);

        CbsSellStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkSellId());
        if (lastStatusStreamEntity != null) {
            if (lastStatusStreamEntity.getStatus() == 1 || lastStatusStreamEntity.getStatus() == 3) {
                entity.setStatus(2);
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());
                statusStreamEntity.setStatus(2);

                sellDao.updateById(entity);
                sellStatusStreamDao.insert(statusStreamEntity);
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
    public Integer submitBack(CbsSellStatusStreamEntity statusStreamEntity) {
        CbsSellEntity entity = sellDao.selectById(statusStreamEntity.getFkSellId());
        CbsSellStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkSellId());
        if (lastStatusStreamEntity != null) {
            if (lastStatusStreamEntity.getStatus() == 2) {
                entity.setStatus(lastStatusStreamEntity.getLastStatus());
                statusStreamEntity.setStatus(lastStatusStreamEntity.getLastStatus());
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());

                sellDao.updateById(entity);
                sellStatusStreamDao.insert(statusStreamEntity);
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
    public void check(CbsSellStatusStreamEntity statusStreamEntity, Long operator) throws RuntimeException {
        CbsSellEntity entity = sellDao.detail(statusStreamEntity.getFkSellId());
        CbsSellStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkSellId());
        if (lastStatusStreamEntity != null) {
            if (entity.getStatus() == 2) {
                entity.setStatus(statusStreamEntity.getStatus());
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());

                sellDao.updateById(entity);
                sellStatusStreamDao.insert(statusStreamEntity);
                // ?????????????????????????????????????????????????????????????????????
                if (statusStreamEntity.getStatus() == 4) {
                    for (CbsSellGoodsEntity sellGoodsEntity : entity.getSellGoodsList()) {
                        // ????????????????????????
                        CbsGoodsPartNoEntity partNoEntity = sellGoodsEntity.getPartNoEntity();
                        partNoEntity.setExportCount(partNoEntity.getExportCount().add(sellGoodsEntity.getCount()));
                        goodsPartNoDao.update(partNoEntity, new UpdateWrapper<CbsGoodsPartNoEntity>().eq("goods_part_no", partNoEntity.getGoodsPartNo()));
                    }
                    insertMoneyIO(entity, operator);
                    // ??????????????????????????????????????????????????????????????????????????????????????????????????????
                    if (entity.getSynStoreOut()) {
                        // ??????
                        CbsStoreGoodsOutEntity storeGoodsOutEntity = new CbsStoreGoodsOutEntity();
                        storeGoodsOutEntity.setFkSellId(entity.getId());
                        storeGoodsOutEntity.setType(3);
                        HashMap<String, Object> resultMap = storeGoodsOutService.saveReturnId(storeGoodsOutEntity);
                        if (resultMap.containsKey("id")) {
                            Long storeGoodsOutId = (long) resultMap.get("id");
                            // ??????
                            storeGoodsOutEntity = storeGoodsOutService.detail(storeGoodsOutId);
                            storeGoodsOutEntity.setStartAddress(entity.getStartAddress());
                            storeGoodsOutEntity.setStartContact(entity.getStartContact());
                            storeGoodsOutEntity.setStartPhone(entity.getStartPhone());
                            storeGoodsOutEntity.setEndAddress(entity.getEndAddress());
                            storeGoodsOutEntity.setEndContact(entity.getEndContact());
                            storeGoodsOutEntity.setEndPhone(entity.getEndPhone());
                            storeGoodsOutEntity.setFkTransitCompanyId(entity.getFkTransitCompanyId());
                            storeGoodsOutEntity.setWagonNumber(entity.getWagonNumber());
                            storeGoodsOutEntity.setTransFeeMoney(BigDecimal.ZERO);
                            storeGoodsOutEntity.setFkStoreId(entity.getFkStoreId());
                            storeGoodsOutEntity.setDepartTime(entity.getDepartTime());
                            storeGoodsOutEntity.setArrivalTime(entity.getArrivalTime());
                            storeGoodsOutEntity.setStoreGoodsOutItemEntityList(new ArrayList<>());
                            storeGoodsOutEntity.setOperator(operator);
                            for (CbsSellGoodsEntity sellGoodsEntity : entity.getSellGoodsList()) {
                                CbsStoreGoodsOutItemEntity storeGoodsOutItemEntity = new CbsStoreGoodsOutItemEntity();
                                storeGoodsOutItemEntity.setFkStoreGoodsOutId(storeGoodsOutId);
                                storeGoodsOutItemEntity.setCreateTime(new Date());
                                storeGoodsOutItemEntity.setGoodsOutCount(sellGoodsEntity.getCount());
                                storeGoodsOutItemEntity.setGoodsPartNo(sellGoodsEntity.getGoodsPartNo());
                                storeGoodsOutItemEntity.setOutFromGoodsPartNo(sellGoodsEntity.getOutFromGoodsPartNo());
                                storeGoodsOutEntity.getStoreGoodsOutItemEntityList().add(storeGoodsOutItemEntity);
                            }
                            storeGoodsOutService.updateAllById(storeGoodsOutEntity);
                            // ??????
                            CbsStoreGoodsOutStatusStreamEntity storeGoodsOutStatusStreamEntity = new CbsStoreGoodsOutStatusStreamEntity();
                            storeGoodsOutStatusStreamEntity.setFkStoreGoodsOutId(storeGoodsOutId);
                            storeGoodsOutStatusStreamEntity.setCreateTime(new Date());
                            storeGoodsOutStatusStreamEntity.setLastStatus(1);
                            storeGoodsOutStatusStreamEntity.setStatus(2);
                            storeGoodsOutStatusStreamEntity.setOperator(operator);
                            storeGoodsOutStatusStreamEntity.setRemark("????????????????????????????????????");
                            storeGoodsOutService.submit(storeGoodsOutStatusStreamEntity);
                            // ??????
                            storeGoodsOutStatusStreamEntity.setLastStatus(2);
                            storeGoodsOutStatusStreamEntity.setStatus(4);
                            storeGoodsOutStatusStreamEntity.setRemark("????????????????????????????????????");
                            storeGoodsOutService.check(storeGoodsOutStatusStreamEntity);
                        }
                    }
                }
            } else {
                throw new RuntimeException("???????????????????????????????????????????????????");
            }
        } else {
            throw new RuntimeException("?????????????????????????????????,??????????????????");
        }
    }

    /**
     * ??????????????????????????????
     */
    @Override
    @Transactional
    public void updateReceipt(Long sellId, List<CbsImgSellReceiptEntity> imgReceiptList, Long operator) {
        if (imgReceiptList != null) {
            // ??????????????????
            imgSellReceiptDao.delete(new QueryWrapper<CbsImgSellReceiptEntity>().eq("fk_sell_id", sellId));
            for (CbsImgSellReceiptEntity imgSellReceiptEntity : imgReceiptList) {
                imgSellReceiptDao.insert(imgSellReceiptEntity);
            }
            // ???????????????????????????????????????????????????
            CbsSellEntity sellEntity = sellDao.selectById(sellId);
            if (sellEntity.getSynStoreOut()) {
                CbsStoreGoodsOutSellEntity storeGoodsOutSellEntity = storeGoodsOutSellService.selectBySellId(sellId);
                if (storeGoodsOutSellEntity != null && storeGoodsOutSellEntity.getGoodsOutEntity() != null) {
                    imgStoreGoodsOutReceiptDao.delete(new QueryWrapper<CbsImgStoreGoodsOutReceiptEntity>().eq("fk_store_goods_out_id", storeGoodsOutSellEntity.getGoodsOutEntity().getId()));
                    for (CbsImgSellReceiptEntity imgSellReceiptEntity : imgReceiptList) {
                        CbsImgStoreGoodsOutReceiptEntity storeGoodsOutReceiptEntity = getStoreGoodsOutReceiptFromSellReceipt(imgSellReceiptEntity);
                        storeGoodsOutReceiptEntity.setFkStoreGoodsOutId(storeGoodsOutSellEntity.getGoodsOutEntity().getId());
                        imgStoreGoodsOutReceiptDao.insert(storeGoodsOutReceiptEntity);
                    }
                }
            }
            // ????????????
            CbsSellStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(sellId);
            if (lastStatusStreamEntity != null) {
                CbsSellStatusStreamEntity statusStreamEntity = new CbsSellStatusStreamEntity();
                statusStreamEntity.setFkSellId(sellId);
                statusStreamEntity.setStatus(5);
                statusStreamEntity.setOperator(operator);
                statusStreamEntity.setRemark(lastStatusStreamEntity.getStatus() == 5 ? "??????????????????" : "??????????????????");
                statusStreamEntity.setCreateTime(new Date());

                sellEntity.setStatus(statusStreamEntity.getStatus());
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());
                sellDao.updateById(sellEntity);
                sellStatusStreamDao.insert(statusStreamEntity);
            }
        }

    }

    /**
     * @Description: ??????????????????
     * ????????????????????????5????????????????????????
     */
    @Override
    @Transactional
    public void checkReceipt(Long sellId, Long operator) throws RuntimeException {
        CbsSellEntity entity = sellDao.selectById(sellId);
        CbsSellStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(sellId);
        if (lastStatusStreamEntity != null) {
            if (entity.getStatus() == 5) {
                CbsSellStatusStreamEntity statusStreamEntity = new CbsSellStatusStreamEntity();
                statusStreamEntity.setFkSellId(sellId);
                statusStreamEntity.setStatus(6);
                statusStreamEntity.setOperator(operator);
                statusStreamEntity.setRemark("?????????");
                statusStreamEntity.setCreateTime(new Date());

                entity.setStatus(statusStreamEntity.getStatus());
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());
                sellDao.updateById(entity);
                sellStatusStreamDao.insert(statusStreamEntity);
            } else {
                throw new RuntimeException("???????????????????????????????????????????????????");
            }
        } else {
            throw new RuntimeException("?????????????????????????????????,??????????????????");
        }
    }

    @Override
    @Transactional
    public void deleteByIds(Long[] ids) {
        List<Long> goodsIds = sellGoodsDao.listIdsBySellIds(ids);
        if (goodsIds != null && !goodsIds.isEmpty()) {
            sellGoodsDao.deleteBatchIds(goodsIds);
        }
        removeByIds(Arrays.asList(ids));
    }

    /**
     * ??????????????????
     */
    private void insertMoneyIO(CbsSellEntity entity, Long operator) {
        // ?????????????????? - ??????
        CbsMoneyInEntity moneyInEntity = new CbsMoneyInEntity();
        moneyInEntity.setTitle("?????????????????? - " + entity.getId() + " ??????????????????");
        moneyInEntity.setFkContractId(entity.getFkContractId());
        moneyInEntity.setCurrencyCode(entity.getContractEntity().getCurrencyCode());
        moneyInEntity.setType(MoneyEnum.MONEY_TYPE_1002.getCode());
        moneyInEntity.setStatus(1);
        moneyInEntity.setOperator(operator);
        BigDecimal money = BigDecimal.ZERO;
        for (CbsSellGoodsEntity sellGoodsEntity : entity.getSellGoodsList()) {
            money = money.add(sellGoodsEntity.getCount().multiply(sellGoodsEntity.getPartNoEntity().getUnitPrice()));
        }
        moneyInEntity.setMoney(money);
        moneyInDao.insert(moneyInEntity);
        // ????????????
        CbsMoneyOutEntity moneyOutEntity = new CbsMoneyOutEntity();
        moneyOutEntity.setTitle("?????????????????? - " + entity.getId() + " ????????????");
        moneyOutEntity.setFkContractId(entity.getFkContractId());
        moneyOutEntity.setCurrencyCode("142");
        moneyOutEntity.setType(MoneyEnum.MONEY_TYPE_21.getCode());
        moneyOutEntity.setStatus(1);
        moneyOutEntity.setOperator(operator);
        moneyOutEntity.setMoney(entity.getTransFeeMoney());
        moneyOutDao.insert(moneyOutEntity);
    }

    /**
     * ?????????????????????
     */
    private CbsSellStatusStreamEntity getLastStatusStreamEntity(Long sellId) {
        List<CbsSellStatusStreamEntity> statusStreamEntityList = sellStatusStreamDao.selectList(new QueryWrapper<CbsSellStatusStreamEntity>().eq("fk_sell_id", sellId).orderByAsc("id"));
        if (statusStreamEntityList != null && !statusStreamEntityList.isEmpty()) {
            return statusStreamEntityList.get(statusStreamEntityList.size() - 1);
        } else {
            return null;
        }
    }

    /**
     * ?????????????????????????????????????????????????????????
     */
    private CbsImgStoreGoodsOutReceiptEntity getStoreGoodsOutReceiptFromSellReceipt(CbsImgSellReceiptEntity sellReceiptEntity) {
        CbsImgStoreGoodsOutReceiptEntity storeGoodsOutReceiptEntity = new CbsImgStoreGoodsOutReceiptEntity();
        storeGoodsOutReceiptEntity.setImgUrl(sellReceiptEntity.getImgUrl());
        storeGoodsOutReceiptEntity.setCreateTime(sellReceiptEntity.getCreateTime());
        storeGoodsOutReceiptEntity.setUpdateTime(sellReceiptEntity.getUpdateTime());
        storeGoodsOutReceiptEntity.setIsValid(sellReceiptEntity.getIsValid());
        storeGoodsOutReceiptEntity.setSort(sellReceiptEntity.getSort());
        return storeGoodsOutReceiptEntity;
    }

    /**
     * ??????????????????????????????
     */
    private String checkSellComplete(CbsSellEntity entity) {
        if (entity.getSellGoodsList().size() <= 0) {
            return "??????????????????????????????";
        } else {
            for (CbsSellGoodsEntity sellGoods : entity.getSellGoodsList()) {
                if (sellGoods.getPartNoEntity().getContractCount().compareTo(BigDecimal.ZERO) <= 0 || sellGoods.getPartNoEntity().getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
                    return "?????????????????????????????????0";
                }
            }
        }
        // ??????
        if (StringUtils.isEmpty(entity.getInvoiceCode())) {
            return "????????????????????????";
        }
        if (entity.getInvoiceMoney() == null || entity.getInvoiceMoney().compareTo(BigDecimal.ZERO) <= 0) {
            return "???????????????????????????";
        }
        // ??????
        if (entity.getImgSellInvoiceList() == null || entity.getImgSellInvoiceList().isEmpty()) {
            return "????????????????????????";
        }
        // ????????????????????????????????????????????????????????????????????????
        if (entity.getSynStoreOut()) {
            // ????????????
            if (entity.getStartAddress() == null || entity.getStartContact() == null || entity.getStartPhone() == null) {
                return "??????????????????????????????????????????????????????";
            }
            if (entity.getEndAddress() == null || entity.getEndContact() == null || entity.getEndPhone() == null) {
                return "??????????????????????????????????????????????????????";
            }
            if (entity.getImgSellDeliveryOrderList() == null || entity.getImgSellDeliveryOrderList().isEmpty()) {
                return "?????????????????????";
            }
        }
        return null;
    }
}