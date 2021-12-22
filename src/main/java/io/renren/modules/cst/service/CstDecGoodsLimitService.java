package io.renren.modules.cst.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cst.entity.CstDecGoodsLimitEntity;

import java.util.Map;

/**
 * 进出口报关单许可证信息表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-02 12:19:55
 */
public interface CstDecGoodsLimitService extends IService<CstDecGoodsLimitEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

