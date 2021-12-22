package io.renren.modules.ctb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.ctb.dao.CtbMoneyTypeDao;
import io.renren.modules.ctb.entity.CtbMoneyTypeEntity;
import io.renren.modules.ctb.service.CtbMoneyTypeService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("ctbMoneyTypeService")
public class CtbMoneyTypeServiceImpl extends ServiceImpl<CtbMoneyTypeDao, CtbMoneyTypeEntity> implements CtbMoneyTypeService {

    @Override
    public List<CtbMoneyTypeEntity> queryIndex(CtbMoneyTypeEntity entity) {
        QueryWrapper<CtbMoneyTypeEntity> qw = new QueryWrapper<>();
        qw.like(entity.getName() != null, "name", entity.getName());
        qw.orderByDesc("id");
        return this.list(qw);
    }

    @Override
    public List<CtbMoneyTypeEntity> listByServiceCompanyId(Long serviceCompanyId) {
        return baseMapper.listByServiceCompanyId(serviceCompanyId);
    }

    @Override
    public List<CtbMoneyTypeEntity> listDef() {
        return baseMapper.listDef();
    }
}