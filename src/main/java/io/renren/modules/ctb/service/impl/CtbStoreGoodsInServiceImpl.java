package io.renren.modules.ctb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.enumeration.CtbDocumentControlEnum;
import io.renren.common.utils.CommonFunction;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.common.validator.Warehousing;
import io.renren.modules.ctb.dao.*;
import io.renren.modules.ctb.entity.*;
import io.renren.modules.ctb.service.CtbStoreGoodsInService;
import io.renren.modules.ctb.vo.UnifiedUploadingVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service("ctbStoreGoodsInService")
public class CtbStoreGoodsInServiceImpl extends ServiceImpl<CtbStoreGoodsInDao, CtbStoreGoodsInEntity> implements CtbStoreGoodsInService {

    private CtbStoreGoodsDao storeGoodsDao;
    private CtbStoreGoodsInDao storeGoodsInDao;
    private CtbStoreGoodsInItemDao storeGoodsInItemDao;
    private CtbStoreGoodsInStatusStreamDao storeGoodsInStatusStreamDao;

    private CtbMoneyOutDao moneyOutDao;
    private CommonFunction commonFunction;

    public CtbStoreGoodsInServiceImpl(CtbStoreGoodsDao storeGoodsDao,
                                      CtbStoreGoodsInDao storeGoodsInDao,
                                      CtbStoreGoodsInItemDao storeGoodsInItemDao,
                                      CtbStoreGoodsInStatusStreamDao storeGoodsInStatusStreamDao,
                                      CtbGoodsPartNoDao goodsPartNoDao,
                                      CtbMoneyOutDao moneyOutDao,
                                      CommonFunction commonFunction) {
        this.storeGoodsDao = storeGoodsDao;
        this.storeGoodsInDao = storeGoodsInDao;
        this.storeGoodsInItemDao = storeGoodsInItemDao;
        this.storeGoodsInStatusStreamDao = storeGoodsInStatusStreamDao;
        this.moneyOutDao = moneyOutDao;
        this.commonFunction = commonFunction;
    }

    @Override
    public PageUtils queryIndex(CtbStoreGoodsInEntity entity) {
        IPage<CtbStoreGoodsInEntity> page = storeGoodsInDao.queryIndex(new QueryPage<CtbStoreGoodsInEntity>().getPage(entity), entity);
        return new PageUtils(page);
    }

    @Override
    @Transactional
    public HashMap<String, Object> saveReturnId(CtbStoreGoodsInEntity entity) {
        HashMap<String, Object> resultMap = new HashMap<>();
        entity.setStatus(1);
        // ???????????????
        storeGoodsInDao.insert(entity);
        // ?????????????????????????????????
        CtbStoreGoodsInStatusStreamEntity statusStreamEntity = new CtbStoreGoodsInStatusStreamEntity();
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
    public CtbStoreGoodsInEntity detail(Long id) {
        return storeGoodsInDao.detail(id);
    }

    @Override
    @Transactional
    public Integer updateAllById(CtbStoreGoodsInEntity entity) {
        storeGoodsInDao.updateById(entity);
        // ????????????????????????
        if (entity.getId() != null) {
            storeGoodsInItemDao.delete(new QueryWrapper<CtbStoreGoodsInItemEntity>().eq("fk_store_goods_in_id", entity.getId()));
        }
        // ????????????????????????
        List<CtbStoreGoodsInItemEntity> storeGoodsInItemEntityList = entity.getStoreGoodsInItemEntityList();
        for (CtbStoreGoodsInItemEntity storeGoodsInItemEntity : storeGoodsInItemEntityList) {
            storeGoodsInItemDao.insert(storeGoodsInItemEntity);
        }
        if (entity.getImgStoreGoodsInDeliveryOrderEntityList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CtbImgStoreGoodsInDeliveryOrderEntity imgStoreGoodsInDeliveryOrderEntity : entity.getImgStoreGoodsInDeliveryOrderEntityList()) {
                imgList.add(imgStoreGoodsInDeliveryOrderEntity.getImgUrl());
            }
            commonFunction.ctbUnifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(CtbDocumentControlEnum.TYPE_STORE_GOODS_IN_DELIVERY_ORDER.getCode())
                    .field("fk_store_goods_in_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        if (entity.getImgStoreGoodsInReceiptEntityList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CtbImgStoreGoodsInReceiptEntity imgStoreGoodsInReceiptEntity : entity.getImgStoreGoodsInReceiptEntityList()) {
                imgList.add(imgStoreGoodsInReceiptEntity.getImgUrl());
            }
            commonFunction.ctbUnifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(CtbDocumentControlEnum.TYPE_STORE_GOODS_IN_RECEIPT.getCode())
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
    public void submit(CtbStoreGoodsInStatusStreamEntity statusStreamEntity) throws RuntimeException {
        CtbStoreGoodsInEntity entity = storeGoodsInDao.detail(statusStreamEntity.getFkStoreGoodsInId());
        String errMsg = checkStoreGoodsInComplete(entity);
        if (errMsg != null) throw new RuntimeException(errMsg);

        CtbStoreGoodsInStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkStoreGoodsInId());
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
    public Integer submitBack(CtbStoreGoodsInStatusStreamEntity statusStreamEntity) {
        CtbStoreGoodsInEntity entity = storeGoodsInDao.selectById(statusStreamEntity.getFkStoreGoodsInId());
        CtbStoreGoodsInStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkStoreGoodsInId());
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
    public void check(CtbStoreGoodsInStatusStreamEntity statusStreamEntity) throws RuntimeException {
        CtbStoreGoodsInEntity entity = storeGoodsInDao.detail(statusStreamEntity.getFkStoreGoodsInId());
        CtbStoreGoodsInStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkStoreGoodsInId());
        if (lastStatusStreamEntity != null) {
            if (entity.getStatus() == 2) {
                // ????????????
                entity.setStatus(statusStreamEntity.getStatus());
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());
                storeGoodsInDao.updateById(entity);
                storeGoodsInStatusStreamDao.insert(statusStreamEntity);
                // ?????????????????????????????????????????????
                if (statusStreamEntity.getStatus() == 4) {
                    // ?????????????????????????????????????????????
                    for (CtbStoreGoodsInItemEntity goodsInItemEntity : entity.getStoreGoodsInItemEntityList()) {
                        // ???????????????
                        CtbStoreGoodsEntity storeGoodsEntity = storeGoodsDao.selectOne(new QueryWrapper<CtbStoreGoodsEntity>().eq("fk_service_company_id", entity.getFkServiceCompanyId()).eq("fk_goods_id", goodsInItemEntity.getFkGoodsId()));
                        if (storeGoodsEntity == null) {
                            storeGoodsEntity = new CtbStoreGoodsEntity();
                            storeGoodsEntity.setFkServiceCompanyId(entity.getFkServiceCompanyId());
                            storeGoodsEntity.setFkGoodsId(goodsInItemEntity.getFkGoodsId());
                            storeGoodsEntity.setGoodsStoreCount(goodsInItemEntity.getGoodsInCount());
                            storeGoodsDao.insert(storeGoodsEntity);
                        } else {
                            storeGoodsEntity.setGoodsStoreCount(storeGoodsEntity.getGoodsStoreCount().add(goodsInItemEntity.getGoodsInCount()));
                            storeGoodsDao.updateById(storeGoodsEntity);
                        }
                    }
                    // ??????????????????
                    if (entity.getTransFeeMoney() != null && entity.getTransFeeMoney().compareTo(BigDecimal.ZERO) > 0) {
                        CtbMoneyOutEntity moneyOutEntity = new CtbMoneyOutEntity();
                        moneyOutEntity.setFkServiceCompanyId(entity.getFkServiceCompanyId());
                        moneyOutEntity.setTitle("???????????? - " + entity.getId() + " ????????????");
                        moneyOutEntity.setCurrencyCode("142"/*?????????*/);
                        moneyOutEntity.setStatus(1);
                        moneyOutEntity.setOperator(statusStreamEntity.getOperator());
                        moneyOutEntity.setUnitMoney(entity.getTransFeeMoney());
                        moneyOutEntity.setCount(new BigDecimal(1));
                        moneyOutEntity.setMoney(entity.getTransFeeMoney());
                        moneyOutEntity.setCny(entity.getTransFeeMoney());
                        moneyOutEntity.setIsReimbursement(false);
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
     * ????????????????????????
     */
    private String checkStoreGoodsInComplete(CtbStoreGoodsInEntity entity) {
        if (entity.getStoreGoodsInItemEntityList().size() <= 0) {
            return "?????????????????????????????????";
        } else {
            // ???????????????????????????????????????0????????????????????????????????????????????????0
            BigDecimal totalCount = BigDecimal.ZERO;
            for (CtbStoreGoodsInItemEntity storeGoodsInItem : entity.getStoreGoodsInItemEntityList()) {
                totalCount = totalCount.add(storeGoodsInItem.getGoodsInCount());
            }
            if (totalCount.compareTo(BigDecimal.ZERO) <= 0) {
                return "??????????????????????????????0";
            }
        }
        // ????????????
        if (entity.getTransitCompanyEntity() == null) return "????????????????????????";
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

    private CtbStoreGoodsInStatusStreamEntity getLastStatusStreamEntity(Long storeGoodsInId) {
        List<CtbStoreGoodsInStatusStreamEntity> statusStreamEntityList = storeGoodsInStatusStreamDao.selectList(new QueryWrapper<CtbStoreGoodsInStatusStreamEntity>().eq("fk_store_goods_in_id", storeGoodsInId).orderByAsc("id"));
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
            CtbStoreGoodsInEntity entity = storeGoodsInDao.detail(id);
            if (entity != null) {
                // status??????4????????????
                Warehousing.isStatusMoreThanThe(entity.getStatus(), 4, "????????????????????????????????????");
                // ??????????????????
                for (CtbStoreGoodsInItemEntity itemEntity : entity.getStoreGoodsInItemEntityList()) {
                    storeGoodsInItemDao.deleteById(itemEntity.getId());
                }
                // ???????????????
                storeGoodsInDao.deleteById(id);
            }
        }
    }

}