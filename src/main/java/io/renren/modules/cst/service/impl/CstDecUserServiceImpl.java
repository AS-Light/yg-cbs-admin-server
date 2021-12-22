package io.renren.modules.cst.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.cst.dao.CstDecUserDao;
import io.renren.modules.cst.entity.CstDecUserEntity;
import io.renren.modules.cst.service.CstDecUserService;


@Service("cstDecUserService")
public class CstDecUserServiceImpl extends ServiceImpl<CstDecUserDao, CstDecUserEntity> implements CstDecUserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CstDecUserEntity> page = this.page(
                new Query<CstDecUserEntity>().getPage(params),
                new QueryWrapper<CstDecUserEntity>()
        );

        return new PageUtils(page);
    }

}