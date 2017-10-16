package com.cheng.helper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**登录
 * @author chengqianliang
 *
 */

@Controller
public class LoginController {
	@RequestMapping(value = "/")
    public String index() {
        // model.addAttribute("crumbs", "联系我们");
//		  memberDO.setPassword(EncodeUtil.encodeHex(CryptoUtil
//		            .hmacSha1(request.getNewPassword().getBytes(), memberDO.getSalt().getBytes())));
        return "login";
    }
}
