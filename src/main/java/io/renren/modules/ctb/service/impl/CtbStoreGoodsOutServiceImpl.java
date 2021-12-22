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
        // 插入出库单
        storeGoodsOutDao.insert(entity);
        // 为出库创建第一个状态流
        CtbStoreGoodsOutStatusStreamEntity statusStreamEntity = new CtbStoreGoodsOutStatusStreamEntity();
        statusStreamEntity.setFkStoreGoodsOutId(entity.getId());
        statusStreamEntity.setRemark("创建");
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
        // 删除原有出库商品
        if (entity.getId() != null) {
            storeGoodsOutItemDao.delete(new QueryWrapper<CtbStoreGoodsOutItemEntity>().eq("fk_store_goods_out_id", entity.getId()));
        }
        // 重新插入出库商品
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
     * 提审
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
                    throw new RuntimeException("未知异常，请联系管理员");
                }
            } else {
                throw new RuntimeException("出库单状态不合法，编辑中和不通过状态才能提交审核");
            }
        } else {
            throw new RuntimeException("没有找到这个出库单,请联系管理员");
        }
    }

    /**
     * @Description: 提审
     * 可以提审的状态：1、编辑中，3、不通过
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
     * @Description: 审核
     * 可以审核的状态：2、提审
     */
    @Override
    @Transactional
    public void check(CtbStoreGoodsOutStatusStreamEntity statusStreamEntity) throws RuntimeException {
        CtbStoreGoodsOutEntity entity = storeGoodsOutDao.detail(statusStreamEntity.getFkStoreGoodsOutId());
        CtbStoreGoodsOutStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkStoreGoodsOutId());
        if (lastStatusStreamEntity != null) {
            if (entity.getStatus() == 2) {
                // 变更状态
                entity.setStatus(statusStreamEntity.getStatus());
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());
                storeGoodsOutDao.updateById(entity);
                storeGoodsOutStatusStreamDao.insert(statusStreamEntity);
                // 状态变为已审核后，导入各类数据
                if (statusStreamEntity.getStatus() == 4) {
                    // 变更库存数量、变更料号实体数量
                    for (CtbStoreGoodsOutItemEntity goodsOutItemEntity : entity.getStoreGoodsOutItemEntityList()) {
                        // 库存数量加
                        CtbStoreGoodsEntity storeGoodsEntity = storeGoodsDao.selectOne(new QueryWrapper<CtbStoreGoodsEntity>().eq("fk_service_company_id", entity.getFkServiceCompanyId()).eq("fk_goods_id", goodsOutItemEntity.getFkGoodsId()));
                        if (storeGoodsEntity == null) {
                            throw new RuntimeException("库存不足，请重试或联系管理员");
                        } else {
                            storeGoodsEntity.setGoodsStoreCount(storeGoodsEntity.getGoodsStoreCount().subtract(goodsOutItemEntity.getGoodsOutCount()));
                            storeGoodsDao.updateById(storeGoodsEntity);
                        }
                    }
                    // 国内运费支出
                    if (entity.getTransFeeMoney() != null && entity.getTransFeeMoney().compareTo(BigDecimal.ZERO) > 0) {
                        CtbMoneyOutEntity moneyOutEntity = new CtbMoneyOutEntity();
                        moneyOutEntity.setFkServiceCompanyId(entity.getFkServiceCompanyId());
                        moneyOutEntity.setTitle("出库单号 - " + entity.getId() + " 国内运费");
                        moneyOutEntity.setCurrencyCode("142"/*人民币*/);
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
                throw new RuntimeException("出库单状态不合法，请先提交审核");
            }
        } else {
            throw new RuntimeException("没有找到这个出库单,请联系管理员");
        }
    }

    /**
     * 判断出库单完整性
     */
    private String checkStoreGoodsOutComplete(CtbStoreGoodsOutEntity entity) {
        if (entity.getStoreGoodsOutItemEntityList().size() <= 0) {
            return "出库单必须包含出库商品";
        } else {
            // 不要求每一种商品数量都大于0，但是必须有至少一种商品数量大于0
            BigDecimal totalCount = BigDecimal.ZERO;
            for (CtbStoreGoodsOutItemEntity storeGoodsOutItem : entity.getStoreGoodsOutItemEntityList()) {
                totalCount = totalCount.add(storeGoodsOutItem.getGoodsOutCount());
            }
            if (totalCount.compareTo(BigDecimal.ZERO) <= 0) {
                return "出库商品数量必须大于0";
            }
        }
        // 运输公司
        if (entity.getTransitCompanyEntity() == null) return "运输公司不能为空";
        // 提货
        if (entity.getStartAddress() == null) return "提货地址不能为空";
        if (entity.getStartContact() == null) return "提货联系人不能为空";
        if (entity.getStartPhone() == null) return "提货联系电话不能为空";
        // 送达
        if (entity.getEndAddress() == null) return "送达地址不能为空";
        if (entity.getEndContact() == null) return "送达联系人不能为空";
        if (entity.getEndPhone() == null) return "送达联系电话不能为空";
        // 签收附件
        if (entity.getImgStoreGoodsOutReceiptEntityList() == null || entity.getImgStoreGoodsOutReceiptEntityList().isEmpty())
            return "签收证明附件不能为空";

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
                // status小于4才能删除
                Warehousing.isStatusMoreThanThe(entity.getStatus(), 4, "出库单审核通过后无法删除");
                // 删除其下商品
                for (CtbStoreGoodsOutItemEntity itemEntity : entity.getStoreGoodsOutItemEntityList()) {
                    storeGoodsOutItemDao.deleteById(itemEntity.getId());
                }
                // 删除出库单
                storeGoodsOutDao.deleteById(id);
            }
        }
    }

}