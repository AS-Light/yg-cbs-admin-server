package io.renren.modules.thr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.thr.entity.ThrCiqDomeOriginEntity;

/**
 * 国内原产地编码
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
public interface ThrCiqDomeOriginService extends IService<ThrCiqDomeOriginEntity> {

    PageUtils queryIndex(ThrCiqDomeOriginEntity entity);

    ThrCiqDomeOriginEntity getOneByCode(ThrCiqDomeOriginEntity entity);
}

