package com.lgl.gms.webapi.fin.persistence.model;

import java.math.BigDecimal;

import com.lgl.gms.webapi.common.model.BaseModel;

import lombok.Data;

/**
 * 법인 BS 정보
 * @author jokim
 * 2022.04.16
 */
@Data
public class BoBsDetModel extends BaseModel {
	
	private Integer rowSeq;
	private Integer bsId;
	private BigDecimal finAmt;

}