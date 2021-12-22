package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.cbs.dao.CbsStoreGoodsOutSellDao;
import io.renren.modules.cbs.entity.CbsStoreGoodsOutSellEntity;
import io.renren.modules.cbs.service.CbsStoreGoodsOutSellService;
import org.springframework.stereotype.Service;


@Service("cbsStoreGoodsOutSellService")
public class CbsStoreGoodsOutSellServiceImpl extends ServiceImpl<CbsStoreGoodsOutSellDao, CbsStoreGoodsOutSellEntity> implements CbsStoreGoodsOutSellService {

    @Override
    public CbsStoreGoodsOutSellEntity selectBySellId(Long sellId) {
        return baseMapper.selectBySellId(sellId);
    }
}