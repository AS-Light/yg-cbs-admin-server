package io.renren.modules.cst.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cst.entity.CstInvtHeaderEntity;

import java.util.Map;

/**
 * 保税核注清单表头
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-09 11:57:02
 */
public interface CstInvtHeaderService extends IService<CstInvtHeaderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

