package io.renren.modules.cst.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.cst.dao.CstNptsDcrHeaderDao;
import io.renren.modules.cst.entity.CstNptsDcrHeaderEntity;
import io.renren.modules.cst.service.CstNptsDcrHeaderService;


@Service("cstNptsDcrHeaderService")
public class CstNptsDcrHeaderServiceImpl extends ServiceImpl<CstNptsDcrHeaderDao, CstNptsDcrHeaderEntity> implements CstNptsDcrHeaderService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CstNptsDcrHeaderEntity> page = this.page(
                new Query<CstNptsDcrHeaderEntity>().getPage(params),
                new QueryWrapper<CstNptsDcrHeaderEntity>()
        );

        return new PageUtils(page);
    }

}