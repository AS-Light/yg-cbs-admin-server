package io.renren.modules.cst.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cst.entity.CstDecListEntity;

import java.util.Map;

/**
 * 进出口报关单表体
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-02 12:19:55
 */
public interface CstDecListService extends IService<CstDecListEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

