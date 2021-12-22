package io.renren.modules.cbs.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.cbs.dao.CbsStoreGoodsOutStatusStreamDao;
import io.renren.modules.cbs.entity.CbsStoreGoodsOutStatusStreamEntity;
import io.renren.modules.cbs.service.CbsStoreGoodsOutStatusStreamService;


@Service("cbsStoreGoodsOutStatusStreamService")
public class CbsStoreGoodsOutStatusStreamServiceImpl extends ServiceImpl<CbsStoreGoodsOutStatusStreamDao, CbsStoreGoodsOutStatusStreamEntity> implements CbsStoreGoodsOutStatusStreamService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CbsStoreGoodsOutStatusStreamEntity> page = this.page(
                new Query<CbsStoreGoodsOutStatusStreamEntity>().getPage(params),
                new QueryWrapper<CbsStoreGoodsOutStatusStreamEntity>()
        );

        return new PageUtils(page);
    }

}