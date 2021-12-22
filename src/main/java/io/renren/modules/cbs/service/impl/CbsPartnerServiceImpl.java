package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.cbs.dao.CbsPartnerDao;
import io.renren.modules.cbs.dao.CbsPartnerTypeDao;
import io.renren.modules.cbs.entity.CbsPartnerEntity;
import io.renren.modules.cbs.entity.CbsPartnerTypeEntity;
import io.renren.modules.cbs.service.CbsPartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service("cbsPartnerService")
public class CbsPartnerServiceImpl extends ServiceImpl<CbsPartnerDao, CbsPartnerEntity> implements CbsPartnerService {

    private CbsPartnerDao partnerDao;
    private CbsPartnerTypeDao partnerTypeDao;

    @Autowired
    public void setPartnerDao(CbsPartnerDao partnerDao) {
        this.partnerDao = partnerDao;
    }

    @Autowired
    public void setPartnerTypeDao(CbsPartnerTypeDao partnerTypeDao) {
        this.partnerTypeDao = partnerTypeDao;
    }

    @Override
    @Transactional
    public Long saveReturnId(CbsPartnerEntity entity) {
        save(entity);
        for (CbsPartnerTypeEntity typeEntity : entity.getTypeList()) {
            typeEntity.setFkPartnerId(entity.getId());
            partnerTypeDao.insert(typeEntity);
        }
        return entity.getId();
    }

    @Override
    @Transactional
    public void update(CbsPartnerEntity entity) {
        CbsPartnerEntity oldEntity = partnerDao.simpleDetail(entity.getId());
        for (CbsPartnerTypeEntity oldTypeEntity : oldEntity.getTypeList()) {
            partnerTypeDao.deleteById(oldTypeEntity.getId());
        }
        updateById(entity);
        for (CbsPartnerTypeEntity newTypeEntity : entity.getTypeList()) {
            newTypeEntity.setFkPartnerId(entity.getId());
            partnerTypeDao.insert(newTypeEntity);
        }
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<CbsPartnerEntity> qw = new QueryWrapper<>();
        qw.like(params.get(Constant.KEY) != null, "name", params.get(Constant.KEY));
        qw.eq("available", 1);
        IPage<CbsPartnerEntity> page = this.page(new Query<CbsPartnerEntity>().getPage(params), qw);
        return new PageUtils(page);
    }

    @Override
    public List<CbsPartnerEntity> listByTypes(List<Integer> types, String name) {
        if (name != null) {
            name = name.trim();
        }
        return partnerDao.listByTypes(types, name);
    }

    @Override
    public CbsPartnerEntity detail(Long id) {
        return getBaseMapper().simpleDetail(id);
    }

    @Override
    public CbsPartnerEntity detailByCode(String code) {
        return getBaseMapper().simpleDetailByCode(code);
    }
}