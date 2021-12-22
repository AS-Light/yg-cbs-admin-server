package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cbs.entity.CbsProduceEntity;
import io.renren.modules.cbs.entity.CbsProduceStatusStreamEntity;

import java.util.HashMap;
import java.util.List;

/**
 * 生产单表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-13 13:55:45
 */
public interface CbsProduceService extends IService<CbsProduceEntity> {
    PageUtils queryIndex(CbsProduceEntity entity);

    CbsProduceEntity detail(Long id);

    HashMap<String, Object> preSave(CbsProduceEntity entity);

    Long saveReturnId(CbsProduceEntity entity) throws RuntimeException;

    void submitStart(CbsProduceStatusStreamEntity statusStreamEntity) throws RuntimeException;

    Integer submitStartBack(CbsProduceStatusStreamEntity statusStreamEntity);

    void checkStart(CbsProduceStatusStreamEntity statusStreamEntity) throws RuntimeException;

    HashMap<String, Object> submitComplete(CbsProduceStatusStreamEntity statusStreamEntity);

    Integer submitBackComplete(CbsProduceStatusStreamEntity statusStreamEntity);

    HashMap<String, Object> checkComplete(CbsProduceStatusStreamEntity statusStreamEntity, Long operator);

    void deleteByIds(Long[] ids);

    List<CbsProduceEntity> listForRawMaterialBack();
}

