/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.org_ctb.service;


import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.org_ctb.entity.OrgCtbMenuEntity;
import io.renren.modules.sys.entity.SysCtbMenuEntity;

import java.util.List;


/**
 * 菜单管理
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface OrgCtbMenuService extends IService<OrgCtbMenuEntity> {

    /**
     * 根据父菜单，查询子菜单
     *
     * @param parentId   父菜单ID
     * @param menuIdList 用户菜单ID
     */
    List<OrgCtbMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList);

    /**
     * 根据父菜单，查询子菜单
     *
     * @param parentId 父菜单ID
     */
    List<OrgCtbMenuEntity> queryListParentId(Long parentId);

    /**
     * 获取不包含按钮的菜单列表
     */
    List<OrgCtbMenuEntity> queryNotButtonList();

    /**
     * 获取用户菜单列表
     */
    List<OrgCtbMenuEntity> getUserMenuList(Long userId, Boolean isAdmin);

    /**
     * 删除
     */
    void delete(Long menuId);


    /**
     * 根据角色ID数组，批量删除
     */
    void saveOrUpdate(Long tenantId, List<Long> menuIdList);

    /**
     * @Description: 查询该公司下所有权限菜单
     * @Param:
     * @return:
     * @Author: chenning
     * @Date: 2019/12/22
     */
    List<SysCtbMenuEntity> selectSysCtbMenuList();
}
