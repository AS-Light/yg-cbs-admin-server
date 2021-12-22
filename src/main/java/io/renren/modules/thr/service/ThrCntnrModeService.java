package io.renren.modules.thr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.thr.entity.ThrCntnrModeEntity;

/**
 * 集装箱规格代码
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
public interface ThrCntnrModeService extends IService<ThrCntnrModeEntity> {

    PageUtils queryIndex(ThrCntnrModeEntity entity);

    ThrCntnrModeEntity getOneByCode(ThrCntnrModeEntity entity);
}

