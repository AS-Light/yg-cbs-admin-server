package io.renren.modules.thr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.thr.entity.ThrEmlTypeEntity;

/**
 * 加工贸易手册类型
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-12-16 11:36:01
 */
public interface ThrEmlTypeService extends IService<ThrEmlTypeEntity> {

    PageUtils queryIndex(ThrEmlTypeEntity entity);

    ThrEmlTypeEntity getOneByCode(ThrEmlTypeEntity entity);
}

