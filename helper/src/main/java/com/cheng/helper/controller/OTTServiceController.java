package com.cheng.helper.controller;

import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.alibaba.fastjson.JSONObject;
import com.cheng.helper.ClientProperties;
import com.cheng.helper.domain.ShopDO;
import com.cheng.helper.domain.ShopQuery;
import com.cheng.helper.service.ShopService;
import com.cheng.helper.utils.OAuthUtil;

@Controller
@RequestMapping(value = "/ottService")
public class OTTServiceController {

    @Autowired
    private ClientProperties clientProperties;
    @Autowired
    private ShopService      shopService;

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @RequestMapping(value = "/callBack")
    public JSONObject code(HttpServletRequest request) {
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
            String code = request.getParameter("code");
            if (StringUtils.isNoneBlank(code)) {
                JSONObject object = OAuthUtil.accessToken(code, clientProperties.getClientId(),
                    clientProperties.getClientSecret());
                System.out.println("JSONObject Token " + object.toJSONString());
                if (object != null && !object.containsKey("error_response")) {
                    ShopQuery shopQuery = new ShopQuery();
                    shopQuery.setOwnerId(object.getString("owner_id"));
                    List<ShopDO> list = shopService.list(shopQuery);
                    if (!list.isEmpty()) {
                        for (ShopDO shopDO : list) {
                            shopDO.setAccessToken(object.getString("access_token"));
                            shopDO.setOwnerName(object.getString("owner_name"));
                            shopDO.setRefreshToken(object.getString("refresh_token"));
                            shopDO.setTokenUpdateTime(new Date());
                            shopService.update(shopDO);
                        }
                    }
                }
            }
        } catch (Exception e) {
            jsonObject.put("success", false);
            jsonObject.put("message", e.getMessage());
        }

        return jsonObject;
    }
}
