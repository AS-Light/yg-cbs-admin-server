package io.renren.modules.thr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.thr.dao.ThrPurposeDao;
import io.renren.modules.thr.entity.ThrPurposeEntity;
import io.renren.modules.thr.service.ThrPurposeService;
import org.springframework.stereotype.Service;


@Service("thrPurposeService")
public class ThrPurposeServiceImpl extends ServiceImpl<ThrPurposeDao, ThrPurposeEntity> implements ThrPurposeService {

    @Override
    public PageUtils queryIndex(ThrPurposeEntity entity) {
        QueryWrapper<ThrPurposeEntity> qw = new QueryWrapper<>();
        qw.like(entity.getPurposeCode() != null, "purpose_code", entity.getPurposeCode());
        qw.or().like(entity.getName() != null, "name", entity.getName());
        IPage<ThrPurposeEntity> page = this.page(new QueryPage<ThrPurposeEntity>().getPage(entity), qw);
        return new PageUtils(page);
    }

    public ThrPurposeEntity getOneByCode(ThrPurposeEntity entity) {
        QueryWrapper<ThrPurposeEntity> qw = new QueryWrapper<ThrPurposeEntity>().eq("purpose_code", entity.getPurposeCode());
        return getOne(qw);
    }
}