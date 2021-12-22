package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cbs.entity.CbsStoreGoodsOutEntity;
import io.renren.modules.cbs.entity.CbsStoreGoodsOutStatusStreamEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * 商品出库表，
继承表包含cbs_store_goods_out_produce、cbs_store_goods_out_export，
原则上本表中一条数据必在其子表中存在一条数据，反之亦然
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-22 16:54:51
 */
public interface CbsStoreGoodsOutService extends IService<CbsStoreGoodsOutEntity> {

    PageUtils queryIndex(CbsStoreGoodsOutEntity entity);

    PageUtils queryPage(Map<String, Object> params);

    CbsStoreGoodsOutEntity detail(Long id);

    HashMap<String, Object> saveReturnId(CbsStoreGoodsOutEntity storeGoodsOutEntity);

    Integer updateAllById(CbsStoreGoodsOutEntity cbsStoreGoodsOut);

    void submit(CbsStoreGoodsOutStatusStreamEntity statusStreamEntity) throws RuntimeException;

    Integer submitBack(CbsStoreGoodsOutStatusStreamEntity statusStreamEntity);

    void check(CbsStoreGoodsOutStatusStreamEntity statusStreamEntity) throws RuntimeException;

    void deleteByIds(Long[] ids);
}

