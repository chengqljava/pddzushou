package com.cheng.helper.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springside.modules.utils.mapper.BeanMapper;
import org.springside.modules.utils.security.CryptoUtil;
import org.springside.modules.utils.text.EncodeUtil;

import com.cheng.helper.domain.UserDO;
import com.cheng.helper.dto.UserDTO;
import com.cheng.helper.service.UserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import  com.cheng.helper.request.LoginRequest;

/**登录
 * @author chengqianliang
 *
 */

@Controller
public class LoginController {
	@Autowired
	private UserService userService;
	 @Autowired
	 private HttpServletRequest       request;
	@RequestMapping(value = "/login")
    public String login() {
        // model.addAttribute("crumbs", "联系我们");
       //		  memberDO.setPassword(EncodeUtil.encodeHex(CryptoUtil
      //		            .hmacSha1(request.getNewPassword().getBytes(), memberDO.getSalt().getBytes())));
        return "login";
    }
    @ApiOperation(value = "登录", notes = "登录")
    @ApiImplicitParam(name = "userRequest", value = "登录", required = true, dataType = "UserRequest", paramType = "body")
    @RequestMapping(value = "/login/verify", method = RequestMethod.POST)
    public String login(@Valid @RequestBody LoginRequest loginRequest) {
        // model.addAttribute("crumbs", "联系我们");
    	UserDO userDO=userService.findUserName(loginRequest.getUserName());
    	HttpSession session = request.getSession();
    	session.setMaxInactiveInterval(30*60);
    	UserDTO userDTO=BeanMapper.map(userDO, UserDTO.class);
    	session.setAttribute("userDTO",userDTO);
    	return "";
    }

	@RequestMapping(value = "/")
    public String index() {
        // model.addAttribute("crumbs", "联系我们");
//		  memberDO.setPassword(EncodeUtil.encodeHex(CryptoUtil
//		            .hmacSha1(request.getNewPassword().getBytes(), memberDO.getSalt().getBytes())));
        return "index";
    }
	@RequestMapping(value = "/")
	public String logout(){
		  HttpSession session = request.getSession(false);
	        if (session != null) {
	            session.removeAttribute("userDTO");
	            session.invalidate();
	        }
		 return "index";
	}
}
