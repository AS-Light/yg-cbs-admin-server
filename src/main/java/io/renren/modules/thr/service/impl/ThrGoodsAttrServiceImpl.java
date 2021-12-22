package io.renren.modules.thr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.thr.dao.ThrGoodsAttrDao;
import io.renren.modules.thr.entity.ThrGoodsAttrEntity;
import io.renren.modules.thr.service.ThrGoodsAttrService;
import org.springframework.stereotype.Service;


@Service("thrGoodsAttrService")
public class ThrGoodsAttrServiceImpl extends ServiceImpl<ThrGoodsAttrDao, ThrGoodsAttrEntity> implements ThrGoodsAttrService {

    @Override
    public PageUtils queryIndex(ThrGoodsAttrEntity entity) {
        QueryWrapper<ThrGoodsAttrEntity> qw = new QueryWrapper<>();
        qw.like(entity.getGoodsAttrCode() != null, "goods_attr_code", entity.getGoodsAttrCode());
        qw.or().like(entity.getName() != null, "name", entity.getName());
        IPage<ThrGoodsAttrEntity> page = this.page(new QueryPage<ThrGoodsAttrEntity>().getPage(entity), qw);
        return new PageUtils(page);
    }

    public ThrGoodsAttrEntity getOneByCode(ThrGoodsAttrEntity entity) {
        QueryWrapper<ThrGoodsAttrEntity> qw = new QueryWrapper<ThrGoodsAttrEntity>().eq("goods_attr_code", entity.getGoodsAttrCode());
        return getOne(qw);
    }
}