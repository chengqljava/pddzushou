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
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "")
    private String            id;

    @ApiModelProperty(value = "")
    private String            userName;

    @ApiModelProperty(value = "")
    private String            password;

    @ApiModelProperty(value = "")
    private String            phone;

    @ApiModelProperty(value = "")
    private Boolean           delFlag;

    @ApiModelProperty(value = "")
    private Date              createTime;

    @ApiModelProperty(value = "")
    private Date              validEndTime;

    @ApiModelProperty(value = "")
    private String            role;

    private String            filtrationNumber;

}
