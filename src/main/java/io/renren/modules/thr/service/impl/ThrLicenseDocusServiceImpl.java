package io.renren.modules.thr.service.impl;

import io.renren.common.utils.QueryPage;
import io.renren.modules.thr.entity.ThrLicenseDocusEntity;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.thr.dao.ThrLicenseDocusDao;
import io.renren.modules.thr.entity.ThrLicenseDocusEntity;
import io.renren.modules.thr.service.ThrLicenseDocusService;


@Service("thrLicenseDocusService")
public class ThrLicenseDocusServiceImpl extends ServiceImpl<ThrLicenseDocusDao, ThrLicenseDocusEntity> implements ThrLicenseDocusService {

    @Override
    public PageUtils queryIndex(ThrLicenseDocusEntity entity) {
        QueryWrapper<ThrLicenseDocusEntity> qw = new QueryWrapper<>();
        qw.like(entity.getCode() != null, "code", entity.getCode());
        qw.or().like(entity.getName() != null, "name", entity.getName());
        IPage<ThrLicenseDocusEntity> page = this.page(new QueryPage<ThrLicenseDocusEntity>().getPage(entity), qw);
        return new PageUtils(page);
    }

    public ThrLicenseDocusEntity getOneByCode(ThrLicenseDocusEntity entity) {
        QueryWrapper<ThrLicenseDocusEntity> qw = new QueryWrapper<ThrLicenseDocusEntity>().eq("code", entity.getCode());
        return getOne(qw);
    }

}