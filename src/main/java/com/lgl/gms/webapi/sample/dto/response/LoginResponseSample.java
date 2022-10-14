package com.lgl.gms.webapi.sample.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LoginResponseSample {

	@JsonProperty("refreshToken")
	public String refreshToken;
	@JsonProperty("accessToken")
	public String accessToken;

	@JsonProperty("name")
	@JsonInclude(Include.NON_NULL)
	public String name;
	@JsonProperty("email")
	@JsonInclude(Include.NON_NULL)
	public String email;

	@JsonProperty("userType")
	@JsonInclude(Include.NON_NULL)
	public String userType;

	@JsonProperty("id")
	@JsonInclude(Include.NON_NULL)
	public Integer id;
}
