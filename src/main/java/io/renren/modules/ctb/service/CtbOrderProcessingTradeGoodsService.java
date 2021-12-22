package io.renren.modules.ctb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.ctb.entity.CtbOrderProcessingTradeGoodsEntity;

/**
 * 加贸商品名录表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
public interface CtbOrderProcessingTradeGoodsService extends IService<CtbOrderProcessingTradeGoodsEntity> {

    CtbOrderProcessingTradeGoodsEntity detail(Long id);
}

