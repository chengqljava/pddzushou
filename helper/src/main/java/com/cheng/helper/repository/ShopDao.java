package com.cheng.helper.repository;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cheng.helper.domain.ShopDO;
import com.cheng.helper.domain.ShopQuery;


@Mapper
public interface ShopDao {
	
	void save(ShopDO shop);
	
	int update(ShopDO shop);
	
	int delete(String id);
	
	ShopDO get(String id);
	
	List<ShopDO> list(ShopQuery shopQuery);
}
