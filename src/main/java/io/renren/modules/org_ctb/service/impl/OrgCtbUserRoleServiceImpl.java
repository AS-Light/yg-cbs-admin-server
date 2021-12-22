/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.org_ctb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.MapUtils;
import io.renren.modules.org_ctb.dao.OrgCtbUserRoleDao;
import io.renren.modules.org_ctb.entity.OrgCtbUserRoleEntity;
import io.renren.modules.org_ctb.service.OrgCtbUserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 用户与角色对应关系
 *
 * @author Mark sunlightcs@gmail.com
 */
@Service("orgCtbUserRoleService")
public class OrgCtbUserRoleServiceImpl extends ServiceImpl<OrgCtbUserRoleDao, OrgCtbUserRoleEntity> implements OrgCtbUserRoleService {

	@Override
	public void saveOrUpdate(Long userId, List<Long> roleIdList) {
		//先删除用户与角色关系
		this.removeByMap(new MapUtils().put("user_id", userId));

		if(roleIdList == null || roleIdList.size() == 0){
			return ;
		}

		//保存用户与角色关系
		for(Long roleId : roleIdList){
			OrgCtbUserRoleEntity orgCtbUserRoleEntity = new OrgCtbUserRoleEntity();
			orgCtbUserRoleEntity.setUserId(userId);
			orgCtbUserRoleEntity.setRoleId(roleId);
			this.save(orgCtbUserRoleEntity);
		}
	}

	@Override
	public List<Long> queryRoleIdList(Long userId) {
		return baseMapper.queryRoleIdList(userId);
	}

	@Override
	public int deleteBatch(Long[] roleIds){
		return baseMapper.deleteBatch(roleIds);
	}
}
