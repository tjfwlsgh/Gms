package com.lgl.gms.webapi.fin.dto.response;

import lombok.Data;

/**
 * 양식정보 법인
 * @author jokim
 * @date 2022.04.13
 */
@Data
public class FrmInfoBoResponse {
	
	private Integer frmId;		// 양식 ID
	private Integer boId;		// 법인 ID
	private Integer colSeq;		// COL 순서
	
	// 법인정보
	private Integer pboId;		// 법인ID(H)
	private Integer trrtId;		// 지역ID
	private String trrtNm;		// 지역명
	private String pBoNm;		// 법인명(H)
	private String boNm;		// 법인명
	private String boNmStr;		// 법인/지사
}