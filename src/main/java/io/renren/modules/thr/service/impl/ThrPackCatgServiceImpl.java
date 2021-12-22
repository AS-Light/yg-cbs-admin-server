package io.renren.modules.thr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.thr.dao.ThrPackCatgDao;
import io.renren.modules.thr.entity.ThrPackCatgEntity;
import io.renren.modules.thr.service.ThrPackCatgService;
import org.springframework.stereotype.Service;


@Service("thrPackCatgService")
public class ThrPackCatgServiceImpl extends ServiceImpl<ThrPackCatgDao, ThrPackCatgEntity> implements ThrPackCatgService {

    @Override
    public PageUtils queryIndex(ThrPackCatgEntity entity) {
        QueryWrapper<ThrPackCatgEntity> qw = new QueryWrapper<>();
        qw.like(entity.getPackCatgCode() != null, "pack_catg_code", entity.getPackCatgCode());
        qw.or().like(entity.getName() != null, "name", entity.getName());
        IPage<ThrPackCatgEntity> page = this.page(new QueryPage<ThrPackCatgEntity>().getPage(entity), qw);
        return new PageUtils(page);
    }

    public ThrPackCatgEntity getOneByCode(ThrPackCatgEntity entity) {
        QueryWrapper<ThrPackCatgEntity> qw = new QueryWrapper<ThrPackCatgEntity>().eq("pack_catg_code", entity.getPackCatgCode());
        return getOne(qw);
    }
}