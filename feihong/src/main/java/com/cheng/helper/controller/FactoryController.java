package com.cheng.helper.controller;


import java.util.List;

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
import com.cheng.helper.domain.FactoryDO;
import com.cheng.helper.domain.FactoryQuery;
import com.cheng.helper.domain.WorkShopDO;
import com.cheng.helper.domain.WorkShopQuery;
import com.cheng.helper.request.FactoryRequest;
import com.cheng.helper.service.FactoryService;
import com.cheng.helper.service.WorkShopService;

import io.swagger.annotations.ApiOperation;

/**
 * @author chengqianliang
 * 工厂
 */
@Controller
@RequestMapping(value = { "/factory" })
public class FactoryController {
    @Autowired
    private FactoryService factoryService;
    @Autowired
    private WorkShopService  workShopService;

    @ApiOperation(value = "车间列表", notes = "车间列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(@RequestParam(required = true, defaultValue = "1") int pageNum,
                       @RequestParam(required = true, defaultValue = "10") int pageSize,
                       Model model) {
        FactoryQuery shopQuery = new FactoryQuery();

        shopQuery.setCurrent(pageNum);
        shopQuery.setSize(pageSize);
        Page<FactoryDO> page = null;
        try {
            page = factoryService.page(shopQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("page", page);
        return "factory/list";
    }

    @ApiOperation(value = "车间新增", notes = "车间新增")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(@RequestParam(required = false, defaultValue = "") Long id, Model model) {
        
    	List<WorkShopDO> list=workShopService.list(new WorkShopQuery());
    	model.addAttribute("wrorkShopList", list);
    	if (id != null) {
            FactoryDO factoryDO = factoryService.get(id);
            if (factoryDO != null) {
                model.addAttribute("factory", factoryDO);
            }
        }
        return "factory/add";
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    public JSONObject saveOrUpdate(@Valid @ModelAttribute("entity") FactoryRequest factoryRequest) {
        FactoryDO factoryDO = BeanMapper.map(factoryRequest, FactoryDO.class);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", true);
        try {
            if (factoryRequest.getId()!=null) {
            	factoryService.update(factoryDO);
            } else {
            	factoryService.save(factoryDO);
            }
        } catch (Exception e) {
            jsonObject.put("success", false);
            jsonObject.put("message", e.getMessage());
        }

        return jsonObject;
    }

}
