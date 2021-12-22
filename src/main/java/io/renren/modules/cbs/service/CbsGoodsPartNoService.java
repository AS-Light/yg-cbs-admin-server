package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cbs.entity.CbsGoodsPartNoEntity;

import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-07 18:33:29
 */
public interface CbsGoodsPartNoService extends IService<CbsGoodsPartNoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

