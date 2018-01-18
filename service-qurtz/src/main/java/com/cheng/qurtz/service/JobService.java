package com.cheng.qurtz.service;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.collection.ListUtil;

import com.cheng.qurtz.JobConstants;
import com.cheng.qurtz.domain.JobDO;
import com.cheng.qurtz.domain.JobQuery;
import com.cheng.qurtz.repository.JobDao;
import com.cheng.qurtz.utils.ScheduleUtil;
import com.cheng.utils.IDGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("jobService")
@Transactional(readOnly = true)
public class JobService {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private JobDao    jobDao;

    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    public void init() {
        List<JobDO> jobList = jobDao.list(new JobQuery());
        if (ListUtil.isNotEmpty(jobList)) {
            for (JobDO job : jobList) {
                CronTrigger cronTrigger = ScheduleUtil.getCronTrigger(scheduler, job.getId());
                if (cronTrigger == null) {
                    ScheduleUtil.createScheduleJob(scheduler, job);
                } else {
                    ScheduleUtil.updateScheduleJob(scheduler, job);
                }
            }
        }
    }

    public JobDO get(String id) {
        return jobDao.get(id);
    }

    public List<JobDO> list(JobQuery jobQuery) {
        return jobDao.list(jobQuery);
    }

    public PageInfo<JobDO> page(JobQuery jobQuery) {
        PageHelper.startPage(jobQuery.getPage(), jobQuery.getSize());
        List<JobDO> list = jobDao.list(jobQuery);
        return new PageInfo<JobDO>(list);
    }

    @Transactional
    public void save(JobDO job) {
        job.setId(IDGenerator.OBJECTID.generate());
        job.setCreateTime(new Date());
        job.setStatus(JobConstants.ScheduleStatus.NORMAL.getValue());
        jobDao.save(job);

        ScheduleUtil.createScheduleJob(scheduler, job);
    }

    @Transactional
    public void update(JobDO job) {
        jobDao.update(job);

        ScheduleUtil.updateScheduleJob(scheduler, job);
    }

    @Transactional
    public void delete(String id) {
        jobDao.delete(id);

        ScheduleUtil.deleteScheduleJob(scheduler, id);
    }

    @Transactional
    public void run(String id) {
        ScheduleUtil.run(scheduler, get(id));
    }

    @Transactional
    public void pause(String id) {
        ScheduleUtil.pauseJob(scheduler, id);

        JobDO jobDO = get(id);
        jobDO.setStatus(JobConstants.ScheduleStatus.PAUSE.getValue());
        jobDao.update(jobDO);
    }

    @Transactional
    public void resume(String id) {
        ScheduleUtil.resumeJob(scheduler, id);

        JobDO jobDO = get(id);
        jobDO.setStatus(JobConstants.ScheduleStatus.NORMAL.getValue());
        jobDao.update(jobDO);
    }
}
