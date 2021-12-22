package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cbs.entity.CbsDirectoryGoodsEntity;

/**
 * 原材料名录表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-14 10:29:32
 */
public interface CbsDirectoryGoodsService extends IService<CbsDirectoryGoodsEntity> {

    PageUtils queryPage(CbsDirectoryGoodsEntity entity);

    CbsDirectoryGoodsEntity detail(Long id);
}

