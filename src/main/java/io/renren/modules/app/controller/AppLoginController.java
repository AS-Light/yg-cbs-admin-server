/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.app.controller;


import com.google.gson.JsonObject;
import io.renren.common.utils.ConfigConstant;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.app.entity.AccessToken;
import io.renren.modules.app.form.LoginForm;
import io.renren.modules.app.service.UserService;
import io.renren.modules.app.utils.JwtUtils;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * APP登录授权
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/app")
public class AppLoginController {
    private static AccessToken token = new AccessToken();

    private UserService userService;
    private JwtUtils jwtUtils;

    public AppLoginController(UserService userService, JwtUtils jwtUtils) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    /**
     * 登录
     */
    @PostMapping("login")
    public R login(@RequestBody LoginForm form) {
        //表单校验
        ValidatorUtils.validateEntity(form);

        //用户登录
        long userId = userService.login(form);

        //生成token
        String token = jwtUtils.generateToken(userId);

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("expire", jwtUtils.getExpire());

        return R.ok(map);
    }


    /**
     * 微信根据code获取unionID
     */
    @GetMapping("unionId/{code}")
    public R unionId(@PathVariable String code) {
        return R.ok(getOpenId(code));
    }

    /**
     * 获取用户的openid 参数：code 微信授权随机生成的code，每次授权生成一次
     */
    public static String getOpenId(String code) {
        String openid = null;
        try {
            JsonObject json = getAccessToken(code);
            openid = json.get("openid").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return openid;
    }

    /**
     * 获取用户信息第一步：获取access_token和open_id
     */
    public static JsonObject getAccessToken(String code) throws Exception {
        if (code == null || code.equals("")) {
            throw new Exception();
        }
        String get_access_token_url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + ConfigConstant.APPID_WX + "&secret=" + ConfigConstant.SECRET_WX + "&js_code=" + code + "&grant_type=authorization_code";
        JsonObject result = httpSendGet(get_access_token_url);
        return result;
    }

    /**
     * 获取 AccessToken
     *
     * @param appId
     * @param secret
     * @return
     */
    public static AccessToken getAccessToken(String appId, String secret) {
        if (token.getTokenTime() != null && token.getTokenTime() > System.currentTimeMillis()) {
            return token;
        }
        String requestUrl = ConfigConstant.ACCESS_TOKEN_URL.replace("APPID", appId).replace("SECRET", secret);
        JsonObject jsonObject = httpSendGet(requestUrl);
        // 如果请求成功
        if (null != jsonObject) {
            try {
                token.setToken(jsonObject.get("access_token").getAsString());
                token.setExpiresIn(jsonObject.get("expires_in").getAsInt());
                token.setTokenTime(System.currentTimeMillis() + 7000000L);
                System.out.println("getAccessToken : " + token);
            } catch (Exception e) {
                token.setExpiresIn(null);
                token.setToken(null);
                token.setTokenTime(null);
                // 获取token失败
                String error = String.format("获取token失败 errcode:{} errmsg:{}", jsonObject.get("errcode").getAsInt(),
                        jsonObject.get("errmsg").getAsString());
                System.out.println("httpSendGet获取openId," + error + e);
            }
        }
        return token;
    }


    /**
     * 模拟GET请求
     *
     * @param requestUrl
     * @return
     */
    private static JsonObject httpSendGet(String requestUrl) {
        JsonObject jsonObject = new JsonObject();
        StringBuffer buffer = new StringBuffer();
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("GET");

            InputStream input = connection.getInputStream();
            InputStreamReader inputReader = new InputStreamReader(input, "UTF-8");
            BufferedReader reader = new BufferedReader(inputReader);

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            inputReader.close();
            input.close();
            connection.disconnect();
            jsonObject = jsonObject.getAsJsonObject(buffer.toString());
        } catch (Exception ex) {
        }
        return jsonObject;
    }

}
