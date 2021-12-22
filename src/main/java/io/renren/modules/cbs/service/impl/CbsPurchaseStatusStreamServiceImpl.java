package io.renren.modules.cbs.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.cbs.dao.CbsPurchaseStatusStreamDao;
import io.renren.modules.cbs.entity.CbsPurchaseStatusStreamEntity;
import io.renren.modules.cbs.service.CbsPurchaseStatusStreamService;


@Service("cbsPurchaseStatusStreamService")
public class CbsPurchaseStatusStreamServiceImpl extends ServiceImpl<CbsPurchaseStatusStreamDao, CbsPurchaseStatusStreamEntity> implements CbsPurchaseStatusStreamService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CbsPurchaseStatusStreamEntity> page = this.page(
                new Query<CbsPurchaseStatusStreamEntity>().getPage(params),
                new QueryWrapper<CbsPurchaseStatusStreamEntity>()
        );

        return new PageUtils(page);
    }

}