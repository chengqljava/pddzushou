package com.cheng.helper.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler",
                                "fieldHandler" }, ignoreUnknown = true)
@ApiModel(description = "")
public class ShopDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "")
	private String id;
	
	@ApiModelProperty(value = "")
	private String name;
	
	@ApiModelProperty(value = "")
	private String key;
	
	@ApiModelProperty(value = "")
	private String secret;
	
	@ApiModelProperty(value = "")
	private Date createTime;
	
	@ApiModelProperty(value = "")
	private String userId;
	
	@ApiModelProperty(value = "")
	private Boolean type;
	
	@ApiModelProperty(value = "")
	private String phone;
	

}
