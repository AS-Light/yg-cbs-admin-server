package io.renren.modules.thr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.thr.entity.ThrProductTypeEntity;

/**
 * 海关加工种类代码表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-12-16 12:59:16
 */
public interface ThrProductTypeService extends IService<ThrProductTypeEntity> {

    PageUtils queryIndex(ThrProductTypeEntity entity);

    ThrProductTypeEntity getOneByCode(ThrProductTypeEntity entity);
}

