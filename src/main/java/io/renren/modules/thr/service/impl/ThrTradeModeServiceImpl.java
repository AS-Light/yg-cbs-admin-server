package io.renren.modules.thr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.thr.dao.ThrTradeModeDao;
import io.renren.modules.thr.entity.ThrTradeModeEntity;
import io.renren.modules.thr.service.ThrTradeModeService;
import org.springframework.stereotype.Service;


@Service("thrTradeModeService")
public class ThrTradeModeServiceImpl extends ServiceImpl<ThrTradeModeDao, ThrTradeModeEntity> implements ThrTradeModeService {

    @Override
    public PageUtils queryIndex(ThrTradeModeEntity entity) {
        QueryWrapper<ThrTradeModeEntity> qw = new QueryWrapper<>();
        qw.like(entity.getTradeModeCode() != null, "trade_mode_code", entity.getTradeModeCode());
        qw.or().like(entity.getName() != null, "name", entity.getName());
        IPage<ThrTradeModeEntity> page = this.page(new QueryPage<ThrTradeModeEntity>().getPage(entity), qw);
        return new PageUtils(page);
    }

    public ThrTradeModeEntity getOneByCode(ThrTradeModeEntity entity) {
        QueryWrapper<ThrTradeModeEntity> qw = new QueryWrapper<ThrTradeModeEntity>().eq("trade_mode_code", entity.getTradeModeCode());
        return getOne(qw);
    }
}