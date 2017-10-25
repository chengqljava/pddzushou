package com.cheng.helper.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springside.modules.utils.mapper.BeanMapper;

import com.alibaba.fastjson.JSONObject;
import com.cheng.helper.domain.UserDO;
import com.cheng.helper.domain.UserQuery;
import com.cheng.helper.request.UserRequest;
import com.cheng.helper.service.UserService;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.ApiOperation;
/**
 * @author chengqianliang
 *用户
 */
@Controller
@RequestMapping(value = {"/user" })
public class UserController {
	 @Autowired
	 private  UserService userService;
	@ApiOperation(value = "用户列表", notes = "用户列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(@RequestParam(required = false, defaultValue = "")String userName,@RequestParam(required = false, defaultValue = "")String phone,@RequestParam(required = true, defaultValue = "1") int pageNum,
            @RequestParam(required = true, defaultValue = "10") int pageSize,Model model){
		UserQuery userQuery=new UserQuery();
		userQuery.setPage(pageNum);
		userQuery.setSize(pageSize);
		userQuery.setUserName(userName);
		userQuery.setPhone(phone);
		PageInfo<UserDO> pageInfo=   userService.page(userQuery);
		model.addAttribute("page", pageInfo);
		model.addAttribute("userName", userName);
		model.addAttribute("phone", phone);
	    return "user/list";
	}
	@ApiOperation(value = "用户新增", notes = "用户新增")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(String id,Model model){
		 if(id!=null){
			 UserDO userDO=userService.get(id);
			 if(userDO!=null){
				 model.addAttribute("user", userDO);
			 }
		 }
		 return "user/add";
	}
	@ApiOperation(value = "用户保存更新", notes = "用户保存更新")
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public JSONObject saveOrUpdate(@Valid @RequestBody UserRequest userRequest){
		UserDO userDO=BeanMapper.map(userRequest, UserDO.class);
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("flag", true);
		try{
		if(userRequest.getId()!=null){
			userService.update(userDO);
		}else{
			userService.save(userDO);
		}
		}catch(Exception e){
			jsonObject.put("flag", false);
			jsonObject.put("message", e.getMessage());
		}
		
		 return jsonObject;
	}
}
