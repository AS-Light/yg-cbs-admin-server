package io.renren.modules.thr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.thr.entity.ThrPurposeEntity;

/**
 * 用途代码
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
public interface ThrPurposeService extends IService<ThrPurposeEntity> {

    PageUtils queryIndex(ThrPurposeEntity entity);

    ThrPurposeEntity getOneByCode(ThrPurposeEntity entity);
}

