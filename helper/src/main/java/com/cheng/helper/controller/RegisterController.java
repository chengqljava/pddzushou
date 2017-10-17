package com.cheng.helper.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springside.modules.utils.mapper.BeanMapper;
import org.springside.modules.utils.security.CryptoUtil;
import org.springside.modules.utils.text.EncodeUtil;

import com.cheng.helper.domain.UserDO;
import com.cheng.helper.enums.Role;
import com.cheng.helper.request.UserRequest;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.cheng.helper.service.UserService;

/**
 * @author chengqianliang
 *注册
 */

@Controller
@RequestMapping(value = { "", "/" })
public class RegisterController {
	@Autowired
	private UserService userService;
	  @RequestMapping(value = "/toRegister")
	    public String toRegister(Model model) {
	        // model.addAttribute("crumbs", "联系我们");
	        return "register";
	    }
	    @ApiOperation(value = "用户注册", notes = "用户注册")
	    @ApiImplicitParam(name = "userRequest", value = "手机验证码注册", required = true, dataType = "UserRequest", paramType = "body")
	    @RequestMapping(value = "/register", method = RequestMethod.POST)
	   //@ResponseBody
	    public String registerSave(@Valid @RequestBody UserRequest userRequest) {
	        // model.addAttribute("crumbs", "联系我们");
	    	UserDO userDO=BeanMapper.map(userRequest, UserDO.class);
	    	userDO.setSalt(EncodeUtil.encodeHex(CryptoUtil.generateHmacSha1Key()));
	    	userDO.setPassword(EncodeUtil.encodeHex(
	             CryptoUtil.hmacSha1(userDO.getPassword().getBytes(), userDO.getSalt().getBytes())));
	    	userDO.setCreateTime(new Date());
	    	userDO.setDelFlag(false);
	    	userDO.setRole(Role.SIMPLE_PDD.getCode());
	    	//userDO.setValidEndTime();
	    	userService.save(userDO);
	        return "shopList";
	    }

	

}
