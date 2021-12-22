package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.cbs.dao.CbsStoreGoodsInProduceDao;
import io.renren.modules.cbs.entity.CbsStoreGoodsInProduceEntity;
import io.renren.modules.cbs.service.CbsStoreGoodsInProduceService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("cbsStoreGoodsInProduceService")
public class CbsStoreGoodsInProduceServiceImpl extends ServiceImpl<CbsStoreGoodsInProduceDao, CbsStoreGoodsInProduceEntity> implements CbsStoreGoodsInProduceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CbsStoreGoodsInProduceEntity> page = this.page(
                new Query<CbsStoreGoodsInProduceEntity>().getPage(params),
                new QueryWrapper<CbsStoreGoodsInProduceEntity>()
        );

        return new PageUtils(page);
    }

}