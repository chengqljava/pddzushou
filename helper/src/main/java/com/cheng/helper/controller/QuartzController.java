package com.cheng.helper.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springside.modules.utils.mapper.BeanMapper;

import com.alibaba.fastjson.JSONObject;
import com.cheng.common.BaseQuery;
import com.cheng.helper.request.JobSaveRequest;
import com.cheng.qurtz.domain.JobDO;
import com.cheng.qurtz.domain.JobLogDO;
import com.cheng.qurtz.domain.JobLogQuery;
import com.cheng.qurtz.domain.JobQuery;
import com.cheng.qurtz.service.JobLogService;
import com.cheng.qurtz.service.JobService;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 定时任务操作接口
 */
@Controller
@RequestMapping(value = "/quartz")
public class QuartzController {

    @Autowired
    private JobService    jobService;
    @Autowired
    private JobLogService jobLogService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String page(@RequestParam(required = true, defaultValue = "1") int pageNum,
                       @RequestParam(required = true, defaultValue = "10") int pageSize,
                       @RequestParam(required = false, defaultValue = "") String searchBeanName,
                       Model model) {

        JobQuery jobQuery = new JobQuery();
        jobQuery.setPage(pageNum);
        jobQuery.setSize(pageSize);
        jobQuery.setBeanName(searchBeanName);
        jobQuery.addOrder(BaseQuery.Direction.DESC, "create_time");
        PageInfo<JobDO> page = jobService.page(jobQuery);
        model.addAttribute("page", page);
        return "quartz/list";
    }

    @ApiOperation(value = "根据ID查询任务信息", notes = "根据ID查询任务信息")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String findById(@PathVariable String id, Model model) {

        JobDO jobDO = jobService.get(id);
        if (null == jobDO) {
            model.addAttribute("error", "不存在");
        }

        return "quartz/detail";

    }

    @ApiOperation(value = "任务新增", notes = "任务新增")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(@RequestParam(required = false, defaultValue = "") String id, Model model) {
        if (id != null) {
            JobDO jobDO = jobService.get(id);
            if (jobDO != null) {
                model.addAttribute("entity", jobDO);
            }
        }
        return "quartz/add";
    }

    @ApiOperation(value = "新增定时任务", notes = "新增定时任务")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    public JSONObject save(@Valid @ModelAttribute("entity") JobSaveRequest jobSaveRequest,
                           BindingResult bindingResult) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", true);

        try {
            JobDO jobDO = BeanMapper.map(jobSaveRequest, JobDO.class);

            if (StringUtils.isNoneBlank(jobDO.getId())) {
                jobService.update(jobDO);
            } else {
                jobService.save(jobDO);
            }
        } catch (Exception e) {
            jsonObject.put("success", false);
            jsonObject.put("message", e.getMessage());
        }
        return jsonObject;
    }

    @ApiOperation(value = "删除定时任务", notes = "删除定时任务")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable String id) {
        jobService.delete(id);
        return "redirect:/quartz/list";

    }

    @ApiOperation(value = "暂停任务", notes = "暂停任务")
    @RequestMapping(value = "/pause/{id}", method = RequestMethod.GET)
    public String pause(@PathVariable String id) {
        jobService.pause(id);
        return "redirect:/quartz/list";
    }

    @ApiOperation(value = "恢复任务", notes = "恢复任务")
    @RequestMapping(value = "/resume/{id}", method = RequestMethod.GET)
    public String resume(@PathVariable String id) {
        jobService.resume(id);
        return "redirect:/quartz/list";
    }

    @ApiOperation(value = "立即执行任务", notes = "立即执行任务")
    @RequestMapping(value = "/run/{id}", method = RequestMethod.GET)
    public String immediateExecution(@PathVariable String id) {
        jobService.run(id);
        return "redirect:/quartz/list";
    }

    @ApiOperation(value = "定时任务日志分页列表", notes = "定时任务日志分页列表")
    @ApiImplicitParams({ @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "int", paramType = "query", required = true, defaultValue = "1"),
                         @ApiImplicitParam(name = "pageSize", value = "每页显示记录条数", dataType = "int", paramType = "query", required = true, defaultValue = "10"),
                         @ApiImplicitParam(name = "searchBeanName", value = "搜索bean名称", dataType = "String", paramType = "query", required = false),
                         @ApiImplicitParam(name = "searchStartDate", value = "搜索开始时间", dataType = "String", paramType = "query", required = false),
                         @ApiImplicitParam(name = "searchEndDate", value = "搜索结束时间", dataType = "String", paramType = "query", required = false) })
    @RequestMapping(value = "/logs/list", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public String logPage(@RequestParam(required = true, defaultValue = "1") int pageNum,
                          @RequestParam(required = true, defaultValue = "10") int pageSize,
                          @RequestParam(required = false, defaultValue = "") String searchBeanName,
                          @RequestParam(required = false, defaultValue = "") String searchStartDate,
                          @RequestParam(required = false, defaultValue = "") String searchEndDate,
                          Model model) {

        JobLogQuery jobLogQuery = new JobLogQuery();
        jobLogQuery.setPage(pageNum);
        jobLogQuery.setSize(pageSize);
        jobLogQuery.setSearchBeanName(searchBeanName);
        jobLogQuery.addOrder(BaseQuery.Direction.DESC, "create_time");
        if (StringUtils.isNotEmpty(searchStartDate) && StringUtils.isNotEmpty(searchEndDate)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            try {
                jobLogQuery.setSearchStartDate(format.parse(searchStartDate.replace("Z", " UTC")));
                jobLogQuery.setSearchEndDate(format.parse(searchEndDate.replace("Z", " UTC")));
            } catch (ParseException e) {
            }
        }
        PageInfo<JobLogDO> page = jobLogService.page(jobLogQuery);
        model.addAttribute("page", page);
        return "quartz/logsList";
    }

    @ApiOperation(value = "查看任务日志详细信息", notes = "查看任务日志详细信息")
    @ApiImplicitParam(name = "id", value = "任务日志ID", dataType = "String", paramType = "path", required = true)
    @RequestMapping(value = "/job/logs/{id}", method = RequestMethod.GET)
    public String logInfo(@PathVariable String id, Model model) {
        JobLogDO jobLogDO = jobLogService.get(id);
        model.addAttribute("jobLogDO", jobLogDO);
        return "quartz/logDetail";

    }

}
