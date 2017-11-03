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
public class GoodsDO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//
	private String goodsId;
	//数量
	private Integer amount;
	//
	private Date createTime;
	//
	private String shopId;
	/**
	 * 图片
	 */
	private String images;

}
