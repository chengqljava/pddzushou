package com.cheng.helper.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springside.modules.utils.mapper.BeanMapper;
import org.springside.modules.utils.security.CryptoUtil;
import org.springside.modules.utils.text.EncodeUtil;

import com.cheng.helper.domain.UserDO;
import com.cheng.helper.dto.UserDTO;
import com.cheng.helper.service.UserService;
import com.cheng.helper.utils.CookieUtils;

/**
 * 登录
 * 
 * @author chengqianliang
 *
 */

@Controller
public class LoginController {
	@Autowired
	private UserService userService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	@RequestMapping(value = "/")
	public String log() {
		return "redirect:/login";
	}
	@RequestMapping(value = "/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping(value = "/login/verify", method = RequestMethod.POST)
	public String login(String username, String password,String remember, Model model) {
		UserDO userDO = userService.findUserName(username);
		if (userDO == null) {
			model.addAttribute("errors", "账户不存在请重试");
			return "login";
		}
		if (!userDO.getPassword()
				.equals(EncodeUtil.encodeHex(CryptoUtil.hmacSha1(password.getBytes(), userDO.getSalt().getBytes())))) {
			model.addAttribute("errors", "密码错误请重试");
			return "login";
		}
//		HttpSession session = request.getSession();
//		session.setMaxInactiveInterval(60 * 60 * 24 * 365);
		UserDTO userDTO = BeanMapper.map(userDO, UserDTO.class);
//		session.setAttribute("userDTO", userDTO);
		
		if(StringUtils.isNoneBlank(remember)){
			CookieUtils.setCookie(response, "userId", userDTO.getId(), 60 * 60 * 24*365);
			CookieUtils.setCookie(response, "remember", remember, 60 * 60 * 24*365);
		}else{
		   CookieUtils.setCookie(response, "userId", userDTO.getId(), 60 * 60 * 24*365);	
		}
		return "redirect:/index";
	}

	@RequestMapping(value = "/index")
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/logout")
	public String logout() {
		CookieUtils.removeCookie(request, response, "userId");
		CookieUtils.removeCookie(request, response, "remember");
		return "login";
	}
}
