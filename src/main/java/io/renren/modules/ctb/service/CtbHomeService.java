package io.renren.modules.ctb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.ctb.entity.CtbHomeEntity;

/**
 * 首页
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-09 11:16:37
 */
public interface CtbHomeService extends IService<CtbHomeEntity> {

    CtbHomeEntity total(Long tenantId);

    CtbHomeEntity canadaTradeApplyToCustoms(Long tenantId, String[] months);

    CtbHomeEntity actual(Long tenantId, String[] months);

}

