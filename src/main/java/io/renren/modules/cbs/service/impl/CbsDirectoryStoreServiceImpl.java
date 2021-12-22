package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.cbs.dao.CbsDirectoryStoreDao;
import io.renren.modules.cbs.entity.CbsDirectoryStoreEntity;
import io.renren.modules.cbs.service.CbsDirectoryStoreService;
import org.springframework.stereotype.Service;


@Service("cbsDirectoryStoreService")
public class CbsDirectoryStoreServiceImpl extends ServiceImpl<CbsDirectoryStoreDao, CbsDirectoryStoreEntity> implements CbsDirectoryStoreService {

    public PageUtils queryPage(CbsDirectoryStoreEntity entity) {
        QueryWrapper<CbsDirectoryStoreEntity> qw = new QueryWrapper<>();
        qw.like(entity.getName() != null, "name", entity.getName());
        qw.eq("available", 1);
        IPage<CbsDirectoryStoreEntity> page = this.page(new QueryPage<CbsDirectoryStoreEntity>().getPage(entity), qw);
        return new PageUtils(page);
    }

}