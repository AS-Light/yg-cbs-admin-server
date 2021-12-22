package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.cbs.dao.CbsDirectoryShipCompanyDao;
import io.renren.modules.cbs.entity.CbsDirectoryShipCompanyEntity;
import io.renren.modules.cbs.service.CbsDirectoryShipCompanyService;
import org.springframework.stereotype.Service;


@Service("cbsDirectoryShipCompanyService")
public class CbsDirectoryShipCompanyServiceImpl extends ServiceImpl<CbsDirectoryShipCompanyDao, CbsDirectoryShipCompanyEntity> implements CbsDirectoryShipCompanyService {

    public PageUtils queryPage(CbsDirectoryShipCompanyEntity entity) {
        QueryWrapper<CbsDirectoryShipCompanyEntity> qw = new QueryWrapper<>();
        qw.like(entity.getName() != null, "name", entity.getName());
        qw.eq("available", 1);
        IPage<CbsDirectoryShipCompanyEntity> page = this.page(new QueryPage<CbsDirectoryShipCompanyEntity>().getPage(entity), qw);
        return new PageUtils(page);
    }

}