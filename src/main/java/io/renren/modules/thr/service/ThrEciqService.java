package io.renren.modules.thr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.thr.entity.ThrEciqEntity;

/**
 * 中华人民共和国行政区划代码表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
public interface ThrEciqService extends IService<ThrEciqEntity> {

    PageUtils queryIndex(ThrEciqEntity entity);

    ThrEciqEntity getOneByCode(ThrEciqEntity entity);
}

