package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cbs.entity.CbsExportEntity;
import io.renren.modules.cbs.entity.CbsExportStatusStreamEntity;
import io.renren.modules.cbs.entity.CbsExportVoyageEntity;

import java.util.HashMap;
import java.util.List;

/**
 * 出口单（主）表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-13 13:55:45
 */
public interface CbsExportService extends IService<CbsExportEntity> {

    PageUtils queryIndex(CbsExportEntity entity);

    List<CbsExportEntity> queryForStoreOut();

    HashMap<String, Object> saveReturnId(CbsExportEntity entity);

    CbsExportEntity detail(Long id);

    Object updateAllById(CbsExportEntity entity);

    Object updateVoyageChange(CbsExportVoyageEntity entity);

    void submit(CbsExportStatusStreamEntity statusStreamEntity) throws RuntimeException;

    Integer submitBack(CbsExportStatusStreamEntity statusStreamEntity);

    void check(CbsExportStatusStreamEntity statusStreamEntity) throws RuntimeException;

    void submitCustom(CbsExportStatusStreamEntity statusStreamEntity) throws RuntimeException;

    Integer submitBackCustom(CbsExportStatusStreamEntity statusStreamEntity);

    void checkCustom(CbsExportStatusStreamEntity statusStreamEntity) throws RuntimeException;

    void deleteByIds(Long[] ids);
}

