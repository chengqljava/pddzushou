package com.cheng.helper.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.cheng.helper.domain.FactoryDO;
import com.cheng.helper.domain.FactoryQuery;
import com.cheng.helper.repository.FactoryDao;
@Service
public class FactoryService {
	@Autowired
	private FactoryDao factoryDao;

	 public Page<FactoryDO> page(FactoryQuery factoryQuery) {
	        List<FactoryDO> list = factoryDao.list(factoryQuery);
	        return factoryQuery.setRecords(list);
	    }
	 
	 public List<FactoryDO> list(FactoryQuery factoryQuery) {
		    factoryQuery.setCurrent(-1);
	        List<FactoryDO> list = factoryDao.list(factoryQuery);
	        return list;
	    }
	 
	 public FactoryDO get(Long id) {
	        return factoryDao.get(id);
	    }
	 
	   @Transactional
	    public boolean update(FactoryDO factory) {
	        return factoryDao.update(factory) > 0 ? true : false;
	    }
	   @Transactional
	    public void save(FactoryDO factory) {
		   factory.setCreateTime(new Date());
		   factoryDao.save(factory) ;
	    }
}
