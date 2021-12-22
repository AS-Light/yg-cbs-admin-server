package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cbs.entity.CbsExportVoyageEntity;

import java.util.Map;

/**
 * 出口航次
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-18 15:18:27
 */
public interface CbsExportVoyageService extends IService<CbsExportVoyageEntity> {

    PageUtils queryPage(Map<String, Object> params);

    CbsExportVoyageEntity selectById(Long id);
}

