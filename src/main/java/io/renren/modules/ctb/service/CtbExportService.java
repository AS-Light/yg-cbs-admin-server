package io.renren.modules.ctb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.ctb.entity.CtbExportEntity;
import io.renren.modules.ctb.entity.CtbExportStatusStreamEntity;
import io.renren.modules.ctb.entity.CtbExportVoyageEntity;

import java.util.HashMap;

/**
 * 进口单（主）表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
public interface CtbExportService extends IService<CtbExportEntity> {
    PageUtils queryIndex(CtbExportEntity entity);

    HashMap<String, Object> saveReturnId(CtbExportEntity entity) throws RuntimeException;

    CtbExportEntity detail(Long id);

    Object updateAllById(CtbExportEntity entity);

    Object updateVoyageChange(CtbExportVoyageEntity entity);

    void submit(CtbExportStatusStreamEntity statusStreamEntity) throws RuntimeException;

    Integer submitBack(CtbExportStatusStreamEntity statusStreamEntity);

    void check(CtbExportStatusStreamEntity statusStreamEntity) throws RuntimeException;

    void deleteByIds(Long[] ids);

    void distributionManager(Long exportId, Long nowManager, Long operator);
}

