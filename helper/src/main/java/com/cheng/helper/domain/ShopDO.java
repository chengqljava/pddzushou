package com.cheng.helper.domain;


import java.io.Serializable;
import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



/**
 * 
 * 
 * @author dongjinsong
 * @email dongjingsong@wulingd.com
 * @date 2017-10-16 19:09:38
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ShopDO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//
	private String name;
	//
	private String key;
	//
	private String secret;
	//
	private Date createTime;
	//
	private String userId;
	//
	private Boolean type;
	//
	private String phone;

}
