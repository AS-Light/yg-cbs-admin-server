package io.renren.modules.cst.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.cst.dao.CstDecListDao;
import io.renren.modules.cst.entity.CstDecListEntity;
import io.renren.modules.cst.service.CstDecListService;


@Service("cstDecListService")
public class CstDecListServiceImpl extends ServiceImpl<CstDecListDao, CstDecListEntity> implements CstDecListService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CstDecListEntity> page = this.page(
                new Query<CstDecListEntity>().getPage(params),
                new QueryWrapper<CstDecListEntity>()
        );

        return new PageUtils(page);
    }

}