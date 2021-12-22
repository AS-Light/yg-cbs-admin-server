package io.renren.modules.ctb.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.ctb.dao.CtbDirectoryTransitCompanyDao;
import io.renren.modules.ctb.entity.CtbDirectoryTransitCompanyEntity;
import io.renren.modules.ctb.service.CtbDirectoryTransitCompanyService;


@Service("ctbDirectoryTransitCompanyService")
public class CtbDirectoryTransitCompanyServiceImpl extends ServiceImpl<CtbDirectoryTransitCompanyDao, CtbDirectoryTransitCompanyEntity> implements CtbDirectoryTransitCompanyService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CtbDirectoryTransitCompanyEntity> page = this.page(
                new Query<CtbDirectoryTransitCompanyEntity>().getPage(params),
                new QueryWrapper<CtbDirectoryTransitCompanyEntity>()
        );

        return new PageUtils(page);
    }

}