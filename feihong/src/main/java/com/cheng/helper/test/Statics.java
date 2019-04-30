package com.cheng.helper.test;

import java.util.Map;
import java.util.TreeMap;

import org.joda.time.DateTime;

import com.alibaba.fastjson.JSONObject;
import com.cheng.utils.SignUtil;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

public class Statics {
	private static String clientId="56b36ebebdb8433b868a647b8e681e67";
	private static String clientSerct="cd8acb2c3635b58bd778bac7aa19b1692dc5a842";
	private static String accessToken="3ad6f0163a5a4608a011bd3e3f2b8dbb04ac1aea";
    private static String                         URL          = "http://gw-api.pinduoduo.com/api/router";

	public static void main(String[] args) {
        DateTime startDateTime=new DateTime();
         DateTime newDateTime=startDateTime.plusHours(-2);
         System.out.println(newDateTime.toString("yyyy-MM-dd HH:mm:ss"));
         System.out.println(startDateTime.getMillis());
         System.out.println(newDateTime.getMillis());
        
		Map<String, Object> params = new TreeMap<String, Object>();
        params.put("client_id", clientId);
        params.put("type", "pdd.order.number.list.get");
        params.put("order_status", 1);
        params.put("timestamp", System.currentTimeMillis());
       // params.put("start_confirm_at", newDateTime.getMillis());
       // params.put("end_confirm_at", startDateTime.getMillis());
        params.put("access_token", accessToken);
        // total_count
        // 两页
        // 先加密
        String sign = SignUtil.signRequest(params, clientSerct);
        params.put("sign", sign);
        HttpRequest httpRequest = HttpRequest.post(URL)
                .contentType("application/x-www-form-urlencoded; charset=UTF-8").form(params);
            HttpResponse httpResponse = httpRequest.send();
            // System.out.println(httpResponse.bodyText());
            // 获取内空转JSON
            JSONObject jsonObject = JSONObject.parseObject(httpResponse.bodyText());
            // System.out.println("pageNumber" + pageNumer);
            System.err.println(httpResponse.bodyText());
	}

}
