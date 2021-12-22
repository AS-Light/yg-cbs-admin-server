/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.org_ctb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.org_ctb.entity.OrgCtbRoleEntity;

import java.util.List;
import java.util.Map;


/**
 * 角色
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface OrgCtbRoleService extends IService<OrgCtbRoleEntity> {

	PageUtils queryPage(Map<String, Object> params);

	void saveRole(OrgCtbRoleEntity role, Boolean isAdmin);

	void update(OrgCtbRoleEntity role, Boolean isAdmin);

	void deleteBatch(Long[] roleIds);

	
	/**
	 * 查询用户创建的角色ID列表
	 */
	List<Long> queryRoleIdList(Long createUserId);
}
