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
import io.renren.modules.ctb.service.CtbStoreGoodsOutService;
import io.renren.modules.ctb.vo.UnifiedUploadingVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service("ctbStoreGoodsOutService")
public class CtbStoreGoodsOutServiceImpl extends ServiceImpl<CtbStoreGoodsOutDao, CtbStoreGoodsOutEntity> implements CtbStoreGoodsOutService {

    private CtbStoreGoodsDao storeGoodsDao;
    private CtbStoreGoodsOutDao storeGoodsOutDao;
    private CtbStoreGoodsOutItemDao storeGoodsOutItemDao;
    private CtbStoreGoodsOutStatusStreamDao storeGoodsOutStatusStreamDao;

    private CtbMoneyOutDao moneyOutDao;
    private CommonFunction commonFunction;

    public CtbStoreGoodsOutServiceImpl(CtbStoreGoodsDao storeGoodsDao,
                                      CtbStoreGoodsOutDao storeGoodsOutDao,
                                      CtbStoreGoodsOutItemDao storeGoodsOutItemDao,
                                      CtbStoreGoodsOutStatusStreamDao storeGoodsOutStatusStreamDao,
                                      CtbMoneyOutDao moneyOutDao,
                                      CommonFunction commonFunction) {
        this.storeGoodsDao = storeGoodsDao;
        this.storeGoodsOutDao = storeGoodsOutDao;
        this.storeGoodsOutItemDao = storeGoodsOutItemDao;
        this.storeGoodsOutStatusStreamDao = storeGoodsOutStatusStreamDao;
        this.moneyOutDao = moneyOutDao;
        this.commonFunction = commonFunction;
    }

    @Override
    public PageUtils queryIndex(CtbStoreGoodsOutEntity entity) {
        IPage<CtbStoreGoodsOutEntity> page = storeGoodsOutDao.queryIndex(new QueryPage<CtbStoreGoodsOutEntity>().getPage(entity), entity);
        return new PageUtils(page);
    }

    @Override
    @Transactional
    public HashMap<String, Object> saveReturnId(CtbStoreGoodsOutEntity entity) {
        HashMap<String, Object> resultMap = new HashMap<>();
        entity.setStatus(1);
        // ???????????????
        storeGoodsOutDao.insert(entity);
        // ?????????????????????????????????
        CtbStoreGoodsOutStatusStreamEntity statusStreamEntity = new CtbStoreGoodsOutStatusStreamEntity();
        statusStreamEntity.setFkStoreGoodsOutId(entity.getId());
        statusStreamEntity.setRemark("??????");
        statusStreamEntity.setLastStatus(0);
        statusStreamEntity.setStatus(1);
        statusStreamEntity.setOperator(entity.getOperator());
        storeGoodsOutStatusStreamDao.insert(statusStreamEntity);
        resultMap.put("code", 200);
        resultMap.put("id", entity.getId());
        return resultMap;
    }

    @Override
    public CtbStoreGoodsOutEntity detail(Long id) {
        return storeGoodsOutDao.detail(id);
    }

    @Override
    @Transactional
    public Integer updateAllById(CtbStoreGoodsOutEntity entity) {
        storeGoodsOutDao.updateById(entity);
        // ????????????????????????
        if (entity.getId() != null) {
            storeGoodsOutItemDao.delete(new QueryWrapper<CtbStoreGoodsOutItemEntity>().eq("fk_store_goods_out_id", entity.getId()));
        }
        // ????????????????????????
        List<CtbStoreGoodsOutItemEntity> storeGoodsOutItemEntityList = entity.getStoreGoodsOutItemEntityList();
        for (CtbStoreGoodsOutItemEntity storeGoodsOutItemEntity : storeGoodsOutItemEntityList) {
            storeGoodsOutItemDao.insert(storeGoodsOutItemEntity);
        }
        if (entity.getImgStoreGoodsOutDeliveryOrderEntityList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CtbImgStoreGoodsOutDeliveryOrderEntity imgStoreGoodsOutDeliveryOrderEntity : entity.getImgStoreGoodsOutDeliveryOrderEntityList()) {
                imgList.add(imgStoreGoodsOutDeliveryOrderEntity.getImgUrl());
            }
            commonFunction.ctbUnifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(CtbDocumentControlEnum.TYPE_STORE_GOODS_OUT_DELIVERY_ORDER.getCode())
                    .field("fk_store_goods_out_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        if (entity.getImgStoreGoodsOutReceiptEntityList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CtbImgStoreGoodsOutReceiptEntity imgStoreGoodsOutReceiptEntity : entity.getImgStoreGoodsOutReceiptEntityList()) {
                imgList.add(imgStoreGoodsOutReceiptEntity.getImgUrl());
            }
            commonFunction.ctbUnifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(CtbDocumentControlEnum.TYPE_STORE_GOODS_OUT_RECEIPT.getCode())
                    .field("fk_store_goods_out_id")
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
    public void submit(CtbStoreGoodsOutStatusStreamEntity statusStreamEntity) throws RuntimeException {
        CtbStoreGoodsOutEntity entity = storeGoodsOutDao.detail(statusStreamEntity.getFkStoreGoodsOutId());
        String errMsg = checkStoreGoodsOutComplete(entity);
        if (errMsg != null) throw new RuntimeException(errMsg);

        CtbStoreGoodsOutStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkStoreGoodsOutId());
        if (lastStatusStreamEntity != null) {
            if (lastStatusStreamEntity.getStatus() == 1 || lastStatusStreamEntity.getStatus() == 3) {
                try {
                    entity.setStatus(2);
                    statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());
                    statusStreamEntity.setStatus(2);

                    storeGoodsOutDao.updateById(entity);
                    storeGoodsOutStatusStreamDao.insert(statusStreamEntity);
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
    public Integer submitBack(CtbStoreGoodsOutStatusStreamEntity statusStreamEntity) {
        CtbStoreGoodsOutEntity entity = storeGoodsOutDao.selectById(statusStreamEntity.getFkStoreGoodsOutId());
        CtbStoreGoodsOutStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkStoreGoodsOutId());
        if (lastStatusStreamEntity != null) {
            if (lastStatusStreamEntity.getStatus() == 2) {
                try {
                    entity.setStatus(lastStatusStreamEntity.getLastStatus());
                    statusStreamEntity.setStatus(lastStatusStreamEntity.getLastStatus());
                    statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());

                    storeGoodsOutDao.updateById(entity);
                    storeGoodsOutStatusStreamDao.insert(statusStreamEntity);
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
    public void check(CtbStoreGoodsOutStatusStreamEntity statusStreamEntity) throws RuntimeException {
        CtbStoreGoodsOutEntity entity = storeGoodsOutDao.detail(statusStreamEntity.getFkStoreGoodsOutId());
        CtbStoreGoodsOutStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkStoreGoodsOutId());
        if (lastStatusStreamEntity != null) {
            if (entity.getStatus() == 2) {
                // ????????????
                entity.setStatus(statusStreamEntity.getStatus());
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());
                storeGoodsOutDao.updateById(entity);
                storeGoodsOutStatusStreamDao.insert(statusStreamEntity);
                // ?????????????????????????????????????????????
                if (statusStreamEntity.getStatus() == 4) {
                    // ?????????????????????????????????????????????
                    for (CtbStoreGoodsOutItemEntity goodsOutItemEntity : entity.getStoreGoodsOutItemEntityList()) {
                        // ???????????????
                        CtbStoreGoodsEntity storeGoodsEntity = storeGoodsDao.selectOne(new QueryWrapper<CtbStoreGoodsEntity>().eq("fk_service_company_id", entity.getFkServiceCompanyId()).eq("fk_goods_id", goodsOutItemEntity.getFkGoodsId()));
                        if (storeGoodsEntity == null) {
                            throw new RuntimeException("??????????????????????????????????????????");
                        } else {
                            storeGoodsEntity.setGoodsStoreCount(storeGoodsEntity.getGoodsStoreCount().subtract(goodsOutItemEntity.getGoodsOutCount()));
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
    private String checkStoreGoodsOutComplete(CtbStoreGoodsOutEntity entity) {
        if (entity.getStoreGoodsOutItemEntityList().size() <= 0) {
            return "?????????????????????????????????";
        } else {
            // ???????????????????????????????????????0????????????????????????????????????????????????0
            BigDecimal totalCount = BigDecimal.ZERO;
            for (CtbStoreGoodsOutItemEntity storeGoodsOutItem : entity.getStoreGoodsOutItemEntityList()) {
                totalCount = totalCount.add(storeGoodsOutItem.getGoodsOutCount());
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
        if (entity.getImgStoreGoodsOutReceiptEntityList() == null || entity.getImgStoreGoodsOutReceiptEntityList().isEmpty())
            return "??????????????????????????????";

        return null;
    }

    private CtbStoreGoodsOutStatusStreamEntity getLastStatusStreamEntity(Long storeGoodsOutId) {
        List<CtbStoreGoodsOutStatusStreamEntity> statusStreamEntityList = storeGoodsOutStatusStreamDao.selectList(new QueryWrapper<CtbStoreGoodsOutStatusStreamEntity>().eq("fk_store_goods_out_id", storeGoodsOutId).orderByAsc("id"));
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
            CtbStoreGoodsOutEntity entity = storeGoodsOutDao.detail(id);
            if (entity != null) {
                // status??????4????????????
                Warehousing.isStatusMoreThanThe(entity.getStatus(), 4, "????????????????????????????????????");
                // ??????????????????
                for (CtbStoreGoodsOutItemEntity itemEntity : entity.getStoreGoodsOutItemEntityList()) {
                    storeGoodsOutItemDao.deleteById(itemEntity.getId());
                }
                // ???????????????
                storeGoodsOutDao.deleteById(id);
            }
        }
    }

}