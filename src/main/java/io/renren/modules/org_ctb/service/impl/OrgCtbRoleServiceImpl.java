/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.org_ctb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.org_ctb.dao.OrgCtbRoleDao;
import io.renren.modules.org_ctb.entity.OrgCtbRoleEntity;
import io.renren.modules.org_ctb.service.OrgCtbRoleMenuService;
import io.renren.modules.org_ctb.service.OrgCtbRoleService;
import io.renren.modules.org_ctb.service.OrgCtbUserRoleService;
import io.renren.modules.org_ctb.service.OrgCtbUserService;
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
@Service("orgCtbRoleService")
public class OrgCtbRoleServiceImpl extends ServiceImpl<OrgCtbRoleDao, OrgCtbRoleEntity> implements OrgCtbRoleService {
    @Autowired
    private OrgCtbRoleMenuService orgCtbRoleMenuService;
    @Autowired
    private OrgCtbUserService orgCtbUserService;
    @Autowired
    private OrgCtbUserRoleService orgCtbUserRoleService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String roleName = (String) params.get("roleName");
        Long createUserId = (Long) params.get("createUserId");

        IPage<OrgCtbRoleEntity> page = this.page(
                new Query<OrgCtbRoleEntity>().getPage(params),
                new QueryWrapper<OrgCtbRoleEntity>()
                        .like(StringUtils.isNotBlank(roleName), "role_name", roleName)
                        .eq(createUserId != null, "create_user_id", createUserId)
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRole(OrgCtbRoleEntity role, Boolean isAdmin) {
        role.setCreateTime(new Date());
        this.save(role);

        //检查权限是否越权
        checkPrems(role, isAdmin);

        //保存角色与菜单关系
        orgCtbRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(OrgCtbRoleEntity role, Boolean isAdmin) {
        this.updateById(role);

        //检查权限是否越权
        checkPrems(role, isAdmin);

        //更新角色与菜单关系
        orgCtbRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] roleIds) {
        //删除角色
        this.removeByIds(Arrays.asList(roleIds));

        //删除角色与菜单关联
        orgCtbRoleMenuService.deleteBatch(roleIds);

        //删除角色与用户关联
        orgCtbUserRoleService.deleteBatch(roleIds);
    }


    @Override
    public List<Long> queryRoleIdList(Long createUserId) {
        return baseMapper.queryRoleIdList(createUserId);
    }

    /**
     * 检查权限是否越权
     */
    private void checkPrems(OrgCtbRoleEntity role, Boolean isAdmin) {
        //如果不是超级管理员，则需要判断角色的权限是否超过自己的权限
        if (isAdmin) {
            return;
        }

        //查询用户所拥有的菜单列表
        List<Long> menuIdList = orgCtbUserService.queryAllMenuId(role.getCreateUserId());

        //判断是否越权
        if (!menuIdList.containsAll(role.getMenuIdList())) {
            throw new RRException("新增角色的权限，已超出你的权限范围");
        }
    }
}
