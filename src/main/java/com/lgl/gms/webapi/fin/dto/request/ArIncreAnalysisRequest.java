package com.lgl.gms.webapi.fin.dto.request;

import java.math.BigDecimal;

import com.lgl.gms.webapi.common.context.UserInfo;
import com.lgl.gms.webapi.fin.persistence.model.ArSmmryCustModel;

import lombok.Data;

@Data
public class ArIncreAnalysisRequest {

	private String arYymm;	// 미수채권 년월
	private Integer boId;	// 법인 ID
	private BigDecimal arSeq;	// 미수채권 순서
	private String boCustCd; // 거래처 코드
	private Integer sheetNum;	// 업로드 시트 번호
	private String trrtNm;	// 지역 명
	private String boNm;	// 법인 명

	private Integer compId = UserInfo.getCompId();
	private String locale = UserInfo.getLocale();
	
}
