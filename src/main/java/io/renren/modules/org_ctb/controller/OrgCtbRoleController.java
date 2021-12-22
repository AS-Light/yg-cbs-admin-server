/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.org_ctb.controller;

import io.renren.common.annotation.OrgCtbLog;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.org_ctb.entity.OrgCtbRoleEntity;
import io.renren.modules.org_ctb.service.OrgCtbRoleMenuService;
import io.renren.modules.org_ctb.service.OrgCtbRoleService;
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
@RequestMapping("org/ctb/role")
public class OrgCtbRoleController extends AbstractController {

    private OrgCtbRoleService orgCtbRoleService;
    private OrgCtbRoleMenuService orgCtbRoleMenuService;

    @Autowired
    public void setOrgCtbRoleMenuService(OrgCtbRoleMenuService orgCtbRoleMenuService) {
        this.orgCtbRoleMenuService = orgCtbRoleMenuService;
    }

    @Autowired
    public void setOrgCtbRoleService(OrgCtbRoleService orgCtbRoleService) {
        this.orgCtbRoleService = orgCtbRoleService;
    }

    /**
     * 角色列表
     */
    @GetMapping("/list")
    @RequiresPermissions("org:ctb:role:list")
    public R list(@RequestParam Map<String, Object> params) {
        //如果不是超级管理员，则只查询自己创建的角色列表
//        if (!Constant.SUPER_ADMIN_NAME.equals(getUser().getUsername())) {
//            params.put("createUserId", getUserId());
//        }

        PageUtils page = orgCtbRoleService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 角色列表
     */
    @GetMapping("/select")
    @RequiresPermissions("org:ctb:role:select")
    public R select() {
        Map<String, Object> map = new HashMap<>();

        //如果不是超级管理员，则只查询自己所拥有的角色列表
//        if (!Constant.SUPER_ADMIN_NAME.equals(getUser().getUsername())) {
//            map.put("create_user_id", getUserId());
//        }
        List<OrgCtbRoleEntity> list = (List<OrgCtbRoleEntity>) orgCtbRoleService.listByMap(map);

        return R.ok().put("list", list);
    }

    /**
     * 角色信息
     */
    @GetMapping("/info/{roleId}")
    @RequiresPermissions("org:ctb:role:info")
    public R info(@PathVariable("roleId") Long roleId) {
        OrgCtbRoleEntity role = orgCtbRoleService.getById(roleId);

        //查询角色对应的菜单
        List<Long> menuIdList = orgCtbRoleMenuService.queryMenuIdList(roleId);
        role.setMenuIdList(menuIdList);

        return R.ok().put("role", role);
    }

    /**
     * 保存角色
     */
    @OrgCtbLog("保存角色")
    @PostMapping("/save")
    @RequiresPermissions("org:ctb:role:save")
    public R save(@RequestBody OrgCtbRoleEntity role) {
        ValidatorUtils.validateEntity(role);

        role.setCreateUserId(getUserId());
        orgCtbRoleService.saveRole(role, Constant.SUPER_ADMIN_NAME.equals(getUser().getUsername()));

        return R.ok();
    }

    /**
     * 修改角色
     */
    @OrgCtbLog("修改角色")
    @PostMapping("/update")
    @RequiresPermissions("org:ctb:role:update")
    public R update(@RequestBody OrgCtbRoleEntity role) {
        ValidatorUtils.validateEntity(role);

        role.setCreateUserId(getUserId());
        orgCtbRoleService.update(role, Constant.SUPER_ADMIN_NAME.equals(getUser().getUsername()));

        return R.ok();
    }

    /**
     * 删除角色
     */
    @OrgCtbLog("删除角色")
    @PostMapping("/delete")
    @RequiresPermissions("org:ctb:role:delete")
    public R delete(@RequestBody Long[] roleIds) {
        orgCtbRoleService.deleteBatch(roleIds);

        return R.ok();
    }
}
