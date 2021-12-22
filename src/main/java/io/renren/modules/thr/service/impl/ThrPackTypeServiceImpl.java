package io.renren.modules.thr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.thr.dao.ThrPackTypeDao;
import io.renren.modules.thr.entity.ThrPackTypeEntity;
import io.renren.modules.thr.service.ThrPackTypeService;
import org.springframework.stereotype.Service;


@Service("thrPackTypeService")
public class ThrPackTypeServiceImpl extends ServiceImpl<ThrPackTypeDao, ThrPackTypeEntity> implements ThrPackTypeService {

    @Override
    public PageUtils queryIndex(ThrPackTypeEntity entity) {
        QueryWrapper<ThrPackTypeEntity> qw = new QueryWrapper<>();
        qw.like(entity.getPackTypeCode() != null, "pack_type_code", entity.getPackTypeCode());
        qw.or().like(entity.getName() != null, "name", entity.getName());
        IPage<ThrPackTypeEntity> page = this.page(new QueryPage<ThrPackTypeEntity>().getPage(entity), qw);
        return new PageUtils(page);
    }

    public ThrPackTypeEntity getOneByCode(ThrPackTypeEntity entity) {
        QueryWrapper<ThrPackTypeEntity> qw = new QueryWrapper<ThrPackTypeEntity>().eq("pack_type_code", entity.getPackTypeCode());
        return getOne(qw);
    }
}