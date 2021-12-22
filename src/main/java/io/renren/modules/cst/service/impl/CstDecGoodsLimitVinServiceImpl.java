package io.renren.modules.cst.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.cst.dao.CstDecGoodsLimitVinDao;
import io.renren.modules.cst.entity.CstDecGoodsLimitVinEntity;
import io.renren.modules.cst.service.CstDecGoodsLimitVinService;


@Service("cstDecGoodsLimitVinService")
public class CstDecGoodsLimitVinServiceImpl extends ServiceImpl<CstDecGoodsLimitVinDao, CstDecGoodsLimitVinEntity> implements CstDecGoodsLimitVinService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CstDecGoodsLimitVinEntity> page = this.page(
                new Query<CstDecGoodsLimitVinEntity>().getPage(params),
                new QueryWrapper<CstDecGoodsLimitVinEntity>()
        );

        return new PageUtils(page);
    }

}