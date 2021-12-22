package io.renren.modules.cst.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.cst.dao.CstInvtHeaderDao;
import io.renren.modules.cst.entity.CstInvtHeaderEntity;
import io.renren.modules.cst.service.CstInvtHeaderService;


@Service("cstInvtHeaderService")
public class CstInvtHeaderServiceImpl extends ServiceImpl<CstInvtHeaderDao, CstInvtHeaderEntity> implements CstInvtHeaderService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CstInvtHeaderEntity> page = this.page(
                new Query<CstInvtHeaderEntity>().getPage(params),
                new QueryWrapper<CstInvtHeaderEntity>()
        );

        return new PageUtils(page);
    }

}