package io.renren.modules.thr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.thr.entity.ThrPackTypeEntity;

/**
 * 危包规格代码、辅助包装代码
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
public interface ThrPackTypeService extends IService<ThrPackTypeEntity> {

    PageUtils queryIndex(ThrPackTypeEntity entity);

    ThrPackTypeEntity getOneByCode(ThrPackTypeEntity entity);
}

