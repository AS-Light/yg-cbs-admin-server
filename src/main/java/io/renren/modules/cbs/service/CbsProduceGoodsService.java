package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cbs.entity.CbsProduceGoodsEntity;

import java.util.Map;

/**
 * 生产的商品缓存
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-26 16:47:33
 */
public interface CbsProduceGoodsService extends IService<CbsProduceGoodsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

