/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.oauth2;

import io.renren.common.utils.Constant;
import io.renren.modules.org_cbs.entity.OrgCbsUserEntity;
import io.renren.modules.org_ctb.entity.OrgCtbUserEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.entity.SysUserTokenEntity;
import io.renren.modules.sys.service.ShiroService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 认证
 *
 * @author Mark sunlightcs@gmail.com
 */
@Component
public class OAuth2Realm extends AuthorizingRealm {
    @Autowired
    private ShiroService shiroService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Object oUser = principals.getPrimaryPrincipal();
        Set<String> permsSet;
        if (oUser instanceof OrgCbsUserEntity) {
            OrgCbsUserEntity cbsUserEntity = (OrgCbsUserEntity) oUser;
            permsSet = shiroService.getUserPermissions(Constant.Org.CBS, cbsUserEntity.getUserId(), Constant.SUPER_ADMIN_NAME.equals(cbsUserEntity.getUsername()));
        } else if (oUser instanceof OrgCtbUserEntity) {
            OrgCtbUserEntity ctbUserEntity = (OrgCtbUserEntity) oUser;
            permsSet = shiroService.getUserPermissions(Constant.Org.CTB, ctbUserEntity.getUserId(), Constant.SUPER_ADMIN_NAME.equals(ctbUserEntity.getUsername()));
        } else {
            SysUserEntity sysUserEntity = (SysUserEntity) oUser;
            permsSet = shiroService.getUserPermissions(Constant.Org.SYS, sysUserEntity.getUserId(), Constant.SUPER_ADMIN_NAME.equals(sysUserEntity.getUsername()));
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String accessToken = (String) token.getPrincipal();

        //根据accessToken，查询用户信息
        SysUserTokenEntity tokenEntity = shiroService.queryByToken(accessToken);
        //token失效
        if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
            throw new IncorrectCredentialsException("token失效，请重新登录");
        }

        //查询用户信息
        Object oUser = shiroService.queryUser(Constant.Org.findByValue(tokenEntity.getOrg()), tokenEntity.getUserId());
        if (oUser instanceof OrgCbsUserEntity && ((OrgCbsUserEntity) oUser).getStatus() == 0) {
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }
        if (oUser instanceof OrgCtbUserEntity && ((OrgCtbUserEntity) oUser).getStatus() == 0) {
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }
        return new SimpleAuthenticationInfo(oUser, accessToken, getName());
    }
}
