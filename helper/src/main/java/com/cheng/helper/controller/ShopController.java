package com.cheng.helper.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springside.modules.utils.mapper.BeanMapper;

import com.alibaba.fastjson.JSONObject;
import com.cheng.helper.config.Context;
import com.cheng.helper.domain.ShopDO;
import com.cheng.helper.domain.ShopQuery;
import com.cheng.helper.dto.UserDTO;
import com.cheng.helper.enums.Role;
import com.cheng.helper.request.ShopRequest;
import com.cheng.helper.service.ShopService;
import com.github.pagehelper.PageInfo;

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
	public String list(@RequestParam(required = true, defaultValue = "1") int pageNum,
            @RequestParam(required = true, defaultValue = "10") int pageSize,Model model){
		UserDTO userDTO=Context.getUser();
		ShopQuery shopQuery=new ShopQuery();
		
		shopQuery.setPage(pageNum);
		shopQuery.setSize(pageSize);
		if(Role.SIMPLE_PDD.getCode().equals(userDTO.getRole())){
		 shopQuery.setUserId(userDTO.getId());
		}
		List<ShopDO> lists=shopService.list(shopQuery);
		PageInfo<ShopDO> page=	shopService.page(shopQuery);
		model.addAttribute("list", lists);
		model.addAttribute("page", page);
	    return "shop/list";
	}
	
	@ApiOperation(value = "商店新增", notes = "商店新增")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(@RequestParam(required = false, defaultValue = "")String id,Model model){
		 if(id!=null){
			 ShopDO shopDO=shopService.get(id);
			 if(shopDO!=null){
				 model.addAttribute("shop", shopDO);
			 }
		 }
		 return "shop/add";
	}
	@ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public JSONObject saveOrUpdate(@Valid @ModelAttribute("entity") ShopRequest shopRequest){
		ShopDO shopDO=BeanMapper.map(shopRequest, ShopDO.class);
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("success", true);
		try{
		if(StringUtils.isNoneBlank(shopRequest.getId())){
			shopDO.setUserId(Context.getUser().getId());
			shopService.update(shopDO);
		}else{
			shopDO.setUserId(Context.getUser().getId());
			shopService.save(shopDO);
		}
		}catch(Exception e){
			jsonObject.put("success", false);
			jsonObject.put("message", e.getMessage());
		}
		
        return jsonObject;
	}
	
	


}
