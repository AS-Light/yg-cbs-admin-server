package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cbs.entity.CbsStoreGoodsInEntity;
import io.renren.modules.cbs.entity.CbsStoreGoodsInImportEntity;
import io.renren.modules.cbs.entity.CbsStoreGoodsInStatusStreamEntity;

import java.util.HashMap;
import java.util.List;

/**
 * 商品入库表，
继承表包含cbs_store_goods_in_import、cbs_store_goods_in_produce、cbs_store_goods_in_purchase_domestic，
原则上本表中一条数据必在其子表中存在一条数据，反之亦然
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-22 16:54:51
 */
public interface CbsStoreGoodsInService extends IService<CbsStoreGoodsInEntity> {

    HashMap<String, Object> saveReturnId(CbsStoreGoodsInEntity entity);

    PageUtils queryIndex(CbsStoreGoodsInEntity entity);

    CbsStoreGoodsInEntity detail(Long id);

    Integer updateAllById(CbsStoreGoodsInEntity cbsStoreGoodsIn);

    List<CbsStoreGoodsInImportEntity> listImportInByOutToProduceId(Long produceId);

    void submit(CbsStoreGoodsInStatusStreamEntity statusStreamEntity) throws RuntimeException;

    Integer submitBack(CbsStoreGoodsInStatusStreamEntity statusStreamEntity);

    void check(CbsStoreGoodsInStatusStreamEntity statusStreamEntity) throws RuntimeException;

    void deleteByIds(Long[] ids);
}

