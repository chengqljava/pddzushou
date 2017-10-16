package com.cheng.helper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author chengqianliang
 *注册
 */

@Controller
@RequestMapping(value = { "", "/" })
public class RegisterController {
	 @RequestMapping(value = "/toRegister")
	    public String toRegister(Model model) {
	        // model.addAttribute("crumbs", "联系我们");
	        return "";
	    }

	

}
