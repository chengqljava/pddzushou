package com.cheng.helper.enums;

public enum ActionEnum {
                        retreat(1, "退"), exchange(2, "换");
    private Integer code;
    private String  desc;

    private ActionEnum(Integer code, String desc) {
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
