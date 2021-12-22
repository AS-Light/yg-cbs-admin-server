package io.renren.modules.thr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.thr.dao.ThrDeclPortDao;
import io.renren.modules.thr.entity.ThrDeclPortEntity;
import io.renren.modules.thr.service.ThrDeclPortService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("thrDeclPortService")
public class ThrDeclPortServiceImpl extends ServiceImpl<ThrDeclPortDao, ThrDeclPortEntity> implements ThrDeclPortService {

    @Override
    public List<ThrDeclPortEntity> listAll() {
        return this.list();
    }

    @Override
    public PageUtils queryIndex(ThrDeclPortEntity entity) {
        QueryWrapper<ThrDeclPortEntity> qw = new QueryWrapper<>();
        qw.like(entity.getName() != null, "name", entity.getName());
        qw.or().like(entity.getDeclPort() != null, "decl_port", entity.getDeclPort());
        IPage<ThrDeclPortEntity> page = this.page(new QueryPage<ThrDeclPortEntity>().getPage(entity), qw);
        return new PageUtils(page);
    }

    public ThrDeclPortEntity getOneByCode(ThrDeclPortEntity entity) {
        QueryWrapper<ThrDeclPortEntity> qw = new QueryWrapper<ThrDeclPortEntity>().eq("decl_port", entity.getDeclPort());
        return getOne(qw);
    }

}