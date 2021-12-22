package io.renren.modules.ctb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.ctb.entity.CtbPartnerTypeEntity;

import java.util.Map;

/**
 * ctb_partner的type表 ，同一个partner可以有多种类型
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
public interface CtbPartnerTypeService extends IService<CtbPartnerTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

