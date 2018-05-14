package com.cheng.helper.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.cheng.helper.domain.Keyword;
import com.cheng.helper.domain.RankRecord;
import com.cheng.helper.service.IKeyWordService;
import com.cheng.helper.service.IRankRecordService;

@Controller
@RequestMapping("/rank")
public class RankRecordController {
    @Autowired
    private IRankRecordService rankRecordService;
    @Autowired
    private IKeyWordService    keyWordService;

    @RequestMapping("/list/{id}")
    public String list(@RequestParam(name = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
                       @RequestParam(name = "pageSize", required = false, defaultValue = "100") Integer pageSize,
                       @PathVariable(required = true) String id, Model model) {

        Keyword keyword = keyWordService.selectById(id);
        if (keyword != null) {
            model.addAttribute("keyword", keyword);
            Page<RankRecord> page = new Page<RankRecord>();
            page.setCurrent(pageNumber);
            page.setSize(pageSize);
            List<String> descs = new ArrayList<String>();
            descs.add(" create_time ");
            page.setDescs(descs);
            Condition condition = new Condition();
            condition.eq("keyword_id", id);
            page = rankRecordService.selectPage(page, condition);
            model.addAttribute("page", page);
        }
        return "rank/list";
    }

}
