package io.renren.modules.thr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.thr.entity.ThrLicenseDocusEntity;

/**
 * 随附单证类型代码表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-06 18:13:02
 */
public interface ThrLicenseDocusService extends IService<ThrLicenseDocusEntity> {

    PageUtils queryIndex(ThrLicenseDocusEntity entity);

    ThrLicenseDocusEntity getOneByCode(ThrLicenseDocusEntity entity);
}

