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
public class ShopQuery extends BasePageQuery {

    private static final long serialVersionUID = 1L;
    private String            userId;
    private String            ownerId;

}
