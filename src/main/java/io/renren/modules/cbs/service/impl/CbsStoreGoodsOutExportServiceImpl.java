package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.cbs.dao.CbsStoreGoodsOutExportDao;
import io.renren.modules.cbs.entity.CbsStoreGoodsOutExportEntity;
import io.renren.modules.cbs.service.CbsStoreGoodsOutExportService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("cbsStoreGoodsOutExportService")
public class CbsStoreGoodsOutExportServiceImpl extends ServiceImpl<CbsStoreGoodsOutExportDao, CbsStoreGoodsOutExportEntity> implements CbsStoreGoodsOutExportService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CbsStoreGoodsOutExportEntity> page = this.page(
                new Query<CbsStoreGoodsOutExportEntity>().getPage(params),
                new QueryWrapper<CbsStoreGoodsOutExportEntity>()
        );

        return new PageUtils(page);
    }

}