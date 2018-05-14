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
@TableName("t_rank_record")
public class RankRecord implements Serializable {
    /**  */
    private static final long serialVersionUID = 1L;
    private String            id;
    private String            keywordId;
    private Integer           rank;
    private Date              createTime;
    private Boolean           resourceNicheFlag;

}
