/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.org_ctb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.org_ctb.dao.OrgCtbRoleMenuDao;
import io.renren.modules.org_ctb.entity.OrgCtbRoleMenuEntity;
import io.renren.modules.org_ctb.service.OrgCtbRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 角色与菜单对应关系
 *
 * @author Mark sunlightcs@gmail.com
 */
@Service("orgCtbRoleMenuService")
public class OrgCtbRoleMenuServiceImpl extends ServiceImpl<OrgCtbRoleMenuDao, OrgCtbRoleMenuEntity> implements OrgCtbRoleMenuService {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
		//先删除角色与菜单关系
		deleteBatch(new Long[]{roleId});

		if(menuIdList.size() == 0){
			return ;
		}

		//保存角色与菜单关系
		for(Long menuId : menuIdList){
			OrgCtbRoleMenuEntity orgCtbRoleMenuEntity = new OrgCtbRoleMenuEntity();
			orgCtbRoleMenuEntity.setMenuId(menuId);
			orgCtbRoleMenuEntity.setRoleId(roleId);

			this.save(orgCtbRoleMenuEntity);
		}
	}

	@Override
	public List<Long> queryMenuIdList(Long roleId) {
		return baseMapper.queryMenuIdList(roleId);
	}

	@Override
	public int deleteBatch(Long[] roleIds){
		return baseMapper.deleteBatch(roleIds);
	}

}
