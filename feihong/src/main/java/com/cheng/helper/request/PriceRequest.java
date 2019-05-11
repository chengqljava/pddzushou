package com.cheng.helper.request;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;

import lombok.Data;

/**
 * @author chengql
 * 价格
 */
@Data
public class PriceRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@TableId
	private Integer id;
	/**
	 * 名称
	 */
	private Float price;
	
	
	

}
