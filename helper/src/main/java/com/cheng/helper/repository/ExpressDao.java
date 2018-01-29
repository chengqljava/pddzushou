package com.cheng.helper.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cheng.helper.domain.ExpressDO;
import com.cheng.helper.domain.ExpressQuery;

@Mapper
public interface ExpressDao {

    void save(ExpressDO express);

    int update(ExpressDO express);

    int delete(String id);

    ExpressDO get(String id);

    List<ExpressDO> list(ExpressQuery expressQuery);
}
