package com.cheng.helper.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.cheng.helper.domain.PriceDO;
import com.cheng.helper.domain.PriceQuery;
import com.cheng.helper.repository.PriceDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class PriceService {
	@Autowired
	private PriceDao priceDao;

	public Page<PriceDO> page(PriceQuery priceQuery) {
		List<PriceDO> list = priceDao.list(priceQuery);
		return priceQuery.setRecords(list);
	}

	public List<PriceDO> list(PriceQuery priceQuery) {
		List<PriceDO> list = priceDao.list(priceQuery);
		return list;
	}

	public PriceDO get(Long id) {
		return priceDao.get(id);
	}

	@Transactional
	public boolean update(PriceDO price) {
		return priceDao.update(price) > 0 ? true : false;
	}

	@Transactional
	public void save(PriceDO meter) {
		priceDao.save(meter);
	}
}
