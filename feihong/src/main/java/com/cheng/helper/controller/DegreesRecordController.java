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
import com.cheng.helper.domain.DegreesRecordBO;
import com.cheng.helper.domain.DegreesRecordDO;
import com.cheng.helper.domain.DegreesRecordQuery;
import com.cheng.helper.domain.DegreesRecordStatisticBO;
import com.cheng.helper.domain.FactoryDO;
import com.cheng.helper.domain.FactoryQuery;
import com.cheng.helper.domain.MeterDO;
import com.cheng.helper.domain.MeterQuery;
import com.cheng.helper.request.DegreesRecordRequest;
import com.cheng.helper.service.DegreesRecordService;
import com.cheng.helper.service.FactoryService;
import com.cheng.helper.service.MeterService;

import io.swagger.annotations.ApiOperation;

/**
 * @author chengqianliang
 * 度数记录
 */
@Controller
@RequestMapping(value = { "/degreesRecord" })
public class DegreesRecordController {
    @Autowired
    private DegreesRecordService degreesRecordService;
    @Autowired
    private FactoryService factoryService;
    @Autowired
    private MeterService meterService;

    @ApiOperation(value = "度数列表", notes = "度数列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(@RequestParam(required = true, defaultValue = "1") int pageNum,
                       @RequestParam(required = true, defaultValue = "10") int pageSize,
                       @RequestParam(required = false, defaultValue = "") Integer factoryId,
                       @RequestParam(required = false, defaultValue = "") Integer meterId,
                       @RequestParam(required = false, defaultValue = "") String searchStartTime,
                       @RequestParam(required = false, defaultValue = "") String searchEndTime,
                       Model model) {
    	DegreesRecordQuery degreesRecordQuery = new DegreesRecordQuery();

    	degreesRecordQuery.setCurrent(pageNum);
    	degreesRecordQuery.setSize(pageSize);
    	degreesRecordQuery.setFactoryId(factoryId);
    	degreesRecordQuery.setMeterId(meterId);
    	degreesRecordQuery.setSearchEndTime(searchEndTime);
    	degreesRecordQuery.setSearchStartTime(searchStartTime);
        Page<DegreesRecordBO> page = null;
        try {
            page = degreesRecordService.page(degreesRecordQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<FactoryDO> list=factoryService.list(new FactoryQuery());
        List<MeterDO> meters=null;
        model.addAttribute("page", page);
        model.addAttribute("factoryId", factoryId);
        model.addAttribute("meterId", meterId);
        model.addAttribute("searchStartTime", searchStartTime);
        model.addAttribute("searchEndTime", searchEndTime);
        model.addAttribute("factorys", list);
        model.addAttribute("meters", meters);
        if(meterId!=null) {
        	MeterQuery meterQuery=new MeterQuery();
        	meterQuery.setFactoryId(factoryId);
        	meters=meterService.list(meterQuery);
        }
        return "degreesRecord/list";
    }

    @ApiOperation(value = "度数新增", notes = "度数新增")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(@RequestParam(required = false, defaultValue = "") Long id, Model model) {
        if (id != null) {
            DegreesRecordDO degreesRecordDO = degreesRecordService.get(id);
            if (degreesRecordDO != null) {
                model.addAttribute("degreesRecord", degreesRecordDO);
            }
        }
        return "degreesRecord/add";
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    public JSONObject saveOrUpdate(@Valid @ModelAttribute("entity") DegreesRecordRequest degreesRecordRequest) {
    	DegreesRecordDO factoryDO = BeanMapper.map(degreesRecordRequest, DegreesRecordDO.class);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", true);
        try {
            if (degreesRecordRequest.getId()!=null) {
            	degreesRecordService.update(factoryDO);
            } else {
            	degreesRecordService.save(factoryDO);
            }
        } catch (Exception e) {
            jsonObject.put("success", false);
            jsonObject.put("message", e.getMessage());
        }

        return jsonObject;
    }
    @ApiOperation(value = "度数列表", notes = "度数列表")
    @RequestMapping(value = "/listStatistics", method = RequestMethod.GET)
    public String listStatistics(@RequestParam(required = true, defaultValue = "1") int pageNum,
                       @RequestParam(required = true, defaultValue = "10") int pageSize,
                       @RequestParam(required = false, defaultValue = "") Integer factoryId,
                       @RequestParam(required = false, defaultValue = "") Integer meterId,
                       @RequestParam(required = false, defaultValue = "") String searchStartTime,
                       @RequestParam(required = false, defaultValue = "") String searchEndTime,
                       @RequestParam(required = false, defaultValue = "")  Integer type,
                       Model model) {
    	DegreesRecordQuery degreesRecordQuery = new DegreesRecordQuery();
    	if(type==null) {
    		degreesRecordQuery.setType(1);
    		model.addAttribute("type", 1);
    	}

    	degreesRecordQuery.setCurrent(pageNum);
    	degreesRecordQuery.setSize(pageSize);
    	degreesRecordQuery.setFactoryId(factoryId);
    	degreesRecordQuery.setMeterId(meterId);
    	degreesRecordQuery.setSearchEndTime(searchEndTime);
    	degreesRecordQuery.setSearchStartTime(searchStartTime);
        Page<DegreesRecordStatisticBO> page = null;
        try {
            page = degreesRecordService.pageStatistics(degreesRecordQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<FactoryDO> list=factoryService.list(new FactoryQuery());
        List<MeterDO> meters=null;
        model.addAttribute("page", page);
        model.addAttribute("factoryId", factoryId);
        model.addAttribute("meterId", meterId);
        model.addAttribute("searchStartTime", searchStartTime);
        model.addAttribute("searchEndTime", searchEndTime);
        model.addAttribute("factorys", list);
        model.addAttribute("meters", meters);
        if(meterId!=null) {
        	MeterQuery meterQuery=new MeterQuery();
        	meterQuery.setFactoryId(factoryId);
        	meters=meterService.list(meterQuery);
        }
        return "degreesRecord/listStatistics";
    }

}
