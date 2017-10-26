package com.cheng.helper.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cheng.helper.config.Context;
import com.cheng.helper.domain.ShopDO;
import com.cheng.helper.domain.ShopQuery;
import com.cheng.helper.dto.UserDTO;
import com.cheng.helper.enums.Role;
import com.cheng.helper.service.ShopService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**订单
 * @author chengqianliang
 *
 */
@Controller
@RequestMapping(value="/order")
public class OrderController {
    @Autowired
    private ShopService shopService;
	@ApiOperation(value = "用户注册", notes = "用户注册")
    @ApiImplicitParam(name = "orderRequest", value = "手机验证码注册", required = true, dataType = "OrderRequest", paramType = "body")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
	public String  orderList(@RequestParam(required = false, defaultValue = "")String shopId,@RequestParam(required = false, defaultValue = "")Integer status,Model model){
		
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
		}
		
		return null;
	}
}
