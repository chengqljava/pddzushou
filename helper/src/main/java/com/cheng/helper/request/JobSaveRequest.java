package com.cheng.helper.request;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler",
                                "fieldHandler" }, ignoreUnknown = true)
public class JobSaveRequest {

    @ApiModelProperty(value = "任务id,为空则新增")
    private String  id;

    @NotNull
    @ApiModelProperty(value = "spring bean名称")
    private String  beanName;

    @NotNull
    @ApiModelProperty(value = "方法名")
    private String  methodName;

    @ApiModelProperty(value = "参数")
    private String  params;

    @NotNull
    @ApiModelProperty(value = "cron表达式")
    private String  cronExpression;

    @ApiModelProperty(value = "任务状态  0：正常  1：暂停")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String  remark;

    @ApiModelProperty(value = "创建时间")
    private Date    createTime;

}
