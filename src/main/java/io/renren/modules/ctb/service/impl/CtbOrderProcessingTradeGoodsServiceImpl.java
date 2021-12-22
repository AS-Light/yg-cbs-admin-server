package io.renren.modules.ctb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.ctb.dao.CtbOrderProcessingTradeGoodsDao;
import io.renren.modules.ctb.entity.CtbOrderProcessingTradeGoodsEntity;
import io.renren.modules.ctb.service.CtbOrderProcessingTradeGoodsService;
import org.springframework.stereotype.Service;


@Service("ctbOrderProcessingTradeGoodsService")
public class CtbOrderProcessingTradeGoodsServiceImpl extends ServiceImpl<CtbOrderProcessingTradeGoodsDao, CtbOrderProcessingTradeGoodsEntity> implements CtbOrderProcessingTradeGoodsService {

    @Override
    public CtbOrderProcessingTradeGoodsEntity detail(Long id) {
        return getBaseMapper().detail(id);
    }

}