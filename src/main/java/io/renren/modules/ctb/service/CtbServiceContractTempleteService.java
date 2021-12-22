package io.renren.modules.ctb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.ctb.entity.CtbServiceContractTempleteEntity;

import java.util.Map;

/**
 * 报关行货运代理合同模板，原则上报关行只有一个模板，有一个ctb_tenant_id=-1的模板作为公共模板
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
public interface CtbServiceContractTempleteService extends IService<CtbServiceContractTempleteEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

