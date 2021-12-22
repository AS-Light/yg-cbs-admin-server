package io.renren.modules.thr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.thr.dao.ThrEmlTypeDao;
import io.renren.modules.thr.entity.ThrEmlTypeEntity;
import io.renren.modules.thr.service.ThrEmlTypeService;
import org.springframework.stereotype.Service;


@Service("thrEmlTypeService")
public class ThrEmlTypeServiceImpl extends ServiceImpl<ThrEmlTypeDao, ThrEmlTypeEntity> implements ThrEmlTypeService {

    @Override
    public PageUtils queryIndex(ThrEmlTypeEntity entity) {
        QueryWrapper<ThrEmlTypeEntity> qw = new QueryWrapper<>();
        qw.like(entity.getCode() != null, "code", entity.getCode());
        qw.or().like(entity.getName() != null, "name", entity.getName());
        IPage<ThrEmlTypeEntity> page = this.page(new QueryPage<ThrEmlTypeEntity>().getPage(entity), qw);
        return new PageUtils(page);
    }

    public ThrEmlTypeEntity getOneByCode(ThrEmlTypeEntity entity) {
        QueryWrapper<ThrEmlTypeEntity> qw = new QueryWrapper<ThrEmlTypeEntity>().eq("code", entity.getCode());
        return getOne(qw);
    }
    
}