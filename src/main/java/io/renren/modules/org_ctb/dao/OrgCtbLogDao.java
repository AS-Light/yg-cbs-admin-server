/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.org_ctb.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.org_ctb.entity.OrgCtbLogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 系统日志
 *
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
@Repository
public interface OrgCtbLogDao extends BaseMapper<OrgCtbLogEntity> {
	
}
