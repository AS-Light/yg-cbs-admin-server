/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.service.impl;

import io.renren.common.utils.Constant;
import io.renren.modules.org_cbs.dao.OrgCbsMenuDao;
import io.renren.modules.org_cbs.dao.OrgCbsUserDao;
import io.renren.modules.org_cbs.entity.OrgCbsMenuEntity;
import io.renren.modules.org_ctb.dao.OrgCtbMenuDao;
import io.renren.modules.org_ctb.dao.OrgCtbUserDao;
import io.renren.modules.org_ctb.entity.OrgCtbMenuEntity;
import io.renren.modules.sys.dao.SysMenuDao;
import io.renren.modules.sys.dao.SysUserDao;
import io.renren.modules.sys.dao.SysUserTokenDao;
import io.renren.modules.sys.entity.SysMenuEntity;
import io.renren.modules.sys.entity.SysUserTokenEntity;
import io.renren.modules.sys.service.ShiroService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShiroServiceImpl implements ShiroService {

    private SysMenuDao sysMenuDao;
    private SysUserDao sysUserDao;
    private OrgCbsMenuDao orgCbsMenuDao;
    private OrgCbsUserDao orgCbsUserDao;
    private OrgCtbMenuDao orgCtbMenuDao;
    private OrgCtbUserDao orgCtbUserDao;
    private SysUserTokenDao sysUserTokenDao;

    public ShiroServiceImpl(SysMenuDao sysMenuDao, SysUserDao sysUserDao, OrgCbsMenuDao orgCbsMenuDao, OrgCbsUserDao orgCbsUserDao, OrgCtbMenuDao orgCtbMenuDao, OrgCtbUserDao orgCtbUserDao, SysUserTokenDao sysUserTokenDao) {
        this.sysMenuDao = sysMenuDao;
        this.sysUserDao = sysUserDao;
        this.orgCbsMenuDao = orgCbsMenuDao;
        this.orgCbsUserDao = orgCbsUserDao;
        this.orgCtbMenuDao = orgCtbMenuDao;
        this.orgCtbUserDao = orgCtbUserDao;
        this.sysUserTokenDao = sysUserTokenDao;
    }

    @Override
    public Set<String> getUserPermissions(Constant.Org org, Long userId, Boolean isAdmin) {
        List<String> permsList = new ArrayList<>(0);

        //系统管理员，拥有最高权限
        switch (org) {
            case SYS: {
                if (isAdmin) {
                    List<SysMenuEntity> menuList = sysMenuDao.selectList(null);
                    permsList = new ArrayList<>(menuList.size());
                    for (SysMenuEntity menu : menuList) {
                        permsList.add(menu.getPerms());
                    }
                } else {
                    permsList = sysUserDao.queryAllPerms(userId);
                }
                break;
            }
            case CBS: {
                if (isAdmin) {
                    List<OrgCbsMenuEntity> menuList = orgCbsMenuDao.selectSimpleDetailList();
                    permsList = new ArrayList<>(menuList.size());
                    for (OrgCbsMenuEntity menu : menuList) {
                        if (menu != null) {
                            permsList.add(menu.getPerms());
                        }
                    }
                } else {
                    permsList = orgCbsUserDao.queryAllPerms(userId);
                }
                break;
            }
            case CTB: {
                if (isAdmin) {
                    List<OrgCtbMenuEntity> menuList = orgCtbMenuDao.selectSimpleDetailList();
                    permsList = new ArrayList<>(menuList.size());
                    for (OrgCtbMenuEntity menu : menuList) {
                        if (menu != null) {
                            permsList.add(menu.getPerms());
                        }
                    }
                } else {
                    permsList = orgCtbUserDao.queryAllPerms(userId);
                }
                break;
            }
        }

        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for (String perms : permsList) {
            if (StringUtils.isBlank(perms))
                continue;
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

    @Override
    public SysUserTokenEntity queryByToken(String token) {
        return sysUserTokenDao.queryByToken(token);
    }

    @Override
    public Object queryUser(Constant.Org org, Long userId) {
        switch (org) {
            case SYS:
                return sysUserDao.selectById(userId);
            case CBS:
                return orgCbsUserDao.selectById(userId);
            case CTB:
                return orgCtbUserDao.selectById(userId);
            default:
                return null;
        }
    }
}
