package io.renren.modules.org_ctb.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.org_ctb.dao.OrgCtbAccountDao;
import io.renren.modules.org_ctb.entity.OrgCtbAccountEntity;
import io.renren.modules.org_ctb.service.OrgCtbAccountService;


@Service("orgCtbAccountService")
public class OrgCtbAccountServiceImpl extends ServiceImpl<OrgCtbAccountDao, OrgCtbAccountEntity> implements OrgCtbAccountService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrgCtbAccountEntity> page = this.page(
                new Query<OrgCtbAccountEntity>().getPage(params),
                new QueryWrapper<OrgCtbAccountEntity>()
        );

        return new PageUtils(page);
    }

}