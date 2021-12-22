/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.org_cbs.controller;

import io.renren.common.annotation.OrgCbsLog;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.Assert;
import io.renren.common.validator.ValidatorUtils;
import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.UpdateGroup;
import io.renren.modules.org_cbs.entity.OrgCbsUserEntity;
import io.renren.modules.org_cbs.service.OrgCbsUserRoleService;
import io.renren.modules.org_cbs.service.OrgCbsUserService;
import io.renren.modules.sys.form.PasswordForm;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("org/cbs/user")
public class OrgCbsUserController extends AbstractController {

    private OrgCbsUserService orgCbsUserService;
    private OrgCbsUserRoleService orgCbsUserRoleService;

    @Autowired
    public void setOrgCbsUserRoleService(OrgCbsUserRoleService orgCbsUserRoleService) {
        this.orgCbsUserRoleService = orgCbsUserRoleService;
    }

    @Autowired
    public void setOrgCbsUserService(OrgCbsUserService orgCbsUserService) {
        this.orgCbsUserService = orgCbsUserService;
    }

    /**
     * 所有用户列表
     */
    @PostMapping("/list")
    @RequiresPermissions("org:cbs:user:list")
    public R list(@RequestBody OrgCbsUserEntity entity) {
        entity.setTenantId(getTenantId());
        PageUtils page = orgCbsUserService.queryPage(entity);

        return R.ok().put("page", page);
    }

    /**
     * 获取登录的用户信息
     */
    @GetMapping("/info")
    public R info() {
        OrgCbsUserEntity userEntity = getUser();
        return R.ok().put("user", userEntity);
    }

    /**
     * 修改登录用户密码
     */
    @OrgCbsLog("修改密码")
    @PostMapping("/password")
    public R password(@RequestBody PasswordForm form) {
        Assert.isBlank(form.getNewPassword(), "新密码不为能空");

        //更新密码
        boolean flag = orgCbsUserService.updatePassword(getUserId(),
                new Sha256Hash(form.getPassword(), getUser().getSalt()).toHex(),
                new Sha256Hash(form.getNewPassword(), getUser().getSalt()).toHex());

        return flag ? R.ok() : R.error("原密码不正确");
    }

    /**
     * 用户信息
     */
    @GetMapping("/info/{userId}")
    @RequiresPermissions("org:cbs:user:info")
    public R info(@PathVariable("userId") Long userId) {
        OrgCbsUserEntity user = orgCbsUserService.getById(userId);

        //获取用户所属的角色列表
        List<Long> roleIdList = orgCbsUserRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);

        return R.ok().put("user", user);
    }

    /**
     * 保存用户
     */
    @OrgCbsLog("保存用户")
    @PostMapping("/save")
    @RequiresPermissions("org:cbs:user:save")
    public R save(@RequestBody OrgCbsUserEntity user) {
        ValidatorUtils.validateEntity(user, AddGroup.class);
        user.setCreateUserId(getUserId());
        user.setTenantId(getTenantId());
        orgCbsUserService.saveUser(user);
        return R.ok();
    }

    /**
     * 修改用户
     */
    @OrgCbsLog("修改用户")
    @PostMapping("/update")
    @RequiresPermissions("org:cbs:user:update")
    public R update(@RequestBody OrgCbsUserEntity user) {
        ValidatorUtils.validateEntity(user, UpdateGroup.class);
        user.setCreateUserId(getUserId());
        orgCbsUserService.update(user);
        return R.ok();
    }

    /**
     * 删除用户
     */
    @OrgCbsLog("删除用户")
    @PostMapping("/delete")
    @RequiresPermissions("org:cbs:user:delete")
    public R delete(@RequestBody Long[] userIds) {
        if (ArrayUtils.contains(userIds, 1L)) {
            return R.error("系统管理员不能删除");
        }
        if (ArrayUtils.contains(userIds, getUserId())) {
            return R.error("当前用户不能删除");
        }
        List<OrgCbsUserEntity> list = new ArrayList<>();
        for (Long id : userIds) {
            list.add(OrgCbsUserEntity.builder().userId(id).status(0).build());
        }
        orgCbsUserService.updateBatchById(list);
        return R.ok();
    }
}
