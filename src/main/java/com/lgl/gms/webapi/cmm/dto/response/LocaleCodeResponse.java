package com.lgl.gms.webapi.cmm.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
public class LocaleCodeResponse {

	@JsonInclude(Include.NON_NULL)
	private String lngCd;
	@JsonInclude(Include.NON_NULL)
	private String trrtCd;
	@JsonInclude(Include.NON_NULL)
	private String lngTyp;
	@JsonInclude(Include.NON_NULL)
	private String lngDesc;
	
}
