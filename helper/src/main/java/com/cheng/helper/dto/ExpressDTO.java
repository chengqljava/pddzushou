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
public class ExpressDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "")
    private String            id;

    @ApiModelProperty(value = "单号")
    private String            orderCode;

    @ApiModelProperty(value = "代号")
    private String            express;

    @ApiModelProperty(value = "备注")
    private String            remark;

    @ApiModelProperty(value = "")
    private Date              createTime;

    @ApiModelProperty(value = "")
    private String            shopId;

    @ApiModelProperty(value = "")
    private Integer           action;

    @ApiModelProperty(value = "代号")
    private String            backExpress;

    @ApiModelProperty(value = "单号")
    private Boolean           backOrder;

    @ApiModelProperty(value = "发回时间")
    private Date              backTime;

}
