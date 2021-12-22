package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cbs.entity.CbsProduceGoodsStreamItemEntity;

import java.util.Map;

/**
 * cbs_produce_goods_stream 的子表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-26 16:15:37
 */
public interface CbsProduceGoodsStreamItemService extends IService<CbsProduceGoodsStreamItemEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

