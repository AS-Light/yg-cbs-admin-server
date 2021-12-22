package io.renren.modules.ctb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.ctb.entity.CtbImportVoyageEntity;

import java.util.Map;

/**
 * 进口航次变更记录，生产报关以最后一次为准
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
public interface CtbImportVoyageService extends IService<CtbImportVoyageEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

