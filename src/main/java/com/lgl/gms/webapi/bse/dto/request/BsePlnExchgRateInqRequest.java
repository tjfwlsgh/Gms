package com.lgl.gms.webapi.bse.dto.request;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.Data;

@Data
public class BsePlnExchgRateInqRequest {
	private String yymm;
	private String crncyCd;
	private String plnExchgRateDet;
	private BigDecimal plnCurExchgRate;
	private String std;
	private String etd;
	private Date rcpDt;
}
