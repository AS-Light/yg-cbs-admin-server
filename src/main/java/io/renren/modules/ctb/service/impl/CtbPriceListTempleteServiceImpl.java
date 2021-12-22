package io.renren.modules.ctb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.ctb.dao.CtbPriceListTempleteDao;
import io.renren.modules.ctb.entity.CtbPriceListTempleteEntity;
import io.renren.modules.ctb.service.CtbPriceListTempleteService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("ctbPriceListTempleteService")
public class CtbPriceListTempleteServiceImpl extends ServiceImpl<CtbPriceListTempleteDao, CtbPriceListTempleteEntity> implements CtbPriceListTempleteService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CtbPriceListTempleteEntity> page = this.page(
                new Query<CtbPriceListTempleteEntity>().getPage(params),
                new QueryWrapper<CtbPriceListTempleteEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public CtbPriceListTempleteEntity getByTenantId(Long tenantId) {
        return baseMapper.selectByTenantId(tenantId);
    }


}