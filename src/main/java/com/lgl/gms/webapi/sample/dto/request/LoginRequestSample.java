package com.lgl.gms.webapi.sample.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LoginRequestSample {

	@JsonProperty("email")
	private String email;

	@JsonProperty("pw")
	private String pw;

}
