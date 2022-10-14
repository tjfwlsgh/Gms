package com.lgl.gms.webapi.cmm.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LoginFailResponse {

	@JsonProperty("lockYn")
	public String lockYn;

	@JsonProperty("failCnt")
	public String failCnt;
}
