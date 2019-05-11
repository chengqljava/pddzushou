package com.cheng.helper.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cheng.helper.domain.MeterDO;
import com.cheng.helper.domain.MeterQuery;

@Mapper
public interface MeterDao {
	     List<MeterDO> list(MeterQuery meterQuery);
	 
	     MeterDO get(@Param("id")Long id);
		 
		 int save(MeterDO meterDO);
		 
		 int update(MeterDO meterDO);
		 
		 int delete(@Param("id")Long id);
}
