package io.renren.modules.ctb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.ctb.entity.CtbStoreGoodsOutEntity;
import io.renren.modules.ctb.entity.CtbStoreGoodsOutStatusStreamEntity;

import java.util.HashMap;

/**
 * 商品出库表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-06-03 14:57:15
 */
public interface CtbStoreGoodsOutService extends IService<CtbStoreGoodsOutEntity> {

    HashMap<String, Object> saveReturnId(CtbStoreGoodsOutEntity entity);

    PageUtils queryIndex(CtbStoreGoodsOutEntity entity);

    CtbStoreGoodsOutEntity detail(Long id);

    Integer updateAllById(CtbStoreGoodsOutEntity ctbStoreGoodsOut);

    void submit(CtbStoreGoodsOutStatusStreamEntity statusStreamEntity) throws RuntimeException;

    Integer submitBack(CtbStoreGoodsOutStatusStreamEntity statusStreamEntity);

    void check(CtbStoreGoodsOutStatusStreamEntity statusStreamEntity) throws RuntimeException;

    void deleteByIds(Long[] ids);
}

