package com.cheng.helper.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author chengql
 * 度数记录
 *
 */
@Data
public class DegreesRecordStatisticBO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	/**
	 * 电表ID
	 */
	private Integer meterId;
	/**
	 * 
	 */
	private Date createTime;
	
	/**
	 * 电表度数
	 */
	private Float meterNumber;
	/**
	 * 采集时间
	 */
	private Date acquisitionTime;
	/**
	 * 钱数
	 */
	private Double money;
	/**
	 * 电表名称
	 */
	
	private String meterName;
	
}
