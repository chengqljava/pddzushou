package com.cheng.helper.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.cheng.helper.domain.DegreesRecordBO;
import com.cheng.helper.domain.DegreesRecordDO;
import com.cheng.helper.domain.DegreesRecordQuery;
import com.cheng.helper.domain.DegreesRecordStatisticBO;
import com.cheng.helper.repository.DegreesRecordDao;
@Service
public class DegreesRecordService{
	@Autowired
	private DegreesRecordDao degreesRecordDao;

	 public Page<DegreesRecordBO> page(DegreesRecordQuery degreesRecordQuery) {
	        List<DegreesRecordBO> list = degreesRecordDao.list(degreesRecordQuery);
	        return degreesRecordQuery.setRecords(list);
	    }
	 
	 public DegreesRecordDO get(Long id) {
	        return degreesRecordDao.get(id);
	    }
	 
	   @Transactional
	    public boolean update(DegreesRecordDO degreesRecord) {
	        return degreesRecordDao.update(degreesRecord) > 0 ? true : false;
	    }
	   @Transactional
	    public void save(DegreesRecordDO degreesRecord) {
		   degreesRecordDao.save(degreesRecord) ;
	    }
	   public Page<DegreesRecordStatisticBO> pageStatistics(DegreesRecordQuery degreesRecordQuery) {
	        List<DegreesRecordStatisticBO> list = degreesRecordDao.listStatistics(degreesRecordQuery);
	        return degreesRecordQuery.setRecords(list);
	    }
	   
}
