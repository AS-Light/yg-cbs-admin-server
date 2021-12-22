package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.cbs.dao.CbsGoodsPartNoDao;
import io.renren.modules.cbs.entity.CbsGoodsPartNoEntity;
import io.renren.modules.cbs.service.CbsGoodsPartNoService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("cbsGoodsPartNoService")
public class CbsGoodsPartNoServiceImpl extends ServiceImpl<CbsGoodsPartNoDao, CbsGoodsPartNoEntity> implements CbsGoodsPartNoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CbsGoodsPartNoEntity> page = this.page(
                new Query<CbsGoodsPartNoEntity>().getPage(params),
                new QueryWrapper<CbsGoodsPartNoEntity>()
        );

        return new PageUtils(page);
    }

}