package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cbs.entity.CbsDirectoryIntermediateProductEntity;

import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-05-28 15:32:13
 */
public interface CbsDirectoryIntermediateProductService extends IService<CbsDirectoryIntermediateProductEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

