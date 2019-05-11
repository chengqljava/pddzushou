package com.cheng.helper.domain;

import com.baomidou.mybatisplus.plugins.Page;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DegreesRecordQuery<T> extends Page<T> {

    private static final long serialVersionUID = 1L;
    private String            userId;
    private String            ownerId;
    private String searchStartTime;
    private String searchEndTime;
    private Integer meterId;
    private Integer factoryId;
    private Integer type;

}
