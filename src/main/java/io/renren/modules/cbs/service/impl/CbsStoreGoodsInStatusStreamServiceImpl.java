package io.renren.modules.cbs.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.cbs.dao.CbsStoreGoodsInStatusStreamDao;
import io.renren.modules.cbs.entity.CbsStoreGoodsInStatusStreamEntity;
import io.renren.modules.cbs.service.CbsStoreGoodsInStatusStreamService;


@Service("cbsStoreGoodsInStatusStreamService")
public class CbsStoreGoodsInStatusStreamServiceImpl extends ServiceImpl<CbsStoreGoodsInStatusStreamDao, CbsStoreGoodsInStatusStreamEntity> implements CbsStoreGoodsInStatusStreamService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CbsStoreGoodsInStatusStreamEntity> page = this.page(
                new Query<CbsStoreGoodsInStatusStreamEntity>().getPage(params),
                new QueryWrapper<CbsStoreGoodsInStatusStreamEntity>()
        );

        return new PageUtils(page);
    }

}