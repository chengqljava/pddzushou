package com.cheng.qurtz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cheng.qurtz.domain.JobLogDO;
import com.cheng.qurtz.domain.JobLogQuery;
import com.cheng.qurtz.repository.JobLogDao;
import com.cheng.utils.IDGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("jobLogService")
@Transactional(readOnly = true)
public class JobLogService {

    @Autowired
    private JobLogDao jobLogDao;

    public JobLogDO get(String id) {
        return jobLogDao.get(id);
    }

    public List<JobLogDO> list(JobLogQuery jobLogQuery) {
        return jobLogDao.list(jobLogQuery);
    }

    public PageInfo<JobLogDO> page(JobLogQuery jobLogQuery) {
        PageHelper.startPage(jobLogQuery.getPage(), jobLogQuery.getSize());
        List<JobLogDO> list = jobLogDao.list(jobLogQuery);
        return new PageInfo<JobLogDO>(list);
    }

    @Transactional
    public void save(JobLogDO jobLog) {
        jobLog.setId(IDGenerator.OBJECTID.generate());
        jobLogDao.save(jobLog);
    }

}
