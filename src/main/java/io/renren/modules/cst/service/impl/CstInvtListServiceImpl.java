package io.renren.modules.cst.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.cst.dao.CstInvtListDao;
import io.renren.modules.cst.entity.CstInvtListEntity;
import io.renren.modules.cst.service.CstInvtListService;


@Service("cstInvtListService")
public class CstInvtListServiceImpl extends ServiceImpl<CstInvtListDao, CstInvtListEntity> implements CstInvtListService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CstInvtListEntity> page = this.page(
                new Query<CstInvtListEntity>().getPage(params),
                new QueryWrapper<CstInvtListEntity>()
        );

        return new PageUtils(page);
    }

}