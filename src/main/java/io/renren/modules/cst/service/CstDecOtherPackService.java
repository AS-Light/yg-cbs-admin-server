package io.renren.modules.cst.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cst.entity.CstDecOtherPackEntity;

import java.util.Map;

/**
 * 进出口报关单 其他包装信息表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-02 12:19:55
 */
public interface CstDecOtherPackService extends IService<CstDecOtherPackEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

