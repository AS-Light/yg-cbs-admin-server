package io.renren.modules.thr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.thr.entity.ThrCountryCodeEntity;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-12-21 15:45:17
 */
public interface ThrCountryCodeService extends IService<ThrCountryCodeEntity> {

    PageUtils queryIndex(ThrCountryCodeEntity entity);

    ThrCountryCodeEntity getOneByCode(ThrCountryCodeEntity entity);
}

