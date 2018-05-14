package com.cheng.helper.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springside.modules.utils.misc.IdGenerator;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.Condition;
import com.cheng.helper.domain.Good;
import com.cheng.helper.domain.Keyword;
import com.cheng.helper.domain.RankRecord;
import com.cheng.helper.request.KeyWordRequest;
import com.cheng.helper.service.IGoodService;
import com.cheng.helper.service.IKeyWordService;
import com.cheng.helper.service.IRankRecordService;
import com.cheng.helper.utils.UrlDownLoadUtils;

@Controller
@RequestMapping("/keyword")
public class KeywordController {
    @Autowired
    private IGoodService       goodService;
    @Autowired
    private IKeyWordService    keyWordService;
    @Autowired
    private IRankRecordService rankRecordService;

    @RequestMapping("/list")
    public String list(@RequestParam(name = "goodId", required = true) String goodId, Model model) {

        Good good = goodService.selectById(goodId);
        if (good != null) {
            model.addAttribute("good", good);
            Condition condition = new Condition();
            condition.eq("good_id", good.getId());
            condition.orderBy("create_time", false);
            List<Keyword> keyWordList = keyWordService.selectList(condition);
            model.addAttribute("keyWordList", keyWordList);
        }
        return "keyword/list";
    }

    @RequestMapping("/input")
    public String input(@RequestParam(name = "goodId", required = true) String goodId,
                        Model model) {
        Good good = goodService.selectById(goodId);
        if (good != null) {
            model.addAttribute("good", good);
        }
        return "keyword/input";
    }

    @RequestMapping("/save")
    public String save(@Valid @ModelAttribute("entity") KeyWordRequest keywordRequest,
                       Model model) {

        String[] keyWords = keywordRequest.getName().split(",");
        Keyword keyWord = null;
        for (String str : keyWords) {
            keyWord = new Keyword();
            keyWord.setCreateTime(new Date());
            keyWord.setGoodId(keywordRequest.getGoodId());
            keyWord.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            keyWord.setName(str);
            keyWordService.insert(keyWord);
        }
        return "redirect:/keyword/list?goodId=" + keywordRequest.getGoodId();
    }

    @RequestMapping("/searchBatchRank")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public JSONObject searchBatchRank(String goodId) {
        Good good = goodService.selectById(goodId);
        Condition condition = new Condition();
        condition.eq("good_id", goodId);
        List<Keyword> list = keyWordService.selectList(condition);
        if (!list.isEmpty()) {
            int rank = 0;
            RankRecord rankRecord = null;
            for (Keyword keyword : list) {
                rank = UrlDownLoadUtils.pinddRankSearch(keyword.getName(), good.getPinGoodId());
                if (rank == 200) {
                    keyword.setRanking("200名以外");
                } else {
                    keyword.setRanking(
                        (UrlDownLoadUtils.oneANDserven(rank) ? "竞价" : "自然") + "排名" + rank);

                }
                keyword.setRankingTime(new Date());
                rankRecord = new RankRecord();
                rankRecord.setCreateTime(new Date());
                rankRecord.setKeywordId(keyword.getId());
                rankRecord.setRank(rank);
                rankRecord.setResourceNicheFlag(UrlDownLoadUtils.oneANDserven(rank));
                rankRecord.setId(IdGenerator.uuid2());
                keyWordService.updateById(keyword);
                rankRecordService.insert(rankRecord);
            }

        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", true);
        return jsonObject;
    }
}
