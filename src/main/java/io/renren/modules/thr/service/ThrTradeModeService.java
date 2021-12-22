package io.renren.modules.thr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.thr.entity.ThrTradeModeEntity;

/**
 * 监管方式代码
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
public interface ThrTradeModeService extends IService<ThrTradeModeEntity> {

    PageUtils queryIndex(ThrTradeModeEntity entity);

    ThrTradeModeEntity getOneByCode(ThrTradeModeEntity entity);
}

