package io.renren.modules.ctb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.ctb.entity.CtbPriceListItemEntity;

import java.util.Map;

/**
 * 报关行报价单表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
public interface CtbPriceListItemService extends IService<CtbPriceListItemEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryIndex(CtbPriceListItemEntity entity);

    String generatePDF(Long id);
}

