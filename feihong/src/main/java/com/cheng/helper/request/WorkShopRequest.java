package com.cheng.helper.request;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author chengql
 *工厂区域
 */
@Data
public class WorkShopRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	/**
	 * 名称
	 */
	private String name;
	private String address;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 联系人
	 */
	private String contacts;
	/**
	 * 联系人手机号
	 */
	private String phoneNumber;
	
	

}
