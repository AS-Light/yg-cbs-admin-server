package io.renren.modules.thr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.thr.entity.ThrDutyModeEntity;

/**
 * 征免方式
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
public interface ThrDutyModeService extends IService<ThrDutyModeEntity> {

    PageUtils queryIndex(ThrDutyModeEntity entity);

    ThrDutyModeEntity getOneByCode(ThrDutyModeEntity entity);
}

