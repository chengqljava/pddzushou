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
public class UserDO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//
	private String userName;
	//
	private String password;
	//
	private String phone;
	//
	private Boolean delFlag;
	//
	private Date createTime;
	//
	private Date validEndTime;
	//
	private String role;

}
