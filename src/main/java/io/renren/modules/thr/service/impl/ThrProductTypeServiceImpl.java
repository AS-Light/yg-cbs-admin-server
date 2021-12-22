package io.renren.modules.thr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.thr.dao.ThrProductTypeDao;
import io.renren.modules.thr.entity.ThrProductTypeEntity;
import io.renren.modules.thr.service.ThrProductTypeService;
import org.springframework.stereotype.Service;


@Service("thrProductTypeService")
public class ThrProductTypeServiceImpl extends ServiceImpl<ThrProductTypeDao, ThrProductTypeEntity> implements ThrProductTypeService {

    @Override
    public PageUtils queryIndex(ThrProductTypeEntity entity) {
        QueryWrapper<ThrProductTypeEntity> qw = new QueryWrapper<>();
        qw.like(entity.getCode() != null, "code", entity.getCode());
        qw.or().like(entity.getName() != null, "name", entity.getName());
        IPage<ThrProductTypeEntity> page = this.page(new QueryPage<ThrProductTypeEntity>().getPage(entity), qw);
        return new PageUtils(page);
    }

    public ThrProductTypeEntity getOneByCode(ThrProductTypeEntity entity) {
        QueryWrapper<ThrProductTypeEntity> qw = new QueryWrapper<ThrProductTypeEntity>().eq("code", entity.getCode());
        return getOne(qw);
    }
}