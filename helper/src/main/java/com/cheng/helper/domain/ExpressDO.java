package com.cheng.helper.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ExpressDO implements Serializable {

    private static final long serialVersionUID = 1L;

    //
    private String            id;
    //单号
    private String            order;
    //代号
    private String            express;
    //备注
    private String            remark;
    //
    private Date              createTime;
    //
    private String            shopId;
    //
    private Integer           action;
    //代号
    private String            backExpress;
    //单号
    private Boolean           backOrder;
    //发回时间
    private Date              backTime;
    /** 用户ID */
    private String            userId;

}
