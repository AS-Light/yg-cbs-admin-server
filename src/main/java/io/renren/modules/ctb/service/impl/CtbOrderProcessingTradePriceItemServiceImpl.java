package io.renren.modules.ctb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.ctb.dao.CtbOrderProcessingTradePriceItemDao;
import io.renren.modules.ctb.entity.CtbOrderProcessingTradePriceItemEntity;
import io.renren.modules.ctb.service.CtbOrderProcessingTradePriceItemService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("ctbOrderProcessingTradePriceItemService")
public class CtbOrderProcessingTradePriceItemServiceImpl extends ServiceImpl<CtbOrderProcessingTradePriceItemDao, CtbOrderProcessingTradePriceItemEntity> implements CtbOrderProcessingTradePriceItemService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CtbOrderProcessingTradePriceItemEntity> page = this.page(
                new Query<CtbOrderProcessingTradePriceItemEntity>().getPage(params),
                new QueryWrapper<CtbOrderProcessingTradePriceItemEntity>()
        );

        return new PageUtils(page);
    }

}