package io.renren.modules.cst.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cst.entity.CstDecHeaderEntity;

import java.util.Map;

/**
 * 报关单表头
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-02 12:19:55
 */
public interface CstDecHeaderService extends IService<CstDecHeaderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

