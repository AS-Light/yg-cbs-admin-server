package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.cbs.dao.CbsProduceGoodsDao;
import io.renren.modules.cbs.entity.CbsProduceGoodsEntity;
import io.renren.modules.cbs.service.CbsProduceGoodsService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("cbsProduceGoodsService")
public class CbsProduceGoodsServiceImpl extends ServiceImpl<CbsProduceGoodsDao, CbsProduceGoodsEntity> implements CbsProduceGoodsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CbsProduceGoodsEntity> page = this.page(
                new Query<CbsProduceGoodsEntity>().getPage(params),
                new QueryWrapper<CbsProduceGoodsEntity>()
        );

        return new PageUtils(page);
    }

}