package io.renren.modules.thr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.thr.dao.ThrCorrelationReasonDao;
import io.renren.modules.thr.entity.ThrCorrelationReasonEntity;
import io.renren.modules.thr.service.ThrCorrelationReasonService;
import org.springframework.stereotype.Service;


@Service("thrCorrelationReasonService")
public class ThrCorrelationReasonServiceImpl extends ServiceImpl<ThrCorrelationReasonDao, ThrCorrelationReasonEntity> implements ThrCorrelationReasonService {

    @Override
    public PageUtils queryIndex(ThrCorrelationReasonEntity entity) {
        QueryWrapper<ThrCorrelationReasonEntity> qw = new QueryWrapper<>();
        qw.like(entity.getName() != null, "name", entity.getName());
        IPage<ThrCorrelationReasonEntity> page = this.page(new QueryPage<ThrCorrelationReasonEntity>().getPage(entity), qw);
        return new PageUtils(page);
    }

    public ThrCorrelationReasonEntity getOneByCode(ThrCorrelationReasonEntity entity) {
        QueryWrapper<ThrCorrelationReasonEntity> qw = new QueryWrapper<ThrCorrelationReasonEntity>().eq("correlation_reason_flag", entity.getCorrelationReasonFlag());
        return getOne(qw);
    }

}