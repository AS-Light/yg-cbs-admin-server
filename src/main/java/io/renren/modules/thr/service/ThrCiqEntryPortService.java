package io.renren.modules.thr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.thr.entity.ThrCiqEntryPortEntity;

/**
 * 国内口岸（入境口岸）代码
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
public interface ThrCiqEntryPortService extends IService<ThrCiqEntryPortEntity> {

    PageUtils queryIndex(ThrCiqEntryPortEntity entity);

    ThrCiqEntryPortEntity getOneByCode(ThrCiqEntryPortEntity entity);
}

