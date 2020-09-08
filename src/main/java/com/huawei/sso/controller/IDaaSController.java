package com.huawei.sso.controller;


import com.alibaba.fastjson.JSON;
import com.huawei.sso.config.IDaaSConfig;
import com.huawei.sso.entity.User;
import com.huawei.sso.service.UserService;
import com.huawei.sso.utils.HttpClientUtils;
import com.huawei.sso.utils.URLEncodeUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/ams/oauth")
public class IDaaSController {

    @Resource
    private IDaaSConfig iDaaSConfig;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String login() {
        return "login";
    }

    //https://{your_domain}/api/ams/oauth/authorize?response_type=code&client_id=2t0d17l08r343dsfsdff0&redirect_uri=http://oauthdemo.bccastle.com/demo/index.jsp&state=15924362
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletResponse response, Model model) throws Exception {


        StringBuilder codeUrl = new StringBuilder("https://huawei.bccastle.com:443/api/ams/oauth/authorize");
        codeUrl.append("?response_type=code");
        codeUrl.append("&client_id="+ iDaaSConfig.getClientId());
        // 回调地址
        String redirect_uri = iDaaSConfig.getRedirectUrl();
        codeUrl.append("&redirect_uri=" + URLEncodeUtil.getURLEncoderString(redirect_uri));

        System.out.println(codeUrl);
        //response.sendRedirect(codeUrl.toString());
        model.addAttribute("codeUrl",codeUrl);

        return "login";
    }

    //https://{your_domain}/api/ams/oauth/token?grant_type=authorization_code&client_id=20170830061623854-E5A8-B2FABDC35&client_secret=9c25b1c7cb9641bba4cb8ce4960e24ea&code=6dce46bb65f67de9f81504c651963086&redirect_uri=http://oauthdemo.bccastle.com/demo/index.jsp
    @RequestMapping(value = "/callBack", method = RequestMethod.GET)
    @ResponseBody
    public User callBack(String code, HttpServletResponse response) throws Exception {
       /* if (code != null ) {
            System.out.println(code);
        }*/
        //获取access_token
        Map<String, Object> zyProperties = getToken(code);

        String openId = getOpenId(zyProperties);
        zyProperties.put("openId",openId);

        User userInfo =  getUserInfo(zyProperties);

        Map<String, Object> refreshToken = refreshToken(zyProperties);

        return userInfo;

     /*   //获取用户信息
        StringBuilder userInfo = new StringBuilder("https://bc.bccastle.com/authentication/pc/#/login/?api/ams/oauth/get_user_info");
        userInfo.append("?access_token=" +access_token);

        String sendUserInfo = JsonUtils.toString(userInfo);
        Map<String, Object> userInfoMap = JSON.parseObject(sendUserInfo , Map.class);

        //实现用户信息
        String token = userService.idaasLogin(userInfoMap);
        String loginPage = "https://www.ictxuetang.com/cict/pc/home/?token"+token;

        response.sendRedirect(loginPage);*/
    }

    @GetMapping("/refreshToken")
    public Map<String,Object> refreshToken(Map<String,Object> zyProperties) throws Exception {
        // 获取refreshToken
        String refreshToken = (String) zyProperties.get("refreshToken");
        StringBuilder url = new StringBuilder("https://huawei.bccastle.com:443/api/ams/oauth/token?");
        url.append("grant_type=refresh_token");
        url.append("&client_id=" + iDaaSConfig.getClientId());
        url.append("&client_secret=" + iDaaSConfig.getSecrect());
        url.append("&refresh_token=" + refreshToken);
        System.out.println("url:" + url.toString());
        String result = HttpClientUtils.get(url.toString(), "UTF-8");
        // 把新获取的token存到map中
        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(result, "&");
        String accessToken = StringUtils.substringAfterLast(items[0], "=");
        Long expiresIn = new Long(StringUtils.substringAfterLast(items[1], "="));
        //String newRefreshToken = StringUtils.substringAfterLast(items[2], "=");
        //重置信息
        zyProperties.put("accessToken", accessToken);
        zyProperties.put("expiresIn", String.valueOf(expiresIn));
        //zyProperties.put("refreshToken", newRefreshToken);
        return zyProperties;
    }


    public Map<String, Object> getToken(String code) throws Exception {

        StringBuilder accessUrl = new StringBuilder("https://huawei.bccastle.com:443/api/ams/oauth/token");
        accessUrl.append("?grant_type=authorization_code");
        accessUrl.append("&client_id=" + iDaaSConfig.getClientId());
        accessUrl.append("&client_secret=" + iDaaSConfig.getSecrect());
        accessUrl.append("&code=" + code);
        // 回调地址
        String redirect_uri = iDaaSConfig.getRedirectUrl();
        accessUrl.append("&redirect_uri=" + URLEncodeUtil.getURLEncoderString(redirect_uri));

        //获得token
        String result = HttpClientUtils.get(accessUrl.toString(), "UTF-8");
        System.out.println("url:" + accessUrl.toString());

        // 把token保存
        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(result, "&");
        String accessToken = StringUtils.substringAfterLast(items[0], "=");
        Long expiresIn = new Long(StringUtils.substringAfterLast(items[1], "="));
        //String refreshToken = StringUtils.substringAfterLast(items[2],"=");

        //token信息
        Map<String,Object > zyProperties = new HashMap<String,Object >();
        zyProperties.put("accessToken", accessToken);
        zyProperties.put("expiresIn", String.valueOf(expiresIn));
        //zyProperties.put("refreshToken", refreshToken);
        return zyProperties;


    }

    public String getOpenId(Map<String,Object> zyProperties) throws Exception {
        // 获取保存的用户的token
        String accessToken = (String) zyProperties.get("accessToken");
        if (!StringUtils.isNotEmpty(accessToken)) {
             //return "未授权";
        }
        StringBuilder url = new StringBuilder("https://huawei.bccastle.com:443/api/ams/oauth/me");
        url.append("?access_token=" + accessToken);
        String result = HttpClientUtils.get(url.toString(), "UTF-8");
        String openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
        return result;
    }

    public User getUserInfo(Map<String,Object> zyProperties) throws Exception {
        // 取token
        String accessToken = (String) zyProperties.get("accessToken");
        String openId = (String) zyProperties.get("openId");
        if (!StringUtils.isNotEmpty(accessToken) || !StringUtils.isNotEmpty(openId)) {
            return null;
        }
        //拼接url
        StringBuilder url = new StringBuilder("https://bc.bccastle.com/authentication/pc/#/login/?api/ams/oauth/get_user_info");
        url.append("?access_token=" + accessToken);
        url.append("&client_id=" + iDaaSConfig.getClientId());
        url.append("&openid=" + openId);
        // 获取相关数据
        String result = HttpClientUtils.get(url.toString(), "UTF-8");
        Object json = JSON.parseObject(result, User.class);
        User userInfo = (User) json;
        return userInfo;
    }






}
