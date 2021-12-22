package io.renren.modules.cst.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.cst.dao.CstNptsDcrExgDao;
import io.renren.modules.cst.entity.CstNptsDcrExgEntity;
import io.renren.modules.cst.service.CstNptsDcrExgService;


@Service("cstNptsDcrExgService")
public class CstNptsDcrExgServiceImpl extends ServiceImpl<CstNptsDcrExgDao, CstNptsDcrExgEntity> implements CstNptsDcrExgService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CstNptsDcrExgEntity> page = this.page(
                new Query<CstNptsDcrExgEntity>().getPage(params),
                new QueryWrapper<CstNptsDcrExgEntity>()
        );

        return new PageUtils(page);
    }

}