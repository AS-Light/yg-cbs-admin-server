package io.renren.modules.cst.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.cst.dao.CstDecHeaderDao;
import io.renren.modules.cst.entity.CstDecHeaderEntity;
import io.renren.modules.cst.service.CstDecHeaderService;


@Service("cstDecHeaderService")
public class CstDecHeaderServiceImpl extends ServiceImpl<CstDecHeaderDao, CstDecHeaderEntity> implements CstDecHeaderService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CstDecHeaderEntity> page = this.page(
                new Query<CstDecHeaderEntity>().getPage(params),
                new QueryWrapper<CstDecHeaderEntity>()
        );

        return new PageUtils(page);
    }

}