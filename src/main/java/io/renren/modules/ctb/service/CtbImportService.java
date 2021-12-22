package io.renren.modules.ctb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.ctb.entity.CtbImportEntity;
import io.renren.modules.ctb.entity.CtbImportStatusStreamEntity;
import io.renren.modules.ctb.entity.CtbImportVoyageEntity;

import java.util.HashMap;

/**
 * 进口单（主）表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
public interface CtbImportService extends IService<CtbImportEntity> {

    PageUtils queryIndex(CtbImportEntity entity);

    HashMap<String, Object> saveReturnId(CtbImportEntity entity) throws RuntimeException;

    CtbImportEntity detail(Long id);

    Object updateAllById(CtbImportEntity entity);

    Object updateVoyageChange(CtbImportVoyageEntity entity);

    void submit(CtbImportStatusStreamEntity statusStreamEntity) throws RuntimeException;

    Integer submitBack(CtbImportStatusStreamEntity statusStreamEntity);

    void check(CtbImportStatusStreamEntity statusStreamEntity) throws RuntimeException;

    void deleteByIds(Long[] ids);

    void distributionManager(Long importId, Long nowManager, Long operator);
}

