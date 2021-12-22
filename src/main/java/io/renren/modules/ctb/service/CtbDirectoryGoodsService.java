package io.renren.modules.ctb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.ctb.entity.CtbDirectoryGoodsEntity;

/**
 * 原材料名录表（报关行副本）
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
public interface CtbDirectoryGoodsService extends IService<CtbDirectoryGoodsEntity> {

    PageUtils queryPage(CtbDirectoryGoodsEntity entity);

    CtbDirectoryGoodsEntity detail(Long id);
}

