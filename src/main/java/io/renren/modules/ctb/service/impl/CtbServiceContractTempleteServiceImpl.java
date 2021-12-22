package io.renren.modules.ctb.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.ctb.dao.CtbServiceContractTempleteDao;
import io.renren.modules.ctb.entity.CtbServiceContractTempleteEntity;
import io.renren.modules.ctb.service.CtbServiceContractTempleteService;


@Service("ctbServiceContractTempleteService")
public class CtbServiceContractTempleteServiceImpl extends ServiceImpl<CtbServiceContractTempleteDao, CtbServiceContractTempleteEntity> implements CtbServiceContractTempleteService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CtbServiceContractTempleteEntity> page = this.page(
                new Query<CtbServiceContractTempleteEntity>().getPage(params),
                new QueryWrapper<CtbServiceContractTempleteEntity>()
        );

        return new PageUtils(page);
    }

}