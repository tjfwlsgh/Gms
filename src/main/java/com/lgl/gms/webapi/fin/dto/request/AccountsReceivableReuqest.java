package com.lgl.gms.webapi.fin.dto.request;

import java.math.BigDecimal;

import com.lgl.gms.webapi.common.context.UserInfo;

import lombok.Data;

@Data
public class AccountsReceivableReuqest {
	
	private String arYymm;	// 미수채권 년월
	private String incclCd;	// 업로드 대상
	private Integer boId;	// 법인 Id
	private Integer sheetNum;	// 업로드 시트 번호

	private Integer compId = UserInfo.getCompId();

}
