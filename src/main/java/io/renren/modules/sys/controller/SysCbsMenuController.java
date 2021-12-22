/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.controller;

import io.renren.common.annotation.SysLog;
import io.renren.common.exception.RRException;
import io.renren.common.utils.Constant;
import io.renren.common.utils.R;
import io.renren.modules.sys.entity.SysCbsMenuEntity;
import io.renren.modules.sys.service.SysCbsMenuService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统菜单
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/sys/cbsmenu")
public class SysCbsMenuController extends AbstractController {
    @Autowired
    private SysCbsMenuService sysCbsMenuService;

    /**
     * 所有菜单列表（一角用）
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:cbsmenu:list")
    public List<SysCbsMenuEntity> list() {
        List<SysCbsMenuEntity> menuList = sysCbsMenuService.list();
        for (SysCbsMenuEntity sysCbsMenuEntity : menuList) {
            SysCbsMenuEntity parentMenuEntity = sysCbsMenuService.getById(sysCbsMenuEntity.getParentId());
            if (parentMenuEntity != null) {
                sysCbsMenuEntity.setParentName(parentMenuEntity.getName());
            }
        }

        return menuList;
    }

    /**
     * 选择菜单(添加、修改菜单)
     */
    @GetMapping("/select")
    @RequiresPermissions("sys:cbsmenu:select")
    public R select() {
        //查询列表数据
        List<SysCbsMenuEntity> menuList = sysCbsMenuService.queryNotButtonList();

        //添加顶级菜单
        SysCbsMenuEntity root = new SysCbsMenuEntity();
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
    @RequiresPermissions("sys:cbsmenu:info")
    public R info(@PathVariable("menuId") Long menuId) {
        SysCbsMenuEntity menu = sysCbsMenuService.getById(menuId);
        return R.ok().put("menu", menu);
    }

    /**
     * 保存
     */
    @SysLog("保存菜单")
    @PostMapping("/save")
    @RequiresPermissions("sys:cbsmenu:save")
    public R save(@RequestBody SysCbsMenuEntity menu) {
        //数据校验
        verifyForm(menu);

        sysCbsMenuService.save(menu);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改菜单")
    @PostMapping("/update")
    @RequiresPermissions("sys:cbsmenu:update")
    public R update(@RequestBody SysCbsMenuEntity menu) {
        //数据校验
        verifyForm(menu);

        sysCbsMenuService.updateById(menu);

        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除菜单")
    @PostMapping("/delete/{menuId}")
    @RequiresPermissions("sys:cbsmenu:delete")
    public R delete(@PathVariable("menuId") long menuId) {

        //判断是否有子菜单或按钮
        List<SysCbsMenuEntity> menuList = sysCbsMenuService.queryListParentId(menuId);
        if (menuList.size() > 0) {
            return R.error("请先删除子菜单或按钮");
        }

        sysCbsMenuService.delete(menuId);

        return R.ok();
    }

    /**
     * 验证参数是否正确
     */
    private void verifyForm(SysCbsMenuEntity menu) {
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
            SysCbsMenuEntity parentMenu = sysCbsMenuService.getById(menu.getParentId());
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
