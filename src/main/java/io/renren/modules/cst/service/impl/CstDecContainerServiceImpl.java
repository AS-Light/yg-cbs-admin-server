package io.renren.modules.cst.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.cst.dao.CstDecContainerDao;
import io.renren.modules.cst.entity.CstDecContainerEntity;
import io.renren.modules.cst.service.CstDecContainerService;


@Service("cstDecContainerService")
public class CstDecContainerServiceImpl extends ServiceImpl<CstDecContainerDao, CstDecContainerEntity> implements CstDecContainerService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CstDecContainerEntity> page = this.page(
                new Query<CstDecContainerEntity>().getPage(params),
                new QueryWrapper<CstDecContainerEntity>()
        );

        return new PageUtils(page);
    }

}