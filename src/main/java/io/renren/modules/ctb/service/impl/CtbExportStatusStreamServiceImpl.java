package io.renren.modules.ctb.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.ctb.dao.CtbExportStatusStreamDao;
import io.renren.modules.ctb.entity.CtbExportStatusStreamEntity;
import io.renren.modules.ctb.service.CtbExportStatusStreamService;


@Service("ctbExportStatusStreamService")
public class CtbExportStatusStreamServiceImpl extends ServiceImpl<CtbExportStatusStreamDao, CtbExportStatusStreamEntity> implements CtbExportStatusStreamService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CtbExportStatusStreamEntity> page = this.page(
                new Query<CtbExportStatusStreamEntity>().getPage(params),
                new QueryWrapper<CtbExportStatusStreamEntity>()
        );

        return new PageUtils(page);
    }

}