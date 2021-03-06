package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.cbs.dao.*;
import io.renren.modules.cbs.entity.*;
import io.renren.modules.cbs.service.CbsProduceGoodsStreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("cbsProduceGoodsStreamService")
public class CbsProduceGoodsStreamServiceImpl extends ServiceImpl<CbsProduceGoodsStreamDao, CbsProduceGoodsStreamEntity> implements CbsProduceGoodsStreamService {
    private CbsProduceDao produceDao;
    private CbsProduceGoodsDao produceGoodsDao;
    private CbsProduceGoodsStreamDao produceGoodsStreamDao;
    private CbsProduceGoodsStreamItemDao produceGoodsStreamItemDao;
    private CbsGoodsPartNoDao goodsPartNoDao;

    @Autowired
    public void setProduceDao(CbsProduceDao produceDao) {
        this.produceDao = produceDao;
    }

    @Autowired
    public void setProduceGoodsDao(CbsProduceGoodsDao produceGoodsDao) {
        this.produceGoodsDao = produceGoodsDao;
    }

    @Autowired
    public void setProduceGoodsStreamDao(CbsProduceGoodsStreamDao produceGoodsStreamDao) {
        this.produceGoodsStreamDao = produceGoodsStreamDao;
    }

    @Autowired
    public void setProduceGoodsStreamItemDao(CbsProduceGoodsStreamItemDao produceGoodsStreamItemDao) {
        this.produceGoodsStreamItemDao = produceGoodsStreamItemDao;
    }

    @Autowired
    public void setGoodsPartNoDao(CbsGoodsPartNoDao goodsPartNoDao) {
        this.goodsPartNoDao = goodsPartNoDao;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CbsProduceGoodsStreamEntity> page = this.page(
                new Query<CbsProduceGoodsStreamEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public CbsProduceGoodsStreamEntity selectById(Long id) {
        return produceGoodsStreamDao.detail(id);
    }

    @Override
    public Long preCreate(CbsProduceGoodsStreamEntity entity) {
        CbsProduceEntity produceEntity = produceDao.detail(entity.getFkProduceId());
        if (produceEntity.getProduceGoodsEntityList() != null) {
            for (CbsProduceGoodsStreamEntity goodsStreamEntity : produceEntity.getProduceGoodsStreamEntityList()) {
                if (goodsStreamEntity.getStatus() == 1) {
                    return goodsStreamEntity.getId();
                }
            }
        }
        return null;
    }

    @Override
    @Transactional
    public HashMap<String, Object> saveOrUpdateWithItems(CbsProduceGoodsStreamEntity entity) {
        HashMap<String, Object> resultMap = checkBeforeUpdate(entity);
        if (resultMap.containsKey("code")) {
            return resultMap;
        }
        saveOrUpdate(entity);
        produceGoodsStreamItemDao.delete(new QueryWrapper<CbsProduceGoodsStreamItemEntity>().eq("fk_produce_goods_stream_id", entity.getId()));
        if (entity.getItemEntityList() != null) {
            entity.getItemEntityList().forEach(itemEntity -> {
                itemEntity.setFkProduceGoodsStreamId(entity.getId());
                produceGoodsStreamItemDao.insert(itemEntity);
            });
        }
        resultMap.put("code", 200);
        return resultMap;
    }

    @Override
    @Transactional
    public HashMap<String, Object> check(Long id) {
        CbsProduceGoodsStreamEntity entity = baseMapper.detail(id);
        HashMap<String, Object> resultMap = checkBeforeUpdate(entity);
        if (resultMap.containsKey("code")) {
            return resultMap;
        }
        CbsProduceEntity produceEntity = produceDao.detail(entity.getFkProduceId());
        if (entity.getStatus() == 2) {
            resultMap.put("code", 500);
            resultMap.put("msg", "????????????????????????");
            return resultMap;
        } else {
            for (CbsProduceGoodsStreamItemEntity itemEntity : entity.getItemEntityList()) {
                CbsProduceGoodsEntity goodsEntity = findProduceGoods(produceEntity.getProduceGoodsEntityList(), itemEntity.getGoodsPartNo());
                if (goodsEntity == null) {
                    continue;
                }
                if (itemEntity.getType() == 1) {
                    // ??????????????????????????????????????????????????????
                    goodsEntity.setStoreCount(goodsEntity.getStoreCount().subtract(itemEntity.getCount()));
                } else {
                    // ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                    goodsEntity.setStoreCount(goodsEntity.getStoreCount().add(itemEntity.getCount()));
                    goodsEntity.setTotalCount(goodsEntity.getTotalCount().add(itemEntity.getCount()));
                    goodsEntity.getPartNoEntity().setProduceCount(goodsEntity.getPartNoEntity().getProduceCount().add(itemEntity.getCount()));
                }
                produceGoodsDao.updateById(goodsEntity);
            }
            // ????????????
            entity.setStatus(2);
            produceGoodsStreamDao.updateById(entity);
            resultMap.put("code", 200);
            return resultMap;
        }
    }

    @Override
    @Transactional
    public void deleteByIds(Long[] ids) {
        List<Long> itemIds = produceGoodsStreamItemDao.listIdsByProduceStreamIds(ids);
        if (itemIds != null && !itemIds.isEmpty()) {
            produceGoodsStreamItemDao.deleteBatchIds(itemIds);
        }
        removeByIds(Arrays.asList(ids));
    }

    private HashMap<String, Object> checkBeforeUpdate(CbsProduceGoodsStreamEntity entity) {
        HashMap<String, Object> resultMap = new HashMap<>();
        if (entity.getItemEntityList() == null || entity.getItemEntityList().isEmpty()) {
            resultMap.put("code", 500);
            resultMap.put("msg", "?????????????????????????????????????????????????????????????????????????????????");
            return resultMap;
        } else {
            // ????????????????????????????????????????????????????????????
            boolean hasMaterial = false;
            boolean hasProduct = false;
            for (CbsProduceGoodsStreamItemEntity itemEntity : entity.getItemEntityList()) {
                if (itemEntity.getType() == 1 && itemEntity.getCount().compareTo(BigDecimal.ZERO) > 0) {
                    hasMaterial = true;
                }
                if (itemEntity.getType() == 2 && itemEntity.getCount().compareTo(BigDecimal.ZERO) > 0) {
                    hasProduct = true;
                }
            }
            if (!hasMaterial || !hasProduct) {
                resultMap.put("code", 500);
                resultMap.put("msg", "???????????????????????????????????????????????????????????????????????????????????????????????????0???????????????????????????????????????0???????????????????????????");
                return resultMap;
            }
            // ?????????????????????+???????????????>???????????????????????????
            if (entity.getForceToChange() == null || !entity.getForceToChange()) {
                CbsProduceEntity produceEntity = produceDao.detail(entity.getFkProduceId());
                for (CbsProduceGoodsStreamItemEntity itemEntity : entity.getItemEntityList()) {
                    for (CbsProduceGoodsEntity produceGoodsEntity : produceEntity.getProduceGoodsEntityList()) {
                        if (itemEntity.getType() == 2 && itemEntity.getGoodsPartNo().equals(produceGoodsEntity.getGoodsPartNo())) {
                            if (itemEntity.getCount().add(produceGoodsEntity.getTotalCount()).compareTo(produceGoodsEntity.getPlanCount()) == 1) {
                                resultMap.put("code", 300);
                                resultMap.put("msg", "???????????????" + produceGoodsEntity.getPartNoEntity().getGoodsEntity().getNickname() + "???????????????????????????????????????????????????????????????????????????????????????????????????");
                                return resultMap;
                            }
                        }
                    }
                }
            }
        }
        return resultMap;
    }

    private CbsProduceGoodsEntity findProduceGoods(List<CbsProduceGoodsEntity> list, Long goodsPartNo) {
        for (CbsProduceGoodsEntity goodsEntity : list) {
            if (goodsEntity.getGoodsPartNo().equals(goodsPartNo)) {
                return goodsEntity;
            }
        }
        return null;
    }

}