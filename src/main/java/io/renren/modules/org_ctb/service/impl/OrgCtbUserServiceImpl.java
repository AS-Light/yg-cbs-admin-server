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
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.org_ctb.dao.OrgCtbUserDao;
import io.renren.modules.org_ctb.entity.OrgCtbUserEntity;
import io.renren.modules.org_ctb.service.OrgCtbRoleService;
import io.renren.modules.org_ctb.service.OrgCtbUserRoleService;
import io.renren.modules.org_ctb.service.OrgCtbUserService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@Service("orgCtbUserService")
public class OrgCtbUserServiceImpl extends ServiceImpl<OrgCtbUserDao, OrgCtbUserEntity> implements OrgCtbUserService {
    @Autowired
    private OrgCtbUserRoleService orgCtbUserRoleService;
    @Autowired
    private OrgCtbRoleService orgCtbRoleService;

    @Override
    public PageUtils queryPage(OrgCtbUserEntity entity) {
        QueryWrapper<OrgCtbUserEntity> qw = new QueryWrapper<>();
        qw.eq("ctb_tenant_id", entity.getCtbTenantId());
        qw.ne("username", "admin");
        if (StringUtils.isNotBlank(entity.getUsername())) {
            qw.and(wrapper -> wrapper.like(StringUtils.isNotBlank(entity.getUsername()), "username", entity.getUsername())
                    .or().like(StringUtils.isNotBlank(entity.getNickname()), "nickname", entity.getUsername()));
        }
        IPage<OrgCtbUserEntity> page = this.page(new QueryPage<OrgCtbUserEntity>().getPage(entity), qw);
        return new PageUtils(page);
    }

    @Override
    public List<OrgCtbUserEntity> listForChoice(OrgCtbUserEntity entity) {
        QueryWrapper<OrgCtbUserEntity> qw = new QueryWrapper<>();
        qw.eq("ctb_tenant_id", entity.getCtbTenantId());
        qw.ne("username", "admin");
        if (StringUtils.isNotBlank(entity.getUsername())) {
            qw.and(wrapper -> wrapper.like(StringUtils.isNotBlank(entity.getUsername()), "username", entity.getUsername())
                    .or().like(StringUtils.isNotBlank(entity.getNickname()), "nickname", entity.getUsername()));
        }
        return baseMapper.selectList(qw);
    }

    @Override
    public List<String> queryAllPerms(Long userId) {
        return baseMapper.queryAllPerms(userId);
    }

    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return baseMapper.queryAllMenuId(userId);
    }

    @Override
    public OrgCtbUserEntity queryByUserName(Long tenantId, String username) {
        return baseMapper.selectOne(new QueryWrapper<OrgCtbUserEntity>().eq("username", username).eq("ctb_tenant_id", tenantId));
    }

    @Override
    @Transactional
    public void saveUser(OrgCtbUserEntity user) {
        //检查角色是否越权
        checkRole(user);

        user.setCreateTime(new Date());
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
        user.setSalt(salt);
        this.save(user);

        //保存用户与角色关系
        orgCtbUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    @Override
    @Transactional
    public void update(OrgCtbUserEntity user) {
        //检查角色是否越权
        checkRole(user);

        if (StringUtils.isBlank(user.getPassword())) {
            user.setPassword(user.getPassword());
        } else {
            user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
        }
        this.updateById(user);

        //保存用户与角色关系
        orgCtbUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    @Override
    public void deleteBatch(Long[] userId) {
        this.removeByIds(Arrays.asList(userId));
    }

    @Override
    public boolean updatePassword(Long userId, String password, String newPassword) {
        return this.update(OrgCtbUserEntity.builder().password(newPassword).build(),
                new QueryWrapper<OrgCtbUserEntity>().eq("user_id", userId).eq("password", password));
    }

    /**
     * 检查角色是否越权
     */
    private void checkRole(OrgCtbUserEntity user) {
        if (user.getRoleIdList() == null || user.getRoleIdList().size() == 0) {
            return;
        }
        //如果不是超级管理员，则需要判断用户的角色是否自己创建
        if (Constant.SUPER_ADMIN_NAME.equals(user.getUsername())) {
            return;
        }

        //查询用户创建的角色列表
        List<Long> roleIdList = orgCtbRoleService.queryRoleIdList(user.getCreateUserId());

        //判断是否越权
//        if (!roleIdList.containsAll(user.getRoleIdList())) {
//            throw new RRException("新增用户所选角色，不是本人创建");
//        }
    }
}