/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.org_cbs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.org_cbs.dao.OrgCbsRoleMenuDao;
import io.renren.modules.org_cbs.entity.OrgCbsRoleMenuEntity;
import io.renren.modules.org_cbs.service.OrgCbsRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 角色与菜单对应关系
 *
 * @author Mark sunlightcs@gmail.com
 */
@Service("orgCbsRoleMenuService")
public class OrgCbsRoleMenuServiceImpl extends ServiceImpl<OrgCbsRoleMenuDao, OrgCbsRoleMenuEntity> implements OrgCbsRoleMenuService {

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
			OrgCbsRoleMenuEntity orgCbsRoleMenuEntity = new OrgCbsRoleMenuEntity();
			orgCbsRoleMenuEntity.setMenuId(menuId);
			orgCbsRoleMenuEntity.setRoleId(roleId);

			this.save(orgCbsRoleMenuEntity);
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
