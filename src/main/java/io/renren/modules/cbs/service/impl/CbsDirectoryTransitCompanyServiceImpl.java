package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.cbs.dao.CbsDirectoryTransitCompanyDao;
import io.renren.modules.cbs.entity.CbsDirectoryTransitCompanyEntity;
import io.renren.modules.cbs.service.CbsDirectoryTransitCompanyService;
import org.springframework.stereotype.Service;


@Service("cbsDirectoryTransitCompanyService")
public class CbsDirectoryTransitCompanyServiceImpl extends ServiceImpl<CbsDirectoryTransitCompanyDao, CbsDirectoryTransitCompanyEntity> implements CbsDirectoryTransitCompanyService {

    public PageUtils queryPage(CbsDirectoryTransitCompanyEntity entity) {
        QueryWrapper<CbsDirectoryTransitCompanyEntity> qw = new QueryWrapper<>();
        qw.like(entity.getName() != null, "name", entity.getName());
        qw.eq("available", 1);
        IPage<CbsDirectoryTransitCompanyEntity> page = this.page(new QueryPage<CbsDirectoryTransitCompanyEntity>().getPage(entity), qw);
        return new PageUtils(page);
    }

}