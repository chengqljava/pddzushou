package com.cheng.helper.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.cheng.helper.domain.WorkShopDO;
import com.cheng.helper.domain.WorkShopQuery;
import com.cheng.helper.repository.WorkShopDao;
@Service
public class WorkShopService  {
	@Autowired
	private WorkShopDao workShopDao;

	 public Page<WorkShopDO> page(WorkShopQuery workShopQuery) {
	        List<WorkShopDO> list = workShopDao.list(workShopQuery);
	        return workShopQuery.setRecords(list);
	    }
	 
	 public List<WorkShopDO> list(WorkShopQuery workShopQuery) {
	        List<WorkShopDO> list = workShopDao.list(workShopQuery);
	        return list;
	    }
	 
	 public WorkShopDO get(Long id) {
	        return workShopDao.get(id);
	    }
	 
	   @Transactional
	    public boolean update(WorkShopDO workShop) {
	        return workShopDao.update(workShop) > 0 ? true : false;
	    }
	   @Transactional
	    public void save(WorkShopDO workShop) {
		     workShop.setCreateTime(new Date());
	         workShopDao.save(workShop) ;
	    }
}
