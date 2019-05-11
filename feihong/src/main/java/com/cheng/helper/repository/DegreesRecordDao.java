package com.cheng.helper.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cheng.helper.domain.DegreesRecordBO;
import com.cheng.helper.domain.DegreesRecordDO;
import com.cheng.helper.domain.DegreesRecordQuery;
import com.cheng.helper.domain.DegreesRecordStatisticBO;

@Mapper
public interface DegreesRecordDao {

	List<DegreesRecordBO> list(DegreesRecordQuery degreesRecordQuery);

	DegreesRecordDO get(@Param("id") Long id);

	int save(DegreesRecordDO meterDO);

	int update(DegreesRecordDO meterDO);

	int delete(@Param("id") Long id);
	
	
	List<DegreesRecordStatisticBO> listStatistics(DegreesRecordQuery degreesRecordQuery);
}
