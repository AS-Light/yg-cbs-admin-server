package io.renren.modules.cbs.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.cbs.dao.CbsPurchaseGoodsDao;
import io.renren.modules.cbs.entity.CbsPurchaseGoodsEntity;
import io.renren.modules.cbs.service.CbsPurchaseGoodsService;


@Service("cbsPurchaseGoodsService")
public class CbsPurchaseGoodsServiceImpl extends ServiceImpl<CbsPurchaseGoodsDao, CbsPurchaseGoodsEntity> implements CbsPurchaseGoodsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CbsPurchaseGoodsEntity> page = this.page(
                new Query<CbsPurchaseGoodsEntity>().getPage(params),
                new QueryWrapper<CbsPurchaseGoodsEntity>()
        );

        return new PageUtils(page);
    }

}