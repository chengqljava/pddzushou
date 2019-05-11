package com.cheng.helper.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springside.modules.utils.mapper.BeanMapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.cheng.helper.domain.PriceDO;
import com.cheng.helper.domain.PriceQuery;
import com.cheng.helper.request.PriceRequest;
import com.cheng.helper.service.PriceService;

import io.swagger.annotations.ApiOperation;

/**
 * @author chengqianliang
 *电价格
 */
@Controller
@RequestMapping(value = { "/price" })
public class PriceController {
    @Autowired
    private PriceService priceService;

    @ApiOperation(value = "电价格列表", notes = "电价格列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(@RequestParam(required = true, defaultValue = "1") int pageNum,
                       @RequestParam(required = true, defaultValue = "10") int pageSize,
                       Model model) {
        PriceQuery priceQuery = new PriceQuery();

        priceQuery.setCurrent(pageNum);
        priceQuery.setSize(pageSize);
        Page<PriceDO> page = null;
        try {
            page = priceService.page(priceQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("page", page);
        return "price/list";
    }

    @ApiOperation(value = "电价格新增", notes = "电价格新增")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(@RequestParam(required = false, defaultValue = "") Long id, Model model) {
        if (id != null) {
        	PriceDO priceDO = priceService.get(id);
            if (priceDO != null) {
                model.addAttribute("price", priceDO);
            }
        }
        return "price/add";
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    public JSONObject saveOrUpdate(@Valid @ModelAttribute("entity") PriceRequest priceRequest) {
        PriceDO priceDO = BeanMapper.map(priceRequest, PriceDO.class);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", true);
        try {
            if (priceRequest.getId()!=null) {
            	priceService.update(priceDO);
            } else {
            	priceService.save(priceDO);
            }
        } catch (Exception e) {
            jsonObject.put("success", false);
            jsonObject.put("message", e.getMessage());
        }

        return jsonObject;
    }

}
