package io.renren.modules.thr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.thr.dao.ThrPortDao;
import io.renren.modules.thr.entity.ThrPortEntity;
import io.renren.modules.thr.service.ThrPortService;
import org.springframework.stereotype.Service;


@Service("thrPortService")
public class ThrPortServiceImpl extends ServiceImpl<ThrPortDao, ThrPortEntity> implements ThrPortService {

    @Override
    public PageUtils queryIndex(ThrPortEntity entity) {
        QueryWrapper<ThrPortEntity> qw = new QueryWrapper<>();
        qw.like(entity.getPortCode() != null, "port_code", entity.getPortCode());
        qw.or().like(entity.getNameEn() != null, "name_en", entity.getNameEn());
        qw.or().like(entity.getNameCn() != null, "name_cn", entity.getNameCn());
        IPage<ThrPortEntity> page = this.page(new QueryPage<ThrPortEntity>().getPage(entity), qw);
        return new PageUtils(page);
    }

    public ThrPortEntity getOneByCode(ThrPortEntity entity) {
        QueryWrapper<ThrPortEntity> qw = new QueryWrapper<ThrPortEntity>().eq("port_code", entity.getPortCode());
        return getOne(qw);
    }
}