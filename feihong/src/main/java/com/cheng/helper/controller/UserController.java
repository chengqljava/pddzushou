package com.cheng.helper.controller;

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
import org.springside.modules.utils.security.CryptoUtil;
import org.springside.modules.utils.text.EncodeUtil;

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
	public String list(@RequestParam(required = false, defaultValue = "")String nameOrPhone,@RequestParam(required = true, defaultValue = "1") int pageNum,
            @RequestParam(required = true, defaultValue = "10") int pageSize,Model model){
		UserQuery userQuery=new UserQuery();
		userQuery.setPage(pageNum);
		userQuery.setSize(pageSize);
		userQuery.setNameOrPhone(nameOrPhone);
		PageInfo<UserDO> pageInfo=   userService.page(userQuery);
		model.addAttribute("page", pageInfo);
		model.addAttribute("nameOrPhone", nameOrPhone);
	    return "user/list";
	}
	@ApiOperation(value = "用户新增", notes = "用户新增")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(String id,Model model){
		 if(StringUtils.isNoneBlank(id)){
			 UserDO userDO=userService.get(id);
			 if(userDO!=null){
				 model.addAttribute("user", userDO);
			 }
		 }
		 return "user/add";
	}
	@ApiOperation(value = "用户保存更新", notes = "用户保存更新")
	@ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public JSONObject saveOrUpdate(@Valid @ModelAttribute("entity") UserRequest userRequest){
		UserDO userDO=BeanMapper.map(userRequest, UserDO.class);
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("success", true);
		try{
		if(StringUtils.isNoneBlank(userRequest.getId())){
			userService.update(userDO);
		}else{
			if(userDO.getPassword()==null){
				userDO.setPassword("111111");
			}
			userDO.setSalt(EncodeUtil.encodeHex(CryptoUtil.generateHmacSha1Key()));
			userDO.setPassword(EncodeUtil.encodeHex(
	             CryptoUtil.hmacSha1(userDO.getPassword().getBytes(), userDO.getSalt().getBytes())));
			userService.save(userDO);
		}
		}catch(Exception e){
			jsonObject.put("success", false);
			jsonObject.put("message", e.getMessage());
		}
		
		 return jsonObject;
	}
}
