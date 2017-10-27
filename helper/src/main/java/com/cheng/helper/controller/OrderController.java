package com.cheng.helper.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
import com.cheng.helper.utils.OrderUtil;

import io.swagger.annotations.ApiOperation;

/**订单
 * @author chengqianliang
 *
 */
@Controller
@RequestMapping(value="/order")
public class OrderController {
	@Autowired
	private  GoodsService goodsService;
    @Autowired
    private ShopService shopService;
	@ApiOperation(value = "订单列表", notes = "订单列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
	public String  orderList(@RequestParam(required = false, defaultValue = "")String shopId,@RequestParam(required = false, defaultValue = "1")Integer status,Model model){
		try{
		UserDTO userDTO=Context.getUser();
		ShopQuery shopQuery=new ShopQuery();
		if(Role.SIMPLE_PDD.getCode().equals(userDTO.getRole())){
		  shopQuery.setUserId(userDTO.getId());
		}
		List<ShopDO> shopList=shopService.list(shopQuery);
		model.addAttribute("shopList", shopList);
		model.addAttribute("shopId", shopId);
		model.addAttribute("status", status);
		if(shopId!=null){
			//生成订单
			ShopDO shopDO=shopService.get(shopId);
			if(shopDO!=null){
			OrderUtil orderUtil=new OrderUtil();
			orderUtil.orderList(shopDO.getKey(), shopDO.getSecret(), status==null?1:status, 1);
	        orderUtil.orderInfo(shopDO.getKey(), shopDO.getSecret());
	        if(orderUtil.isEndTask()){
	          model.addAttribute("list", orderUtil.parseList());
	        }
	        //110937
	        //08C11D55B379AE9FEFEC96FB3B59EF520F2D0696
	        //System.out.println(JSONObject.toJSONString(orderUtil.parseList().size()));
	        //System.out.println(JSONObject.toJSONString(orderUtil.parseList()));
	        //大于50的保存数据库
	        
	        if(!orderUtil.parseList().isEmpty()){
	        	List<GoodsDO> goods=new ArrayList<GoodsDO>();
	        	GoodsDO goodsDO=null;
	        	for(GoodMessage goodMessage:orderUtil.parseList()){
	        		if(goodMessage.getGoodAmount()>10){
	        			goodsDO=new GoodsDO();
	        			goodsDO.setAmount(String.valueOf(goodMessage.getGoodAmount()));
	        			goodsDO.setCreateTime(new Date());
	        			goodsDO.setGoodsId(goodMessage.getGoodIds());
	        			goodsDO.setShopId(shopId);
	        			goodsDO.setId(IDGenerator.OBJECTID.generate());
	        			goods.add(goodsDO);
	        		}
	        		
	        	}
	        	if(!goods.isEmpty()){
	        		goodsService.saveBatch(goods);
	        	}
	        }
			}
		}
		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", e.getMessage());
		}
		
		return "order/list";
	}
}
