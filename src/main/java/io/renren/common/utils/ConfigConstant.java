/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.common.utils;

/**
 * 系统参数相关Key
 *
 * @author Mark sunlightcs@gmail.com
 */
public class ConfigConstant {
    /**
     * 云存储配置KEY
     */
    public final static String CLOUD_STORAGE_CONFIG_KEY = "CLOUD_STORAGE_CONFIG_KEY";

    // 公众号ID appId
    public final static String APPID_WX = "weixin.wn.appId";
    // 管理组的凭证密钥，每个secret代表了对应用、通讯录、接口的不同权限；不同的管理组拥有不同的secret
    public final static String SECRET_WX = "weixin.wn.appId";
    // online send_message
    public final static String GZH_OPENID = "openId";
    public final static String GZH_CODE = "code";
    public final static String ACCESS_TOKEN_CACHE = "";
    public final static String JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
    public final static String JSAPI_TICKET_CACHE = "";
    public final static String WX_USER_INFO = "WX_USER_INFO";
    // 小程序session key
    public final static String SESSION_KEY = "";
    // 获取access_token
    public final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=SECRET";
    // 根据openId获取用户信息
    public final static String USER_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
    // code 换取 session_key
    public final static String SESSION_KEY_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
    // 获取小程序二维码
    public final static String AQRCODE_URL = "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=ACCESS_TOKEN";
    // 获取小程序码（唯一）
    public final static String ACODE_ONLY_URL = "https://api.weixin.qq.com/wxa/getwxacode?access_token=ACCESS_TOKEN";
    // 获取小程序码（根据参数生成）
    public final static String ACODE_EACH_URL = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=ACCESS_TOKEN";
    // 微信企业对个人支付的接口(零钱)
    public final static String WEIXIN_B2C_PAY_CHANGE_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
}
