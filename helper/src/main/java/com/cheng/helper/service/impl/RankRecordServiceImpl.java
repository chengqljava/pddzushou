package com.cheng.helper.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cheng.helper.domain.RankRecord;
import com.cheng.helper.repository.RankRecordMapper;
import com.cheng.helper.service.IRankRecordService;

@Service("rankRecordService")
public class RankRecordServiceImpl extends ServiceImpl<RankRecordMapper, RankRecord>
                                   implements IRankRecordService {

}
