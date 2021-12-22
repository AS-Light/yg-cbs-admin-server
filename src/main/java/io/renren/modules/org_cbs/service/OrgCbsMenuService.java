/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.org_cbs.service;


import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.org_cbs.entity.OrgCbsMenuEntity;
import io.renren.modules.sys.entity.SysCbsMenuEntity;

import java.util.List;


/**
 * 菜单管理
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface OrgCbsMenuService extends IService<OrgCbsMenuEntity> {

    /**
     * 根据父菜单，查询子菜单
     *
     * @param parentId   父菜单ID
     * @param menuIdList 用户菜单ID
     */
    List<OrgCbsMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList);

    /**
     * 根据父菜单，查询子菜单
     *
     * @param parentId 父菜单ID
     */
    List<OrgCbsMenuEntity> queryListParentId(Long parentId);

    /**
     * 获取不包含按钮的菜单列表
     */
    List<OrgCbsMenuEntity> queryNotButtonList();

    /**
     * 获取用户菜单列表
     */
    List<OrgCbsMenuEntity> getUserMenuList(Long userId, Boolean isAdmin);

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
    List<SysCbsMenuEntity> selectSysCbsMenuList();
}
