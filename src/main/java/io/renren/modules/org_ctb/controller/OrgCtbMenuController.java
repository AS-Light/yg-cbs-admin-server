/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.org_ctb.controller;

import io.renren.common.annotation.SysLog;
import io.renren.common.exception.RRException;
import io.renren.common.utils.Constant;
import io.renren.common.utils.R;
import io.renren.modules.org_ctb.entity.OrgCtbMenuEntity;
import io.renren.modules.org_ctb.service.OrgCtbMenuService;
import io.renren.modules.sys.entity.SysCtbMenuEntity;
import io.renren.modules.sys.service.ShiroService;
import io.renren.modules.sys.service.SysCtbMenuService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 系统菜单
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("org/ctb/menu")
public class OrgCtbMenuController extends AbstractController {

    private OrgCtbMenuService orgCtbMenuService;
    private ShiroService shiroService;
    private SysCtbMenuService sysCtbMenuService;

    @Autowired
    public void setOrgCtbMenuService(OrgCtbMenuService orgCtbMenuService) {
        this.orgCtbMenuService = orgCtbMenuService;
    }

    @Autowired
    public void setShiroService(ShiroService shiroService) {
        this.shiroService = shiroService;
    }

    @Autowired
    public void setSysCtbMenuService(SysCtbMenuService sysCtbMenuService) {
        this.sysCtbMenuService = sysCtbMenuService;
    }

    /**
     * 导航菜单
     */
    @GetMapping("/nav")
    public R nav() {
        List<OrgCtbMenuEntity> menuList = orgCtbMenuService.getUserMenuList(getUserId(), Constant.SUPER_ADMIN_NAME.equalsIgnoreCase(getUser().getUsername()));
        Set<String> permissions = shiroService.getUserPermissions(Constant.Org.CTB, getUserId(), Constant.SUPER_ADMIN_NAME.equals(getUser().getUsername()));
        return R.ok().put("menuList", menuList).put("permissions", permissions);
    }

    /**
     * 查询该公司下所有权限菜单（公司用）
     */
    @GetMapping("/list")
    @RequiresPermissions("org:ctb:menu:list")
    public List<SysCtbMenuEntity> list() {
        List<SysCtbMenuEntity> menuList = orgCtbMenuService.selectSysCtbMenuList();

        for (SysCtbMenuEntity sysCtbMenuEntity : menuList) {
            SysCtbMenuEntity parentMenuEntity = sysCtbMenuService.getById(sysCtbMenuEntity.getParentId());
            if (parentMenuEntity != null) {
                sysCtbMenuEntity.setParentName(parentMenuEntity.getName());
            }
        }

        return menuList;
    }

    /**
     * 选择菜单(添加、修改菜单) - 沒用上
     */
    @GetMapping("/select")
    @RequiresPermissions("org:ctb:menu:select")
    public R select() {
        //查询列表数据
        List<OrgCtbMenuEntity> menuList = orgCtbMenuService.queryNotButtonList();

        //添加顶级菜单
        OrgCtbMenuEntity root = new OrgCtbMenuEntity();
        root.setMenuId(0L);
        root.setName("一级菜单");
        root.setParentId(-1L);
        root.setOpen(true);
        menuList.add(root);

        return R.ok().put("menuList", menuList);
    }

    /**
     * 菜单信息
     */
    @GetMapping("/info/{menuId}")
    @RequiresPermissions("org:ctb:menu:info")
    public R info(@PathVariable("menuId") Long menuId) {
        OrgCtbMenuEntity menu = orgCtbMenuService.getById(menuId);
        return R.ok().put("menu", menu);
    }

    /**
     * 保存
     */
    @SysLog("保存菜单")
    @PostMapping("/save")
    @RequiresPermissions("org:ctb:menu:save")
    public R save(@RequestBody OrgCtbMenuEntity menu) {
        //数据校验
        verifyForm(menu);

        orgCtbMenuService.save(menu);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改菜单")
    @PostMapping("/update")
    @RequiresPermissions("org:ctb:menu:update")
    public R update(@RequestBody OrgCtbMenuEntity menu) {
        //数据校验
        verifyForm(menu);

        orgCtbMenuService.updateById(menu);

        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除菜单")
    @PostMapping("/delete/{menuId}")
    @RequiresPermissions("org:ctb:menu:delete")
    public R delete(@PathVariable("menuId") long menuId) {
        if (menuId <= 31) {
            return R.error("系统菜单，不能删除");
        }

        //判断是否有子菜单或按钮
        List<OrgCtbMenuEntity> menuList = orgCtbMenuService.queryListParentId(menuId);
        if (menuList.size() > 0) {
            return R.error("请先删除子菜单或按钮");
        }

        orgCtbMenuService.delete(menuId);

        return R.ok();
    }

    /**
     * 验证参数是否正确
     */
    private void verifyForm(OrgCtbMenuEntity menu) {
        if (StringUtils.isBlank(menu.getName())) {
            throw new RRException("菜单名称不能为空");
        }

        if (menu.getParentId() == null) {
            throw new RRException("上级菜单不能为空");
        }

        //菜单
        if (menu.getType() == Constant.MenuType.MENU.getValue()) {
            if (StringUtils.isBlank(menu.getUrl())) {
                throw new RRException("菜单URL不能为空");
            }
        }

        //上级菜单类型
        int parentType = Constant.MenuType.CATALOG.getValue();
        if (menu.getParentId() != 0) {
            OrgCtbMenuEntity parentMenu = orgCtbMenuService.getById(menu.getParentId());
            parentType = parentMenu.getType();
        }

        //目录、菜单
        if (menu.getType() == Constant.MenuType.CATALOG.getValue() ||
                menu.getType() == Constant.MenuType.MENU.getValue()) {
            if (parentType != Constant.MenuType.CATALOG.getValue()) {
                throw new RRException("上级菜单只能为目录类型");
            }
            return;
        }

        //按钮
        if (menu.getType() == Constant.MenuType.BUTTON.getValue()) {
            if (parentType != Constant.MenuType.MENU.getValue()) {
                throw new RRException("上级菜单只能为菜单类型");
            }
            return;
        }
    }
}
