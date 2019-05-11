package com.cheng.helper.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cheng.helper.domain.FactoryDO;
import com.cheng.helper.domain.FactoryQuery;


@Mapper
public interface FactoryDao {

	
 List<FactoryDO> list(FactoryQuery factoryQuery);
	 
 FactoryDO get(@Param("id")Long id);
	 
	 int save(FactoryDO factoryDO);
	 
	 int update(FactoryDO factoryDO);
	 
	 int delete(@Param("id")Long id);
}
