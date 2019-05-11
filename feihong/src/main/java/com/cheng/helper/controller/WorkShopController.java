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
import com.cheng.helper.domain.WorkShopDO;
import com.cheng.helper.domain.WorkShopQuery;
import com.cheng.helper.request.WorkShopRequest;
import com.cheng.helper.service.WorkShopService;

import io.swagger.annotations.ApiOperation;

/**
 * @author chengqianliang
 *工厂区域
 */
@Controller
@RequestMapping(value = { "/workShop" })
public class WorkShopController {
    @Autowired
    private WorkShopService workShopService;

    @ApiOperation(value = "工厂列表", notes = "工厂列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(@RequestParam(required = true, defaultValue = "1") int pageNum,
                       @RequestParam(required = true, defaultValue = "10") int pageSize,
                       Model model) {
        WorkShopQuery shopQuery = new WorkShopQuery();

        shopQuery.setCurrent(pageNum);
        shopQuery.setSize(pageSize);
        Page<WorkShopDO> page = null;
        try {
            page = workShopService.page(shopQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("page", page);
        return "workShop/list";
    }

    @ApiOperation(value = "工厂新增", notes = "工厂新增")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(@RequestParam(required = false, defaultValue = "") Long id, Model model) {
        if (id != null) {
            WorkShopDO workShopDO = workShopService.get(id);
            if (workShopDO != null) {
                model.addAttribute("workShop", workShopDO);
            }
        }
        return "workShop/add";
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    public JSONObject saveOrUpdate(@Valid @ModelAttribute("entity") WorkShopRequest workShopRequest) {
        WorkShopDO workShopDO = BeanMapper.map(workShopRequest, WorkShopDO.class);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", true);
        try {
            if (workShopRequest.getId()!=null) {
                workShopService.update(workShopDO);
            } else {
            	workShopService.save(workShopDO);
            }
        } catch (Exception e) {
            jsonObject.put("success", false);
            jsonObject.put("message", e.getMessage());
        }

        return jsonObject;
    }

}
