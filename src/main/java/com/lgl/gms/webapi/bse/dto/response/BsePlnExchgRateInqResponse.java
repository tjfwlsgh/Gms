package com.lgl.gms.webapi.bse.dto.response;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
public class BsePlnExchgRateInqResponse {
	@JsonInclude(Include.NON_NULL)
	private String yymm;
	@JsonInclude(Include.NON_NULL)
	private String crncyCd;
	@JsonInclude(Include.NON_NULL)
	private String exchgRateDet;
	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal curExchgRate;

	@JsonInclude(Include.NON_NULL)
	private String std;
	@JsonInclude(Include.NON_NULL)
	private String etd;
	@JsonInclude(Include.NON_NULL)
	private Date rcpDt;
	
	
	public BsePlnExchgRateInqResponse() {}
}
