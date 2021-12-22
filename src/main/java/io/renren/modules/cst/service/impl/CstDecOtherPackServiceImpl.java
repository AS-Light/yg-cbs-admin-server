package io.renren.modules.cst.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.cst.dao.CstDecOtherPackDao;
import io.renren.modules.cst.entity.CstDecOtherPackEntity;
import io.renren.modules.cst.service.CstDecOtherPackService;


@Service("cstDecOtherPackService")
public class CstDecOtherPackServiceImpl extends ServiceImpl<CstDecOtherPackDao, CstDecOtherPackEntity> implements CstDecOtherPackService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CstDecOtherPackEntity> page = this.page(
                new Query<CstDecOtherPackEntity>().getPage(params),
                new QueryWrapper<CstDecOtherPackEntity>()
        );

        return new PageUtils(page);
    }

}