package io.renren.modules.ctb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.ctb.entity.CtbExportGoodsEntity;

import java.util.Map;

/**
 * 出口货物清单
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
public interface CtbExportGoodsService extends IService<CtbExportGoodsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

