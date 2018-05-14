package com.cheng.helper.request;

import java.io.Serializable;

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
public class GoodRequest implements Serializable {
    /**  */
    private static final long serialVersionUID = 1L;
    private String            goodId;

}
