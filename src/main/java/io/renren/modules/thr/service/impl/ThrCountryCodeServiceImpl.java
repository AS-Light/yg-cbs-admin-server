package io.renren.modules.thr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.thr.dao.ThrCountryCodeDao;
import io.renren.modules.thr.entity.ThrCountryCodeEntity;
import io.renren.modules.thr.service.ThrCountryCodeService;
import org.springframework.stereotype.Service;


@Service("thrCountryCodeService")
public class ThrCountryCodeServiceImpl extends ServiceImpl<ThrCountryCodeDao, ThrCountryCodeEntity> implements ThrCountryCodeService {

    @Override
    public PageUtils queryIndex(ThrCountryCodeEntity entity) {
        QueryWrapper<ThrCountryCodeEntity> qw = new QueryWrapper<>();
        qw.like(entity.getCode() != null, "code", entity.getCode());
        qw.or().like(entity.getShortKey() != null, "short_key", entity.getShortKey());
        qw.or().like(entity.getNameEn() != null, "name_en", entity.getNameEn());
        qw.or().like(entity.getNameCn() != null, "name_cn", entity.getNameCn());
        IPage<ThrCountryCodeEntity> page = this.page(new QueryPage<ThrCountryCodeEntity>().getPage(entity), qw);
        return new PageUtils(page);
    }

    public ThrCountryCodeEntity getOneByCode(ThrCountryCodeEntity entity) {
        QueryWrapper<ThrCountryCodeEntity> qw = new QueryWrapper<ThrCountryCodeEntity>().eq("code", entity.getCode());
        return getOne(qw);
    }

}