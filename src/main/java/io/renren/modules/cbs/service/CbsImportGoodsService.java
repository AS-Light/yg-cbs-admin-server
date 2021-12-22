package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cbs.entity.CbsImportGoodsEntity;

import java.util.Map;

/**
 * 原材进货记录
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-18 15:18:27
 */
public interface CbsImportGoodsService extends IService<CbsImportGoodsEntity> {

    PageUtils queryPage(Map<String, Object> params);

    CbsImportGoodsEntity selectById(Long id);
}

