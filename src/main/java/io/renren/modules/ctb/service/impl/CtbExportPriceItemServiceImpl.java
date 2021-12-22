package io.renren.modules.ctb.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.ctb.dao.CtbExportPriceItemDao;
import io.renren.modules.ctb.entity.CtbExportPriceItemEntity;
import io.renren.modules.ctb.service.CtbExportPriceItemService;


@Service("ctbExportPriceItemService")
public class CtbExportPriceItemServiceImpl extends ServiceImpl<CtbExportPriceItemDao, CtbExportPriceItemEntity> implements CtbExportPriceItemService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CtbExportPriceItemEntity> page = this.page(
                new Query<CtbExportPriceItemEntity>().getPage(params),
                new QueryWrapper<CtbExportPriceItemEntity>()
        );

        return new PageUtils(page);
    }

}