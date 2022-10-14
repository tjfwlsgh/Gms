package com.lgl.gms.webapi.bse.dto.response;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.apache.poi.ss.usermodel.Row;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.lgl.gms.webapi.common.constant.Constants;
import com.lgl.gms.webapi.common.util.ExcelUtil;
import com.lgl.gms.webapi.inc.dto.response.IncExpPlnExcelResponse;

import lombok.Data;

@Data
public class BseExchgRateInqExcelResponse {
	
	private String exchgRateDet;			// 통화
	
	private String boughtCash;		// 현찰사실때

	private String sellingCash;		// 현찰파실때
	
	private String sendMoney;		// 송금보내실때
	
	private String receiveMoney;	// 송금받으실때
	
	private String boughtTc;		// T/C사실때
	
	private String	sellingForeignCurrency;	// 외화수표파실때
	
	private String curExchgRate;	// 매매기준율
	
	private String exchangeRate;	// 환가료율
	
	private String conversionRateUs;	// 미화환산율
	
	private List<String> errorColumns;	//에러컬럼들
	private String errorMsg;			// 유효성체크 에러 메세지
	
	// 헤더정보
	private String yymm;		// 년월
	
	/**
     * 엑셀 업로드 처리 객체
     * @param row
     * @return
     */
    public static BseExchgRateInqExcelResponse from(Row row) {
    	return ExcelUtil.setObjectMapping(new BseExchgRateInqExcelResponse(), row);
    }
	
	

}
