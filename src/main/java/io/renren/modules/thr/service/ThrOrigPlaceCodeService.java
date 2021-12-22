package io.renren.modules.thr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.thr.entity.ThrOrigPlaceCodeEntity;

/**
 * 原产地区代码表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
public interface ThrOrigPlaceCodeService extends IService<ThrOrigPlaceCodeEntity> {

    PageUtils queryIndex(ThrOrigPlaceCodeEntity entity);

    ThrOrigPlaceCodeEntity getOneByCode(ThrOrigPlaceCodeEntity entity);
}

