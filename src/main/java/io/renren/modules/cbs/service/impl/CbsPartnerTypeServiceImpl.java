package io.renren.modules.cbs.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.cbs.dao.CbsPartnerTypeDao;
import io.renren.modules.cbs.entity.CbsPartnerTypeEntity;
import io.renren.modules.cbs.service.CbsPartnerTypeService;


@Service("cbsPartnerTypeService")
public class CbsPartnerTypeServiceImpl extends ServiceImpl<CbsPartnerTypeDao, CbsPartnerTypeEntity> implements CbsPartnerTypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CbsPartnerTypeEntity> page = this.page(
                new Query<CbsPartnerTypeEntity>().getPage(params),
                new QueryWrapper<CbsPartnerTypeEntity>()
        );

        return new PageUtils(page);
    }

}