/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.org_cbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.org_cbs.entity.OrgCbsRoleMenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色与菜单对应关系
 *
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
@Repository
public interface OrgCbsRoleMenuDao extends BaseMapper<OrgCbsRoleMenuEntity> {
	
	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> queryMenuIdList(Long roleId);

	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(Long[] roleIds);
}