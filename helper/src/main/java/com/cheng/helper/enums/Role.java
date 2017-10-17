package com.cheng.helper.enums;

public enum Role {
	ADMIN("admin","超级管理员"),SIMPLE_PDD("pdd","简单管理员");
	private String code;
	private String desc;
	private Role(String code, String desc) {
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
