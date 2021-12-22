package io.renren.modules.ctb.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.ctb.dao.CtbStoreGoodsDao;
import io.renren.modules.ctb.entity.CtbStoreGoodsEntity;
import io.renren.modules.ctb.service.CtbStoreGoodsService;
import org.springframework.stereotype.Service;


@Service("ctbStoreGoodsService")
public class CtbStoreGoodsServiceImpl extends ServiceImpl<CtbStoreGoodsDao, CtbStoreGoodsEntity> implements CtbStoreGoodsService {

    @Override
    public PageUtils queryIndex(CtbStoreGoodsEntity entity) {
        IPage<CtbStoreGoodsEntity> page = baseMapper.queryIndex(new QueryPage<CtbStoreGoodsEntity>().getPage(entity), entity);
        return new PageUtils(page);
    }

    @Override
    public CtbStoreGoodsEntity detail(Long id) {
        return baseMapper.detail(id);
    }
}