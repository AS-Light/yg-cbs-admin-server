package io.renren.modules.thr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.thr.dao.ThrCiqDomeOriginDao;
import io.renren.modules.thr.entity.ThrCiqDomeOriginEntity;
import io.renren.modules.thr.service.ThrCiqDomeOriginService;
import org.springframework.stereotype.Service;


@Service("thrCiqDomeOriginService")
public class ThrCiqDomeOriginServiceImpl extends ServiceImpl<ThrCiqDomeOriginDao, ThrCiqDomeOriginEntity> implements ThrCiqDomeOriginService {

    @Override
    public PageUtils queryIndex(ThrCiqDomeOriginEntity entity) {
        QueryWrapper<ThrCiqDomeOriginEntity> qw = new QueryWrapper<>();
        qw.like(entity.getCiqDomeOriginCode() != null, "ciq_dome_origin_code", entity.getCiqDomeOriginCode());
        qw.or().like(entity.getName() != null, "name", entity.getName());
        IPage<ThrCiqDomeOriginEntity> page = this.page(new QueryPage<ThrCiqDomeOriginEntity>().getPage(entity), qw);
        return new PageUtils(page);
    }

    public ThrCiqDomeOriginEntity getOneByCode(ThrCiqDomeOriginEntity entity) {
        QueryWrapper<ThrCiqDomeOriginEntity> qw = new QueryWrapper<ThrCiqDomeOriginEntity>().eq("ciq_dome_origin_code", entity.getCiqDomeOriginCode());
        return getOne(qw);
    }

}