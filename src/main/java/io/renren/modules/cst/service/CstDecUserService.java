package io.renren.modules.cst.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cst.entity.CstDecUserEntity;

import java.util.Map;

/**
 * 进出口报关单 使用人信息表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-02 12:19:55
 */
public interface CstDecUserService extends IService<CstDecUserEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

