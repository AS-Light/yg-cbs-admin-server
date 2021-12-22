package io.renren.modules.ctb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.ctb.entity.CtbImportGoodsEntity;

import java.util.Map;

/**
 * 进口货物清单
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
public interface CtbImportGoodsService extends IService<CtbImportGoodsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

