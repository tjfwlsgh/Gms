package com.lgl.gms.webapi.cmm.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
public class AuthCodeResponse {

	@JsonInclude(Include.NON_NULL)
	private String authCd;
	@JsonInclude(Include.NON_NULL)
	private String authNm;
	@JsonInclude(Include.NON_NULL)
	private String authNmENg;
	
}
