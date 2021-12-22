package io.renren.modules.ctb.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.ctb.dao.CtbDirectoryShipCompanyDao;
import io.renren.modules.ctb.entity.CtbDirectoryShipCompanyEntity;
import io.renren.modules.ctb.service.CtbDirectoryShipCompanyService;


@Service("ctbDirectoryShipCompanyService")
public class CtbDirectoryShipCompanyServiceImpl extends ServiceImpl<CtbDirectoryShipCompanyDao, CtbDirectoryShipCompanyEntity> implements CtbDirectoryShipCompanyService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CtbDirectoryShipCompanyEntity> page = this.page(
                new Query<CtbDirectoryShipCompanyEntity>().getPage(params),
                new QueryWrapper<CtbDirectoryShipCompanyEntity>()
        );

        return new PageUtils(page);
    }

}