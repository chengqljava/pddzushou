package com.cheng.helper.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

public class UrlDownLoadUtils {

    private final static String PIN_URL        = "http://mobile.yangkeduo.com/goods2.html?goods_id=";
    private final static String PIN_URL_SEARCH = "http://apiv3.yangkeduo.com/search?page=%d&size=%d&sort=default&requery=0&list_id=search_UMQWEB&q=%s";
    // http://apiv3.yangkeduo.com/search?page=1&size=50&sort=default&requery=0&list_id=search_UMQWEB&q=%E4%B8%AD%E8%80%81%E5%B9%B4%E8%BF%9E%E8%A1%A3%E8%A3%99&flip=118;21;0;100&pdduid=3855191214
    private final static int    PAGE           = 1;
    private final static int    SIZE           = 165;

    public static JSONObject pinddDetail(String goodId) {

        JSONObject jsonObject = null;
        try {
            String html = urlGet(PIN_URL + goodId);
            int index = html.indexOf("window.rawData=");
            int close = html.indexOf("<script>!function");
            html = html.substring(index, close);
            html = html.replace("window.rawData=", "");
            html = html.replace("</script>", "");
            html = html.replace(";", "");
            jsonObject = JSONObject.parseObject(html);
            jsonObject = JSONObject.parseObject(jsonObject.getString("goods"));
        } catch (Exception e) {
            // TODO: handle exception
        }
        return jsonObject;
    }

    /**
     * 搜索
     * @param keyword
     * @return
     */
    public static JSONArray pinddSearch(String keyword) {
        return pinddSearch(keyword, PAGE, SIZE);
    }

    /**
     * 搜索指定分页长度
     * @param keyword
     * @param page
     * @param size
     * @return
     */
    public static JSONArray pinddSearch(String keyword, int page, int size) {
        String url = String.format(PIN_URL_SEARCH, page, size, encodeStr(keyword));
        String bodyText = urlGet(url);
        JSONObject json = JSONObject.parseObject(bodyText);
        JSONArray jsonArray = JSONObject.parseArray(json.getString("items"));
        return jsonArray;

    }

    /**
     * 排名搜索
     * @param keyword
     * @return
     */
    public static int pinddRankSearch(String keyword, String goodId) {
        JSONArray jsonArray = pinddSearch(keyword, PAGE, SIZE);
        if (!jsonArray.isEmpty()) {
            JSONObject jsonObject = null;
            for (int i = 0; i < jsonArray.size(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                if (goodId.equals(jsonObject.getString("goods_id"))) {
                    return i + 1;
                }
            }
        }
        return 200;
    }

    /**
     * GET
     * @param url
     * @return
     */
    public static String urlGet(String url) {
        HttpRequest httpRequest = HttpRequest.get(url);
        HttpResponse response = httpRequest.send();
        return response.bodyText();
    }

    /**
     * POST
     * @param url
     * @return
     */
    public static String urlPost(String url, Map<String, Object> params) {
        HttpRequest httpRequest = HttpRequest.post(url).form(params);
        HttpResponse httpResponse = httpRequest.send();
        return httpResponse.bodyText();
    }

    /**
     * 编码
     * @return
     */
    public static String encodeStr(String keyword) {
        return URLEncoder.encode(keyword);
    }

    /**
     * 解码
     * @param keyword
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String decodeStr(String keyword) {
        try {
            return URLDecoder.decode(keyword, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }

    public static boolean oneANDserven(int index) {
        return (index - 1) % 6 == 0;
    }

    public static int priceRank(int index) {
        return (index - 1) / 6;
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i < 200; i++) {
            if (!oneANDserven(i)) {
                list.add(i);
            }
        }
        System.out.println(list.size());
        JSONArray jsonArray = pinddSearch("中老年连衣裙");
        System.out.println(jsonArray);
        if (!jsonArray.isEmpty()) {
            JSONObject jsonObject = null;
            JSONObject adObject = null;
            Map<Integer, String> hashMap = new HashMap<Integer, String>();
            for (int i = 0; i < jsonArray.size(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.containsKey("ad")) {
                    adObject = jsonObject.getJSONObject("ad");
                    hashMap.put(i + 1, jsonObject.getString("goods_name") + "    keyword:"
                                       + adObject.getString("keyword") + oneANDserven(i + 1));
                } else {
                    hashMap.put(i + 1, jsonObject.getString("goods_name") + "  "
                                       + jsonObject.getString("goods_id"));
                }
            }
            System.out.println(JSONObject.toJSONString(hashMap));

        }
        //  System.out.println(urlGet(
        //      "http://apiv3.yangkeduo.com/search?page=1&size=50&sort=default&requery=0&list_id=search_UMQWEB&q=%E4%B8%AD%E8%80%81%E5%B9%B4%E8%BF%9E%E8%A1%A3%E8%A3%99"));

    }

}
