package com.cheng.helper.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cheng.helper.domain.ShopDO;
import com.cheng.helper.domain.ShopQuery;
import com.cheng.helper.repository.ShopDao;
import com.cheng.utils.IDGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("shopService")
@Transactional(readOnly = true)
public class ShopService {

    @Autowired
    private ShopDao shopDao;

    public ShopDO get(String id) {
        return shopDao.get(id);
    }

    public List<ShopDO> list(ShopQuery shopQuery) {
        return shopDao.list(shopQuery);
    }

    public PageInfo<ShopDO> page(ShopQuery shopQuery) {
        PageHelper.startPage(shopQuery.getPage(), shopQuery.getSize());
        List<ShopDO> list = shopDao.list(shopQuery);
        return new PageInfo<ShopDO>(list);
    }

    @Transactional
    public void save(ShopDO shop) {
        shop.setCreateTime(new Date());
        shop.setId(IDGenerator.OBJECTID.generate());
        shopDao.save(shop);
    }

    @Transactional
    public boolean update(ShopDO shop) {
        return shopDao.update(shop) > 0 ? true : false;
    }

    @Transactional
    public void delete(String id) {
        shopDao.delete(id);
    }

    public List<ShopDO> refreshTokenList(int hours) {
        return shopDao.refreshTokenList(hours);
    }

    @Transactional
    public void batchUpdate(List<ShopDO> list) {
        shopDao.batchUpdate(list);
    }

    @Transactional
    public void updateCleanToken(String id) {
        shopDao.updateCleanToken(id);
    }
}
