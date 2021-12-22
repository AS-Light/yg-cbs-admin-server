package io.renren.modules.thr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.thr.dao.ThrTrafModeDao;
import io.renren.modules.thr.entity.ThrTrafModeEntity;
import io.renren.modules.thr.service.ThrTrafModeService;
import org.springframework.stereotype.Service;


@Service("thrTrafModeService")
public class ThrTrafModeServiceImpl extends ServiceImpl<ThrTrafModeDao, ThrTrafModeEntity> implements ThrTrafModeService {

    @Override
    public PageUtils queryIndex(ThrTrafModeEntity entity) {
        QueryWrapper<ThrTrafModeEntity> qw = new QueryWrapper<>();
        qw.like(entity.getTrafModeCode() != null, "traf_mode_code", entity.getTrafModeCode());
        qw.or().like(entity.getName() != null, "name", entity.getName());
        IPage<ThrTrafModeEntity> page = this.page(new QueryPage<ThrTrafModeEntity>().getPage(entity), qw);
        return new PageUtils(page);
    }

    public ThrTrafModeEntity getOneByCode(ThrTrafModeEntity entity) {
        QueryWrapper<ThrTrafModeEntity> qw = new QueryWrapper<ThrTrafModeEntity>().eq("traf_mode_code", entity.getTrafModeCode());
        return getOne(qw);
    }
}