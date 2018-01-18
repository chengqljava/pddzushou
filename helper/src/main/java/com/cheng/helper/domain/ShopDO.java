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
public class ShopDO implements Serializable {

    private static final long serialVersionUID = 1L;

    //
    private String            id;
    //
    private String            name;
    //
    private String            ownerId;
    //
    private String            secret;
    //
    private Date              createTime;
    //
    private String            userId;
    //
    private Boolean           type;
    //
    private String            phone;
    private String            ownerName;            //p
    private String            accessToken;
    private String            refreshToken;
    private Date              tokenUpdateTime;

}
