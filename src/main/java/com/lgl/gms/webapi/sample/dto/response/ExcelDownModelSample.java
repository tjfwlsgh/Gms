package com.lgl.gms.webapi.sample.dto.response;

import org.apache.poi.ss.usermodel.HorizontalAlignment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.lgl.gms.webapi.common.excel.ExcelColumn;
import com.lgl.gms.webapi.common.excel.ExcelMemo;

import lombok.Data;

/**
 * 엑셀다운로드시 DTO선언
 * @author jokim
 */
@Data
public class ExcelDownModelSample {
	
	@JsonInclude(Include.NON_NULL)	
	private int id;
	
	@JsonInclude(Include.NON_NULL)
	@ExcelColumn
	private String username;

	@JsonInclude(Include.NON_NULL)
	@ExcelMemo(targetField = "username")
	private String company;
	
	@JsonInclude(Include.NON_NULL)	
	private String address;
	
	@JsonInclude(Include.NON_NULL)
	@ExcelColumn
	private String gender;

	@JsonInclude(Include.NON_NULL)	
	private String userType;

	@JsonInclude(Include.NON_NULL)
	@ExcelColumn(columnWidth=5000)
	private String phone;
	
	@ExcelColumn(columnWidth=7000, bodyHorizonAlign = HorizontalAlignment.RIGHT, headerName = "이메일")
	@JsonInclude(Include.NON_NULL)
	private String email;
	
	@JsonInclude(Include.NON_NULL)	
	private String createdDt;
	
	@JsonInclude(Include.NON_NULL)
	private String updatedDt;


}
