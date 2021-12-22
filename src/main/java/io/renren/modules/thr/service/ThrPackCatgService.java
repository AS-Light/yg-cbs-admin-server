package io.renren.modules.thr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.thr.entity.ThrPackCatgEntity;

/**
 * 包装种类
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
public interface ThrPackCatgService extends IService<ThrPackCatgEntity> {

    PageUtils queryIndex(ThrPackCatgEntity entity);

    ThrPackCatgEntity getOneByCode(ThrPackCatgEntity entity);
}

