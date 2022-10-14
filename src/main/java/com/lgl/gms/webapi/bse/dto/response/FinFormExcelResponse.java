package com.lgl.gms.webapi.bse.dto.response;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.apache.poi.ss.usermodel.Row;

import com.lgl.gms.webapi.common.util.ExcelUtil;

import lombok.Data;

/**
 * 재무양식관리 엑셀업로드객체(양식미포함)
 * @author jokim
 * @date 2022.05.09
 */
@Data
public class FinFormExcelResponse {
	
	@NotBlank(message="{validation.notblank}")
	private String frmNm;			// 양식명
	
	private String frmNmEng;		// 양식명(영문)
	
	private String lvCl;			// Level 구분
	
	private String rowSeq;			// ROW 순서
	
	private String accId;			// 계정ID
	
	private List<String> errorColumns;	//에러컬럼들
	private String errorMsg;			// 유효성체크 에러 메세지
	
	
	/**
     * 엑셀 업로드 처리 객체
     * @param row
     * @return
     */
    public static FinFormExcelResponse from(Row row) {
    	return ExcelUtil.setObjectMapping(new FinFormExcelResponse(), row);
    }
}
