package io.renren.modules.thr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.thr.entity.ThrTransModeEntity;

/**
 * 成交方式
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
public interface ThrTransModeService extends IService<ThrTransModeEntity> {

    PageUtils queryIndex(ThrTransModeEntity entity);

    ThrTransModeEntity getOneByCode(ThrTransModeEntity entity);
}

