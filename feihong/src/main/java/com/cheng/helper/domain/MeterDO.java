package com.cheng.helper.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;

import lombok.Data;

/**
 * @author chengql
 * 电表
 */
@Data
public class MeterDO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@TableId
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
