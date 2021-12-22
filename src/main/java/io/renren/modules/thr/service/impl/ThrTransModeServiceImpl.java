package io.renren.modules.thr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.thr.dao.ThrTransModeDao;
import io.renren.modules.thr.entity.ThrTransModeEntity;
import io.renren.modules.thr.service.ThrTransModeService;
import org.springframework.stereotype.Service;


@Service("thrTransModeService")
public class ThrTransModeServiceImpl extends ServiceImpl<ThrTransModeDao, ThrTransModeEntity> implements ThrTransModeService {

    @Override
    public PageUtils queryIndex(ThrTransModeEntity entity) {
        QueryWrapper<ThrTransModeEntity> qw = new QueryWrapper<>();
        qw.like(entity.getTransModeCode() != null, "trans_mode_code", entity.getTransModeCode());
        qw.or().like(entity.getName() != null, "name", entity.getName());
        IPage<ThrTransModeEntity> page = this.page(new QueryPage<ThrTransModeEntity>().getPage(entity), qw);
        return new PageUtils(page);
    }

    public ThrTransModeEntity getOneByCode(ThrTransModeEntity entity) {
        QueryWrapper<ThrTransModeEntity> qw = new QueryWrapper<ThrTransModeEntity>().eq("trans_mode_code", entity.getTransModeCode());
        return getOne(qw);
    }
}