package io.renren.modules.cbs.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.cbs.dao.CbsSellStatusStreamDao;
import io.renren.modules.cbs.entity.CbsSellStatusStreamEntity;
import io.renren.modules.cbs.service.CbsSellStatusStreamService;


@Service("cbsSellStatusStreamService")
public class CbsSellStatusStreamServiceImpl extends ServiceImpl<CbsSellStatusStreamDao, CbsSellStatusStreamEntity> implements CbsSellStatusStreamService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CbsSellStatusStreamEntity> page = this.page(
                new Query<CbsSellStatusStreamEntity>().getPage(params),
                new QueryWrapper<CbsSellStatusStreamEntity>()
        );

        return new PageUtils(page);
    }

}