/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.org_cbs.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.Constant;
import io.renren.common.utils.MapUtils;
import io.renren.modules.org_cbs.dao.OrgCbsMenuDao;
import io.renren.modules.org_cbs.entity.OrgCbsMenuEntity;
import io.renren.modules.org_cbs.service.OrgCbsMenuService;
import io.renren.modules.org_cbs.service.OrgCbsRoleMenuService;
import io.renren.modules.org_cbs.service.OrgCbsUserService;
import io.renren.modules.sys.entity.SysCbsMenuEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service("orgCbsMenuService")
public class OrgCbsMenuServiceImpl extends ServiceImpl<OrgCbsMenuDao, OrgCbsMenuEntity> implements OrgCbsMenuService {
    @Autowired
    private OrgCbsUserService orgCbsUserService;
    @Autowired
    private OrgCbsRoleMenuService orgCbsRoleMenuService;

    @Override
    public List<OrgCbsMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList) {
        List<OrgCbsMenuEntity> menuList = queryListParentId(parentId);
        if (menuIdList == null) {
            return menuList;
        }

        List<OrgCbsMenuEntity> userMenuList = new ArrayList<>();
        for (OrgCbsMenuEntity menu : menuList) {
            if (menuIdList.contains(menu.getMenuId())) {
                userMenuList.add(menu);
            }
        }
        return userMenuList;
    }

    @Override
    public List<OrgCbsMenuEntity> queryListParentId(Long parentId) {
        return baseMapper.queryListParentId(parentId);
    }

    @Override
    public List<OrgCbsMenuEntity> queryNotButtonList() {
        return baseMapper.queryNotButtonList();
    }

    @Override
    public List<OrgCbsMenuEntity> getUserMenuList(Long userId, Boolean isAdmin) {
        //系统管理员，拥有最高权限
        if (isAdmin) {
            return getAllMenuList(null);
        }

        //用户菜单列表
        List<Long> menuIdList = orgCbsUserService.queryAllMenuId(userId);
        return getAllMenuList(menuIdList);
    }

    @Override
    public void delete(Long menuId) {
        //删除菜单
        this.removeById(menuId);
        //删除菜单与角色关联
        orgCbsRoleMenuService.removeByMap(new MapUtils().put("menu_id", menuId));
    }

    /**
     * 获取所有菜单列表
     */
    private List<OrgCbsMenuEntity> getAllMenuList(List<Long> menuIdList) {
        //查询根菜单列表
        List<OrgCbsMenuEntity> menuList = queryListParentId(0L, menuIdList);
        //递归获取子菜单
        getMenuTreeList(menuList, menuIdList);

        return menuList;
    }

    /**
     * 递归
     */
    private List<OrgCbsMenuEntity> getMenuTreeList(List<OrgCbsMenuEntity> menuList, List<Long> menuIdList) {
        List<OrgCbsMenuEntity> subMenuList = new ArrayList<OrgCbsMenuEntity>();

        for (OrgCbsMenuEntity entity : menuList) {
            //目录
            if (entity.getType() == Constant.MenuType.CATALOG.getValue()) {
                entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
            }
            subMenuList.add(entity);
        }

        return subMenuList;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Long tenantId, List<Long> menuIdList) {
        //先删除公司与菜单关系
        baseMapper.delete(new QueryWrapper<OrgCbsMenuEntity>().eq("tenant_id", tenantId));
        if (menuIdList.size() == 0) {
            return;
        }
        //保存公司与菜单关系
        List<OrgCbsMenuEntity> orgCbsMenuList = new ArrayList<>();
        for (Long menuId : menuIdList) {
            OrgCbsMenuEntity OrgCbsMenu = new OrgCbsMenuEntity();
            OrgCbsMenu.setTenantId(tenantId);
            OrgCbsMenu.setFkSysCbsMenuId(menuId);
            orgCbsMenuList.add(OrgCbsMenu);
        }
        this.saveBatch(orgCbsMenuList, orgCbsMenuList.size());
    }

    /**
     * @Description: 查询该公司下所有权限菜单
     * @Param:
     * @return:
     * @Author: chenning
     * @Date: 2019/12/22
     */
    @Override
    public List<SysCbsMenuEntity> selectSysCbsMenuList() {
        return baseMapper.selectSysCbsMenuList();
    }


}
