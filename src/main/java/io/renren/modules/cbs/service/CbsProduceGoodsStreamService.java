package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cbs.entity.CbsProduceGoodsStreamEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * 生产流水
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-26 16:15:37
 */
public interface CbsProduceGoodsStreamService extends IService<CbsProduceGoodsStreamEntity> {

    PageUtils queryPage(Map<String, Object> params);

    CbsProduceGoodsStreamEntity selectById(Long id);

    Long preCreate(CbsProduceGoodsStreamEntity entity);

    HashMap<String, Object> saveOrUpdateWithItems(CbsProduceGoodsStreamEntity entity);

    HashMap<String, Object> check(Long id);

    void deleteByIds(Long[] ids);
}

