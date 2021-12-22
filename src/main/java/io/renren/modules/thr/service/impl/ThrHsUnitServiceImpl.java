package io.renren.modules.thr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.thr.dao.ThrHsUnitDao;
import io.renren.modules.thr.entity.ThrHsUnitEntity;
import io.renren.modules.thr.service.ThrHsUnitService;
import org.springframework.stereotype.Service;


@Service("thrHsUnitService")
public class ThrHsUnitServiceImpl extends ServiceImpl<ThrHsUnitDao, ThrHsUnitEntity> implements ThrHsUnitService {

    @Override
    public PageUtils queryIndex(ThrHsUnitEntity entity) {
        QueryWrapper<ThrHsUnitEntity> qw = new QueryWrapper<>();
        qw.like(entity.getUnitCode() != null, "unit_code", entity.getUnitCode());
        qw.or().like(entity.getName() != null, "name", entity.getName());
        IPage<ThrHsUnitEntity> page = this.page(new QueryPage<ThrHsUnitEntity>().getPage(entity), qw);
        return new PageUtils(page);
    }

    public ThrHsUnitEntity getOneByCode(ThrHsUnitEntity entity) {
        QueryWrapper<ThrHsUnitEntity> qw = new QueryWrapper<ThrHsUnitEntity>().eq("unit_code", entity.getUnitCode());
        return getOne(qw);
    }
}