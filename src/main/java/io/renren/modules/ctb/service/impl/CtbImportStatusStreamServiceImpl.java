package io.renren.modules.ctb.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.ctb.dao.CtbImportStatusStreamDao;
import io.renren.modules.ctb.entity.CtbImportStatusStreamEntity;
import io.renren.modules.ctb.service.CtbImportStatusStreamService;


@Service("ctbImportStatusStreamService")
public class CtbImportStatusStreamServiceImpl extends ServiceImpl<CtbImportStatusStreamDao, CtbImportStatusStreamEntity> implements CtbImportStatusStreamService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CtbImportStatusStreamEntity> page = this.page(
                new Query<CtbImportStatusStreamEntity>().getPage(params),
                new QueryWrapper<CtbImportStatusStreamEntity>()
        );

        return new PageUtils(page);
    }

}