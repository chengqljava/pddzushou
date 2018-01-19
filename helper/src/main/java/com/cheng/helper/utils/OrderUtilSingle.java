package com.cheng.helper.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cheng.helper.dto.GoodMessage;
import com.cheng.utils.SignUtil;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

public class OrderUtilSingle {

    private String                         URL          = "http://gw-api.pinduoduo.com/api/router";
    //private List<String> orderSNs = Collections.synchronizedList(new ArrayList<String>());
    private static BlockingQueue<Runnable> queue        = new LinkedBlockingQueue<Runnable>();     // 固定为10的线程队列
    private static ThreadPoolExecutor      executor     = new ThreadPoolExecutor(4, 20, 1,
        TimeUnit.HOURS, queue);
    private List<String>                   orderSNSInfo = Collections
        .synchronizedList(new ArrayList<String>());

    public void orderList(String clientId, String clientSerct, String accessToken, int orderStatus,
                          int pageNumer) {
        Map<String, Object> params = new TreeMap<String, Object>();
        params.put("client_id", clientId);
        params.put("type", "pdd.order.number.list.get");
        params.put("order_status", orderStatus);
        params.put("timestamp", System.currentTimeMillis() + "");
        params.put("data_type", "JSON");
        params.put("page", pageNumer);
        params.put("access_token", accessToken);
        // total_count
        // 两页
        // 先加密
        String sign = SignUtil.signRequest(params, clientSerct);
        params.put("sign", sign);
        // 参数序列
        // String paramSer = SignUtil.parmsStr(params);
        // System.out.println(paramSer);
        HttpRequest httpRequest = HttpRequest.post(URL)
            .contentType("application/x-www-form-urlencoded; charset=UTF-8").form(params);
        HttpResponse httpResponse = httpRequest.send();
        // System.out.println(httpResponse.bodyText());
        // 获取内空转JSON
        JSONObject jsonObject = JSONObject.parseObject(httpResponse.bodyText());
        // System.out.println("pageNumber" + pageNumer);
        // System.err.println("jsonObject" + jsonObject);

        // 获取信息
        JSONObject order_sn_list_get_response = jsonObject
            .getJSONObject("order_sn_list_get_response");

        // 获取总数
        int total_count = order_sn_list_get_response.getIntValue("total_count");
        if (total_count > 0) {
            // 获取列表
            JSONArray order_sn_list = order_sn_list_get_response.getJSONArray("order_sn_list");
            // 具体定
            JSONObject orderSN = null;
            if (order_sn_list != null && order_sn_list.size() > 0) {
                for (int i = 0; i < order_sn_list.size(); i++) {
                    orderSN = order_sn_list.getJSONObject(i);
                    //orderSNs.add(orderSN.getString("order_sn"));
                    executor.execute(new Thread(new OrderDetailThread(clientId, clientSerct,
                        accessToken, orderSN.getString("order_sn"))));
                }

            }
            if (total_count > 0) {
                pageNumer++;
                this.orderList(clientId, clientSerct, accessToken, orderStatus, pageNumer);
            }
        }
    }

    class OrderDetailThread implements Runnable {
        private HttpRequest         httpRequest  = null;
        private HttpResponse        httpResponse = null;
        private Map<String, Object> params       = null;
        private String              clientId;
        private String              accessToken;
        private String              orderCode;
        private String              clientSerct;

        public OrderDetailThread(String clientId, String clientSerct, String accessToken,
                                 String orderCode) {
            this.clientId = clientId;
            this.accessToken = accessToken;
            this.orderCode = orderCode;
            this.clientSerct = clientSerct;
        }

        @Override
        public void run() {
            params = new TreeMap<String, Object>();
            params.put("client_id", clientId);
            params.put("type", "pdd.order.information.get");
            params.put("timestamp", System.currentTimeMillis());
            params.put("data_type", "JSON");
            params.put("order_sn", orderCode);
            params.put("access_token", accessToken);
            // 先加密
            String sign = SignUtil.signRequest(params, clientSerct);
            params.put("sign", sign);
            httpRequest = HttpRequest.post(URL)
                .contentType("application/x-www-form-urlencoded; charset=UTF-8").form(params);
            httpResponse = httpRequest.send();
            // System.out.println(httpResponse.bodyText());
            // 获取内空转JSON
            orderSNSInfo.add(httpResponse.bodyText());
        }

    }
    //
    //	public List<String> getOrderSNs() {
    //		return orderSNs;
    //	}
    //
    //	public void setOrderSNs(List<String> orderSNs) {
    //		this.orderSNs = orderSNs;
    //	}

    public List<String> getOrderSNSInfo() {
        return orderSNSInfo;
    }

    public void setOrderSNSInfo(List<String> orderSNSInfo) {
        this.orderSNSInfo = orderSNSInfo;
    }

    public boolean isEndTask() {
        while (true) {
            if (OrderUtilSingle.executor.getActiveCount() == 0) {
                return true;
            }
        }
    }

    public List<GoodMessage> parseList(String filterPhones) {
        if (!orderSNSInfo.isEmpty()) {
            JSONObject orderInfo = null;
            JSONArray itemList = null;
            String goodsId = null;
            Map<String, GoodMessage> goodIdsMap = new HashMap<String, GoodMessage>();
            GoodMessage goodMessage = null;
            List<GoodMessage> goodMessageList = new ArrayList<GoodMessage>();
            for (String order : orderSNSInfo) {

                orderInfo = JSONObject.parseObject(order).getJSONObject("order_info_get_response")
                    .getJSONObject("order_info");
                if (StringUtils.isNoneBlank(filterPhones)) {
                    if (filterPhones.contains(orderInfo.getString("receiver_phone"))) {
                        continue;
                    }
                }
                itemList = orderInfo.getJSONArray("item_list");
                for (int i = 0; i < itemList.size(); i++) {
                    goodsId = itemList.getJSONObject(i).getString("goods_id");
                    if (goodIdsMap.containsKey(goodsId)) {
                        goodMessage = goodIdsMap.get(goodsId);
                    } else {
                        goodMessage = new GoodMessage(goodsId);
                        goodIdsMap.put(goodsId, goodMessage);
                        goodMessageList.add(goodMessage);
                    }
                    goodMessage.parse(itemList.getJSONObject(i).getString("outer_id"),
                        itemList.getJSONObject(i).getString("goods_spec"),
                        itemList.getJSONObject(i).getIntValue("goods_count"),
                        itemList.getJSONObject(i).getString("goods_img"));
                }

            }
            return goodMessageList;
        } else {
            return null;
        }

    }

    public static void main(String[] args) {
        OrderUtilSingle order = new OrderUtilSingle();

        order.orderList("56b36ebebdb8433b868a647b8e681e67",
            "cd8acb2c3635b58bd778bac7aa19b1692dc5a842", "3380c4e7bf78425492d485266c9f58285b0742cd",
            1, 1);
        //		System.out.println(order.getOrderSNs().size());
        if (order.isEndTask()) {
            System.out.println(order.getOrderSNSInfo().size());
        }

        System.out.println(JSONObject.toJSONString(order.getOrderSNSInfo()));
    }

}
