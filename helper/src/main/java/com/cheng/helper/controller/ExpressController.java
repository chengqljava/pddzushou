package com.cheng.helper.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springside.modules.utils.mapper.BeanMapper;

import com.alibaba.fastjson.JSONObject;
import com.cheng.helper.config.Context;
import com.cheng.helper.domain.ExpressDO;
import com.cheng.helper.domain.ExpressQuery;
import com.cheng.helper.domain.ShopDO;
import com.cheng.helper.domain.ShopQuery;
import com.cheng.helper.dto.UserDTO;
import com.cheng.helper.enums.ActionEnum;
import com.cheng.helper.enums.ExpressCodeEnum;
import com.cheng.helper.enums.Role;
import com.cheng.helper.request.ExpressRequest;
import com.cheng.helper.service.ExpressService;
import com.cheng.helper.service.ShopService;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/express")
public class ExpressController {

    @Autowired
    private ExpressService expressService;
    @Autowired
    private ShopService    shopService;

    @ApiOperation(value = "根据ID获取", notes = "")
    @RequestMapping(value = "/expresss/{id}", method = RequestMethod.GET)
    public String getExpress(@ApiParam(value = "ID", required = true) @PathVariable String id,
                             Model model) {
        ExpressDO expressDO = expressService.get(id);
        model.addAttribute("entity", expressDO);
        Map<String, String> mapExpressEnums = new HashMap<String, String>();
        for (ExpressCodeEnum expressCodeEnum : ExpressCodeEnum.values()) {
            mapExpressEnums.put(expressCodeEnum.getCode(), expressCodeEnum.getDesc());
        }
        model.addAttribute("mapExpressEnums", mapExpressEnums);
        Map<String, String> mapActionEnums = new HashMap<>();
        for (ActionEnum actionEnum : ActionEnum.values()) {
            mapActionEnums.put(actionEnum.getCode(), actionEnum.getDesc());
        }
        model.addAttribute("actionEnum", mapActionEnums);
        UserDTO userDTO = Context.getUser();
        ShopQuery shopQuery = new ShopQuery();
        if (Role.SIMPLE_PDD.getCode().equals(userDTO.getRole())) {
            shopQuery.setUserId(userDTO.getId());
        }
        List<ShopDO> shopList = shopService.list(shopQuery);
        model.addAttribute("shopList", shopList);

        return "express/detail";
    }

    @ApiOperation(value = "获取列表", notes = "")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listExpress(@RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                              @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                              @RequestParam(name = "shopId", required = false) String shopId,
                              @RequestParam(name = "express", required = false) String express,
                              @RequestParam(name = "startTime", required = false) String startTime,
                              @RequestParam(name = "endTime", required = false) String endTime,
                              @RequestParam(name = "order", required = false) String order,
                              Model model) {
        ExpressQuery query = new ExpressQuery();
        if (pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize <= 0) {
            pageSize = 10;
        }
        query.setPage(pageNum);
        query.setSize(pageSize);
        PageInfo<ExpressDO> page = expressService.page(query);
        model.addAttribute("page", page);
        Map<String, String> mapExpressEnums = new HashMap<String, String>();
        for (ExpressCodeEnum expressCodeEnum : ExpressCodeEnum.values()) {
            mapExpressEnums.put(expressCodeEnum.getCode(), expressCodeEnum.getDesc());
        }
        model.addAttribute("mapExpressEnums", mapExpressEnums);
        Map<String, String> mapActionEnums = new HashMap<>();
        for (ActionEnum actionEnum : ActionEnum.values()) {
            mapActionEnums.put(actionEnum.getCode(), actionEnum.getDesc());
        }
        model.addAttribute("actionEnum", mapActionEnums);
        UserDTO userDTO = Context.getUser();
        ShopQuery shopQuery = new ShopQuery();
        if (Role.SIMPLE_PDD.getCode().equals(userDTO.getRole())) {
            shopQuery.setUserId(userDTO.getId());
        }
        List<ShopDO> shopList = shopService.list(shopQuery);
        model.addAttribute("shopList", shopList);
        return "express/list";
    }

    @ApiOperation(value = "新增编辑", notes = "新增编辑")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(@RequestParam(required = false, defaultValue = "") String id, Model model) {
        if (id != null) {
            ExpressDO expressDO = expressService.get(id);
            if (expressDO != null) {
                model.addAttribute("expressDO", expressDO);
            }
            Map<String, String> mapExpressEnums = new HashMap<String, String>();
            for (ExpressCodeEnum expressCodeEnum : ExpressCodeEnum.values()) {
                mapExpressEnums.put(expressCodeEnum.getCode(), expressCodeEnum.getDesc());
            }
            model.addAttribute("mapExpressEnums", mapExpressEnums);
            Map<String, String> mapActionEnums = new HashMap<>();
            for (ActionEnum actionEnum : ActionEnum.values()) {
                mapActionEnums.put(actionEnum.getCode(), actionEnum.getDesc());
            }
            model.addAttribute("actionEnum", mapActionEnums);

            UserDTO userDTO = Context.getUser();
            ShopQuery shopQuery = new ShopQuery();
            if (Role.SIMPLE_PDD.getCode().equals(userDTO.getRole())) {
                shopQuery.setUserId(userDTO.getId());
            }
            List<ShopDO> shopList = shopService.list(shopQuery);
            model.addAttribute("shopList", shopList);
        }
        return "express/add";
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    public JSONObject saveOrUpdate(@Valid @ModelAttribute("entity") ExpressRequest expressRequest) {
        ExpressDO expressDO = BeanMapper.map(expressRequest, ExpressDO.class);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", true);
        try {
            if (StringUtils.isNoneBlank(expressRequest.getId())) {
                expressService.update(expressDO);
            } else {
                expressService.save(expressDO);
            }
        } catch (Exception e) {
            jsonObject.put("success", false);
            jsonObject.put("message", e.getMessage());
        }

        return jsonObject;
    }

}
