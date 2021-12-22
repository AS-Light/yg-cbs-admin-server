package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.cbs.dao.CbsStoreGoodsOutProduceDao;
import io.renren.modules.cbs.entity.CbsStoreGoodsOutProduceEntity;
import io.renren.modules.cbs.service.CbsStoreGoodsOutProduceService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("cbsStoreGoodsOutProduceService")
public class CbsStoreGoodsOutProduceServiceImpl extends ServiceImpl<CbsStoreGoodsOutProduceDao, CbsStoreGoodsOutProduceEntity> implements CbsStoreGoodsOutProduceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CbsStoreGoodsOutProduceEntity> page = this.page(
                new Query<CbsStoreGoodsOutProduceEntity>().getPage(params),
                new QueryWrapper<CbsStoreGoodsOutProduceEntity>()
        );

        return new PageUtils(page);
    }

}