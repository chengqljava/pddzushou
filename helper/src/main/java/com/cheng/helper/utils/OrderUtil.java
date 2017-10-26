package com.cheng.helper.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cheng.helper.dto.GoodsIdOuterIdSpec;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

public class OrderUtil {

	private String URL = "http://open.yangkeduo.com/api/router";
	private List<String> orderSNs = Collections.synchronizedList(new ArrayList<String>());
	private static BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(); // 固定为10的线程队列
	private static ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 20, 1, TimeUnit.HOURS, queue);
	private List<String> orderSNSInfo = Collections.synchronizedList(new ArrayList<String>());

	public void orderList(String mallId, String secret, int orderStatus, int pageNumer) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("mall_id", mallId);
		params.put("type", "pdd.order.number.list.get");
		params.put("order_status", orderStatus);
		params.put("timestamp", System.currentTimeMillis() + "");
		params.put("data_type", "JSON");
		params.put("page", pageNumer);
		// total_count
		// 两页
		// 先加密
		String sign = SignUtil.signRequest(params, secret);
		params.put("sign", sign);
		// 参数序列
		// String paramSer = SignUtil.parmsStr(params);
		// System.out.println(paramSer);
		HttpRequest httpRequest = HttpRequest.post(URL).contentType("application/x-www-form-urlencoded; charset=UTF-8")
				.form(params);
		HttpResponse httpResponse = httpRequest.send();
		System.out.println(httpResponse.bodyText());
		// 获取内空转JSON
		JSONObject jsonObject = JSONObject.parseObject(httpResponse.bodyText());
		System.out.println("pageNumber" + pageNumer);
		System.err.println("jsonObject" + jsonObject);
		// 获取信息
		JSONObject order_sn_list_get_response = jsonObject.getJSONObject("order_sn_list_get_response");
		// 获取列表
		JSONArray order_sn_list = order_sn_list_get_response.getJSONArray("order_sn_list");
		// 获取总数
		int total_count = order_sn_list_get_response.getIntValue("total_count");
		// 具体定
		JSONObject orderSN = null;
		if (order_sn_list != null && order_sn_list.size() > 0) {
			for (int i = 0; i < order_sn_list.size(); i++) {
				orderSN = order_sn_list.getJSONObject(i);
				orderSNs.add(orderSN.getString("order_sn"));
			}

		}
		if (total_count > 0) {
			pageNumer++;
			this.orderList(mallId, secret, orderStatus, pageNumer);
		}
	}

	public void orderInfo(String mallId, String secret) {
		if (!orderSNs.isEmpty()) {
			for (String orderSN : orderSNs) {
		        executor.execute(new Thread(new OrderDetailThread(mallId, secret,
		        		orderSN)));
			}
		}
	}

	class OrderDetailThread implements Runnable {
		private HttpRequest httpRequest = null;
		private HttpResponse httpResponse = null;
		private Map<String, Object> params = null;
		private String mallId;
		private String secret;
		private String orderCode;

		public OrderDetailThread(String mallId, String secret, String orderCode) {
			this.mallId = mallId;
			this.secret = secret;
			this.orderCode = orderCode;
		}

		@Override
		public void run() {
			params = new TreeMap<String, Object>();
			params.put("mall_id", mallId);
			params.put("type", "pdd.order.information.get");
			params.put("timestamp", System.currentTimeMillis());
			params.put("data_type", "JSON");
			params.put("order_sn", orderCode);
			// 先加密
			String sign = SignUtil.signRequest(params, secret);
			params.put("sign", sign);
			httpRequest = HttpRequest.post(URL)
					.contentType("application/x-www-form-urlencoded; charset=UTF-8").form(params);
			httpResponse = httpRequest.send();
			// System.out.println(httpResponse.bodyText());
			// 获取内空转JSON
			System.out.println(httpResponse.bodyText());
			orderSNSInfo.add(httpResponse.bodyText());
		}

	}
	
	
	public List<String> getOrderSNs() {
		return orderSNs;
	}

	public void setOrderSNs(List<String> orderSNs) {
		this.orderSNs = orderSNs;
	}

	public List<String> getOrderSNSInfo() {
		return orderSNSInfo;
	}

	public void setOrderSNSInfo(List<String> orderSNSInfo) {
		this.orderSNSInfo = orderSNSInfo;
	}
	public boolean isEndTask() {  
	    while (true) {  
	      if (this.executor.getActiveCount() == 0) {  
	         return true;  
	      }  
	    }  
	  } 
	public void parseJSON(){
		JSONObject jsonObject=null;
		Map<String,Map<String,GoodsIdOuterIdSpec>> goodIds=new HashMap<String,Map<String,GoodsIdOuterIdSpec>>();
		List<GoodsIdOuterIdSpec> goodsIdOuterIdSpecs=new ArrayList<GoodsIdOuterIdSpec>();
		for(String orderInfo:orderSNSInfo){
			jsonObject=JSONObject.parseObject(orderInfo);
			
		}
	}

	public static void main(String[] args) {
		OrderUtil order = new OrderUtil();
        order.orderList("110937", "1308706231", 1, 1);
        System.out.println(order.getOrderSNs().size());
        order.orderInfo("110937", "1308706231");
        if(order.isEndTask()){
         System.out.println(order.getOrderSNSInfo().size());
        }
		
	}

}