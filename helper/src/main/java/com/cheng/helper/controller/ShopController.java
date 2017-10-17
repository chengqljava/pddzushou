package com.cheng.helper.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springside.modules.utils.mapper.BeanMapper;

import com.cheng.helper.config.Context;
import com.cheng.helper.domain.ShopDO;
import com.cheng.helper.domain.ShopQuery;
import com.cheng.helper.dto.UserDTO;
import com.cheng.helper.request.ShopRequest;
import com.cheng.helper.service.ShopService;

import io.swagger.annotations.ApiOperation;

/**
 * @author chengqianliang
 *商店
 */
@Controller
@RequestMapping(value = {"/shop" })
public class ShopController {
	@Autowired
	private ShopService shopService;
	@ApiOperation(value = "商店列表", notes = "商店列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model){
		UserDTO userDTO=Context.getUser();
		ShopQuery shopQuery=new ShopQuery();
		shopQuery.setUserId(userDTO.getId());
		List<ShopDO> lists=shopService.list(shopQuery);
		model.addAttribute("list", lists);
	    return "shop/list";
	}
	
	@ApiOperation(value = "商店新增", notes = "商店新增")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(String id,Model model){
		 if(id!=null){
			 ShopDO shopDO=shopService.get(id);
			 if(shopDO!=null){
				 model.addAttribute("shop", shopDO);
			 }
		 }
		 return "shop/add";
	}
	@ApiOperation(value = "商店新增", notes = "商店新增")
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public String saveOrUpdate(@Valid @RequestBody ShopRequest shopRequest){
		ShopDO shopDO=BeanMapper.map(shopRequest, ShopDO.class);
		if(shopRequest.getId()!=null){
			shopService.update(shopDO);
		}else{
			shopService.save(shopDO);;
		}
		 return "redirect:shop/list";
	}
	
	


}
