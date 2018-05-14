package com.cheng.helper.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.cheng.helper.domain.Good;
import com.cheng.helper.request.GoodRequest;
import com.cheng.helper.service.IGoodService;
import com.cheng.helper.utils.UrlDownLoadUtils;

@Controller
@RequestMapping("/good")
public class GoodController {
    @Autowired
    private IGoodService goodService;

    @RequestMapping("/list")
    public String list(@RequestParam(name = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
                       @RequestParam(name = "pageSize", required = false, defaultValue = "15") Integer pageSize,
                       Model model) {

        Page<Good> page = new Page<Good>();
        page.setCurrent(pageNumber);
        page.setSize(pageSize);
        List<String> descs = new ArrayList<String>();
        descs.add(" create_time ");
        page.setDescs(descs);

        page = goodService.selectPage(page);
        model.addAttribute("page", page);

        System.out.println("total" + page.getTotal());

        return "good/list";
    }

    @RequestMapping("/input")
    public String input(Model model) {

        // model.setViewName("add");
        return "good/input";
    }

    @RequestMapping("/save")
    public String save(@Valid @ModelAttribute("entity") GoodRequest goodRequest, Model model) {
        JSONObject jsonObject = UrlDownLoadUtils.pinddDetail(goodRequest.getGoodId());
        if (jsonObject != null) {
            Good good = new Good();
            good.setCreateTime(new Date());
            good.setPinGoodId(goodRequest.getGoodId());
            good.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            good.setImgUrl(jsonObject.getString("thumbUrl"));
            good.setMallId(jsonObject.getString("mallID"));
            good.setName(jsonObject.getString("goodsName"));
            goodService.insert(good);

        }
        return "redirect:/good/list";
    }
}
