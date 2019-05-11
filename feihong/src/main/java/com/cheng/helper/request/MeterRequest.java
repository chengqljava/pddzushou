package com.cheng.helper.request;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author chengql
 * 电表
 */
@Data
public class MeterRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 工厂ID
	 */
	private Integer factoryId;
	
	

}
