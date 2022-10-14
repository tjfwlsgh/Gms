package com.lgl.gms.webapi.bse.persistence.model;

import com.lgl.gms.webapi.common.model.BaseModel;

import lombok.Data;

/**
 * @FileName    : BseBoCustModel.java
 * @Date        : 22.03.28
 * @Author      : hj.Chung
 * @Description : Model vo
 * @History     :
 */
 
@Data
public class BseBoCustModel extends BaseModel {
	
	private Integer boId;		// 법인 ID
	private String boCustCd;	// 법인 거래처 코드
	private String custNm;		// 거래처 명
	private String custSnm;		// 거래처 단축명
	private String custNmEng;	// 거래처 명 영어
	private String custSnmEng;	// 거래처 단축명 영어
	private String delYn;		// 삭제 Yn
//	private Date regDt;			// 등록 일자
//	private Date updDt;			// 수정 일자

}
