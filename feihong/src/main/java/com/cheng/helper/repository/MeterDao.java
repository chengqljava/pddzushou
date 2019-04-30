package com.cheng.helper.repository;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cheng.helper.domain.MeterDO;

@Mapper
public interface MeterDao  extends BaseMapper<MeterDO>{

}
