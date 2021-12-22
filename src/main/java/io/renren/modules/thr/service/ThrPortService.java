package io.renren.modules.thr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.thr.entity.ThrPortEntity;

/**
 * 国际口岸（亦表示：港口、启运口岸、经停口岸、国际抵达口岸等）
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
public interface ThrPortService extends IService<ThrPortEntity> {

    PageUtils queryIndex(ThrPortEntity entity);

    ThrPortEntity getOneByCode(ThrPortEntity entity);
}

