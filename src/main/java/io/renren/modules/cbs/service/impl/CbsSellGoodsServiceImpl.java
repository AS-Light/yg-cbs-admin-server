package io.renren.modules.cbs.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.cbs.dao.CbsSellGoodsDao;
import io.renren.modules.cbs.entity.CbsSellGoodsEntity;
import io.renren.modules.cbs.service.CbsSellGoodsService;


@Service("cbsSellGoodsService")
public class CbsSellGoodsServiceImpl extends ServiceImpl<CbsSellGoodsDao, CbsSellGoodsEntity> implements CbsSellGoodsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CbsSellGoodsEntity> page = this.page(
                new Query<CbsSellGoodsEntity>().getPage(params),
                new QueryWrapper<CbsSellGoodsEntity>()
        );

        return new PageUtils(page);
    }

}