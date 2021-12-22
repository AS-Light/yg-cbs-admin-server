package io.renren.modules.thr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.thr.entity.ThrDeclPortEntity;

import java.util.List;

/**
 * 国内关区（关别）代码
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
public interface ThrDeclPortService extends IService<ThrDeclPortEntity> {

    List<ThrDeclPortEntity> listAll();

    PageUtils queryIndex(ThrDeclPortEntity entity);

    ThrDeclPortEntity getOneByCode(ThrDeclPortEntity entity);
}

