package com.lgl.gms.webapi.inc.dto.response;

import java.math.BigDecimal;

import org.apache.poi.ss.usermodel.HorizontalAlignment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.lgl.gms.webapi.common.excel.ExcelColumn;
import com.lgl.gms.webapi.common.excel.ExcelMemo;

import lombok.Data;

/**
 * 손익조회 Response
 * @author jokim
 * @date 2022.03.18
 */
@Data
public class BoIncPerfInqExcelDownResponse {
	
	@ExcelColumn(headerName = "xls.header.itmNm")
	private String itmNm;			// 손익항목명
	
	@ExcelColumn(bodyHorizonAlign = HorizontalAlignment.RIGHT, headerName = "xls.header.mon,1")
	private BigDecimal mon01Amt;	// 월 01 금액	
	
	@ExcelColumn(bodyHorizonAlign = HorizontalAlignment.RIGHT, headerName = "xls.header.mon,2")
	private BigDecimal mon02Amt;	// 월 02 금액	
	
	@ExcelColumn(bodyHorizonAlign = HorizontalAlignment.RIGHT, headerName = "xls.header.mon,3")
	private BigDecimal mon03Amt;	// 월 03 금액	
	
	@ExcelColumn(bodyHorizonAlign = HorizontalAlignment.RIGHT, headerName = "xls.header.mon,4")
	private BigDecimal mon04Amt;	// 월 04 금액	
	
	@ExcelColumn(bodyHorizonAlign = HorizontalAlignment.RIGHT, headerName = "xls.header.mon,5")
	private BigDecimal mon05Amt;	// 월 05 금액	
	
	@ExcelColumn(bodyHorizonAlign = HorizontalAlignment.RIGHT, headerName = "xls.header.mon,6")
	private BigDecimal mon06Amt;	// 월 06 금액	
	
	@ExcelColumn(bodyHorizonAlign = HorizontalAlignment.RIGHT, headerName = "xls.header.mon,7")
	private BigDecimal mon07Amt;	// 월 07 금액	
	
	@ExcelColumn(bodyHorizonAlign = HorizontalAlignment.RIGHT, headerName = "xls.header.mon,8")
	private BigDecimal mon08Amt;	// 월 08 금액	
	
	@ExcelColumn(bodyHorizonAlign = HorizontalAlignment.RIGHT, headerName = "xls.header.mon,9")
	private BigDecimal mon09Amt;	// 월 09 금액	
	
	@ExcelColumn(bodyHorizonAlign = HorizontalAlignment.RIGHT, headerName = "xls.header.mon,10")
	private BigDecimal mon10Amt;	// 월 10 금액	
	
	@ExcelColumn(bodyHorizonAlign = HorizontalAlignment.RIGHT, headerName = "xls.header.mon,11")
	private BigDecimal mon11Amt;	// 월 11 금액
	
	@ExcelColumn(bodyHorizonAlign = HorizontalAlignment.RIGHT, headerName = "xls.header.mon,12")
	private BigDecimal mon12Amt;	// 월 12 금액
	
	@ExcelColumn(bodyHorizonAlign = HorizontalAlignment.RIGHT, headerName = "xls.header.sum")
	private BigDecimal sum;		// 누계
	
	// 메모
	@ExcelMemo(targetField = "mon01Amt")
	private String mon01Memo;		// 월 01 메모	
	@ExcelMemo(targetField = "mon02Amt")
	private String mon02Memo;		// 월 02 메모	
	@ExcelMemo(targetField = "mon03Amt")
	private String mon03Memo;		// 월 03 메모
	@ExcelMemo(targetField = "mon04Amt")
	private String mon04Memo;		// 월 04 메모
	@ExcelMemo(targetField = "mon05Amt")
	private String mon05Memo;		// 월 05 메모	
	@ExcelMemo(targetField = "mon06Amt")
	private String mon06Memo;		// 월 06 메모
	@ExcelMemo(targetField = "mon07Amt")
	private String mon07Memo;		// 월 07 메모
	@ExcelMemo(targetField = "mon08Amt")
	private String mon08Memo;		// 월 08 메모	
	@ExcelMemo(targetField = "mon09Amt")
	private String mon09Memo;		// 월 09 메모	
	@ExcelMemo(targetField = "mon10Amt")
	private String mon10Memo;		// 월 10 메모	
	@ExcelMemo(targetField = "mon11Amt")
	private String mon11Memo;		// 월 11 메모
	@ExcelMemo(targetField = "mon12Amt")
	private String mon12Memo;		// 월 12 메모
	
	
}
