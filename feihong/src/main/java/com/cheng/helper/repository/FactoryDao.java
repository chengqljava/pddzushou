package com.cheng.helper.repository;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cheng.helper.domain.FactoryDO;


@Mapper
public interface FactoryDao extends BaseMapper<FactoryDO>{

}
