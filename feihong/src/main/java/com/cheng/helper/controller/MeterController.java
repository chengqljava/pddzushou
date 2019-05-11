package com.cheng.helper.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.cheng.helper.domain.MeterDO;
import com.cheng.helper.domain.MeterQuery;
import com.cheng.helper.request.MeterRequest;
import com.cheng.helper.service.FactoryService;
import com.cheng.helper.service.MeterService;

import io.swagger.annotations.ApiOperation;

/**
 * @author chengqianliang
 *电表名称
 */
@Controller
@RequestMapping(value = { "/meter" })
public class MeterController {
    @Autowired
    private MeterService meterService;
    @Autowired
    private FactoryService  factoryService;

    @ApiOperation(value = "电表列表", notes = "电表列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(@RequestParam(required = true, defaultValue = "1") int pageNum,
                       @RequestParam(required = true, defaultValue = "10") int pageSize,
                       Model model) {
        MeterQuery meterQuery = new MeterQuery();
        meterQuery.setCurrent(pageNum);
        meterQuery.setSize(pageSize);
        Page<MeterDO> page = null;
        try {
            page = meterService.page(meterQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("page", page);
        Map<Integer,String> factoryMap=new HashMap<Integer, String>();
        List<FactoryDO> list=factoryService.list(new FactoryQuery());
        if(null!=list&&list.size()>0) {
        	for(FactoryDO factoryDO:list) {
        		factoryMap.put(factoryDO.getId(), factoryDO.getName());
        	}
        	model.addAttribute("factoryMap", factoryMap);
        }
        return "meter/list";
    }

    @ApiOperation(value = "电表新增", notes = "电表新增")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(@RequestParam(required = false, defaultValue = "") Long id, Model model) {
    	List<FactoryDO> list=factoryService.list(new FactoryQuery());
    	model.addAttribute("factoryList", list);
    	
    	if (id != null) {
        	MeterDO meterDO = meterService.get(id);
            if (meterDO != null) {
                model.addAttribute("meter", meterDO);
            }
        }
        return "meter/add";
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    public JSONObject saveOrUpdate(@Valid @ModelAttribute("entity") MeterRequest meterRequest) {
        MeterDO meterDO = BeanMapper.map(meterRequest, MeterDO.class);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", true);
        try {
            if (meterRequest.getId()!=null) {
            	meterService.update(meterDO);
            } else {
            	meterService.save(meterDO);
            }
        } catch (Exception e) {
            jsonObject.put("success", false);
            jsonObject.put("message", e.getMessage());
        }

        return jsonObject;
    }
    
    @ApiOperation(value = "电表列表", notes = "电表列表")
    @RequestMapping(value = "/list/ajax", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> listAjax(Integer factoryId) {
    	MeterQuery meterQuery=new MeterQuery();
    	meterQuery.setFactoryId(factoryId);
    	meterQuery.setSize(100);
    	List<MeterDO> meters=	meterService.list(meterQuery);
    	Map<String,Object> map=new HashMap<String, Object>();
    	map.put("success", true);
    	map.put("data", meters);
        return map;
    }

}
