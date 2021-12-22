package io.renren.modules.thr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.thr.entity.ThrCiqCodeEntity;

/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-20 14:42:22
 */
public interface ThrCiqCodeService extends IService<ThrCiqCodeEntity> {

    PageUtils queryIndex(ThrCiqCodeEntity entity);

    ThrCiqCodeEntity getOneByCode(ThrCiqCodeEntity entity);
}

