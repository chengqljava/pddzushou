package com.cheng.helper.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cheng.helper.domain.Keyword;
import com.cheng.helper.repository.KeywordMapper;
import com.cheng.helper.service.IKeyWordService;

@Service("keyWordService")
public class KeyWordServiceImpl extends ServiceImpl<KeywordMapper, Keyword>
                                implements IKeyWordService {

}
