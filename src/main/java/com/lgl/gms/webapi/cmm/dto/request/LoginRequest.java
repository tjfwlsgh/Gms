package com.lgl.gms.webapi.cmm.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LoginRequest {

	@JsonProperty("userId")
	private String userId;

	@JsonProperty("userPwd")
	private String userPwd;
	
	@JsonProperty("compId")
	private Integer compId;
	

}
