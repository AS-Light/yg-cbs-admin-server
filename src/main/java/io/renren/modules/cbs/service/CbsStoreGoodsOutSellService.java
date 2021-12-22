package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.cbs.entity.CbsStoreGoodsOutSellEntity;

/**
 * 国内采购入库表，与cbs_store_goods_in表为1对1继承关系
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-02-20 17:14:19
 */
public interface CbsStoreGoodsOutSellService extends IService<CbsStoreGoodsOutSellEntity> {

    CbsStoreGoodsOutSellEntity selectBySellId(Long sellId);
}

