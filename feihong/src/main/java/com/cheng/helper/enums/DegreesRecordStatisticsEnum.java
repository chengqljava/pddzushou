package com.cheng.helper.enums;

public enum DegreesRecordStatisticsEnum {
	FACTROY("FACTORY","车间"),METER("METER","车间");

	private String code;
    private String desc;

    private DegreesRecordStatisticsEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
