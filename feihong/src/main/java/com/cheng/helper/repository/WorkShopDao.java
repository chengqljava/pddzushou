package com.cheng.helper.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cheng.helper.domain.WorkShopDO;
import com.cheng.helper.domain.WorkShopQuery;


@Mapper
public interface WorkShopDao{

	 List<WorkShopDO> list(WorkShopQuery workShopQuery);
	 
	 WorkShopDO get(@Param("id")Long id);
	 
	 int save(WorkShopDO workShopDO);
	 
	 int update(WorkShopDO workShopDO);
	 
	 int delete(@Param("id")Long id);
}
