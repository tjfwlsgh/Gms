package com.lgl.gms.webapi.sample.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LoginFailResponseSample {

	@JsonProperty("lockYn")
	public String lockYn;

	@JsonProperty("failCnt")
	public String failCnt;
}
