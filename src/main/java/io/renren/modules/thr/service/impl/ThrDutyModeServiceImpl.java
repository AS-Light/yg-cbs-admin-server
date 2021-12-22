package io.renren.modules.thr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.thr.dao.ThrDutyModeDao;
import io.renren.modules.thr.entity.ThrDutyModeEntity;
import io.renren.modules.thr.service.ThrDutyModeService;
import org.springframework.stereotype.Service;


@Service("thrDutyModeService")
public class ThrDutyModeServiceImpl extends ServiceImpl<ThrDutyModeDao, ThrDutyModeEntity> implements ThrDutyModeService {

    @Override
    public PageUtils queryIndex(ThrDutyModeEntity entity) {
        QueryWrapper<ThrDutyModeEntity> qw = new QueryWrapper<>();
        qw.like(entity.getName() != null, "name", entity.getName());
        qw.or().like(entity.getDutyMode() != null, "duty_mode", entity.getDutyMode());
        IPage<ThrDutyModeEntity> page = this.page(new QueryPage<ThrDutyModeEntity>().getPage(entity), qw);
        return new PageUtils(page);
    }

    public ThrDutyModeEntity getOneByCode(ThrDutyModeEntity entity) {
        QueryWrapper<ThrDutyModeEntity> qw = new QueryWrapper<ThrDutyModeEntity>().eq("duty_mode", entity.getDutyMode());
        return getOne(qw);
    }
}