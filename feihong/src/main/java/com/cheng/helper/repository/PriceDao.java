package com.cheng.helper.repository;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cheng.helper.domain.PriceDO;

@Mapper
public interface PriceDao extends BaseMapper<PriceDO>{

}
