package com.cheng.qurtz.utils;

import java.util.Date;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.cheng.common.SpringContextHolder;
import com.cheng.qurtz.JobConstants;
import com.cheng.qurtz.domain.JobDO;
import com.cheng.qurtz.domain.JobLogDO;
import com.cheng.qurtz.service.JobLogService;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScheduleJob extends QuartzJobBean {

    ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(3,
        new BasicThreadFactory.Builder().namingPattern("quartz-schedule-pool-%d").daemon(true)
            .build());

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        String jsonJob = context.getMergedJobDataMap().getString(JobConstants.JOB_PARAM_KEY);
        JobDO jobDO = new Gson().fromJson(jsonJob, JobDO.class);

        JobLogService jobLogService = SpringContextHolder.getBean(JobLogService.class);
        //数据库保存执行记录
        JobLogDO jobLogDO = new JobLogDO();
        jobLogDO.setJobId(jobDO.getId());
        jobLogDO.setBeanName(jobDO.getBeanName());
        jobLogDO.setMethodName(jobDO.getMethodName());
        jobLogDO.setParams(jobDO.getParams());
        jobLogDO.setCreateTime(new Date());

        //任务开始时间
        long startTime = System.currentTimeMillis();

        try {

            //执行任务
            log.info("任务准备执行，任务ID：" + jobDO.getId());
            ScheduleRunnable task = new ScheduleRunnable(jobDO.getBeanName(), jobDO.getMethodName(),
                jobDO.getParams());
            Future<?> future = executorService.submit(task);

            future.get();

            //任务执行总时长
            long times = System.currentTimeMillis() - startTime;
            jobLogDO.setTimes((int) times);
            //任务状态    0：成功    1：失败
            jobLogDO.setStatus(0);

            log.info("任务执行完毕，任务ID：" + jobLogDO.getJobId() + "  总共耗时：" + times + "毫秒");

        } catch (Exception e) {
            log.error("任务执行失败，任务ID：" + jobLogDO.getJobId(), e);

            //任务执行总时长
            long times = System.currentTimeMillis() - startTime;
            jobLogDO.setTimes((int) times);

            //任务状态    0：成功    1：失败
            jobLogDO.setStatus(1);
            jobLogDO.setError(StringUtils.substring(e.toString(), 0, 2000));
        } finally {
            jobLogService.save(jobLogDO);
        }

    }
}
