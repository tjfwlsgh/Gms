package com.lgl.gms.webapi.fin.persistence.model;

import java.math.BigDecimal;

import com.lgl.gms.webapi.common.model.BaseModel;

import lombok.Data;

/**
 * 법인 PL 정보
 * @author jokim
 * 2022.04.16
 */
@Data
public class BoPlInfoModel extends BaseModel {
	
	private Integer plId;
	private Integer boId;
	private Integer frmId;
	private String clsYymm;
	private String crncyCd;
	private String clsBseDt;
	private BigDecimal bseExchgRate;

}
