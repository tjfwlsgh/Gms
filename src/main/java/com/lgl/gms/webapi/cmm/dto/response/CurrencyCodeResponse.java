package com.lgl.gms.webapi.cmm.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
public class CurrencyCodeResponse {

	@JsonInclude(Include.NON_NULL)
	private String crncyCd;
	@JsonInclude(Include.NON_NULL)
	private String crncyNm;
		
}
