package io.renren.modules.thr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.thr.entity.ThrTrafModeEntity;

/**
 * 运输方式表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
public interface ThrTrafModeService extends IService<ThrTrafModeEntity> {

    PageUtils queryIndex(ThrTrafModeEntity entity);

    ThrTrafModeEntity getOneByCode(ThrTrafModeEntity entity);
}

