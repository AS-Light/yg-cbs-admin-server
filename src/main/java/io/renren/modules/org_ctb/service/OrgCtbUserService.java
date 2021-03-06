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
import io.renren.modules.org_ctb.entity.OrgCtbUserEntity;

import java.util.List;


/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface OrgCtbUserService extends IService<OrgCtbUserEntity> {

	PageUtils queryPage(OrgCtbUserEntity entity);

	List<OrgCtbUserEntity> listForChoice(OrgCtbUserEntity entity);

	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(Long userId);
	
	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);

	/**
	 * 根据用户名，查询系统用户
	 */
	OrgCtbUserEntity queryByUserName(Long tenantId, String username);

	/**
	 * 保存用户
	 */
	void saveUser(OrgCtbUserEntity user);
	
	/**
	 * 修改用户
	 */
	void update(OrgCtbUserEntity user);
	
	/**
	 * 删除用户
	 */
	void deleteBatch(Long[] userIds);

	/**
	 * 修改密码
	 * @param userId       用户ID
	 * @param password     原密码
	 * @param newPassword  新密码
	 */
	boolean updatePassword(Long userId, String password, String newPassword);
}
