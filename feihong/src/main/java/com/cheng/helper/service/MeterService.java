package com.cheng.helper.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.cheng.helper.domain.MeterDO;
import com.cheng.helper.domain.MeterQuery;
import com.cheng.helper.repository.MeterDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service
public class MeterService {
	@Autowired
	private MeterDao meterDao;

	 public Page<MeterDO> page(MeterQuery meterQuery) {
	        List<MeterDO> list = meterDao.list(meterQuery);
	        return meterQuery.setRecords(list);
	    }
	 public List<MeterDO> list(MeterQuery meterQuery) {
		     meterQuery.setCurrent(-1);
	        List<MeterDO> list = meterDao.list(meterQuery);
	        return list;
	    }
	 
	 public MeterDO get(Long id) {
	        return meterDao.get(id);
	    }
	 
	   @Transactional
	    public boolean update(MeterDO meter) {
	        return meterDao.update(meter) > 0 ? true : false;
	    }
	   @Transactional
	    public void save(MeterDO meter) {
		   meter.setCreateTime(new Date());
		   meterDao.save(meter) ;
	    }
		   
}
