package com.cheng.helper.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cheng.helper.domain.GoodsDO;
import com.cheng.helper.domain.GoodsQuery;
import com.cheng.helper.repository.GoodsDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


@Service("goodsService")
@Transactional(readOnly = true)
public class GoodsService{

	@Autowired
	private GoodsDao goodsDao;
	
	public GoodsDO get(String id){
		return goodsDao.get(id);
	}
	
	public List<GoodsDO> list(GoodsQuery goodsQuery){
		return goodsDao.list(goodsQuery);
	}
	
	public PageInfo<GoodsDO> page(GoodsQuery goodsQuery){
		PageHelper.startPage(goodsQuery.getPage(), goodsQuery.getSize());
        List<GoodsDO> list = goodsDao.list(goodsQuery);
        return new PageInfo<GoodsDO>(list);
	}
	
	@Transactional
	public void save(GoodsDO goods){
		goodsDao.save(goods);
	}
	
	@Transactional
	public boolean update(GoodsDO goods){
		return goodsDao.update(goods) > 0 ? true : false;
	}
	
	@Transactional
	public void delete(String id){
		goodsDao.delete(id);
	}
	
}
