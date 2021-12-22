package io.renren.modules.org_cbs.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.org_cbs.dao.OrgCbsAccountDao;
import io.renren.modules.org_cbs.entity.OrgCbsAccountEntity;
import io.renren.modules.org_cbs.service.OrgCbsAccountService;


@Service("orgCbsAccountService")
public class OrgCbsAccountServiceImpl extends ServiceImpl<OrgCbsAccountDao, OrgCbsAccountEntity> implements OrgCbsAccountService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrgCbsAccountEntity> page = this.page(
                new Query<OrgCbsAccountEntity>().getPage(params),
                new QueryWrapper<OrgCbsAccountEntity>()
        );

        return new PageUtils(page);
    }

}