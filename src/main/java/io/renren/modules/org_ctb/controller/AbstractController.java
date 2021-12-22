/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.org_ctb.controller;

import io.renren.common.utils.Constant;
import io.renren.modules.org_ctb.entity.OrgCtbUserEntity;
import io.renren.modules.sys.service.ShiroService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * Controller公共组件
 *
 * @author Mark sunlightcs@gmail.com
 */
public abstract class AbstractController {
	private ShiroService shiroService;

	@Autowired
	public void setShiroService(ShiroService shiroService) {
		this.shiroService = shiroService;
	}

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected OrgCtbUserEntity getUser() {
		return (OrgCtbUserEntity) SecurityUtils.getSubject().getPrincipal();
	}

	protected Set<String> getPermissions() {
		return shiroService.getUserPermissions(Constant.Org.CTB, getUserId(), Constant.SUPER_ADMIN_NAME.equals(getUser().getUsername()));
	}

	protected Long getUserId() {
		return getUser().getUserId();
	}

	protected Long getCtbTenantId() {
		return getUser().getCtbTenantId();
	}
}
