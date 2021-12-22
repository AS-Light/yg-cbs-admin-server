/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.org_ctb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.org_ctb.dao.OrgCtbLogDao;
import io.renren.modules.org_ctb.entity.OrgCtbLogEntity;
import io.renren.modules.org_ctb.service.OrgCtbLogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("orgCtbLogService")
public class OrgCtbLogServiceImpl extends ServiceImpl<OrgCtbLogDao, OrgCtbLogEntity> implements OrgCtbLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String) params.get("key");

        IPage<OrgCtbLogEntity> page = this.page(
                new Query<OrgCtbLogEntity>().getPage(params),
                new QueryWrapper<OrgCtbLogEntity>().like(StringUtils.isNotBlank(key), "username", key).orderByDesc("create_date")
        );

        return new PageUtils(page);
    }
}
