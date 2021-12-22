package io.renren.modules.thr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.thr.dao.ThrCiqEntryPortDao;
import io.renren.modules.thr.entity.ThrCiqEntryPortEntity;
import io.renren.modules.thr.service.ThrCiqEntryPortService;
import org.springframework.stereotype.Service;


@Service("thrCiqEntryPortService")
public class ThrCiqEntryPortServiceImpl extends ServiceImpl<ThrCiqEntryPortDao, ThrCiqEntryPortEntity> implements ThrCiqEntryPortService {
    @Override
    public PageUtils queryIndex(ThrCiqEntryPortEntity entity) {
        QueryWrapper<ThrCiqEntryPortEntity> qw = new QueryWrapper<>();
        qw.like(entity.getCiqEntyPortCode() != null, "ciq_enty_port_code", entity.getCiqEntyPortCode());
        qw.or().like(entity.getName() != null, "name", entity.getName());
        IPage<ThrCiqEntryPortEntity> page = this.page(new QueryPage<ThrCiqEntryPortEntity>().getPage(entity), qw);
        return new PageUtils(page);
    }

    public ThrCiqEntryPortEntity getOneByCode(ThrCiqEntryPortEntity entity) {
        QueryWrapper<ThrCiqEntryPortEntity> qw = new QueryWrapper<ThrCiqEntryPortEntity>().eq("ciq_enty_port_code", entity.getCiqEntyPortCode());
        return getOne(qw);
    }
}