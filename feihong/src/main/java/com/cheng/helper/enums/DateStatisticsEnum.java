package com.cheng.helper.enums;

public enum DateStatisticsEnum {
	DAY(1,"日"),MONTH(2,"月");

	private Integer code;
    private String desc;

    private DateStatisticsEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
