package io.renren.modules.ctb.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.ctb.dao.CtbPriceListDao;
import io.renren.modules.ctb.entity.CtbPriceListEntity;
import io.renren.modules.ctb.service.CtbPriceListService;


@Service("ctbPriceListService")
public class CtbPriceListServiceImpl extends ServiceImpl<CtbPriceListDao, CtbPriceListEntity> implements CtbPriceListService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CtbPriceListEntity> page = this.page(
                new Query<CtbPriceListEntity>().getPage(params),
                new QueryWrapper<CtbPriceListEntity>()
        );

        return new PageUtils(page);
    }

}