/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.org_ctb.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.Constant;
import io.renren.common.utils.R;
import io.renren.modules.org_ctb.entity.OrgCtbCompanyEntity;
import io.renren.modules.org_ctb.entity.OrgCtbUserEntity;
import io.renren.modules.org_ctb.service.OrgCtbCompanyService;
import io.renren.modules.org_ctb.service.OrgCtbUserService;
import io.renren.modules.sys.form.SysLoginForm;
import io.renren.modules.sys.service.SysCaptchaService;
import io.renren.modules.sys.service.SysUserTokenService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

/**
 * 登录相关
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
public class OrgCtbLoginController extends AbstractController {
    @Autowired
    private OrgCtbCompanyService orgCtbCompanyService;
    @Autowired
    private OrgCtbUserService orgCtbUserService;
    @Autowired
    private SysUserTokenService sysUserTokenService;
    @Autowired
    private SysCaptchaService sysCaptchaService;

    /**
     * 验证码
     */
    @GetMapping("org/ctb/captcha.jpg")
    public void captcha(HttpServletResponse response, String uuid) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //获取图片验证码
        BufferedImage image = sysCaptchaService.getCaptcha(uuid);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    /**
     * 登录
     */
    @PostMapping("/org/ctb/login")
    public Map<String, Object> login(@RequestBody SysLoginForm form) throws IOException {
        boolean captcha = sysCaptchaService.validate(form.getUuid(), form.getCaptcha());
        if (!captcha) {
            return R.error("验证码不正确");
        }

        // 公司信息
        OrgCtbCompanyEntity company = orgCtbCompanyService.selectByCode(form.getCompanyCode());
        if (company == null) {
            return R.error("公司不存在");
        }

        //用户信息
        OrgCtbUserEntity user = orgCtbUserService.getOne(new QueryWrapper<OrgCtbUserEntity>().eq("username", form.getUsername()).eq("ctb_tenant_id", company.getId()));

        //账号不存在、密码错误
        if (user == null || !user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
            return R.error("账号或密码不正确");
        }

        //账号锁定
        if (user.getStatus() == 0) {
            return R.error("账号已被锁定,请联系管理员");
        }

        //生成token，并保存到数据库
        return sysUserTokenService.createToken(Constant.Org.CTB, user.getUserId());
    }


    /**
     * 退出
     */
    @PostMapping("/org/ctb/logout")
    public R logout() {
        sysUserTokenService.logout(Constant.Org.CTB, getUserId());
        return R.ok();
    }


    /**
     * 登录
     */
    @PostMapping("/org/ctb/loginApp")
    public R loginApp(@RequestBody SysLoginForm form) {
        //用户信息
        OrgCtbUserEntity user;
        if (StringUtils.isEmpty(form.getUnionId())) {
            // 公司信息
            OrgCtbCompanyEntity company = orgCtbCompanyService.selectByCode(form.getCompanyCode());
            if (company == null) {
                return R.error("公司不存在");
            }
            user = orgCtbUserService.getOne(new QueryWrapper<OrgCtbUserEntity>().eq("username", form.getUsername()).eq("ctb_tenant_id", company.getId()));
            // 密码错误
            if (!user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
                return R.error("请提供正确的账号或密码");
            }
        } else {
            user = orgCtbUserService.getOne(new QueryWrapper<OrgCtbUserEntity>().eq("union_id", form.getUnionId()));
        }

        //账号不存在
        if (user == null) {
            return R.error("账户不存在");
        }

        //账号锁定
        if (user.getStatus() == 0) {
            return R.error("账号已被锁定,请联系管理员");
        }

        //生成token，并保存到数据库
        return sysUserTokenService.createToken(Constant.Org.CTB, user.getUserId());
    }

}
