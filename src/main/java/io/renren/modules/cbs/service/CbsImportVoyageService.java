package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cbs.entity.CbsImportVoyageEntity;

import java.util.Map;

/**
 * 进口航次
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-18 15:18:27
 */
public interface CbsImportVoyageService extends IService<CbsImportVoyageEntity> {

    PageUtils queryPage(Map<String, Object> params);

    CbsImportVoyageEntity selectById(Long id);
}

