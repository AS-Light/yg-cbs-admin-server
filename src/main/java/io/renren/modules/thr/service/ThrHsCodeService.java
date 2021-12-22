package io.renren.modules.thr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.thr.entity.ThrHsCodeEntity;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-20 14:42:21
 */
public interface ThrHsCodeService extends IService<ThrHsCodeEntity> {

    PageUtils queryIndex(ThrHsCodeEntity entity);

    ThrHsCodeEntity getOneByCode(ThrHsCodeEntity entity);
}

