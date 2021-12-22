package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cbs.entity.CbsPartnerTypeEntity;

import java.util.Map;

/**
 * cbs_partner的type表 ，同一个partner可以有多种类型
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-12-25 11:46:38
 */
public interface CbsPartnerTypeService extends IService<CbsPartnerTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

