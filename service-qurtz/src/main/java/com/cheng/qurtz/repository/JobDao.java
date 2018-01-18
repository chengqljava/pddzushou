package com.cheng.qurtz.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cheng.qurtz.domain.JobDO;
import com.cheng.qurtz.domain.JobQuery;

/**
 * 定时任务
 */
@Mapper
public interface JobDao {

    void save(JobDO job);

    int update(JobDO job);

    int delete(String id);

    JobDO get(String id);

    List<JobDO> list(JobQuery jobQuery);
}
