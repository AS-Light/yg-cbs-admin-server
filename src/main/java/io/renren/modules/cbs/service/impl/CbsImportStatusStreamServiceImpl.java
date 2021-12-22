package io.renren.modules.cbs.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.cbs.dao.CbsImportStatusStreamDao;
import io.renren.modules.cbs.entity.CbsImportStatusStreamEntity;
import io.renren.modules.cbs.service.CbsImportStatusStreamService;


@Service("cbsImportStatusStreamService")
public class CbsImportStatusStreamServiceImpl extends ServiceImpl<CbsImportStatusStreamDao, CbsImportStatusStreamEntity> implements CbsImportStatusStreamService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CbsImportStatusStreamEntity> page = this.page(
                new Query<CbsImportStatusStreamEntity>().getPage(params),
                new QueryWrapper<CbsImportStatusStreamEntity>()
        );

        return new PageUtils(page);
    }

}