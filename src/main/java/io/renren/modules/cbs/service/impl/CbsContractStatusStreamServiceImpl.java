package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.cbs.dao.CbsContractStatusStreamDao;
import io.renren.modules.cbs.entity.CbsContractStatusStreamEntity;
import io.renren.modules.cbs.service.CbsContractStatusStreamService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("cbsContractStatusStreamService")
public class CbsContractStatusStreamServiceImpl extends ServiceImpl<CbsContractStatusStreamDao, CbsContractStatusStreamEntity> implements CbsContractStatusStreamService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CbsContractStatusStreamEntity> page = this.page(
                new Query<CbsContractStatusStreamEntity>().getPage(params),
                new QueryWrapper<CbsContractStatusStreamEntity>()
        );

        return new PageUtils(page);
    }

}