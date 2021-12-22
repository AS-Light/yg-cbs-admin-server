package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cbs.entity.CbsSellGoodsEntity;

import java.util.Map;

/**
 * 出口货物清单
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-02-20 17:14:19
 */
public interface CbsSellGoodsService extends IService<CbsSellGoodsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

