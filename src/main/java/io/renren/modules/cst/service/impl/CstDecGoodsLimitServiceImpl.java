package io.renren.modules.cst.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.cst.dao.CstDecGoodsLimitDao;
import io.renren.modules.cst.entity.CstDecGoodsLimitEntity;
import io.renren.modules.cst.service.CstDecGoodsLimitService;


@Service("cstDecGoodsLimitService")
public class CstDecGoodsLimitServiceImpl extends ServiceImpl<CstDecGoodsLimitDao, CstDecGoodsLimitEntity> implements CstDecGoodsLimitService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CstDecGoodsLimitEntity> page = this.page(
                new Query<CstDecGoodsLimitEntity>().getPage(params),
                new QueryWrapper<CstDecGoodsLimitEntity>()
        );

        return new PageUtils(page);
    }

}