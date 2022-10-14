package com.lgl.gms.webapi.cmm.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
public class CountryCodeResponse {

	@JsonInclude(Include.NON_NULL)
	private String cntryCd;
	@JsonInclude(Include.NON_NULL)
	private String cntryNm;
	@JsonInclude(Include.NON_NULL)
	private String isoCntryCd;
	@JsonInclude(Include.NON_NULL)
	private String crncyCd;
		
}
