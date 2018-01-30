package com.cheng.helper.domain;

import com.cheng.common.BasePageQuery;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ExpressQuery extends BasePageQuery {

    private static final long serialVersionUID = 1L;

    private String            shopId;
    private String            express;
    private String            searchStartTime;
    private String            searchEndTime;
    private String            orderCode;
    private Integer           action;
    private String            userId;

}
