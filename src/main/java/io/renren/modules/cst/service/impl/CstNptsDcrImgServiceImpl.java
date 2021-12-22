package io.renren.modules.cst.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.cst.dao.CstNptsDcrImgDao;
import io.renren.modules.cst.entity.CstNptsDcrImgEntity;
import io.renren.modules.cst.service.CstNptsDcrImgService;


@Service("cstNptsDcrImgService")
public class CstNptsDcrImgServiceImpl extends ServiceImpl<CstNptsDcrImgDao, CstNptsDcrImgEntity> implements CstNptsDcrImgService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CstNptsDcrImgEntity> page = this.page(
                new Query<CstNptsDcrImgEntity>().getPage(params),
                new QueryWrapper<CstNptsDcrImgEntity>()
        );

        return new PageUtils(page);
    }

}