package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cbs.entity.CbsStoreGoodsByNoEntity;
import io.renren.modules.cbs.entity.CbsStoreGoodsEntity;

import java.util.List;
import java.util.Map;

/**
 * 产品库存表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-22 16:54:51
 */
public interface CbsStoreGoodsService extends IService<CbsStoreGoodsEntity> {
    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryIndex(CbsStoreGoodsByNoEntity entity);

    List<CbsStoreGoodsEntity> listForOutMaterialToProduce(Long produceId);

    List<CbsStoreGoodsEntity> listForOutProductToExport(Long exportId);

    List<CbsStoreGoodsEntity> listForOutExport(Long exportId);

    List<CbsStoreGoodsEntity> listForOutSell(Long sellId);
}

