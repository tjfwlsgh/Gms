package com.lgl.gms.webapi.cmm.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LoginResponse {

	public LoginResponse() {};
	
	@JsonProperty("refreshToken")
	public String refToken;
	@JsonProperty("accessToken")
	public String accessToken;

	@JsonInclude(Include.NON_NULL)
	public LoginUserInfo UserInfo;

	List<?> menus;
	
	List<?> auths;
	
}
