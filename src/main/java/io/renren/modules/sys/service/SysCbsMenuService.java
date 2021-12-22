/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.sys.entity.SysCbsMenuEntity;

import java.util.List;


/**
 * 菜单管理
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface SysCbsMenuService extends IService<SysCbsMenuEntity> {

	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 * @param menuIdList  用户菜单ID
	 */
	List<SysCbsMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList);

	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 */
	List<SysCbsMenuEntity> queryListParentId(Long parentId);
	
	/**
	 * 获取不包含按钮的菜单列表
	 */
	List<SysCbsMenuEntity> queryNotButtonList();
	
	/**
	 * 获取用户菜单列表
	 */
	List<SysCbsMenuEntity> getUserMenuList(Long userId);

	/**
	 * 删除
	 */
	void delete(Long menuId);
}
