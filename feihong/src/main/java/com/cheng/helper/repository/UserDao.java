package com.cheng.helper.repository;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cheng.helper.domain.UserDO;
import com.cheng.helper.domain.UserQuery;



@Mapper
public interface UserDao extends BaseMapper<UserDO>{
	
	void save(UserDO user);
	
	int update(UserDO user);
	
	int delete(String id);
	
	UserDO get(String id);
	
	List<UserDO> list(UserQuery userQuery);
	
	UserDO findUserName(@Param("userName")String userName);
}
