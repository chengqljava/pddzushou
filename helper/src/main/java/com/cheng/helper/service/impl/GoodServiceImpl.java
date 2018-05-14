package com.cheng.helper.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cheng.helper.domain.Good;
import com.cheng.helper.repository.GoodMapper;
import com.cheng.helper.service.IGoodService;

@Service("goodService")
public class GoodServiceImpl extends ServiceImpl<GoodMapper, Good> implements IGoodService {

}
