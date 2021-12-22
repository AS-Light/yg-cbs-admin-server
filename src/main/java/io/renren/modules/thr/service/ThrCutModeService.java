package io.renren.modules.thr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.thr.entity.ThrCutModeEntity;

/**
 * 征免性质表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
public interface ThrCutModeService extends IService<ThrCutModeEntity> {

    PageUtils queryIndex(ThrCutModeEntity entity);

    ThrCutModeEntity getOneByCode(ThrCutModeEntity entity);
}

