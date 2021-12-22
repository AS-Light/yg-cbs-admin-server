package io.renren.modules.ctb.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.ctb.dao.CtbExportGoodsDao;
import io.renren.modules.ctb.entity.CtbExportGoodsEntity;
import io.renren.modules.ctb.service.CtbExportGoodsService;


@Service("ctbExportGoodsService")
public class CtbExportGoodsServiceImpl extends ServiceImpl<CtbExportGoodsDao, CtbExportGoodsEntity> implements CtbExportGoodsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CtbExportGoodsEntity> page = this.page(
                new Query<CtbExportGoodsEntity>().getPage(params),
                new QueryWrapper<CtbExportGoodsEntity>()
        );

        return new PageUtils(page);
    }

}