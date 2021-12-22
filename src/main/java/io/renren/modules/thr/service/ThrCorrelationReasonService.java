package io.renren.modules.thr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.thr.entity.ThrCorrelationReasonEntity;

/**
 * 关联理由
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
public interface ThrCorrelationReasonService extends IService<ThrCorrelationReasonEntity> {

    PageUtils queryIndex(ThrCorrelationReasonEntity entity);

    ThrCorrelationReasonEntity getOneByCode(ThrCorrelationReasonEntity entity);
}

