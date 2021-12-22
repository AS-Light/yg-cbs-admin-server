/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.org_cbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.org_cbs.dao.OrgCbsLogDao;
import io.renren.modules.org_cbs.entity.OrgCbsLogEntity;
import io.renren.modules.org_cbs.service.OrgCbsLogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("orgCbsLogService")
public class OrgCbsLogServiceImpl extends ServiceImpl<OrgCbsLogDao, OrgCbsLogEntity> implements OrgCbsLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String) params.get("key");

        IPage<OrgCbsLogEntity> page = this.page(
                new Query<OrgCbsLogEntity>().getPage(params),
                new QueryWrapper<OrgCbsLogEntity>().like(StringUtils.isNotBlank(key), "username", key).orderByDesc("create_date")
        );

        return new PageUtils(page);
    }
}
