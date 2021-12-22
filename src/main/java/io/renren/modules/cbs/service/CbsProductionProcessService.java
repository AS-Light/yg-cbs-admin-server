package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cbs.entity.CbsProductionProcessEntity;

import java.util.Map;

/**
 * 生产工艺表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-05-28 15:32:13
 */
public interface CbsProductionProcessService extends IService<CbsProductionProcessEntity> {

    PageUtils queryPage(Map<String, Object> params);

    CbsProductionProcessEntity detail(Long id);
}

