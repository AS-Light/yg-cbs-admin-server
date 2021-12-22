package io.renren.modules.thr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.thr.dao.ThrOrigPlaceCodeDao;
import io.renren.modules.thr.entity.ThrOrigPlaceCodeEntity;
import io.renren.modules.thr.service.ThrOrigPlaceCodeService;
import org.springframework.stereotype.Service;


@Service("thrOrigPlaceCodeService")
public class ThrOrigPlaceCodeServiceImpl extends ServiceImpl<ThrOrigPlaceCodeDao, ThrOrigPlaceCodeEntity> implements ThrOrigPlaceCodeService {

    @Override
    public PageUtils queryIndex(ThrOrigPlaceCodeEntity entity) {
        QueryWrapper<ThrOrigPlaceCodeEntity> qw = new QueryWrapper<>();
        qw.like(entity.getOrigPlaceCode() != null, "orig_place_code", entity.getOrigPlaceCode());
        qw.or().like(entity.getNameEn() != null, "name_en", entity.getNameEn());
        qw.or().like(entity.getNameCn() != null, "name_cn", entity.getNameCn());
        IPage<ThrOrigPlaceCodeEntity> page = this.page(new QueryPage<ThrOrigPlaceCodeEntity>().getPage(entity), qw);
        return new PageUtils(page);
    }

    public ThrOrigPlaceCodeEntity getOneByCode(ThrOrigPlaceCodeEntity entity) {
        QueryWrapper<ThrOrigPlaceCodeEntity> qw = new QueryWrapper<ThrOrigPlaceCodeEntity>().eq("orig_place_code", entity.getOrigPlaceCode());
        return getOne(qw);
    }
}