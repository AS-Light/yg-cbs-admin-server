package io.renren.modules.thr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.thr.entity.ThrHsUnitEntity;

/**
 * HS标准计量单位
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
public interface ThrHsUnitService extends IService<ThrHsUnitEntity> {

    PageUtils queryIndex(ThrHsUnitEntity entity);

    ThrHsUnitEntity getOneByCode(ThrHsUnitEntity entity);
}

