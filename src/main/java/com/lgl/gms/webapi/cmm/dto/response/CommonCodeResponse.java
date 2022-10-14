package com.lgl.gms.webapi.cmm.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
public class CommonCodeResponse {
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
		
}
