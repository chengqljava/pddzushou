package com.cheng.helper.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GoodMessage {
	private String goodIds;
	private Map<String,GoodsIdOuterIdSpec>  map;
	private List<GoodsIdOuterIdSpec> list;
	private int goodAmount;
	public GoodMessage() {
		super();
		map=new HashMap<String,GoodsIdOuterIdSpec>();
		list=new ArrayList<GoodsIdOuterIdSpec>();
	}
	
	public GoodMessage(String goodIds) {
		super();
		this.goodIds = goodIds;
		map=new HashMap<String,GoodsIdOuterIdSpec>();
		list=new ArrayList<GoodsIdOuterIdSpec>();
	}
	public void parse(String outer_id,String goods_spec,int goods_count,String  goods_img){
		GoodsIdOuterIdSpec goodsIdOuterIdSpec=null;
		if(map.containsKey(outer_id+"_"+goods_spec)){
			goodsIdOuterIdSpec=map.get(outer_id+"_"+goods_spec);
		}else{
			goodsIdOuterIdSpec=new GoodsIdOuterIdSpec();
			goodsIdOuterIdSpec.setGoodsId(goodIds);
			goodsIdOuterIdSpec.setGoodsImg(goods_img);
			goodsIdOuterIdSpec.setGoodsSpec(goods_spec);
			goodsIdOuterIdSpec.setOuterId(outer_id);
			map.put(outer_id+"_"+goods_spec, goodsIdOuterIdSpec);
			list.add(goodsIdOuterIdSpec);
		}
		goodsIdOuterIdSpec.setGoodsCount(goodsIdOuterIdSpec.getGoodsCount()+goods_count);
		goodAmount=goodsIdOuterIdSpec.getGoodsCount();
	}

	public String getGoodIds() {
		return goodIds;
	}
	public void setGoodIds(String goodIds) {
		this.goodIds = goodIds;
	}

	public Map<String, GoodsIdOuterIdSpec> getMap() {
		return map;
	}

	public void setMap(Map<String, GoodsIdOuterIdSpec> map) {
		this.map = map;
	}

	public List<GoodsIdOuterIdSpec> getList() {
		return list;
	}

	public void setList(List<GoodsIdOuterIdSpec> list) {
		this.list = list;
	}

	public int getGoodAmount() {
		return goodAmount;
	}

	public void setGoodAmount(int goodAmount) {
		this.goodAmount = goodAmount;
	}
	
	

}
