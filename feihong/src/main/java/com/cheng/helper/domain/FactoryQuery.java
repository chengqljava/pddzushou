package com.cheng.helper.domain;

import com.baomidou.mybatisplus.plugins.Page;
import com.cheng.common.BasePageQuery;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class FactoryQuery extends Page<FactoryDO> {

    private static final long serialVersionUID = 1L;
    private String            userId;
    private String            ownerId;

}
