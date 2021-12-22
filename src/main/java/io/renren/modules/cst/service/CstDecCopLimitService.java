package io.renren.modules.cst.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cst.entity.CstDecCopLimitEntity;

import java.util.Map;

/**
 * 进出口报关单 企业资质信息表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-02 12:19:55
 */
public interface CstDecCopLimitService extends IService<CstDecCopLimitEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

