package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.cbs.dao.CbsImportGoodsDao;
import io.renren.modules.cbs.entity.CbsImportGoodsEntity;
import io.renren.modules.cbs.service.CbsImportGoodsService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("cbsImportGoodsService")
public class CbsImportGoodsServiceImpl extends ServiceImpl<CbsImportGoodsDao, CbsImportGoodsEntity> implements CbsImportGoodsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CbsImportGoodsEntity> page = this.page(
                new Query<CbsImportGoodsEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public CbsImportGoodsEntity selectById(Long id) {
        return baseMapper.simpleDetail(id);
    }
}