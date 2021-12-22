package io.renren.modules.ctb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.ctb.entity.CtbGoodsPartNoEntity;

import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:51
 */
public interface CtbGoodsPartNoService extends IService<CtbGoodsPartNoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

