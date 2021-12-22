package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.cbs.dao.CbsProductionProcessDao;
import io.renren.modules.cbs.entity.CbsProductionProcessEntity;
import io.renren.modules.cbs.service.CbsProductionProcessService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("cbsProductionProcessService")
public class CbsProductionProcessServiceImpl extends ServiceImpl<CbsProductionProcessDao, CbsProductionProcessEntity> implements CbsProductionProcessService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CbsProductionProcessEntity> page = this.page(
                new Query<CbsProductionProcessEntity>().getPage(params),
                new QueryWrapper<CbsProductionProcessEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public CbsProductionProcessEntity detail(Long id) {
        return baseMapper.detail(id);
    }

}