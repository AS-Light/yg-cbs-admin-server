package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cbs.entity.CbsStoreGoodsOutItemEntity;

import java.util.Map;

/**
 * 原料出库表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-22 16:54:51
 */
public interface CbsStoreGoodsOutItemService extends IService<CbsStoreGoodsOutItemEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

