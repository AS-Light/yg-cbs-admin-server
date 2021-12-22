package io.renren.modules.thr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.thr.entity.ThrLicTypeEntity;

/**
 * 企业产品许可类别代码表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
public interface ThrLicTypeService extends IService<ThrLicTypeEntity> {

    PageUtils queryIndex(ThrLicTypeEntity entity);

    ThrLicTypeEntity getOneByCode(ThrLicTypeEntity entity);
}

