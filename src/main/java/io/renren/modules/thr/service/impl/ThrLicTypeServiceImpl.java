package io.renren.modules.thr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.thr.dao.ThrLicTypeDao;
import io.renren.modules.thr.entity.ThrLicTypeEntity;
import io.renren.modules.thr.service.ThrLicTypeService;
import org.springframework.stereotype.Service;


@Service("thrLicTypeService")
public class ThrLicTypeServiceImpl extends ServiceImpl<ThrLicTypeDao, ThrLicTypeEntity> implements ThrLicTypeService {

    @Override
    public PageUtils queryIndex(ThrLicTypeEntity entity) {
        QueryWrapper<ThrLicTypeEntity> qw = new QueryWrapper<>();
        qw.like(entity.getLicTypeCode() != null, "lic_type_code", entity.getLicTypeCode());
        qw.or().like(entity.getName() != null, "name", entity.getName());
        IPage<ThrLicTypeEntity> page = this.page(new QueryPage<ThrLicTypeEntity>().getPage(entity), qw);
        return new PageUtils(page);
    }

    public ThrLicTypeEntity getOneByCode(ThrLicTypeEntity entity) {
        QueryWrapper<ThrLicTypeEntity> qw = new QueryWrapper<ThrLicTypeEntity>().eq("lic_type_code", entity.getLicTypeCode());
        return getOne(qw);
    }
}