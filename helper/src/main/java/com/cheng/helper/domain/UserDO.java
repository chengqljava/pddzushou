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
	//用户名
	private String userName;
	//密码
	private String password;
	//手机号
	private String phone;
	//是否
	private Boolean delFlag;
	//创建时间
	private Date createTime;
	//有效时间
	private Date validEndTime;
	//用色
	private String role;
	//密码对称
	private String salt;

}
