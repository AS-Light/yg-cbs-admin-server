package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.cbs.entity.CbsHomeEntity;

/**
 * 首页
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-09 11:16:37
 */
public interface CbsHomeService extends IService<CbsHomeEntity> {

    CbsHomeEntity total(Long tenantId);

    CbsHomeEntity expected(Long tenantId, String[] months);

    CbsHomeEntity actual(Long tenantId, String[] months);

    CbsHomeEntity upcoming(Long tenantId);
}

