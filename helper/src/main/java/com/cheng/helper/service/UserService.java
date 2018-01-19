package com.cheng.helper.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cheng.helper.domain.UserDO;
import com.cheng.helper.domain.UserQuery;
import com.cheng.helper.repository.UserDao;
import com.cheng.utils.IDGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("userService")
public class UserService {

    @Autowired
    private UserDao userDao;

    public UserDO get(String id) {
        return userDao.get(id);
    }

    public UserDO findUserName(String userName) {
        return userDao.findUserName(userName);
    }

    public List<UserDO> list(UserQuery userQuery) {
        return userDao.list(userQuery);
    }

    public PageInfo<UserDO> page(UserQuery userQuery) {
        PageHelper.startPage(userQuery.getPage(), userQuery.getSize());
        List<UserDO> list = userDao.list(userQuery);
        return new PageInfo<UserDO>(list);
    }

    @Transactional
    public void save(UserDO user) {
        user.setId(IDGenerator.OBJECTID.generate());
        user.setCreateTime(new Date());
        user.setDelFlag(false);
        userDao.save(user);
    }

    @Transactional
    public boolean update(UserDO user) {
        return userDao.update(user) > 0 ? true : false;
    }

    @Transactional
    public void delete(String id) {
        userDao.delete(id);
    }

}
