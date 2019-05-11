package com.cheng.helper.request;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;

import lombok.Data;

/**
 * @author chengql
 *车间
 */
@Data
public class FactoryRequest implements Serializable{

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
	private String address;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 工厂ID
	 */
	private Integer workShopId;
	
	

}
