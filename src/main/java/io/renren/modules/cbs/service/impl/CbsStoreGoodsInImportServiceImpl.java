package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.cbs.dao.CbsStoreGoodsInImportDao;
import io.renren.modules.cbs.entity.CbsStoreGoodsInImportEntity;
import io.renren.modules.cbs.service.CbsStoreGoodsInImportService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("cbsStoreGoodsInImportService")
public class CbsStoreGoodsInImportServiceImpl extends ServiceImpl<CbsStoreGoodsInImportDao, CbsStoreGoodsInImportEntity> implements CbsStoreGoodsInImportService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CbsStoreGoodsInImportEntity> page = this.page(
                new Query<CbsStoreGoodsInImportEntity>().getPage(params),
                new QueryWrapper<CbsStoreGoodsInImportEntity>()
        );

        return new PageUtils(page);
    }

}