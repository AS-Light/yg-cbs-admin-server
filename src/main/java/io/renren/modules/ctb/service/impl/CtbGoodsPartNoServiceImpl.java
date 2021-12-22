package io.renren.modules.ctb.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.ctb.dao.CtbGoodsPartNoDao;
import io.renren.modules.ctb.entity.CtbGoodsPartNoEntity;
import io.renren.modules.ctb.service.CtbGoodsPartNoService;


@Service("ctbGoodsPartNoService")
public class CtbGoodsPartNoServiceImpl extends ServiceImpl<CtbGoodsPartNoDao, CtbGoodsPartNoEntity> implements CtbGoodsPartNoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CtbGoodsPartNoEntity> page = this.page(
                new Query<CtbGoodsPartNoEntity>().getPage(params),
                new QueryWrapper<CtbGoodsPartNoEntity>()
        );

        return new PageUtils(page);
    }

}