package com.lgl.gms.webapi.bse.dto.request;

import java.sql.Date;

import com.lgl.gms.webapi.common.context.UserInfo;

import lombok.Data;

@Data
public class BseBoCustMgmtRequest {
	private Integer boId;		// 법인 ID
	private String boCustCd;	// 법인 거래처 코드
	private String custNm;		// 거래처 명
	private String custSnm;		// 거래처 단축명
	private String custNmEng;	// 거래처 명 영어
	private String custSnmEng;	// 거래처 단축명 영어
	private String delYn;		// 삭제 Yn
	
	private Integer subBoId;		// 지사 ID
	
	private String locale = UserInfo.getLocale();	
	
}
