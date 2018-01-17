package com.cheng.helper.utils;

import com.alibaba.fastjson.JSONObject;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

public class OAuthUtil {
    public static final String CODE         = "http://mms.pinduoduo.com/open.html";
    public static final String ACCESS_TOKEN = "http://open-api.pinduoduo.com/oauth/token";
    public static final String REDIRECT_URI = "http://118.31.38.95:8383/ottService/callBack";

    public static JSONObject accessToken(String code, String clientId, String clientSecret) {
        JSONObject params = new JSONObject();
        params.put("code", code);
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("grant_type", "authorization_code");
        params.put("data_type", "JSON");
        params.put("redirect_uri", REDIRECT_URI);
        params.put("state", "1212");
        HttpRequest httpRequest = HttpRequest.post(ACCESS_TOKEN)
            .contentType("application/json", "UTF-8").body(params.toJSONString());
        HttpResponse httpResponse = httpRequest.send();
        // System.out.println(httpResponse.bodyText());
        // 获取内空转JSON
        JSONObject jsonObject = JSONObject.parseObject(httpResponse.bodyText());
        return jsonObject;
    }

    public static void main(String[] args) {
        System.out.println(accessToken("23232", "klasdfd", "clientSecret"));
    }
}
