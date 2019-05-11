package com.cheng.helper.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cheng.helper.domain.PriceDO;
import com.cheng.helper.domain.PriceQuery;

@Mapper
public interface PriceDao {
	List<PriceDO> list(PriceQuery priceQuery);

	PriceDO get(@Param("id") Long id);

	void save(PriceDO meterDO);

	int update(PriceDO meterDO);

	int delete(@Param("id") Long id);
}
