package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.cbs.dao.CbsProduceGoodsStreamItemDao;
import io.renren.modules.cbs.entity.CbsProduceGoodsStreamItemEntity;
import io.renren.modules.cbs.service.CbsProduceGoodsStreamItemService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("cbsProduceGoodsStreamItemService")
public class CbsProduceGoodsStreamItemServiceImpl extends ServiceImpl<CbsProduceGoodsStreamItemDao, CbsProduceGoodsStreamItemEntity> implements CbsProduceGoodsStreamItemService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CbsProduceGoodsStreamItemEntity> page = this.page(
                new Query<CbsProduceGoodsStreamItemEntity>().getPage(params),
                new QueryWrapper<CbsProduceGoodsStreamItemEntity>()
        );

        return new PageUtils(page);
    }

}