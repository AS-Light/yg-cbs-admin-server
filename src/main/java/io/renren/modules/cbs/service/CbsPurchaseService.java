package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cbs.entity.CbsPurchaseEntity;
import io.renren.modules.cbs.entity.CbsPurchaseStatusStreamEntity;

import java.util.HashMap;
import java.util.List;

/**
 * 商品入库表，
 * 继承表包含cbs_store_goods_in_import、cbs_store_goods_in_produce、cbs_store_goods_in_purchase_domestic，
 * 原则上本表中一条数据必在其子表中存在一条数据，反之亦然
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-02-20 17:14:19
 */
public interface CbsPurchaseService extends IService<CbsPurchaseEntity> {

    PageUtils queryIndex(CbsPurchaseEntity entity);

    List<CbsPurchaseEntity> queryForStoreIn();

    Long saveReturnId(CbsPurchaseEntity entity);

    CbsPurchaseEntity detail(Long id);

    Object updateAllById(CbsPurchaseEntity entity);

    void submit(CbsPurchaseStatusStreamEntity statusStreamEntity) throws RuntimeException;

    Integer submitBack(CbsPurchaseStatusStreamEntity statusStreamEntity);

    HashMap<String, Object> check(CbsPurchaseStatusStreamEntity statusStreamEntity, Long operator);

    void deleteByIds(Long[] ids);
}

