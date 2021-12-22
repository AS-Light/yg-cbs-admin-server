package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.cbs.dao.CbsImportVoyageDao;
import io.renren.modules.cbs.entity.CbsImportVoyageEntity;
import io.renren.modules.cbs.service.CbsImportVoyageService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("cbsImportVoyageService")
public class CbsImportVoyageServiceImpl extends ServiceImpl<CbsImportVoyageDao, CbsImportVoyageEntity> implements CbsImportVoyageService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CbsImportVoyageEntity> page = this.page(
                new Query<CbsImportVoyageEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public CbsImportVoyageEntity selectById(Long id) {
        return baseMapper.simpleDetail(id);
    }
}