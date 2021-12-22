package io.renren.modules.thr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.thr.dao.ThrEciqDao;
import io.renren.modules.thr.entity.ThrEciqEntity;
import io.renren.modules.thr.service.ThrEciqService;
import org.springframework.stereotype.Service;


@Service("thrEciqService")
public class ThrEciqServiceImpl extends ServiceImpl<ThrEciqDao, ThrEciqEntity> implements ThrEciqService {

    @Override
    public PageUtils queryIndex(ThrEciqEntity entity) {
        QueryWrapper<ThrEciqEntity> qw = new QueryWrapper<>();
        qw.like(entity.getEciqCode() != null, "eciq_code", entity.getEciqCode());
        qw.or().like(entity.getNameEn() != null, "name_en", entity.getNameEn());
        qw.or().like(entity.getNameCn() != null, "name_cn", entity.getNameCn());
        IPage<ThrEciqEntity> page = this.page(new QueryPage<ThrEciqEntity>().getPage(entity), qw);
        return new PageUtils(page);
    }

    public ThrEciqEntity getOneByCode(ThrEciqEntity entity) {
        QueryWrapper<ThrEciqEntity> qw = new QueryWrapper<ThrEciqEntity>().eq("eciq_code", entity.getEciqCode());
        return getOne(qw);
    }
}