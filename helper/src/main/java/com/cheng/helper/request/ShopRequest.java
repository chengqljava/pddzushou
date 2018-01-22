package com.cheng.helper.request;

import java.io.Serializable;
import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author chengqianliang
 *
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ShopRequest implements Serializable {

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

}
