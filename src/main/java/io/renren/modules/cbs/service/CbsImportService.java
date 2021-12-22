package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cbs.entity.CbsImportEntity;
import io.renren.modules.cbs.entity.CbsImportStatusStreamEntity;
import io.renren.modules.cbs.entity.CbsImportVoyageEntity;

import java.util.HashMap;
import java.util.List;

/**
 * 进口单（主）表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-13 13:55:45
 */
public interface CbsImportService extends IService<CbsImportEntity> {

    PageUtils queryIndex(CbsImportEntity entity);

    List<CbsImportEntity> queryForStoreIn();

    HashMap<String, Object> saveReturnId(CbsImportEntity entity);

    CbsImportEntity detail(Long id);

    Object updateAllById(CbsImportEntity entity);

    Object updateVoyageChange(CbsImportVoyageEntity entity);

    void submit(CbsImportStatusStreamEntity statusStreamEntity) throws RuntimeException;

    Integer submitBack(CbsImportStatusStreamEntity statusStreamEntity);

    void check(CbsImportStatusStreamEntity statusStreamEntity) throws RuntimeException;

    void submitCustom(CbsImportStatusStreamEntity statusStreamEntity) throws RuntimeException;

    Integer submitBackCustom(CbsImportStatusStreamEntity statusStreamEntity);

    void checkCustom(CbsImportStatusStreamEntity statusStreamEntity, Long operator) throws RuntimeException;

    void deleteByIds(Long[] ids);
}

