package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.cbs.dao.CbsDirectoryIntermediateProductDao;
import io.renren.modules.cbs.entity.CbsDirectoryIntermediateProductEntity;
import io.renren.modules.cbs.service.CbsDirectoryIntermediateProductService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("cbsDirectoryIntermediateProductService")
public class CbsDirectoryIntermediateProductServiceImpl extends ServiceImpl<CbsDirectoryIntermediateProductDao, CbsDirectoryIntermediateProductEntity> implements CbsDirectoryIntermediateProductService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CbsDirectoryIntermediateProductEntity> page = this.page(
                new Query<CbsDirectoryIntermediateProductEntity>().getPage(params),
                new QueryWrapper<CbsDirectoryIntermediateProductEntity>()
        );

        return new PageUtils(page);
    }

}