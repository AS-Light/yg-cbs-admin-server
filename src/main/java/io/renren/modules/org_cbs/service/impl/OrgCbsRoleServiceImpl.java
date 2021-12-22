/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.org_cbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.org_cbs.dao.OrgCbsRoleDao;
import io.renren.modules.org_cbs.entity.OrgCbsRoleEntity;
import io.renren.modules.org_cbs.service.OrgCbsRoleMenuService;
import io.renren.modules.org_cbs.service.OrgCbsRoleService;
import io.renren.modules.org_cbs.service.OrgCbsUserRoleService;
import io.renren.modules.org_cbs.service.OrgCbsUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 角色
 *
 * @author Mark sunlightcs@gmail.com
 */
@Service("orgCbsRoleService")
public class OrgCbsRoleServiceImpl extends ServiceImpl<OrgCbsRoleDao, OrgCbsRoleEntity> implements OrgCbsRoleService {
    @Autowired
    private OrgCbsRoleMenuService orgCbsRoleMenuService;
    @Autowired
    private OrgCbsUserService orgCbsUserService;
    @Autowired
    private OrgCbsUserRoleService orgCbsUserRoleService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String roleName = (String) params.get("roleName");
        Long createUserId = (Long) params.get("createUserId");

        IPage<OrgCbsRoleEntity> page = this.page(
                new Query<OrgCbsRoleEntity>().getPage(params),
                new QueryWrapper<OrgCbsRoleEntity>()
                        .like(StringUtils.isNotBlank(roleName), "role_name", roleName)
                        .eq(createUserId != null, "create_user_id", createUserId)
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRole(OrgCbsRoleEntity role, Boolean isAdmin) {
        role.setCreateTime(new Date());
        this.save(role);

        //检查权限是否越权
        checkPrems(role, isAdmin);

        //保存角色与菜单关系
        orgCbsRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(OrgCbsRoleEntity role, Boolean isAdmin) {
        this.updateById(role);

        //检查权限是否越权
        checkPrems(role, isAdmin);

        //更新角色与菜单关系
        orgCbsRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] roleIds) {
        //删除角色
        this.removeByIds(Arrays.asList(roleIds));

        //删除角色与菜单关联
        orgCbsRoleMenuService.deleteBatch(roleIds);

        //删除角色与用户关联
        orgCbsUserRoleService.deleteBatch(roleIds);
    }


    @Override
    public List<Long> queryRoleIdList(Long createUserId) {
        return baseMapper.queryRoleIdList(createUserId);
    }

    /**
     * 检查权限是否越权
     */
    private void checkPrems(OrgCbsRoleEntity role, Boolean isAdmin) {
        //如果不是超级管理员，则需要判断角色的权限是否超过自己的权限
        if (isAdmin) {
            return;
        }

        //查询用户所拥有的菜单列表
        List<Long> menuIdList = orgCbsUserService.queryAllMenuId(role.getCreateUserId());

        //判断是否越权
        if (!menuIdList.containsAll(role.getMenuIdList())) {
            throw new RRException("新增角色的权限，已超出你的权限范围");
        }
    }
}
