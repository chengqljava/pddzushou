package com.cheng.helper.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cheng.helper.domain.ShopDO;
import com.cheng.helper.dto.OrderDTO;
import com.cheng.helper.request.OrderRequest;
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
	public List<OrderDTO> orderList(@Valid @RequestBody OrderRequest orderRequest ){
		ShopDO shopDO=shopService.get(orderRequest.getId());
		
		return null;
	}
}
