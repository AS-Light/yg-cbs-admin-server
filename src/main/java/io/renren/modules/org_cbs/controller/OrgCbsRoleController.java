/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.org_cbs.controller;

import io.renren.common.annotation.OrgCbsLog;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.org_cbs.entity.OrgCbsRoleEntity;
import io.renren.modules.org_cbs.service.OrgCbsRoleMenuService;
import io.renren.modules.org_cbs.service.OrgCbsRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色管理
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("org/cbs/role")
public class OrgCbsRoleController extends AbstractController {

    private OrgCbsRoleService orgCbsRoleService;
    private OrgCbsRoleMenuService orgCbsRoleMenuService;

    @Autowired
    public void setOrgCbsRoleMenuService(OrgCbsRoleMenuService orgCbsRoleMenuService) {
        this.orgCbsRoleMenuService = orgCbsRoleMenuService;
    }

    @Autowired
    public void setOrgCbsRoleService(OrgCbsRoleService orgCbsRoleService) {
        this.orgCbsRoleService = orgCbsRoleService;
    }

    /**
     * 角色列表
     */
    @GetMapping("/list")
    @RequiresPermissions("org:cbs:role:list")
    public R list(@RequestParam Map<String, Object> params) {
        //如果不是超级管理员，则只查询自己创建的角色列表
//        if (!Constant.SUPER_ADMIN_NAME.equals(getUser().getUsername())) {
//            params.put("createUserId", getUserId());
//        }

        PageUtils page = orgCbsRoleService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 角色列表
     */
    @GetMapping("/select")
    @RequiresPermissions("org:cbs:role:select")
    public R select() {
        Map<String, Object> map = new HashMap<>();

        //如果不是超级管理员，则只查询自己所拥有的角色列表
//        if (!Constant.SUPER_ADMIN_NAME.equals(getUser().getUsername())) {
//            map.put("create_user_id", getUserId());
//        }
        List<OrgCbsRoleEntity> list = (List<OrgCbsRoleEntity>) orgCbsRoleService.listByMap(map);

        return R.ok().put("list", list);
    }

    /**
     * 角色信息
     */
    @GetMapping("/info/{roleId}")
    @RequiresPermissions("org:cbs:role:info")
    public R info(@PathVariable("roleId") Long roleId) {
        OrgCbsRoleEntity role = orgCbsRoleService.getById(roleId);

        //查询角色对应的菜单
        List<Long> menuIdList = orgCbsRoleMenuService.queryMenuIdList(roleId);
        role.setMenuIdList(menuIdList);

        return R.ok().put("role", role);
    }

    /**
     * 保存角色
     */
    @OrgCbsLog("保存角色")
    @PostMapping("/save")
    @RequiresPermissions("org:cbs:role:save")
    public R save(@RequestBody OrgCbsRoleEntity role) {
        ValidatorUtils.validateEntity(role);

        role.setCreateUserId(getUserId());
        orgCbsRoleService.saveRole(role, Constant.SUPER_ADMIN_NAME.equals(getUser().getUsername()));

        return R.ok();
    }

    /**
     * 修改角色
     */
    @OrgCbsLog("修改角色")
    @PostMapping("/update")
    @RequiresPermissions("org:cbs:role:update")
    public R update(@RequestBody OrgCbsRoleEntity role) {
        ValidatorUtils.validateEntity(role);

        role.setCreateUserId(getUserId());
        orgCbsRoleService.update(role, Constant.SUPER_ADMIN_NAME.equals(getUser().getUsername()));

        return R.ok();
    }

    /**
     * 删除角色
     */
    @OrgCbsLog("删除角色")
    @PostMapping("/delete")
    @RequiresPermissions("org:cbs:role:delete")
    public R delete(@RequestBody Long[] roleIds) {
        orgCbsRoleService.deleteBatch(roleIds);

        return R.ok();
    }
}
