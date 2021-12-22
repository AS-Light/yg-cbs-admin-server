package io.renren.modules.ctb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.ctb.entity.CtbStoreGoodsEntity;

/**
 * 产品库存表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-06-03 14:57:15
 */
public interface CtbStoreGoodsService extends IService<CtbStoreGoodsEntity> {
    PageUtils queryIndex(CtbStoreGoodsEntity entity);

    CtbStoreGoodsEntity detail(Long id);
}

