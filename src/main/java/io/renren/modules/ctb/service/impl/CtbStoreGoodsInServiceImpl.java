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
        // 插入入库单
        storeGoodsInDao.insert(entity);
        // 为入库创建第一个状态流
        CtbStoreGoodsInStatusStreamEntity statusStreamEntity = new CtbStoreGoodsInStatusStreamEntity();
        statusStreamEntity.setFkStoreGoodsInId(entity.getId());
        statusStreamEntity.setRemark("创建");
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
        // 删除原有入库商品
        if (entity.getId() != null) {
            storeGoodsInItemDao.delete(new QueryWrapper<CtbStoreGoodsInItemEntity>().eq("fk_store_goods_in_id", entity.getId()));
        }
        // 重新插入入库商品
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
     * 提审
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
                    throw new RuntimeException("未知异常，请联系管理员");
                }
            } else {
                throw new RuntimeException("入库单状态不合法，编辑中和不通过状态才能提交审核");
            }
        } else {
            throw new RuntimeException("没有找到这个入库单,请联系管理员");
        }
    }

    /**
     * @Description: 提审
     * 可以提审的状态：1、编辑中，3、不通过
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
     * @Description: 审核
     * 可以审核的状态：2、提审
     */
    @Override
    @Transactional
    public void check(CtbStoreGoodsInStatusStreamEntity statusStreamEntity) throws RuntimeException {
        CtbStoreGoodsInEntity entity = storeGoodsInDao.detail(statusStreamEntity.getFkStoreGoodsInId());
        CtbStoreGoodsInStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkStoreGoodsInId());
        if (lastStatusStreamEntity != null) {
            if (entity.getStatus() == 2) {
                // 变更状态
                entity.setStatus(statusStreamEntity.getStatus());
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());
                storeGoodsInDao.updateById(entity);
                storeGoodsInStatusStreamDao.insert(statusStreamEntity);
                // 状态变为已审核后，导入各类数据
                if (statusStreamEntity.getStatus() == 4) {
                    // 变更库存数量、变更料号实体数量
                    for (CtbStoreGoodsInItemEntity goodsInItemEntity : entity.getStoreGoodsInItemEntityList()) {
                        // 库存数量加
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
                    // 国内运费支出
                    if (entity.getTransFeeMoney() != null && entity.getTransFeeMoney().compareTo(BigDecimal.ZERO) > 0) {
                        CtbMoneyOutEntity moneyOutEntity = new CtbMoneyOutEntity();
                        moneyOutEntity.setFkServiceCompanyId(entity.getFkServiceCompanyId());
                        moneyOutEntity.setTitle("入库单号 - " + entity.getId() + " 国内运费");
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
                throw new RuntimeException("入库单状态不合法，请先提交审核");
            }
        } else {
            throw new RuntimeException("没有找到这个入库单,请联系管理员");
        }
    }

    /**
     * 判断入库单完整性
     */
    private String checkStoreGoodsInComplete(CtbStoreGoodsInEntity entity) {
        if (entity.getStoreGoodsInItemEntityList().size() <= 0) {
            return "入库单必须包含入库商品";
        } else {
            // 不要求每一种商品数量都大于0，但是必须有至少一种商品数量大于0
            BigDecimal totalCount = BigDecimal.ZERO;
            for (CtbStoreGoodsInItemEntity storeGoodsInItem : entity.getStoreGoodsInItemEntityList()) {
                totalCount = totalCount.add(storeGoodsInItem.getGoodsInCount());
            }
            if (totalCount.compareTo(BigDecimal.ZERO) <= 0) {
                return "入库商品数量必须大于0";
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
        if (entity.getImgStoreGoodsInReceiptEntityList() == null || entity.getImgStoreGoodsInReceiptEntityList().isEmpty())
            return "签收证明附件不能为空";

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
                // status小于4才能删除
                Warehousing.isStatusMoreThanThe(entity.getStatus(), 4, "入库单审核通过后无法删除");
                // 删除其下商品
                for (CtbStoreGoodsInItemEntity itemEntity : entity.getStoreGoodsInItemEntityList()) {
                    storeGoodsInItemDao.deleteById(itemEntity.getId());
                }
                // 删除出库单
                storeGoodsInDao.deleteById(id);
            }
        }
    }

}