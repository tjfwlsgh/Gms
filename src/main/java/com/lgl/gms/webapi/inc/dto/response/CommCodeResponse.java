package com.lgl.gms.webapi.inc.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
public class CommCodeResponse {
	
	@JsonInclude(Include.NON_NULL)
	private Integer tccvId;
	@JsonInclude(Include.NON_NULL)
	private String stdCd;
	@JsonInclude(Include.NON_NULL)
	private String stdCdNm;
	@JsonInclude(Include.NON_NULL)
	private String stdCdNmEng;
	@JsonInclude(Include.NON_NULL)
	private String bseCd;
	
	@JsonInclude(Include.NON_NULL)
	private String cdVal;	
	@JsonInclude(Include.NON_NULL)
	private String codeNm;
		
}
