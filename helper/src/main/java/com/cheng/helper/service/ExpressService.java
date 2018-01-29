package com.cheng.helper.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cheng.helper.domain.ExpressDO;
import com.cheng.helper.domain.ExpressQuery;
import com.cheng.helper.repository.ExpressDao;
import com.cheng.utils.IDGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("expressService")
@Transactional(readOnly = true)
public class ExpressService {

    @Autowired
    private ExpressDao expressDao;

    public ExpressDO get(String id) {
        return expressDao.get(id);
    }

    public List<ExpressDO> list(ExpressQuery expressQuery) {
        return expressDao.list(expressQuery);
    }

    public PageInfo<ExpressDO> page(ExpressQuery expressQuery) {
        PageHelper.startPage(expressQuery.getPage(), expressQuery.getSize());
        List<ExpressDO> list = expressDao.list(expressQuery);
        return new PageInfo<ExpressDO>(list);
    }

    @Transactional
    public void save(ExpressDO express) {
        express.setCreateTime(new Date());
        express.setId(IDGenerator.OBJECTID.generate());
        expressDao.save(express);
    }

    @Transactional
    public boolean update(ExpressDO express) {
        return expressDao.update(express) > 0 ? true : false;
    }

    @Transactional
    public void delete(String id) {
        expressDao.delete(id);
    }

}
