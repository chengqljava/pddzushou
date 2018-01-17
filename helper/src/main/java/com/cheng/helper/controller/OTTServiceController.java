package com.cheng.helper.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping(value = "/ottService")
public class OTTServiceController {

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @RequestMapping(value = "/callBack")
    public JSONObject code(HttpServletRequest request) {
        // model.addAttribute("crumbs", "联系我们");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", true);
        try {
            Enumeration<String> dnumerations = request.getParameterNames();
            String key = null;
            while (dnumerations.hasMoreElements()) {
                key = dnumerations.nextElement();
                System.out.println(
                    "key" + dnumerations.nextElement() + "value" + request.getParameter(key));
            }
        } catch (Exception e) {
            jsonObject.put("success", false);
            jsonObject.put("message", e.getMessage());
        }

        return jsonObject;
    }
}
