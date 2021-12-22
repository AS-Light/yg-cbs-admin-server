package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.cbs.dao.CbsExportVoyageDao;
import io.renren.modules.cbs.entity.CbsExportVoyageEntity;
import io.renren.modules.cbs.service.CbsExportVoyageService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("cbsExportVoyageService")
public class CbsExportVoyageServiceImpl extends ServiceImpl<CbsExportVoyageDao, CbsExportVoyageEntity> implements CbsExportVoyageService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CbsExportVoyageEntity> page = this.page(
                new Query<CbsExportVoyageEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public CbsExportVoyageEntity selectById(Long id) {
        return baseMapper.simpleDetail(id);
    }
}