package com.lgl.gms.webapi.bse.persistence.model;

import com.lgl.gms.webapi.common.model.BaseModel;

import lombok.Data;

@Data
public class FrmInfoDetModel extends BaseModel {
	
	private Integer frmId;		// 양식 ID
	private Integer rowSeq;		// ROW 순서
	private String frmNm;		// 양식 명
	private String frmNmEng;	// 양식 명 영어
	private Integer lvCl;		// Level 구분	
	private Integer accId;		// 계정 ID
	
}
