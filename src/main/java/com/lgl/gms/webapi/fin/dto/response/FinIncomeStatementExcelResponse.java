package com.lgl.gms.webapi.fin.dto.response;

import java.util.List;

import org.apache.poi.ss.usermodel.Row;

import com.lgl.gms.webapi.common.util.ExcelUtil;

import lombok.Data;

/**
 * 양식정보 법인
 * @author jokim
 * @date 2022.04.13
 */
@Data
public class FinIncomeStatementExcelResponse {

	private String frmNm;			// 계정과목
	
	private List<FrmBo> frmBos;		// 법인 헤더정보 또는 법인별 재무금액
	
	private String sumFinAmt;		// 재무금액 법인합산
	
		
	/**
     * 엑셀 업로드 처리 객체
     * @param row
     * @return
     */
    public static FinIncomeStatementExcelResponse from(Row row) {
    	
    	return ExcelUtil.setObjectMapping(new FinIncomeStatementExcelResponse(), row);
    }    
  

	@Data
    public static class FrmBo {
		private String boId;
    	private String finValue;		// 법인헤더정보 및 법인별 재무금액
    }
	
	/**
	 * 메타데이터 정의
	 */
	public static List<FrmBo> META_DATA;	// 법인정보리스트(양식)

}
