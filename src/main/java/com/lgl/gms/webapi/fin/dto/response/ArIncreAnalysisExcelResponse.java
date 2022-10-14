package com.lgl.gms.webapi.fin.dto.response;

import java.util.List;

import javax.validation.constraints.Pattern;

import org.apache.poi.ss.usermodel.Row;

import com.lgl.gms.webapi.common.constant.Constants;
import com.lgl.gms.webapi.common.util.ExcelUtil;

import lombok.Data;

@Data
public class ArIncreAnalysisExcelResponse {
	
	private String trrtNm; 	// 지역
	
	private String boNm;	// 법인 명
	
	private String custNm;	// 거래처 명
	
	private String bllPrd;  // 청구기간
	
	private String crdtTrm; // credit Terms (신용거래조건)
	
	@Pattern(regexp=Constants.PATTERN_REGEX.FLOAT, message="{validation.onlyfloat}")
	private String rcvbBndsTamt;	// 미회수 채권 합계액
	
	@Pattern(regexp=Constants.PATTERN_REGEX.FLOAT, message="{validation.onlyfloat}")
	private String rcvbBndsAmt30;	// 미회수 채권 금액 30
	
	@Pattern(regexp=Constants.PATTERN_REGEX.FLOAT, message="{validation.onlyfloat}")
	private String rcvbBndsAmt60;	// 미회수 채권 금액 60
	
	@Pattern(regexp=Constants.PATTERN_REGEX.FLOAT, message="{validation.onlyfloat}")
	private String rcvbBndsAmt90;	// 미회수 채권 금액 90
	
	@Pattern(regexp=Constants.PATTERN_REGEX.FLOAT, message="{validation.onlyfloat}")
	private String rcvbBndsAmt91;	// 미회수 채권 금액 91

	private String lastMonth;	// 회수 계획 (전월)
	
	private String numPlnTxt;	// 회수 계획 텍스트 (당월)
	
	private String custCategory; 	// 거래처 업종

	private String ntpytRsnTxt;	// 미입금 사유 텍스트 
	
	private String contract;	// 계약서 존재 여부
	
	private String merchantShip; // 상선
	
	private String claimYn;	// 계약서 上 입금지연이자 청구문자 존재여부
	
	private String basedEmail;	// 계약서 없을 時 이메일 等 근거 (송부하세요)
	
	private String proof;	// 계약 증빙 없음
	
	private String gaia;	// GAIA

	
//	private String arYymm;	// 미수채권 년월
//	private String boId;	// 법인 ID
	private String boCustCd;	// 법인 거래처 코드
	private String bllStd;	// 청구 시작일
	private String bllEtd;	// 청구 종료일
	

	
	private List<String> errorColumns;	//에러컬럼들
	private String errorMsg;			// 유효성체크 에러 메세지

	
	/**
     * 엑셀 업로드 처리 객체
     * @param row
     * @return
     */
    public static ArIncreAnalysisExcelResponse from(Row row) {
    	return ExcelUtil.setObjectMapping(new ArIncreAnalysisExcelResponse(), row);
    }


}
