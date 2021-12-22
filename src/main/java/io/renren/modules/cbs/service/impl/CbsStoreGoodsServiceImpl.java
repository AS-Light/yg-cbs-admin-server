package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.QueryPage;
import io.renren.modules.cbs.dao.CbsStoreGoodsDao;
import io.renren.modules.cbs.entity.CbsStoreGoodsByNoEntity;
import io.renren.modules.cbs.entity.CbsStoreGoodsEntity;
import io.renren.modules.cbs.service.CbsStoreGoodsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("cbsStoreGoodsService")
public class CbsStoreGoodsServiceImpl extends ServiceImpl<CbsStoreGoodsDao, CbsStoreGoodsEntity> implements CbsStoreGoodsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CbsStoreGoodsEntity> page = this.page(
                new Query<CbsStoreGoodsEntity>().getPage(params),
                new QueryWrapper<CbsStoreGoodsEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryIndex(CbsStoreGoodsByNoEntity entity) {
        IPage<CbsStoreGoodsByNoEntity> page = baseMapper.queryIndex(new QueryPage<CbsStoreGoodsByNoEntity>().getPage(entity), entity);
        return new PageUtils(page);
    }

    @Override
    public List<CbsStoreGoodsEntity> listForOutMaterialToProduce(Long produceId) {
        return baseMapper.listForOutMaterialToProduce(produceId);
    }

    @Override
    public List<CbsStoreGoodsEntity> listForOutProductToExport(Long exportId) {
        return baseMapper.listForOutProductToExport(exportId);
    }

    @Override
    public List<CbsStoreGoodsEntity> listForOutExport(Long exportId) {
        return baseMapper.listForOutExport(exportId);
    }

    @Override
    public List<CbsStoreGoodsEntity> listForOutSell(Long sellId) {
        return baseMapper.listForOutSell(sellId);
    }

}