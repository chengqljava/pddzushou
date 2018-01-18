package com.cheng.qurtz.domain;

import com.cheng.common.BasePageQuery;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 定时任务
 * 
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class JobQuery extends BasePageQuery {

    private static final long serialVersionUID = 1L;

    private String            beanName;
}
