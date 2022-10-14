package com.lgl.gms.webapi.fin.dto.response;

import java.util.List;

import lombok.Data;

/**
 * 재무 > 손익계산서 리스트 Response
 * @author jokim
 * @date 2022.04.13
 */
@Data
public class FinIncomeStatementResponse {
	
	private String frmNm;		// 계정과목
	
	private List<FrmBo> frmBos;		// 법인 헤더정보 또는 법인별 재무금액
	
	private String sumFinAmt;		// 재무금액 법인합산
	

	@Data
    public static class FrmBo {
		private String boId;
    	private String finValue;		// 법인헤더정보 및 법인별 재무금액
    }


}
