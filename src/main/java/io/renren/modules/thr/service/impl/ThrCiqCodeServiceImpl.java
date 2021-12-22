package io.renren.modules.thr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.thr.dao.ThrCiqCodeDao;
import io.renren.modules.thr.entity.ThrCiqCodeEntity;
import io.renren.modules.thr.service.ThrCiqCodeService;
import org.springframework.stereotype.Service;


@Service("thrCiqCodeService")
public class ThrCiqCodeServiceImpl extends ServiceImpl<ThrCiqCodeDao, ThrCiqCodeEntity> implements ThrCiqCodeService {

    @Override
    public PageUtils queryIndex(ThrCiqCodeEntity entity) {
        QueryWrapper<ThrCiqCodeEntity> qw = new QueryWrapper<>();
        qw.like(entity.getCiqCode() != null, "ciq_code", entity.getCiqCode());
        qw.or().like(entity.getHsCode() != null, "hs_code", entity.getHsCode());
        qw.or().like(entity.getName() != null, "name", entity.getName());
        IPage<ThrCiqCodeEntity> page = this.page(new QueryPage<ThrCiqCodeEntity>().getPage(entity), qw);
        return new PageUtils(page);
    }

    public ThrCiqCodeEntity getOneByCode(ThrCiqCodeEntity entity) {
        QueryWrapper<ThrCiqCodeEntity> qw = new QueryWrapper<ThrCiqCodeEntity>().eq("hs_code", entity.getHsCode()).eq("ciq_code", entity.getCiqCode());
        return getOne(qw);
    }
}