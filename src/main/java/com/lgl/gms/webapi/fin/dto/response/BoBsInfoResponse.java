package com.lgl.gms.webapi.fin.dto.response;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 법인 BS 정보
 * @author jokim
 * 2022.04.16
 */
@Data
public class BoBsInfoResponse {
	
	private Integer bsId;
	private Integer boId;
	private Integer frmId;
	private String clsYymm;
	private String crncyCd;
	private String clsBseDt;
	private BigDecimal bseExchgRate;

}
