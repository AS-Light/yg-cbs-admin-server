package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.cbs.dao.CbsStoreGoodsInItemDao;
import io.renren.modules.cbs.entity.CbsStoreGoodsInItemEntity;
import io.renren.modules.cbs.service.CbsStoreGoodsInItemService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("cbsStoreGoodsInItemService")
public class CbsStoreGoodsInItemServiceImpl extends ServiceImpl<CbsStoreGoodsInItemDao, CbsStoreGoodsInItemEntity> implements CbsStoreGoodsInItemService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CbsStoreGoodsInItemEntity> page = this.page(
                new Query<CbsStoreGoodsInItemEntity>().getPage(params),
                new QueryWrapper<CbsStoreGoodsInItemEntity>()
        );

        return new PageUtils(page);
    }

}