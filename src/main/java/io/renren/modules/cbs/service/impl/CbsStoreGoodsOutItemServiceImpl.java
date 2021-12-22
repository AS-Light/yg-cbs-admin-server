package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.cbs.dao.CbsStoreGoodsOutItemDao;
import io.renren.modules.cbs.entity.CbsStoreGoodsOutItemEntity;
import io.renren.modules.cbs.service.CbsStoreGoodsOutItemService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("cbsStoreGoodsOutItemService")
public class CbsStoreGoodsOutItemServiceImpl extends ServiceImpl<CbsStoreGoodsOutItemDao, CbsStoreGoodsOutItemEntity> implements CbsStoreGoodsOutItemService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CbsStoreGoodsOutItemEntity> page = this.page(
                new Query<CbsStoreGoodsOutItemEntity>().getPage(params),
                new QueryWrapper<CbsStoreGoodsOutItemEntity>()
        );

        return new PageUtils(page);
    }


}