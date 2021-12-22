package io.renren.modules.ctb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.ctb.entity.CtbPriceListTempleteEntity;

import java.util.Map;

/**
 * 报关行报价单模板，原则上报关行只有一个模板，有一个ctb_tenant_id=-1的模板作为公共模板
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
public interface CtbPriceListTempleteService extends IService<CtbPriceListTempleteEntity> {

    PageUtils queryPage(Map<String, Object> params);

    CtbPriceListTempleteEntity getByTenantId(Long tenantId);
}

