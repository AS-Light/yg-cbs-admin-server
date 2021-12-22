package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.cbs.dao.CbsDirectoryProduceCompanyDao;
import io.renren.modules.cbs.entity.CbsDirectoryProduceCompanyEntity;
import io.renren.modules.cbs.service.CbsDirectoryProduceCompanyService;
import org.springframework.stereotype.Service;


@Service("cbsDirectoryProduceCompanyService")
public class CbsDirectoryProduceCompanyServiceImpl extends ServiceImpl<CbsDirectoryProduceCompanyDao, CbsDirectoryProduceCompanyEntity> implements CbsDirectoryProduceCompanyService {

    public PageUtils queryPage(CbsDirectoryProduceCompanyEntity entity) {
        QueryWrapper<CbsDirectoryProduceCompanyEntity> qw = new QueryWrapper<>();
        qw.like(entity.getName() != null, "name", entity.getName());
        qw.eq("available", 1);
        IPage<CbsDirectoryProduceCompanyEntity> page = this.page(new QueryPage<CbsDirectoryProduceCompanyEntity>().getPage(entity), qw);
        return new PageUtils(page);
    }

}