package com.cheng.qurtz.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cheng.qurtz.domain.JobLogDO;
import com.cheng.qurtz.domain.JobLogQuery;

/**
 * 定时任务日志
 * 
 */
@Mapper
public interface JobLogDao {

    void save(JobLogDO jobLog);

    JobLogDO get(String id);

    List<JobLogDO> list(JobLogQuery jobLogQuery);
}
