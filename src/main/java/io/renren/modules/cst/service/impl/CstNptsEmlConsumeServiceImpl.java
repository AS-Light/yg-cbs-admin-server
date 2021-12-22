package io.renren.modules.cst.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.cst.dao.CstNptsEmlConsumeDao;
import io.renren.modules.cst.entity.CstNptsEmlConsumeEntity;
import io.renren.modules.cst.service.CstNptsEmlConsumeService;


@Service("cstNptsEmlConsumeService")
public class CstNptsEmlConsumeServiceImpl extends ServiceImpl<CstNptsEmlConsumeDao, CstNptsEmlConsumeEntity> implements CstNptsEmlConsumeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CstNptsEmlConsumeEntity> page = this.page(
                new Query<CstNptsEmlConsumeEntity>().getPage(params),
                new QueryWrapper<CstNptsEmlConsumeEntity>()
        );

        return new PageUtils(page);
    }

}