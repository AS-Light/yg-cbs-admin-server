package io.renren.modules.cst.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cst.entity.CstNptsDcrExgEntity;

import java.util.Map;

/**
 * 手册报核成品表体
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-09 11:57:02
 */
public interface CstNptsDcrExgService extends IService<CstNptsDcrExgEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

