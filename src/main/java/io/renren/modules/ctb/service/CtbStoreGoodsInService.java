package io.renren.modules.ctb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.ctb.entity.CtbStoreGoodsInEntity;
import io.renren.modules.ctb.entity.CtbStoreGoodsInStatusStreamEntity;

import java.util.HashMap;

/**
 * 商品入库表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-06-03 14:57:15
 */
public interface CtbStoreGoodsInService extends IService<CtbStoreGoodsInEntity> {

    HashMap<String, Object> saveReturnId(CtbStoreGoodsInEntity entity);

    PageUtils queryIndex(CtbStoreGoodsInEntity entity);

    CtbStoreGoodsInEntity detail(Long id);

    Integer updateAllById(CtbStoreGoodsInEntity ctbStoreGoodsIn);

    void submit(CtbStoreGoodsInStatusStreamEntity statusStreamEntity) throws RuntimeException;

    Integer submitBack(CtbStoreGoodsInStatusStreamEntity statusStreamEntity);

    void check(CtbStoreGoodsInStatusStreamEntity statusStreamEntity) throws RuntimeException;

    void deleteByIds(Long[] ids);
}

