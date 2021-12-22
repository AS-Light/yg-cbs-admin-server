/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.org_ctb.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.Constant;
import io.renren.common.utils.MapUtils;
import io.renren.modules.org_ctb.dao.OrgCtbMenuDao;
import io.renren.modules.org_ctb.entity.OrgCtbMenuEntity;
import io.renren.modules.org_ctb.service.OrgCtbMenuService;
import io.renren.modules.org_ctb.service.OrgCtbRoleMenuService;
import io.renren.modules.org_ctb.service.OrgCtbUserService;
import io.renren.modules.sys.entity.SysCtbMenuEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service("orgCtbMenuService")
public class OrgCtbMenuServiceImpl extends ServiceImpl<OrgCtbMenuDao, OrgCtbMenuEntity> implements OrgCtbMenuService {
    @Autowired
    private OrgCtbUserService orgCtbUserService;
    @Autowired
    private OrgCtbRoleMenuService orgCtbRoleMenuService;

    @Override
    public List<OrgCtbMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList) {
        List<OrgCtbMenuEntity> menuList = queryListParentId(parentId);
        if (menuIdList == null) {
            return menuList;
        }

        List<OrgCtbMenuEntity> userMenuList = new ArrayList<>();
        for (OrgCtbMenuEntity menu : menuList) {
            if (menuIdList.contains(menu.getMenuId())) {
                userMenuList.add(menu);
            }
        }
        return userMenuList;
    }

    @Override
    public List<OrgCtbMenuEntity> queryListParentId(Long parentId) {
        return baseMapper.queryListParentId(parentId);
    }

    @Override
    public List<OrgCtbMenuEntity> queryNotButtonList() {
        return baseMapper.queryNotButtonList();
    }

    @Override
    public List<OrgCtbMenuEntity> getUserMenuList(Long userId, Boolean isAdmin) {
        //系统管理员，拥有最高权限
        if (isAdmin) {
            return getAllMenuList(null);
        }

        //用户菜单列表
        List<Long> menuIdList = orgCtbUserService.queryAllMenuId(userId);
        return getAllMenuList(menuIdList);
    }

    @Override
    public void delete(Long menuId) {
        //删除菜单
        this.removeById(menuId);
        //删除菜单与角色关联
        orgCtbRoleMenuService.removeByMap(new MapUtils().put("menu_id", menuId));
    }

    /**
     * 获取所有菜单列表
     */
    private List<OrgCtbMenuEntity> getAllMenuList(List<Long> menuIdList) {
        //查询根菜单列表
        List<OrgCtbMenuEntity> menuList = queryListParentId(0L, menuIdList);
        //递归获取子菜单
        getMenuTreeList(menuList, menuIdList);

        return menuList;
    }

    /**
     * 递归
     */
    private List<OrgCtbMenuEntity> getMenuTreeList(List<OrgCtbMenuEntity> menuList, List<Long> menuIdList) {
        List<OrgCtbMenuEntity> subMenuList = new ArrayList<OrgCtbMenuEntity>();

        for (OrgCtbMenuEntity entity : menuList) {
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
        baseMapper.delete(new QueryWrapper<OrgCtbMenuEntity>().eq("ctb_tenant_id", tenantId));
        if (menuIdList.size() == 0) {
            return;
        }
        //保存公司与菜单关系
        List<OrgCtbMenuEntity> orgCtbMenuList = new ArrayList<>();
        for (Long menuId : menuIdList) {
            OrgCtbMenuEntity OrgCtbMenu = new OrgCtbMenuEntity();
            OrgCtbMenu.setCtbTenantId(tenantId);
            OrgCtbMenu.setFkSysCtbMenuId(menuId);
            orgCtbMenuList.add(OrgCtbMenu);
        }
        this.saveBatch(orgCtbMenuList, orgCtbMenuList.size());
    }

    /**
     * @Description: 查询该公司下所有权限菜单
     * @Param:
     * @return:
     * @Author: chenning
     * @Date: 2019/12/22
     */
    @Override
    public List<SysCtbMenuEntity> selectSysCtbMenuList() {
        return baseMapper.selectSysCtbMenuList();
    }


}
