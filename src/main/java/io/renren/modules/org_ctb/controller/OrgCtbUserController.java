/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.org_ctb.controller;

import io.renren.common.annotation.OrgCtbLog;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.Assert;
import io.renren.common.validator.ValidatorUtils;
import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.UpdateGroup;
import io.renren.modules.org_ctb.entity.OrgCtbUserEntity;
import io.renren.modules.org_ctb.service.OrgCtbUserRoleService;
import io.renren.modules.org_ctb.service.OrgCtbUserService;
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
@RequestMapping("org/ctb/user")
public class OrgCtbUserController extends AbstractController {

    private OrgCtbUserService orgCtbUserService;
    private OrgCtbUserRoleService orgCtbUserRoleService;

    @Autowired
    public void setOrgCtbUserRoleService(OrgCtbUserRoleService orgCtbUserRoleService) {
        this.orgCtbUserRoleService = orgCtbUserRoleService;
    }

    @Autowired
    public void setOrgCtbUserService(OrgCtbUserService orgCtbUserService) {
        this.orgCtbUserService = orgCtbUserService;
    }

    /**
     * 所有用户列表
     */
    @PostMapping("/list")
    @RequiresPermissions("org:ctb:user:list")
    public R list(@RequestBody OrgCtbUserEntity entity) {
        entity.setCtbTenantId(getCtbTenantId());
        PageUtils page = orgCtbUserService.queryPage(entity);

        return R.ok().put("page", page);
    }

    /**
     * 选择操作员列表
     */
    @PostMapping("/listForChoice")
    @RequiresPermissions("org:ctb:user:list")
    public R listForChoice(@RequestBody OrgCtbUserEntity entity) {
        entity.setCtbTenantId(getCtbTenantId());
        return R.ok().put("list", orgCtbUserService.listForChoice(entity));
    }

    /**
     * 获取登录的用户信息
     */
    @GetMapping("/info")
    public R info() {
        return R.ok().put("user", getUser());
    }

    /**
     * 修改登录用户密码
     */
    @OrgCtbLog("修改密码")
    @PostMapping("/password")
    public R password(@RequestBody PasswordForm form) {
        Assert.isBlank(form.getNewPassword(), "新密码不为能空");

        //更新密码
        boolean flag = orgCtbUserService.updatePassword(getUserId(),
                new Sha256Hash(form.getPassword(), getUser().getSalt()).toHex(),
                new Sha256Hash(form.getNewPassword(), getUser().getSalt()).toHex());

        return flag ? R.ok() : R.error("原密码不正确");
    }

    /**
     * 用户信息
     */
    @GetMapping("/info/{userId}")
    @RequiresPermissions("org:ctb:user:info")
    public R info(@PathVariable("userId") Long userId) {
        OrgCtbUserEntity user = orgCtbUserService.getById(userId);

        //获取用户所属的角色列表
        List<Long> roleIdList = orgCtbUserRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);

        return R.ok().put("user", user);
    }

    /**
     * 保存用户
     */
    @OrgCtbLog("保存用户")
    @PostMapping("/save")
    @RequiresPermissions("org:ctb:user:save")
    public R save(@RequestBody OrgCtbUserEntity user) {
        ValidatorUtils.validateEntity(user, AddGroup.class);
        user.setCreateUserId(getUserId());
        user.setCtbTenantId(getCtbTenantId());
        orgCtbUserService.saveUser(user);
        return R.ok();
    }

    /**
     * 修改用户
     */
    @OrgCtbLog("修改用户")
    @PostMapping("/update")
    @RequiresPermissions("org:ctb:user:update")
    public R update(@RequestBody OrgCtbUserEntity user) {
        ValidatorUtils.validateEntity(user, UpdateGroup.class);
        user.setCreateUserId(getUserId());
        orgCtbUserService.update(user);
        return R.ok();
    }

    /**
     * 删除用户
     */
    @OrgCtbLog("删除用户")
    @PostMapping("/delete")
    @RequiresPermissions("org:ctb:user:delete")
    public R delete(@RequestBody Long[] userIds) {
        if (ArrayUtils.contains(userIds, 1L)) {
            return R.error("系统管理员不能删除");
        }
        if (ArrayUtils.contains(userIds, getUserId())) {
            return R.error("当前用户不能删除");
        }
        List<OrgCtbUserEntity> list = new ArrayList<>();
        for (Long id : userIds) {
            list.add(OrgCtbUserEntity.builder().userId(id).status(0).build());
        }
        orgCtbUserService.updateBatchById(list);
        return R.ok();
    }
}
