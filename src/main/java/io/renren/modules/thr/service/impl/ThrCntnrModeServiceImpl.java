package io.renren.modules.thr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.thr.dao.ThrCntnrModeDao;
import io.renren.modules.thr.entity.ThrCntnrModeEntity;
import io.renren.modules.thr.service.ThrCntnrModeService;
import org.springframework.stereotype.Service;


@Service("thrCntnrModeService")
public class ThrCntnrModeServiceImpl extends ServiceImpl<ThrCntnrModeDao, ThrCntnrModeEntity> implements ThrCntnrModeService {

    @Override
    public PageUtils queryIndex(ThrCntnrModeEntity entity) {
        QueryWrapper<ThrCntnrModeEntity> qw = new QueryWrapper<>();
        qw.like(entity.getCntnrModeCode() != null, "cntnr_mode_code", entity.getCntnrModeCode());
        qw.or().like(entity.getName() != null, "name", entity.getName());
        IPage<ThrCntnrModeEntity> page = this.page(new QueryPage<ThrCntnrModeEntity>().getPage(entity), qw);
        return new PageUtils(page);
    }

    public ThrCntnrModeEntity getOneByCode(ThrCntnrModeEntity entity) {
        QueryWrapper<ThrCntnrModeEntity> qw = new QueryWrapper<ThrCntnrModeEntity>().eq("cntnr_mode_code", entity.getCntnrModeCode());
        return getOne(qw);
    }
}