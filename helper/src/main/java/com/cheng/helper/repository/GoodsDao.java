package com.cheng.helper.repository;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cheng.helper.domain.GoodsDO;
import com.cheng.helper.domain.GoodsQuery;



@Mapper
public interface GoodsDao {
	
	void save(GoodsDO goods);
	
	int update(GoodsDO goods);
	
	int delete(String id);
	
	GoodsDO get(String id);
	
	List<GoodsDO> list(GoodsQuery goodsQuery);
	
	void saveBatch(@Param("goods")List<GoodsDO> goods);
}
