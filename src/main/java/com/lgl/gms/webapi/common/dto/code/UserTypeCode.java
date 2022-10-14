package com.lgl.gms.webapi.common.dto.code;

public enum UserTypeCode {

	USER("U"), ADMIN("A");

	private String value;

	UserTypeCode(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}

}
