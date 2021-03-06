package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.enumeration.DocumentControlEnum;
import io.renren.common.enumeration.InOutEnum;
import io.renren.common.enumeration.PurchaseEnum;
import io.renren.common.enumeration.StoreGoodsEnum;
import io.renren.common.utils.CommonFunction;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.common.validator.Warehousing;
import io.renren.modules.cbs.dao.*;
import io.renren.modules.cbs.entity.*;
import io.renren.modules.cbs.service.CbsImportService;
import io.renren.modules.cbs.service.CbsPurchaseService;
import io.renren.modules.cbs.service.CbsStoreGoodsInService;
import io.renren.modules.cbs.vo.UnifiedUploadingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Service("cbsStoreGoodsInService")
public class CbsStoreGoodsInServiceImpl extends ServiceImpl<CbsStoreGoodsInDao, CbsStoreGoodsInEntity> implements CbsStoreGoodsInService {

    private CbsStoreGoodsDao storeGoodsDao;
    private CbsProduceGoodsDao produceGoodsDao;
    private CbsStoreGoodsInDao storeGoodsInDao;
    private CbsStoreGoodsInImportDao storeGoodsInImportDao;
    private CbsStoreGoodsInProduceDao storeGoodsInProduceDao;
    private CbsStoreGoodsInPurchaseDao storeGoodsInPurchaseDao;
    private CbsStoreGoodsInProduceBackDao storeGoodsInProduceBackDao;
    private CbsStoreGoodsInItemDao storeGoodsInItemDao;
    private CbsImgStoreGoodsInDeliveryOrderDao imgStoreGoodsInDeliveryOrderDao;
    private CbsImgStoreGoodsInReceiptDao imgStoreGoodsInReceiptDao;
    private CbsStoreGoodsInStatusStreamDao storeGoodsInStatusStreamDao;
    private CbsGoodsPartNoDao goodsPartNoDao;

    private CbsMoneyOutDao moneyOutDao;
    private CbsImportService importService;
    private CbsPurchaseService purchaseService;
    private CommonFunction commonFunction;

    private CbsProduceDao cbsProduceDao;
    private CbsImportDao cbsImportDao;
    private CbsPurchaseDao cbsPurchaseDao;

    public CbsStoreGoodsInServiceImpl(CbsStoreGoodsDao storeGoodsDao,
                                      CbsProduceGoodsDao produceGoodsDao,
                                      CbsStoreGoodsInDao storeGoodsInDao,
                                      CbsStoreGoodsInImportDao storeGoodsInImportDao,
                                      CbsStoreGoodsInProduceDao storeGoodsInProduceDao,
                                      CbsStoreGoodsInPurchaseDao storeGoodsInPurchaseDao,
                                      CbsStoreGoodsInProduceBackDao storeGoodsInProduceBackDao,
                                      CbsStoreGoodsInItemDao storeGoodsInItemDao,
                                      CbsImgStoreGoodsInDeliveryOrderDao imgStoreGoodsInDeliveryOrderDao,
                                      CbsImgStoreGoodsInReceiptDao imgStoreGoodsInReceiptDao,
                                      CbsStoreGoodsInStatusStreamDao storeGoodsInStatusStreamDao,
                                      CbsGoodsPartNoDao goodsPartNoDao,
                                      CbsMoneyOutDao moneyOutDao,
                                      CommonFunction commonFunction,
                                      CbsProduceDao cbsProduceDao,
                                      CbsImportDao cbsImportDao,
                                      CbsPurchaseDao cbsPurchaseDao) {
        this.storeGoodsDao = storeGoodsDao;
        this.produceGoodsDao = produceGoodsDao;
        this.storeGoodsInDao = storeGoodsInDao;
        this.storeGoodsInImportDao = storeGoodsInImportDao;
        this.storeGoodsInProduceDao = storeGoodsInProduceDao;
        this.storeGoodsInPurchaseDao = storeGoodsInPurchaseDao;
        this.storeGoodsInProduceBackDao = storeGoodsInProduceBackDao;
        this.storeGoodsInItemDao = storeGoodsInItemDao;
        this.imgStoreGoodsInDeliveryOrderDao = imgStoreGoodsInDeliveryOrderDao;
        this.imgStoreGoodsInReceiptDao = imgStoreGoodsInReceiptDao;
        this.storeGoodsInStatusStreamDao = storeGoodsInStatusStreamDao;
        this.goodsPartNoDao = goodsPartNoDao;
        this.moneyOutDao = moneyOutDao;
        this.commonFunction = commonFunction;
        this.cbsProduceDao = cbsProduceDao;
        this.cbsImportDao = cbsImportDao;
        this.cbsPurchaseDao = cbsPurchaseDao;
    }

    @Autowired
    public void setImportService(CbsImportService importService) {
        this.importService = importService;
    }

    @Autowired
    public void setPurchaseService(CbsPurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @Override
    public PageUtils queryIndex(CbsStoreGoodsInEntity entity) {
        IPage<CbsStoreGoodsInEntity> page = storeGoodsInDao.queryIndex(new QueryPage<CbsStoreGoodsInEntity>().getPage(entity), entity);
        return new PageUtils(page);
    }

    @Override
    @Transactional
    public HashMap<String, Object> saveReturnId(CbsStoreGoodsInEntity entity) {
        HashMap<String, Object> resultMap;
        resultMap = checkBeforeCreate(entity);
        if (resultMap.containsKey("code")) {
            return resultMap;
        }

        entity.setStatus(1);
        if (entity.getType() == 1) {
            // ????????????
            storeGoodsInDao.insert(entity);
            CbsImportEntity importEntity = importService.detail(entity.getFkImportId());
            entity.setHasDeliveryCode(importEntity.getHasDeliveryCode());
            if (importEntity.getHasDeliveryCode()) {
                entity.setDeliveryCode(importEntity.getDeliveryCode());
                storeGoodsInDao.updateById(entity);
            } else
                for (CbsImgImportDeliveryOrderEntity deliveryOrderEntity : importEntity.getImgImportDeliveryOrderList()) {
                    CbsImgStoreGoodsInDeliveryOrderEntity storeGoodsInDeliveryOrderEntity = deliveryOrderFromImport(deliveryOrderEntity);
                    storeGoodsInDeliveryOrderEntity.setFkStoreGoodsInId(entity.getId());
                    imgStoreGoodsInDeliveryOrderDao.insert(storeGoodsInDeliveryOrderEntity);
                }
            // ?????????????????????????????????????????????????????????????????????????????????????????????
            CbsStoreGoodsInImportEntity goodsInImportEntity = new CbsStoreGoodsInImportEntity();
            goodsInImportEntity.setFkImportId(entity.getFkImportId());
            goodsInImportEntity.setFkStoreGoodsInId(entity.getId());
            storeGoodsInImportDao.insert(goodsInImportEntity);
        } else if (entity.getType() == 2) {
            // ??????????????????
            // ??????produce_id????????????cbs_produce_goods?????????store_count????????????0???????????????????????????????????????
            boolean allCountComplete = true;
            List<CbsProduceGoodsEntity> cbsProduceGoodsEntityList = produceGoodsDao.selectList(new QueryWrapper<CbsProduceGoodsEntity>().eq("fk_produce_id", entity.getFkProduceId()).eq("goods_type", 2));
            for (CbsProduceGoodsEntity cbsProduceGoodsEntity : cbsProduceGoodsEntityList) {
                if (cbsProduceGoodsEntity.getStoreCount().compareTo(BigDecimal.ZERO) > 0) {
                    allCountComplete = false;
                }
            }
            if (allCountComplete) {
                resultMap.put("code", 400);
                resultMap.put("msg", "????????????????????????????????????");
                return resultMap;
            }
            storeGoodsInDao.insert(entity);
            CbsStoreGoodsInProduceEntity goodsInProduceEntity = new CbsStoreGoodsInProduceEntity();
            goodsInProduceEntity.setFkProduceId(entity.getFkProduceId());
            goodsInProduceEntity.setFkStoreGoodsInId(entity.getId());
            storeGoodsInProduceDao.insert(goodsInProduceEntity);
        } else if (entity.getType() == 3) {
            // ??????????????????
            storeGoodsInDao.insert(entity);
            CbsPurchaseEntity purchaseEntity = purchaseService.detail(entity.getFkPurchaseId());
            for (CbsImgPurchaseDeliveryOrderEntity deliveryOrderEntity : purchaseEntity.getImgPurchaseDeliveryOrderList()) {
                CbsImgStoreGoodsInDeliveryOrderEntity storeGoodsInDeliveryOrderEntity = deliveryOrderFromPurchase(deliveryOrderEntity);
                storeGoodsInDeliveryOrderEntity.setFkStoreGoodsInId(entity.getId());
                imgStoreGoodsInDeliveryOrderDao.insert(storeGoodsInDeliveryOrderEntity);
            }
            // ?????????????????????????????????????????????????????????????????????????????????????????????
            CbsStoreGoodsInPurchaseEntity goodsInPurchaseEntity = new CbsStoreGoodsInPurchaseEntity();
            goodsInPurchaseEntity.setFkPurchaseId(entity.getFkPurchaseId());
            goodsInPurchaseEntity.setFkStoreGoodsInId(entity.getId());
            storeGoodsInPurchaseDao.insert(goodsInPurchaseEntity);
        } else if (entity.getType() == 4) {
            // ??????????????????
            List<CbsStoreGoodsInItemEntity> storeGoodsInItemEntityList = new ArrayList<>();
            List<CbsProduceGoodsEntity> cbsProduceGoodsEntityList = produceGoodsDao.selectList(new QueryWrapper<CbsProduceGoodsEntity>().eq("fk_produce_id", entity.getFkProduceBackId()).eq("goods_type", 1));
            for (CbsProduceGoodsEntity produceGoodsEntity : cbsProduceGoodsEntityList) {
                if (produceGoodsEntity.getStoreCount().compareTo(BigDecimal.ZERO) > 0) {
                    CbsStoreGoodsInItemEntity storeGoodsInItemEntity = new CbsStoreGoodsInItemEntity();
                    storeGoodsInItemEntity.setGoodsPartNo(produceGoodsEntity.getGoodsPartNo());
                    storeGoodsInItemEntity.setGoodsInCount(produceGoodsEntity.getStoreCount());
                    storeGoodsInItemEntity.setCreateTime(new Date());
                    storeGoodsInItemEntity.setGoodsStoreCount(BigDecimal.ZERO);
                    storeGoodsInItemEntityList.add(storeGoodsInItemEntity);
                }
            }
            // ??????????????????????????????
            if (storeGoodsInItemEntityList.isEmpty()) {
                resultMap.put("code", 400);
                resultMap.put("msg", "?????????????????????????????????????????????????????????");
                return resultMap;
            }
            storeGoodsInDao.insert(entity);
            CbsStoreGoodsInProduceBackEntity goodsInProduceBackEntity = new CbsStoreGoodsInProduceBackEntity();
            goodsInProduceBackEntity.setFkProduceId(entity.getFkProduceBackId());
            goodsInProduceBackEntity.setFkStoreGoodsInId(entity.getId());
            storeGoodsInProduceBackDao.insert(goodsInProduceBackEntity);
            // ????????????????????????
            for (CbsStoreGoodsInItemEntity storeGoodsInItemEntity : storeGoodsInItemEntityList) {
                storeGoodsInItemEntity.setFkStoreGoodsInId(entity.getId());
                storeGoodsInItemDao.insert(storeGoodsInItemEntity);
            }
        }
        // ?????????????????????????????????
        CbsStoreGoodsInStatusStreamEntity statusStreamEntity = new CbsStoreGoodsInStatusStreamEntity();
        statusStreamEntity.setFkStoreGoodsInId(entity.getId());
        statusStreamEntity.setRemark("??????");
        statusStreamEntity.setLastStatus(0);
        statusStreamEntity.setStatus(1);
        statusStreamEntity.setOperator(entity.getOperator());
        storeGoodsInStatusStreamDao.insert(statusStreamEntity);
        resultMap.put("code", 200);
        resultMap.put("id", entity.getId());
        return resultMap;
    }

    @Override
    public List<CbsStoreGoodsInImportEntity> listImportInByOutToProduceId(Long produceId) {
        return storeGoodsInImportDao.listByOutToProduceId(produceId);
    }

    @Override
    public CbsStoreGoodsInEntity detail(Long id) {
        return storeGoodsInDao.detail(id);
    }

    @Override
    @Transactional
    public Integer updateAllById(CbsStoreGoodsInEntity entity) {
        storeGoodsInDao.updateById(entity);
        Long contractId = 0L;
        // entity.getId ??????  cbs_store_goods_in_produce ??? id ????????? cbs_produce ??? contractId
        CbsStoreGoodsInProduceEntity cbsStoreGoodsInProduceEntity = storeGoodsInProduceDao.selectOne(new QueryWrapper<CbsStoreGoodsInProduceEntity>().eq("fk_store_goods_in_id", entity.getId()));
        if (cbsStoreGoodsInProduceEntity != null) {
            CbsProduceEntity cbsProduceEntity = cbsProduceDao.selectById(cbsStoreGoodsInProduceEntity.getFkProduceId());
            if (cbsProduceEntity != null) {
                contractId = cbsProduceEntity.getFkContractId();
            }
        }
        // entity.getId ??????  cbs_store_goods_in_purchase ??? id ????????? cbs_purchase ??? contractId
        CbsStoreGoodsInImportEntity cbsStoreGoodsInImportEntity = storeGoodsInImportDao.selectOne(new QueryWrapper<CbsStoreGoodsInImportEntity>().eq("fk_store_goods_in_id", entity.getId()));
        if (cbsStoreGoodsInImportEntity != null) {
            CbsImportEntity cbsImportEntity = cbsImportDao.selectById(cbsStoreGoodsInImportEntity.getFkImportId());
            if (cbsImportEntity != null) {
                contractId = cbsImportEntity.getFkContractId();
            }
        }
        // entity.getId ??????  cbs_store_goods_in_purchase ??? id ????????? cbs_import ??? contractId
        CbsStoreGoodsInPurchaseEntity cbsStoreGoodsInPurchaseEntity = storeGoodsInPurchaseDao.selectOne(new QueryWrapper<CbsStoreGoodsInPurchaseEntity>().eq("fk_store_goods_in_id", entity.getId()));
        if (cbsStoreGoodsInPurchaseEntity != null) {
            CbsPurchaseEntity cbsPurchaseEntity = cbsPurchaseDao.selectById(cbsStoreGoodsInPurchaseEntity.getFkPurchaseId());
            if (cbsPurchaseEntity != null) {
                contractId = cbsPurchaseEntity.getFkContractId();
            }
        }
        // entity.getId ??????  cbs_store_goods_in_produce_back ??? id ????????? cbs_import ??? contractId
        CbsStoreGoodsInProduceBackEntity cbsStoreGoodsInProduceBackEntity = storeGoodsInProduceBackDao.selectOne(new QueryWrapper<CbsStoreGoodsInProduceBackEntity>().eq("fk_store_goods_in_id", entity.getId()));
        if (cbsStoreGoodsInProduceBackEntity != null) {
            CbsProduceEntity cbsProduceEntity = cbsProduceDao.selectById(cbsStoreGoodsInProduceBackEntity.getFkProduceId());
            if (cbsProduceEntity != null) {
                contractId = cbsProduceEntity.getFkContractId();
            }
        }

        if (entity.getStoreGoodsInItemEntityList() != null) {
            storeGoodsInItemDao.delete(new QueryWrapper<CbsStoreGoodsInItemEntity>().eq("fk_store_goods_in_id", entity.getId()));
            for (CbsStoreGoodsInItemEntity storeGoodsInItemEntity : entity.getStoreGoodsInItemEntityList()) {
                storeGoodsInItemDao.insert(storeGoodsInItemEntity);
            }
        }
        if (entity.getImgStoreGoodsInDeliveryOrderEntityList() != null) {
            /*imgStoreGoodsInDeliveryOrderDao.delete(new QueryWrapper<CbsImgStoreGoodsInDeliveryOrderEntity>().eq("fk_store_goods_in_id", entity.getId()));
            for (CbsImgStoreGoodsInDeliveryOrderEntity imgStoreGoodsInDeliveryOrderEntity : entity.getImgStoreGoodsInDeliveryOrderEntityList()) {
                imgStoreGoodsInDeliveryOrderEntity.setFkStoreGoodsInId(entity.getId());
                imgStoreGoodsInDeliveryOrderDao.insert(imgStoreGoodsInDeliveryOrderEntity);
            }*/
            List<String> imgList = new ArrayList<>();
            for (CbsImgStoreGoodsInDeliveryOrderEntity imgStoreGoodsInDeliveryOrderEntity : entity.getImgStoreGoodsInDeliveryOrderEntityList()) {
                imgList.add(imgStoreGoodsInDeliveryOrderEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_STORE_GOODS_IN_DELIVERY_ORDER.getCode())
                    .contractId(contractId)
                    .field("fk_store_goods_in_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        if (entity.getImgStoreGoodsInReceiptEntityList() != null) {
            /*imgStoreGoodsInReceiptDao.delete(new QueryWrapper<CbsImgStoreGoodsInReceiptEntity>().eq("fk_store_goods_in_id", entity.getId()));
            for (CbsImgStoreGoodsInReceiptEntity imgStoreGoodsInReceiptEntity : entity.getImgStoreGoodsInReceiptEntityList()) {
                imgStoreGoodsInReceiptEntity.setFkStoreGoodsInId(entity.getId());
                imgStoreGoodsInReceiptDao.insert(imgStoreGoodsInReceiptEntity);
            }*/
            List<String> imgList = new ArrayList<>();
            for (CbsImgStoreGoodsInReceiptEntity imgStoreGoodsInReceiptEntity : entity.getImgStoreGoodsInReceiptEntityList()) {
                imgList.add(imgStoreGoodsInReceiptEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_STORE_GOODS_IN_RECEIPT.getCode())
                    .contractId(contractId)
                    .field("fk_store_goods_in_id")
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
    public void submit(CbsStoreGoodsInStatusStreamEntity statusStreamEntity) throws RuntimeException {
        CbsStoreGoodsInEntity entity = storeGoodsInDao.detail(statusStreamEntity.getFkStoreGoodsInId());
        String errMsg = checkStoreGoodsInComplete(entity);
        if (errMsg != null) throw new RuntimeException(errMsg);

        CbsStoreGoodsInStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkStoreGoodsInId());
        if (lastStatusStreamEntity != null) {
            if (lastStatusStreamEntity.getStatus() == 1 || lastStatusStreamEntity.getStatus() == 3) {
                try {
                    entity.setStatus(2);
                    statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());
                    statusStreamEntity.setStatus(2);

                    storeGoodsInDao.updateById(entity);
                    storeGoodsInStatusStreamDao.insert(statusStreamEntity);
                } catch (Exception e) {
                    throw new RuntimeException("?????????????????????????????????");
                }
            } else {
                throw new RuntimeException("????????????????????????????????????????????????????????????????????????");
            }
        } else {
            throw new RuntimeException("???????????????????????????,??????????????????");
        }
    }

    /**
     * @Description: ??????
     * ????????????????????????1???????????????3????????????
     */
    @Override
    @Transactional
    public Integer submitBack(CbsStoreGoodsInStatusStreamEntity statusStreamEntity) {
        CbsStoreGoodsInEntity entity = storeGoodsInDao.selectById(statusStreamEntity.getFkStoreGoodsInId());
        CbsStoreGoodsInStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkStoreGoodsInId());
        if (lastStatusStreamEntity != null) {
            if (lastStatusStreamEntity.getStatus() == 2) {
                try {
                    entity.setStatus(lastStatusStreamEntity.getLastStatus());
                    statusStreamEntity.setStatus(lastStatusStreamEntity.getLastStatus());
                    statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());

                    storeGoodsInDao.updateById(entity);
                    storeGoodsInStatusStreamDao.insert(statusStreamEntity);
                    return 1;
                } catch (Exception e) {
                    return -2;
                }
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
    public void check(CbsStoreGoodsInStatusStreamEntity statusStreamEntity) throws RuntimeException {
        CbsStoreGoodsInEntity entity = storeGoodsInDao.detail(statusStreamEntity.getFkStoreGoodsInId());
        CbsStoreGoodsInStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkStoreGoodsInId());
        if (lastStatusStreamEntity != null) {
            if (entity.getStatus() == 2) {
                // ????????????
                entity.setStatus(statusStreamEntity.getStatus());
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());
                storeGoodsInDao.updateById(entity);
                storeGoodsInStatusStreamDao.insert(statusStreamEntity);
                // ?????????????????????????????????????????????
                if (statusStreamEntity.getStatus() == 4) {
                    if (StoreGoodsEnum.CONTRACT_TYPE_1.getCode() == entity.getType()) {
                        // cbs_import ??????????????? 14
                        Long importId = storeGoodsInDao.selectImportId(entity.getId());
                        importService.updateById(CbsImportEntity.builder().id(importId).status(InOutEnum.STATUS_14.getCode()).build());
                    } else if (StoreGoodsEnum.CONTRACT_TYPE_3.getCode() == entity.getType()) {
                        // cbs_purchase ??????????????? 5
                        Long purchaseId = storeGoodsInDao.selectPurchaseId(entity.getId());
                        purchaseService.updateById(CbsPurchaseEntity.builder().id(purchaseId).status(PurchaseEnum.STATUS_5.getCode()).build());
                    }
                    // ?????????????????????????????????????????????
                    for (CbsStoreGoodsInItemEntity goodsInItemEntity : entity.getStoreGoodsInItemEntityList()) {
                        // ?????????????????????????????????????????????
                        CbsGoodsPartNoEntity partNoEntity = goodsPartNoDao.selectByCode(goodsInItemEntity.getGoodsPartNo());
                        partNoEntity.setStoreInCount(partNoEntity.getStoreInCount().add(goodsInItemEntity.getGoodsInCount()));
                        partNoEntity.setStoreCount(partNoEntity.getStoreCount().add(goodsInItemEntity.getGoodsInCount()));
                        goodsPartNoDao.updateById(partNoEntity);
                        // ???????????????
                        CbsStoreGoodsEntity storeGoodsEntity = storeGoodsDao.selectOne(new QueryWrapper<CbsStoreGoodsEntity>().eq("goods_part_no", goodsInItemEntity.getGoodsPartNo()));
                        if (storeGoodsEntity == null) {
                            storeGoodsEntity = new CbsStoreGoodsEntity();
                            storeGoodsEntity.setFkStoreId(entity.getFkStoreId());
                            storeGoodsEntity.setGoodsPartNo(goodsInItemEntity.getGoodsPartNo());
                            storeGoodsEntity.setGoodsStoreCount(goodsInItemEntity.getGoodsInCount());
                            storeGoodsDao.insert(storeGoodsEntity);
                        } else {
                            storeGoodsEntity.setGoodsStoreCount(storeGoodsEntity.getGoodsStoreCount().add(goodsInItemEntity.getGoodsInCount()));
                            storeGoodsDao.updateById(storeGoodsEntity);
                        }
                        // ???????????????????????????????????????
                        if (entity.getType() == 2) {
                            QueryWrapper<CbsProduceGoodsEntity> qw = new QueryWrapper<>();
                            qw.eq("goods_part_no", goodsInItemEntity.getGoodsPartNo());
                            qw.eq("fk_produce_id", entity.getFkProduceId());
                            CbsProduceGoodsEntity produceGoodsEntity = produceGoodsDao.selectOne(qw);
                            produceGoodsEntity.setStoreCount(produceGoodsEntity.getStoreCount().subtract(goodsInItemEntity.getGoodsInCount()));
                            produceGoodsDao.updateById(produceGoodsEntity);
                        }
                        // ???????????????????????????????????????
                        if (entity.getType() == 4) {
                            QueryWrapper<CbsProduceGoodsEntity> qw = new QueryWrapper<>();
                            qw.eq("goods_part_no", goodsInItemEntity.getGoodsPartNo());
                            qw.eq("fk_produce_id", entity.getFkProduceBackId());
                            CbsProduceGoodsEntity produceGoodsEntity = produceGoodsDao.selectOne(qw);
                            produceGoodsEntity.setTotalCount(produceGoodsEntity.getStoreCount().subtract(goodsInItemEntity.getGoodsInCount()));
                            produceGoodsEntity.setStoreCount(produceGoodsEntity.getStoreCount().subtract(goodsInItemEntity.getGoodsInCount()));
                            produceGoodsDao.updateById(produceGoodsEntity);
                        }
                    }
                    // ??????????????????
                    if (entity.getTransFeeMoney() != null && entity.getTransFeeMoney().compareTo(BigDecimal.ZERO) > 0) {
                        CbsMoneyOutEntity moneyOutEntity = new CbsMoneyOutEntity();
                        moneyOutEntity.setTitle("???????????? - " + entity.getId() + " ????????????");
                        // 1??????????????? 2??????????????? 3?????????????????????
                        switch (entity.getType()) {
                            case 1:
                                moneyOutEntity.setFkContractId(entity.getImportEntity().getFkContractId());
                                break;
                            case 2:
                                moneyOutEntity.setFkContractId(entity.getProduceEntity().getFkContractId());
                                break;
                            case 3:
                                moneyOutEntity.setFkContractId(entity.getPurchaseEntity().getFkContractId());
                                break;
                        }
                        moneyOutEntity.setCurrencyCode("142"/*?????????*/);
                        moneyOutEntity.setType(21);
                        moneyOutEntity.setStatus(1);
                        moneyOutEntity.setOperator(statusStreamEntity.getOperator());
                        moneyOutEntity.setMoney(entity.getTransFeeMoney());
                        moneyOutDao.insert(moneyOutEntity);
                    }
                }
            } else {
                throw new RuntimeException("?????????????????????????????????????????????");
            }
        } else {
            throw new RuntimeException("???????????????????????????,??????????????????");
        }
    }

    /**
     * ???????????????????????????????????????
     */
    private CbsImgStoreGoodsInDeliveryOrderEntity deliveryOrderFromImport(CbsImgImportDeliveryOrderEntity deliveryOrderEntity) {
        CbsImgStoreGoodsInDeliveryOrderEntity storeGoodsInDeliveryOrderEntity = new CbsImgStoreGoodsInDeliveryOrderEntity();
        storeGoodsInDeliveryOrderEntity.setCreateTime(deliveryOrderEntity.getCreateTime());
        storeGoodsInDeliveryOrderEntity.setImgUrl(deliveryOrderEntity.getImgUrl());
        storeGoodsInDeliveryOrderEntity.setIsValid(deliveryOrderEntity.getIsValid());
        storeGoodsInDeliveryOrderEntity.setUpdateTime(new Date());
        storeGoodsInDeliveryOrderEntity.setSort(deliveryOrderEntity.getSort());
        return storeGoodsInDeliveryOrderEntity;
    }

    /**
     * ???????????????????????????????????????
     */
    private CbsImgStoreGoodsInDeliveryOrderEntity deliveryOrderFromPurchase(CbsImgPurchaseDeliveryOrderEntity deliveryOrderEntity) {
        CbsImgStoreGoodsInDeliveryOrderEntity storeGoodsInDeliveryOrderEntity = new CbsImgStoreGoodsInDeliveryOrderEntity();
        storeGoodsInDeliveryOrderEntity.setCreateTime(deliveryOrderEntity.getCreateTime());
        storeGoodsInDeliveryOrderEntity.setImgUrl(deliveryOrderEntity.getImgUrl());
        storeGoodsInDeliveryOrderEntity.setIsValid(deliveryOrderEntity.getIsValid());
        storeGoodsInDeliveryOrderEntity.setUpdateTime(new Date());
        storeGoodsInDeliveryOrderEntity.setSort(deliveryOrderEntity.getSort());
        return storeGoodsInDeliveryOrderEntity;
    }

    /**
     * ???????????????????????????????????????
     */
    private HashMap<String, Object> checkBeforeCreate(CbsStoreGoodsInEntity entity) {
        HashMap<String, Object> resultMap = new HashMap<>();
        if (entity.getType() == 1) {
            QueryWrapper<CbsStoreGoodsInImportEntity> qw = new QueryWrapper<>();
            qw.eq("fk_import_id", entity.getFkImportId());
            List<CbsStoreGoodsInImportEntity> oldStoreGoodsInImportList = storeGoodsInImportDao.selectList(qw);
            if (oldStoreGoodsInImportList == null || oldStoreGoodsInImportList.isEmpty()) {
                return resultMap;
            } else {
                CbsStoreGoodsInEntity oldEntity = storeGoodsInDao.selectById(oldStoreGoodsInImportList.get(0).getFkStoreGoodsInId());
                if (oldEntity.getStatus() == 1 || oldEntity.getStatus() == 3) {
                    resultMap.put("code", 200);
                    resultMap.put("id", oldEntity.getId());
                    return resultMap;
                } else if (oldEntity.getStatus() == 2) {
                    resultMap.put("code", 400);
                    resultMap.put("msg", "???????????????????????????????????????????????????????????????????????????????????????");
                    return resultMap;
                } else if (oldEntity.getStatus() >= 4) {
                    resultMap.put("code", 500);
                    resultMap.put("msg", "??????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
                    return resultMap;
                }
            }
        } else if (entity.getType() == 2) {
            QueryWrapper<CbsStoreGoodsInProduceEntity> qw = new QueryWrapper<>();
            qw.eq("fk_produce_id", entity.getFkProduceId());
            List<CbsStoreGoodsInProduceEntity> oldStoreGoodsInProduceList = storeGoodsInProduceDao.selectList(qw);
            if (oldStoreGoodsInProduceList == null || oldStoreGoodsInProduceList.isEmpty()) {
                return resultMap;
            } else {
                List<Long> storeGoodsInIds = new ArrayList<>();
                for (CbsStoreGoodsInProduceEntity oldStoreGoodsInProduceEntity : oldStoreGoodsInProduceList) {
                    storeGoodsInIds.add(oldStoreGoodsInProduceEntity.getFkStoreGoodsInId());
                }
                List<CbsStoreGoodsInEntity> oldEntityList = storeGoodsInDao.selectBatchIds(storeGoodsInIds);
                for (CbsStoreGoodsInEntity oldEntity : oldEntityList) {
                    if (oldEntity.getStatus() == 1 || oldEntity.getStatus() == 3) {
                        resultMap.put("code", 200);
                        resultMap.put("id", oldEntity.getId());
                        return resultMap;
                    }
                }
                for (CbsStoreGoodsInEntity oldEntity : oldEntityList) {
                    if (oldEntity.getStatus() == 1 || oldEntity.getStatus() == 3) {
                        resultMap.put("code", 400);
                        resultMap.put("msg", "???????????????????????????????????????????????????????????????????????????????????????");
                        return resultMap;
                    }
                }
            }
        } else if (entity.getType() == 3) {
            QueryWrapper<CbsStoreGoodsInPurchaseEntity> qw = new QueryWrapper<>();
            qw.eq("fk_purchase_id", entity.getFkPurchaseId());
            List<CbsStoreGoodsInPurchaseEntity> oldStoreGoodsInPurchaseList = storeGoodsInPurchaseDao.selectList(qw);
            if (oldStoreGoodsInPurchaseList == null || oldStoreGoodsInPurchaseList.isEmpty()) {
                return resultMap;
            } else {
                CbsStoreGoodsInEntity oldEntity = storeGoodsInDao.selectById(oldStoreGoodsInPurchaseList.get(0).getFkStoreGoodsInId());
                if (oldEntity.getStatus() == 1 || oldEntity.getStatus() == 3) {
                    resultMap.put("code", 200);
                    resultMap.put("id", oldEntity.getId());
                    return resultMap;
                } else if (oldEntity.getStatus() == 2) {
                    resultMap.put("code", 400);
                    resultMap.put("msg", "?????????????????????????????????????????????????????????????????????????????????????????????");
                    return resultMap;
                } else if (oldEntity.getStatus() >= 4) {
                    resultMap.put("code", 500);
                    resultMap.put("msg", "????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
                    return resultMap;
                }
            }
        } else if (entity.getType() == 4) {
            QueryWrapper<CbsStoreGoodsInProduceBackEntity> qw = new QueryWrapper<>();
            qw.eq("fk_produce_id", entity.getFkProduceBackId());
            List<CbsStoreGoodsInProduceBackEntity> oldStoreGoodsInProduceBackList = storeGoodsInProduceBackDao.selectList(qw);
            if (oldStoreGoodsInProduceBackList == null || oldStoreGoodsInProduceBackList.isEmpty()) {
                return resultMap;
            } else {
                List<Long> storeGoodsInIds = new ArrayList<>();
                for (CbsStoreGoodsInProduceBackEntity oldStoreGoodsInProduceBackEntity : oldStoreGoodsInProduceBackList) {
                    storeGoodsInIds.add(oldStoreGoodsInProduceBackEntity.getFkStoreGoodsInId());
                }
                List<CbsStoreGoodsInEntity> oldEntityList = storeGoodsInDao.selectBatchIds(storeGoodsInIds);
                for (CbsStoreGoodsInEntity oldEntity : oldEntityList) {
                    if (oldEntity.getStatus() == 1 || oldEntity.getStatus() == 3) {
                        resultMap.put("code", 200);
                        resultMap.put("id", oldEntity.getId());
                        return resultMap;
                    }
                }
                for (CbsStoreGoodsInEntity oldEntity : oldEntityList) {
                    if (oldEntity.getStatus() == 2) {
                        resultMap.put("code", 400);
                        resultMap.put("msg", "???????????????????????????????????????????????????????????????????????????????????????");
                        return resultMap;
                    }
                }
            }
        }
        return resultMap;
    }



    /**
     * ????????????????????????
     */
    private String checkStoreGoodsInComplete(CbsStoreGoodsInEntity entity) {
        if (entity.getStoreGoodsInItemEntityList().size() <= 0) {
            return "?????????????????????????????????";
        } else {
            // ???????????????????????????????????????0????????????????????????????????????????????????0
            BigDecimal totalCount = BigDecimal.ZERO;
            for (CbsStoreGoodsInItemEntity storeGoodsInItem : entity.getStoreGoodsInItemEntityList()) {
                totalCount = totalCount.add(storeGoodsInItem.getGoodsInCount());
            }
            if (totalCount.compareTo(BigDecimal.ZERO) <= 0) {
                return "??????????????????????????????0";
            }
        }
        // ????????????
        if (entity.getTransitCompanyEntity() == null) return "????????????????????????";
        // ??????
        if (entity.getStoreEntity() == null) return "????????????????????????";
        // ??????
        if (entity.getStartAddress() == null) return "????????????????????????";
        if (entity.getStartContact() == null) return "???????????????????????????";
        if (entity.getStartPhone() == null) return "??????????????????????????????";
        // ??????
        if (entity.getEndAddress() == null) return "????????????????????????";
        if (entity.getEndContact() == null) return "???????????????????????????";
        if (entity.getEndPhone() == null) return "??????????????????????????????";
        // ????????????
        if (entity.getImgStoreGoodsInReceiptEntityList() == null || entity.getImgStoreGoodsInReceiptEntityList().isEmpty())
            return "??????????????????????????????";

        return null;
    }

    private CbsStoreGoodsInStatusStreamEntity getLastStatusStreamEntity(Long storeGoodsInId) {
        List<CbsStoreGoodsInStatusStreamEntity> statusStreamEntityList = storeGoodsInStatusStreamDao.selectList(new QueryWrapper<CbsStoreGoodsInStatusStreamEntity>().eq("fk_store_goods_in_id", storeGoodsInId).orderByAsc("id"));
        if (statusStreamEntityList != null && !statusStreamEntityList.isEmpty()) {
            return statusStreamEntityList.get(statusStreamEntityList.size() - 1);
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public void deleteByIds(Long[] ids) {
        for (Long id : ids) {
            CbsStoreGoodsInEntity entity = storeGoodsInDao.detail(id);
            if (entity != null) {
                // status??????4????????????
                Warehousing.isStatusMoreThanThe(entity.getStatus(), 4, "????????????????????????????????????");
                // ??????????????????
                for (CbsStoreGoodsInItemEntity itemEntity : entity.getStoreGoodsInItemEntityList()) {
                    storeGoodsInItemDao.deleteById(itemEntity.getId());
                }
                // ?????????????????????
                switch (entity.getType()) {
                    case 1:
                        storeGoodsInImportDao.delete(new QueryWrapper<CbsStoreGoodsInImportEntity>().eq("fk_store_goods_in_id", id));
                        break;
                    case 2:
                        storeGoodsInProduceDao.delete(new QueryWrapper<CbsStoreGoodsInProduceEntity>().eq("fk_store_goods_in_id", id));
                        break;
                    case 3:
                        storeGoodsInPurchaseDao.delete(new QueryWrapper<CbsStoreGoodsInPurchaseEntity>().eq("fk_store_goods_in_id", id));
                        break;
                }
                // ???????????????
                storeGoodsInDao.deleteById(id);
            }
        }
    }
}