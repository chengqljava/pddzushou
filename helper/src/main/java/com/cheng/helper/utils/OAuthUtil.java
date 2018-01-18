package com.cheng.helper.utils;

import com.alibaba.fastjson.JSONObject;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

public class OAuthUtil {
    public static final String CODE         = "http://mms.pinduoduo.com/open.html";
    public static final String ACCESS_TOKEN = "http://open-api.pinduoduo.com/oauth/token";
    public static final String REDIRECT_URI = "http://118.31.38.95:8383/ottService/callBack";

    /**
     *  {
              "scope": [
                  "pdd.logistics.companies.get",
                  "pdd.logistics.online.send",
                  "pdd.order.information.get",
                  "pdd.order.number.list.get",
                  "pdd.order.number.list.increment.get",
                  "pdd.order.status.get",
                  "pdd.refund.list.increment.get",
                  "pdd.refund.status.check",
                  "pdd.virtual.mobile.charge.notify"
              ],
              "access_token": "19eeaea897914e95907488d2f94b21ef26ef2687",
              "expires_in": 31535999,
              "refresh_token": "75cd5b38e60d46c7a566521c792e0e7e9e341b4f",
              "owner_id": "213213"
              "owner_name": "pdd213213"
            }
     * @param code
     * @param clientId
     * @param clientSecret
     * @return
     */
    public static JSONObject accessToken(String code, String clientId, String clientSecret) {
        JSONObject params = new JSONObject();
        params.put("code", code);
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("grant_type", "authorization_code");
        params.put("redirect_uri", REDIRECT_URI);
        params.put("state", "1212");
        HttpRequest httpRequest = HttpRequest.post(ACCESS_TOKEN)
            .contentType("application/json", "UTF-8").body(params.toJSONString());
        HttpResponse httpResponse = httpRequest.send();
        JSONObject jsonObject = JSONObject.parseObject(httpResponse.bodyText());
        return jsonObject;
    }

    /**
     *    {
              "scope": [
                  "pdd.logistics.companies.get",
                  "pdd.logistics.online.send",
                  "pdd.order.information.get",
                  "pdd.order.number.list.get",
                  "pdd.order.number.list.increment.get",
                  "pdd.order.status.get",
                  "pdd.refund.list.increment.get",
                  "pdd.refund.status.check",
                  "pdd.virtual.mobile.charge.notify"
              ],
              "access_token": "19eeaea897914e95907488d2f94b21ef26ef2687",
              "expires_in": 31535999,
              "refresh_token": "75cd5b38e60d46c7a566521c792e0e7e9e341b4f",
              "owner_id": "1"，
              "owner_name": "pdd213213"
            }
     * @param refreshToken
     * @param clientId
     * @param clientSecret
     * @return
     */
    public static JSONObject refreshToken(String refreshToken, String clientId,
                                          String clientSecret) {
        JSONObject params = new JSONObject();
        params.put("refresh_token", refreshToken);
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("grant_type", "refresh_token");
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
        System.out.println(accessToken("0bbed33ac7c5428e9493de111a6aba13e0024397",
            "56b36ebebdb8433b868a647b8e681e67", "cd8acb2c3635b58bd778bac7aa19b1692dc5a842"));
    }

}
