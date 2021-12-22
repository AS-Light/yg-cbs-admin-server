package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.cbs.dao.CbsExportStatusStreamDao;
import io.renren.modules.cbs.entity.CbsExportStatusStreamEntity;
import io.renren.modules.cbs.service.CbsExportStatusStreamService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("cbsExportStatusStreamService")
public class CbsExportStatusStreamServiceImpl extends ServiceImpl<CbsExportStatusStreamDao, CbsExportStatusStreamEntity> implements CbsExportStatusStreamService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CbsExportStatusStreamEntity> page = this.page(
                new Query<CbsExportStatusStreamEntity>().getPage(params),
                new QueryWrapper<CbsExportStatusStreamEntity>()
        );

        return new PageUtils(page);
    }

}
