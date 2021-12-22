package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cbs.entity.CbsStoreGoodsInImportEntity;

import java.util.Map;

/**
 * 进口入库表，与cbs_store_goods_in表为1对1继承关系
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-22 16:54:51
 */
public interface CbsStoreGoodsInImportService extends IService<CbsStoreGoodsInImportEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

