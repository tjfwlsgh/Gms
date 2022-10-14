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
public class BoPlDetModel extends BaseModel {
	
	private Integer rowSeq;
	private Integer plId;
	private BigDecimal finAmt;

}