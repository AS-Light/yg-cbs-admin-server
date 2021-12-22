package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.cbs.dao.CbsDirectoryGoodsDao;
import io.renren.modules.cbs.entity.CbsDirectoryGoodsEntity;
import io.renren.modules.cbs.service.CbsDirectoryGoodsService;
import org.springframework.stereotype.Service;


@Service("cbsDirectoryGoodsService")
public class CbsDirectoryGoodsServiceImpl extends ServiceImpl<CbsDirectoryGoodsDao, CbsDirectoryGoodsEntity> implements CbsDirectoryGoodsService {

    final CbsDirectoryGoodsDao goodsDao;

    public CbsDirectoryGoodsServiceImpl(CbsDirectoryGoodsDao goodsDao) {
        this.goodsDao = goodsDao;
    }

    @Override
    public PageUtils queryPage(CbsDirectoryGoodsEntity entity) {
        QueryWrapper<CbsDirectoryGoodsEntity> qw = new QueryWrapper<>();
        qw.like(entity.getName() != null, "name", entity.getName());
        qw.like(entity.getNickname() != null, "nickname", entity.getNickname());
        qw.like(entity.getHsCode() != null, "hs_code", entity.getHsCode());
        qw.eq("available", 1);
        IPage page = this.page(new QueryPage<CbsDirectoryGoodsEntity>().getPage(entity), qw);
        return new PageUtils(page);
    }

    @Override
    public CbsDirectoryGoodsEntity detail(Long id) {
        return goodsDao.detail(id);
    }
}