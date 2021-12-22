package io.renren.modules.thr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.thr.dao.ThrCutModeDao;
import io.renren.modules.thr.entity.ThrCutModeEntity;
import io.renren.modules.thr.service.ThrCutModeService;
import org.springframework.stereotype.Service;


@Service("thrCutModeService")
public class ThrCutModeServiceImpl extends ServiceImpl<ThrCutModeDao, ThrCutModeEntity> implements ThrCutModeService {

    @Override
    public PageUtils queryIndex(ThrCutModeEntity entity) {
        QueryWrapper<ThrCutModeEntity> qw = new QueryWrapper<>();
        qw.like(entity.getCutModeCode() != null, "cut_mode_code", entity.getCutModeCode());
        qw.or().like(entity.getName() != null, "name", entity.getName());
        IPage<ThrCutModeEntity> page = this.page(new QueryPage<ThrCutModeEntity>().getPage(entity), qw);
        return new PageUtils(page);
    }

    public ThrCutModeEntity getOneByCode(ThrCutModeEntity entity) {
        QueryWrapper<ThrCutModeEntity> qw = new QueryWrapper<ThrCutModeEntity>().eq("cut_mode_code", entity.getCutModeCode());
        return getOne(qw);
    }
}