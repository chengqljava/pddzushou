package com.cheng.helper.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cheng.helper.config.Context;
import com.cheng.helper.domain.GoodsDO;
import com.cheng.helper.domain.ShopDO;
import com.cheng.helper.domain.ShopQuery;
import com.cheng.helper.dto.GoodMessage;
import com.cheng.helper.dto.UserDTO;
import com.cheng.helper.enums.Role;
import com.cheng.helper.service.GoodsService;
import com.cheng.helper.service.ShopService;
import com.cheng.helper.utils.IDGenerator;
import com.cheng.helper.utils.OrderUtilSingle;

import io.swagger.annotations.ApiOperation;

/**
 * 订单
 * 
 * @author chengqianliang
 *
 */
@Controller
@RequestMapping(value = "/order")
public class OrderController {
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private ShopService shopService;

	@ApiOperation(value = "订单列表", notes = "订单列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String orderList(@RequestParam(required = false, defaultValue = "") String shopId,
			@RequestParam(required = false, defaultValue = "") Integer status,
			@RequestParam(required = false, defaultValue = "") String filterPhones, Model model) {
		try {
			UserDTO userDTO = Context.getUser();
			ShopQuery shopQuery = new ShopQuery();
			if (Role.SIMPLE_PDD.getCode().equals(userDTO.getRole())) {
				shopQuery.setUserId(userDTO.getId());
			}
			List<ShopDO> shopList = shopService.list(shopQuery);
			model.addAttribute("shopList", shopList);
			model.addAttribute("shopId", shopId);
			model.addAttribute("status", status);
			model.addAttribute("filterPhones", filterPhones);
			if (shopId != null) {
				// 生成订单
				ShopDO shopDO = shopService.get(shopId);
				List<GoodMessage> goodMessages = null;
				if (shopDO != null) {
					OrderUtilSingle orderUtil = new OrderUtilSingle();
					orderUtil.orderList(shopDO.getKey(), shopDO.getSecret(), status == null ? 1 : status, 1);
					//orderUtil.orderInfo(shopDO.getKey(), shopDO.getSecret());
					if (orderUtil.isEndTask()) {
						goodMessages = orderUtil.parseList(filterPhones);
						model.addAttribute("list", goodMessages);
						if (goodMessages != null && !goodMessages.isEmpty()) {
							List<GoodsDO> goods = new ArrayList<GoodsDO>();
							GoodsDO goodsDO = null;
							for (GoodMessage goodMessage : goodMessages) {
								if (goodMessage.getGoodAmount() > 50) {
									goodsDO = new GoodsDO();
									goodsDO.setAmount(goodMessage.getGoodAmount());
									goodsDO.setCreateTime(new Date());
									goodsDO.setGoodsId(goodMessage.getGoodIds());
									goodsDO.setShopId(shopId);
									goodsDO.setId(IDGenerator.OBJECTID.generate());
									goodsDO.setImages(goodMessage.getImageURL());
									goods.add(goodsDO);
								}

							}
							if (!goods.isEmpty()) {
								goodsService.saveBatch(goods);
							}
						}
					}
					// 110937
					// 08C11D55B379AE9FEFEC96FB3B59EF520F2D0696
					// System.out.println(JSONObject.toJSONString(orderUtil.parseList().size()));
					// System.out.println(JSONObject.toJSONString(orderUtil.parseList()));
					// 大于50的保存数据库
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", e.getMessage());
		}

		return "order/list";
	}

	@ApiOperation(value = "订单列表", notes = "订单列表")
	@RequestMapping(value = "/list/json", method = RequestMethod.GET)
	public String orderListJSON(@RequestParam(required = false, defaultValue = "") String shopId,
			@RequestParam(required = false, defaultValue = "") Integer status,
			@RequestParam(required = false, defaultValue = "") String filterPhones, Model model) {
		try {
			UserDTO userDTO = Context.getUser();
			ShopQuery shopQuery = new ShopQuery();
			if (Role.SIMPLE_PDD.getCode().equals(userDTO.getRole())) {
				shopQuery.setUserId(userDTO.getId());
			}
			List<ShopDO> shopList = shopService.list(shopQuery);
			model.addAttribute("shopList", shopList);
		
		
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", e.getMessage());
		}
		return "order/listjson";
	}
	@ApiOperation(value = "订单列表", notes = "订单列表")
	@ResponseStatus(HttpStatus.OK)
    @ResponseBody
	@RequestMapping(value = "/ajaxOrderInfo", method = RequestMethod.POST)
	public JSONObject ajaxOrderInfo(String shopId, Integer status, String filterPhones) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", true);
		try {
			ShopDO shopDO = shopService.get(shopId);
			if (shopDO != null) {
				OrderUtilSingle orderUtil = new OrderUtilSingle();
				orderUtil.orderList(shopDO.getKey(), shopDO.getSecret(), status == null ? 1 : status, 1);
				//orderUtil.orderInfo(shopDO.getKey(), shopDO.getSecret());
				if (orderUtil.isEndTask()) {
					System.out.println( orderUtil.getOrderSNSInfo());
					jsonObject.put("list", orderUtil.getOrderSNSInfo());
					
				}
			}
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e.getMessage());
		}
      
		return jsonObject;
	}
	
	@ApiOperation(value = "订单列表", notes = "订单列表")
	@ResponseStatus(HttpStatus.OK)
    @ResponseBody
	@RequestMapping(value = "/ajaxOrderRecoderInfo", method = RequestMethod.POST)
	public JSONObject ajaxOrderRecoderInfo(String shopId,  String goodRecord) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", true);
		try {
			List<GoodsDO> goods = new ArrayList<GoodsDO>();
			GoodsDO goodsDO = null;
			JSONArray jsonArray=JSONObject.parseArray("["+goodRecord+"]");
			System.out.println(jsonArray.toJSONString()+jsonArray.size());
			JSONObject json=null;
			for (int i=0;i<jsonArray.size();i++) {
				json=jsonArray.getJSONObject(i);
					goodsDO = new GoodsDO();
					goodsDO.setAmount(json.getInteger("goodsCount"));
					goodsDO.setCreateTime(new Date());
					goodsDO.setGoodsId(json.getString("goodIds"));
					goodsDO.setShopId(shopId);
					goodsDO.setId(IDGenerator.OBJECTID.generate());
					goodsDO.setImages(json.getString("goodsImg"));
					goods.add(goodsDO);
				

			}
			if (!goods.isEmpty()) {
				goodsService.saveBatch(goods);
			}
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e.getMessage());
		}
      
		return jsonObject;
	}

}
