package com.lgl.gms.webapi.bse.dto.request;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.Data;

@Data
public class BseExchgRateInqRequest {
	
	private String yymm;
	private String crncyCd;
	private String exchgRateDet;
	private BigDecimal curExchgRate;
	private String std;
	private String etd;
	private Date rcpDt;

}
