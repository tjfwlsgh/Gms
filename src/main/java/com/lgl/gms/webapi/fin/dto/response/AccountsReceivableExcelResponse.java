package com.lgl.gms.webapi.fin.dto.response;

import java.util.List;

import javax.validation.constraints.Pattern;

import org.apache.poi.ss.usermodel.Row;

import com.lgl.gms.webapi.common.constant.Constants;
import com.lgl.gms.webapi.common.util.ExcelUtil;

import lombok.Data;

@Data
public class AccountsReceivableExcelResponse {
	
	private String trrtNm; 	// 지역
	
	private String boNm;	// 법인 명
	
	private String unrdBndsAmt; // 미경과 채권
	
	@Pattern(regexp=Constants.PATTERN_REGEX.FLOAT, message="{validation.onlyfloat}")
	private String rcvbBndsAmt30;	// 미회수 채권 금액 30
	
	@Pattern(regexp=Constants.PATTERN_REGEX.FLOAT, message="{validation.onlyfloat}")
	private String rcvbBndsAmt60;	// 미회수 채권 금액 60
	
	@Pattern(regexp=Constants.PATTERN_REGEX.FLOAT, message="{validation.onlyfloat}")
	private String rcvbBndsAmt90;	// 미회수 채권 금액 90
	
	@Pattern(regexp=Constants.PATTERN_REGEX.FLOAT, message="{validation.onlyfloat}")
	private String rcvbBndsAmt91;	// 미회수 채권 금액 90일 이상
	
	@Pattern(regexp=Constants.PATTERN_REGEX.FLOAT, message="{validation.onlyfloat}")
	private String rcvbBndsTamt;	// 미회수 채권 합계액
	
	
	private List<String> errorColumns;	//에러컬럼들
	private String errorMsg;			// 유효성체크 에러 메세지
	
	
	/**
     * 엑셀 업로드 처리 객체
     * @param row
     * @return
     */
    public static AccountsReceivableExcelResponse from(Row row) {
    	return ExcelUtil.setObjectMapping(new AccountsReceivableExcelResponse(), row);
    }

}
