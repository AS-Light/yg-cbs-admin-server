package io.renren.modules.ctb.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.ctb.dao.CtbImportGoodsDao;
import io.renren.modules.ctb.entity.CtbImportGoodsEntity;
import io.renren.modules.ctb.service.CtbImportGoodsService;


@Service("ctbImportGoodsService")
public class CtbImportGoodsServiceImpl extends ServiceImpl<CtbImportGoodsDao, CtbImportGoodsEntity> implements CtbImportGoodsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CtbImportGoodsEntity> page = this.page(
                new Query<CtbImportGoodsEntity>().getPage(params),
                new QueryWrapper<CtbImportGoodsEntity>()
        );

        return new PageUtils(page);
    }

}