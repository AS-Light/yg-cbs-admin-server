package io.renren.modules.ctb.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.ctb.dao.CtbPartnerTypeDao;
import io.renren.modules.ctb.entity.CtbPartnerTypeEntity;
import io.renren.modules.ctb.service.CtbPartnerTypeService;


@Service("ctbPartnerTypeService")
public class CtbPartnerTypeServiceImpl extends ServiceImpl<CtbPartnerTypeDao, CtbPartnerTypeEntity> implements CtbPartnerTypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CtbPartnerTypeEntity> page = this.page(
                new Query<CtbPartnerTypeEntity>().getPage(params),
                new QueryWrapper<CtbPartnerTypeEntity>()
        );

        return new PageUtils(page);
    }

}