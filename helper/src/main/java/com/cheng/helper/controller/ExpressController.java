package com.cheng.helper.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.cheng.common.BaseQuery.Direction;
import com.cheng.common.BaseQuery.Order;
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
        Map<String, String> mapExpressEnums = new LinkedHashMap<String, String>();
        for (ExpressCodeEnum expressCodeEnum : ExpressCodeEnum.values()) {
            mapExpressEnums.put(expressCodeEnum.getCode(), expressCodeEnum.getDesc());
        }
        model.addAttribute("mapExpressEnums", mapExpressEnums);
        Map<Integer, String> mapActionEnums = new LinkedHashMap<>();
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
                              @RequestParam(name = "searchStartTime", required = false) String searchStartTime,
                              @RequestParam(name = "searchEndTime", required = false) String searchEndTime,
                              @RequestParam(name = "orderCode", required = false) String orderCode,
                              @RequestParam(name = "action", required = false) Integer action,
                              Model model) {
        model.addAttribute("shopId", shopId);
        model.addAttribute("express", express);
        model.addAttribute("searchStartTime", searchStartTime);
        model.addAttribute("searchEndTime", searchEndTime);
        model.addAttribute("orderCode", orderCode);
        model.addAttribute("action", action);
        ExpressQuery query = new ExpressQuery();
        if (pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize <= 0) {
            pageSize = 10;
        }
        query.setPage(pageNum);
        query.setSize(pageSize);
        query.setAction(action);
        query.setExpress(express);
        query.setOrderCode(orderCode);
        query.setSearchEndTime(searchEndTime);
        query.setSearchStartTime(searchStartTime);
        query.setShopId(shopId);
        List<Order> orders = new ArrayList<>();
        Order order = new Order(Direction.DESC, "create_time");
        orders.add(order);
        query.setOrders(orders);
        UserDTO userDTO = Context.getUser();
        if (Role.SIMPLE_PDD.getCode().equals(userDTO.getRole())) {
            query.setUserId(userDTO.getId());
        }
        PageInfo<ExpressDO> page = expressService.page(query);
        model.addAttribute("page", page);
        Map<String, String> mapExpressEnums = new LinkedHashMap<String, String>();
        for (ExpressCodeEnum expressCodeEnum : ExpressCodeEnum.values()) {
            mapExpressEnums.put(expressCodeEnum.getCode(), expressCodeEnum.getDesc());
        }
        model.addAttribute("mapExpressEnums", mapExpressEnums);
        Map<Integer, String> mapActionEnums = new LinkedHashMap<>();
        for (ActionEnum actionEnum : ActionEnum.values()) {
            mapActionEnums.put(actionEnum.getCode(), actionEnum.getDesc());
        }
        model.addAttribute("mapActionEnums", mapActionEnums);

        ShopQuery shopQuery = new ShopQuery();
        if (Role.SIMPLE_PDD.getCode().equals(userDTO.getRole())) {
            shopQuery.setUserId(userDTO.getId());
        }
        List<ShopDO> shopList = shopService.list(shopQuery);
        model.addAttribute("shopList", shopList);
        Map<String, String> mapShop = new HashMap<>();
        if (!shopList.isEmpty()) {
            for (ShopDO shop : shopList) {
                mapShop.put(shop.getId(), shop.getName());
            }
            model.addAttribute("mapShop", mapShop);
        }
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
            Map<String, String> mapExpressEnums = new LinkedHashMap<String, String>();
            for (ExpressCodeEnum expressCodeEnum : ExpressCodeEnum.values()) {
                mapExpressEnums.put(expressCodeEnum.getCode(), expressCodeEnum.getDesc());
            }
            model.addAttribute("mapExpressEnums", mapExpressEnums);
            Map<Integer, String> mapActionEnums = new LinkedHashMap<>();
            for (ActionEnum actionEnum : ActionEnum.values()) {
                mapActionEnums.put(actionEnum.getCode(), actionEnum.getDesc());
            }
            model.addAttribute("mapActionEnums", mapActionEnums);

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

    @ApiOperation(value = "新增编辑", notes = "新增编辑")
    @RequestMapping(value = "/back", method = RequestMethod.GET)
    public String back(@RequestParam(required = false, defaultValue = "") String id, Model model) {
        ExpressDO expressDO = expressService.get(id);
        if (expressDO != null) {
            model.addAttribute("expressDO", expressDO);
        }
        Map<String, String> mapExpressEnums = new LinkedHashMap<String, String>();
        for (ExpressCodeEnum expressCodeEnum : ExpressCodeEnum.values()) {
            mapExpressEnums.put(expressCodeEnum.getCode(), expressCodeEnum.getDesc());
        }
        model.addAttribute("mapExpressEnums", mapExpressEnums);
        Map<Integer, String> mapActionEnums = new LinkedHashMap<>();
        for (ActionEnum actionEnum : ActionEnum.values()) {
            mapActionEnums.put(actionEnum.getCode(), actionEnum.getDesc());
        }
        model.addAttribute("mapActionEnums", mapActionEnums);

        UserDTO userDTO = Context.getUser();
        ShopQuery shopQuery = new ShopQuery();
        if (Role.SIMPLE_PDD.getCode().equals(userDTO.getRole())) {
            shopQuery.setUserId(userDTO.getId());
        }
        List<ShopDO> shopList = shopService.list(shopQuery);
        model.addAttribute("shopList", shopList);
        return "express/edit";
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    public JSONObject saveOrUpdate(@Valid @ModelAttribute("entity") ExpressRequest expressRequest) {
        ExpressDO expressDO = BeanMapper.map(expressRequest, ExpressDO.class);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", true);
        expressDO.setUserId(Context.getUser().getId());
        try {
            if (StringUtils.isNoneBlank(expressRequest.getId())) {
                if (StringUtils.isNoneBlank(expressDO.getBackExpress())
                    && StringUtils.isNoneBlank(expressDO.getBackOrder())
                    && expressDO.getBackTime() == null) {
                    expressDO.setBackTime(new Date());
                }
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
