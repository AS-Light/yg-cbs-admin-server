package io.renren.modules.thr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.thr.dao.ThrCurrencyDao;
import io.renren.modules.thr.entity.ThrCurrencyEntity;
import io.renren.modules.thr.service.ThrCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("thrCurrencyService")
public class ThrCurrencyServiceImpl extends ServiceImpl<ThrCurrencyDao, ThrCurrencyEntity> implements ThrCurrencyService {

    @Autowired
    ThrCurrencyDao currencyDao;

    @Override
    public List<ThrCurrencyEntity> queryIndex(ThrCurrencyEntity params) {
        QueryWrapper<ThrCurrencyEntity> wrapper = new QueryWrapper<>();
        wrapper.like(params.getCode() != null, "code", params.getCode());
        wrapper.or().like(params.getNameCn() != null, "name_cn", params.getNameCn());
        wrapper.or().like(params.getAbbreviation() != null, "abbreviation", params.getAbbreviation());
        wrapper.orderByAsc("code");
        return this.list(wrapper);
    }

    @Override
    public void updateRateWithList(List<ThrCurrencyEntity> list) {
        currencyDao.updateRateWithList(list);
    }

    public ThrCurrencyEntity getOneByCode(ThrCurrencyEntity entity) {
        QueryWrapper<ThrCurrencyEntity> qw = new QueryWrapper<ThrCurrencyEntity>().eq("code", entity.getCode());
        return getOne(qw);
    }
}