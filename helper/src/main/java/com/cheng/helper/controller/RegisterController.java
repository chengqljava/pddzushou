package com.cheng.helper.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springside.modules.utils.mapper.BeanMapper;
import org.springside.modules.utils.security.CryptoUtil;
import org.springside.modules.utils.text.EncodeUtil;
import org.springframework.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import com.cheng.helper.domain.UserDO;
import com.cheng.helper.dto.UserDTO;
import com.cheng.helper.enums.Role;
import com.cheng.helper.request.UserRequest;

import com.cheng.helper.service.UserService;

/**
 * @author chengqianliang 注册
 */

@Controller
@RequestMapping(value = "/register")
public class RegisterController {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "")
	public String toRegister(Model model) {
		// model.addAttribute("crumbs", "联系我们");
		return "register";
	}

	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public JSONObject registerSave(@Valid @ModelAttribute("entity") UserRequest userRequest) {
		// model.addAttribute("crumbs", "联系我们");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", true);
		try {
			if (userService.findUserName(userRequest.getUserName()) != null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "用户名已存在请修改");
				return jsonObject;
			}
			UserDO userDO = BeanMapper.map(userRequest, UserDO.class);
			userDO.setSalt(EncodeUtil.encodeHex(CryptoUtil.generateHmacSha1Key()));
			userDO.setPassword(EncodeUtil
					.encodeHex(CryptoUtil.hmacSha1(userDO.getPassword().getBytes(), userDO.getSalt().getBytes())));
			userDO.setRole(Role.SIMPLE_PDD.getCode());
			// userDO.setValidEndTime();
			userService.save(userDO);
			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(60 * 60 * 24 * 365);
			UserDTO userDTO = BeanMapper.map(userDO, UserDTO.class);
			session.setAttribute("userDTO", userDTO);

		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e.getMessage());
		}

		return jsonObject;
	}

}
