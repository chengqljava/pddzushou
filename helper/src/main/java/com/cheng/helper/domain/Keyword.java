package com.cheng.helper.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableName;

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
@TableName("t_keyword")
public class Keyword implements Serializable {
    /**  */
    private static final long serialVersionUID = 1L;
    private String            id;
    private String            goodId;
    private String            name;
    private Date              createTime;
    private String            ranking;
    private Date              rankingTime;

}
