package io.renren.modules.ctb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.ctb.dao.CtbPartnerDao;
import io.renren.modules.ctb.dao.CtbPartnerTypeDao;
import io.renren.modules.ctb.entity.CtbPartnerEntity;
import io.renren.modules.ctb.entity.CtbPartnerTypeEntity;
import io.renren.modules.ctb.service.CtbPartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service("ctbPartnerService")
public class CtbPartnerServiceImpl extends ServiceImpl<CtbPartnerDao, CtbPartnerEntity> implements CtbPartnerService {

    private CtbPartnerDao partnerDao;
    private CtbPartnerTypeDao partnerTypeDao;

    @Autowired
    public void setPartnerDao(CtbPartnerDao partnerDao) {
        this.partnerDao = partnerDao;
    }

    @Autowired
    public void setPartnerTypeDao(CtbPartnerTypeDao partnerTypeDao) {
        this.partnerTypeDao = partnerTypeDao;
    }

    @Override
    @Transactional
    public Long saveReturnId(CtbPartnerEntity entity) {
        save(entity);
        for (CtbPartnerTypeEntity typeEntity : entity.getTypeList()) {
            typeEntity.setFkPartnerId(entity.getId());
            partnerTypeDao.insert(typeEntity);
        }
        return entity.getId();
    }

    @Override
    @Transactional
    public void update(CtbPartnerEntity entity) {
        CtbPartnerEntity oldEntity = partnerDao.simpleDetail(entity.getId());
        for (CtbPartnerTypeEntity oldTypeEntity : oldEntity.getTypeList()) {
            partnerTypeDao.deleteById(oldTypeEntity.getId());
        }
        updateById(entity);
        for (CtbPartnerTypeEntity newTypeEntity : entity.getTypeList()) {
            newTypeEntity.setFkPartnerId(entity.getId());
            partnerTypeDao.insert(newTypeEntity);
        }
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<CtbPartnerEntity> qw = new QueryWrapper<>();
        qw.like(params.get(Constant.KEY) != null, "name", params.get(Constant.KEY));
        qw.eq("available", 1);
        IPage<CtbPartnerEntity> page = this.page(new Query<CtbPartnerEntity>().getPage(params), qw);
        return new PageUtils(page);
    }

    @Override
    public List<CtbPartnerEntity> listByTypes(List<Integer> types, String name) {
        if (name != null) {
            name = name.trim();
        }
        return partnerDao.listByTypes(types, name);
    }

    @Override
    public CtbPartnerEntity detail(Long id) {
        return getBaseMapper().simpleDetail(id);
    }

    @Override
    public CtbPartnerEntity detailByCode(String code) {
        return getBaseMapper().simpleDetailByCode(code);
    }
}