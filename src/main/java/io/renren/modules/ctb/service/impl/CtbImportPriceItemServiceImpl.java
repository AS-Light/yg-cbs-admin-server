package io.renren.modules.ctb.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.ctb.dao.CtbImportPriceItemDao;
import io.renren.modules.ctb.entity.CtbImportPriceItemEntity;
import io.renren.modules.ctb.service.CtbImportPriceItemService;


@Service("ctbImportPriceItemService")
public class CtbImportPriceItemServiceImpl extends ServiceImpl<CtbImportPriceItemDao, CtbImportPriceItemEntity> implements CtbImportPriceItemService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CtbImportPriceItemEntity> page = this.page(
                new Query<CtbImportPriceItemEntity>().getPage(params),
                new QueryWrapper<CtbImportPriceItemEntity>()
        );

        return new PageUtils(page);
    }

}