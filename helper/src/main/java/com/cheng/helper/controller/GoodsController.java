package com.cheng.helper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cheng.helper.common.BaseQuery.Direction;
import com.cheng.helper.domain.GoodsDO;
import com.cheng.helper.domain.GoodsQuery;
import com.cheng.helper.service.GoodsService;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.ApiOperation;

/**
 * 商品
 * 
 * @author chengqianliang
 *
 */
@Controller
@RequestMapping(value = { "/goods" })
public class GoodsController {
	@Autowired
	private GoodsService goodsService;

	@ApiOperation(value = "商品列表", notes = "商品列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(@RequestParam(required = true, defaultValue = "1") int pageNum,
			@RequestParam(required = true, defaultValue = "10") int pageSize,
			@RequestParam(required = false, defaultValue = "") Integer amount,
			@RequestParam(required = false, defaultValue = "") String start,
			@RequestParam(required = false, defaultValue = "") String end, Model model) {
		GoodsQuery goodsQuery = new GoodsQuery();
		goodsQuery.setPage(pageNum);
		goodsQuery.setSize(pageSize);
		goodsQuery.setAmount(amount);
		goodsQuery.setStart(start);
		goodsQuery.setEnd(end);
		goodsQuery.addOrder(Direction.DESC, "create_time");
		goodsQuery.addOrder(Direction.DESC, "amount");
		PageInfo<GoodsDO> goodsPage = goodsService.page(goodsQuery);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("amount", amount);
		model.addAttribute("start", start);
		model.addAttribute("end", end);
		model.addAttribute("page", goodsPage);
		return "goods/list";
	}

}
