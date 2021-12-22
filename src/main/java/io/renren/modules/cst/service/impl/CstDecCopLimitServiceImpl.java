package io.renren.modules.cst.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.cst.dao.CstDecCopLimitDao;
import io.renren.modules.cst.entity.CstDecCopLimitEntity;
import io.renren.modules.cst.service.CstDecCopLimitService;


@Service("cstDecCopLimitService")
public class CstDecCopLimitServiceImpl extends ServiceImpl<CstDecCopLimitDao, CstDecCopLimitEntity> implements CstDecCopLimitService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CstDecCopLimitEntity> page = this.page(
                new Query<CstDecCopLimitEntity>().getPage(params),
                new QueryWrapper<CstDecCopLimitEntity>()
        );

        return new PageUtils(page);
    }

}