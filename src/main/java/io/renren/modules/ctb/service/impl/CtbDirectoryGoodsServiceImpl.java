package io.renren.modules.ctb.service.impl;

import io.renren.common.utils.QueryPage;
import io.renren.modules.ctb.dao.CtbDirectoryGoodsDao;
import io.renren.modules.ctb.entity.CtbDirectoryGoodsEntity;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.ctb.dao.CtbDirectoryGoodsDao;
import io.renren.modules.ctb.entity.CtbDirectoryGoodsEntity;
import io.renren.modules.ctb.service.CtbDirectoryGoodsService;


@Service("ctbDirectoryGoodsService")
public class CtbDirectoryGoodsServiceImpl extends ServiceImpl<CtbDirectoryGoodsDao, CtbDirectoryGoodsEntity> implements CtbDirectoryGoodsService {

    private CtbDirectoryGoodsDao goodsDao;

    public CtbDirectoryGoodsServiceImpl(CtbDirectoryGoodsDao goodsDao) {
        this.goodsDao = goodsDao;
    }

    @Override
    public PageUtils queryPage(CtbDirectoryGoodsEntity entity) {
        QueryWrapper<CtbDirectoryGoodsEntity> qw = new QueryWrapper<>();
        qw.like(entity.getName() != null, "name", entity.getName());
        qw.like(entity.getNickname() != null, "nickname", entity.getNickname());
        qw.like(entity.getHsCode() != null, "hs_code", entity.getHsCode());
        qw.eq("available", 1);
        IPage page = this.page(new QueryPage<CtbDirectoryGoodsEntity>().getPage(entity), qw);
        return new PageUtils(page);
    }

    @Override
    public CtbDirectoryGoodsEntity detail(Long id) {
        return goodsDao.detail(id);
    }

}