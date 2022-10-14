package com.lgl.gms.webapi.sample.dto.response;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.apache.poi.ss.usermodel.Row;

import com.lgl.gms.webapi.common.constant.Constants;
import com.lgl.gms.webapi.common.excel.ExcelCodeColumn;
import com.lgl.gms.webapi.common.util.ExcelUtil;

import lombok.Data;

/**
 * 엑셀샘플 DTO , 멤버변수 타입은 모두 String으로 만든다
 * @author jokim
 *	@date 2021.01.27
 *	@description 엑셀 column 순서와 맞추어야함
 */
@Data
public class ExcelUploadModelSample {

	@Pattern(regexp=Constants.PATTERN_REGEX.NUM, message="{validation.onlynumber}")
	@NotBlank
	private String num;				// 순번
	
	@NotBlank(message="{validation.notblank}")
	@Pattern(regexp=Constants.PATTERN_REGEX.MONTH, message="{validation.month}")
	private String month;			// 월
	
	@NotBlank(message="{validation.cust}")
	private String boCustCd;		// 거래처코드
	
	@NotBlank(message="{validation.cust}")
	private String custNm;		// 거래처명
	
	@NotBlank(message="{validation.notblank}")
	private String svcNm;			// 서비스유형명
	
	private String grp1CdNm;				// 그룹1
	
	@NotBlank(message="{validation.notblank}")
	private String grp2CdNm;				// 그룹2
	
	private String grp3CdNm;				// 그룹3
	
	@Pattern(regexp=Constants.PATTERN_REGEX.FLOAT, message="{validation.onlyfloat}")
	@NotBlank(message="{validation.notblank}")
	private String salAmt;			// 매출액
	
	@Pattern(regexp=Constants.PATTERN_REGEX.FLOAT, message="{validation.onlyfloat}")
	@NotBlank(message="{validation.notblank}")
	private String salCst;			// 매출원가
	
	@Pattern(regexp=Constants.PATTERN_REGEX.FLOAT, message="{validation.onlyfloat}")
	@NotBlank(message="{validation.notblank}")
	private String seaeAmt;		// 판매관리비

	@Pattern(regexp=Constants.PATTERN_REGEX.FLOAT, message="{validation.onlyfloat}")
	private String lbrCost;			// 인건비
	
	@Pattern(regexp=Constants.PATTERN_REGEX.FLOAT, message="{validation.onlyfloat}")
	private String expAmt;			// 경비
	
	@Pattern(regexp=Constants.PATTERN_REGEX.FLOAT, message="{validation.onlyfloat}")
	@NotBlank(message="{validation.notblank}")
	private String ifoAmt;			// 영업이익
	
	@NotBlank(message="{validation.notblank}")
	private String ifoTyp;			// 영업구분
	
	@NotBlank(message="{validation.notblank}")
	private String ifoMain;			// 영업주체
	
	private String fromArea;			// From_Area
	private String fromSbuArea;		// From SBU Area
	private String toSbuArea;			// To SBU Area
	
	private String remark;			// 비고
	
	private List<String> errorColumns;	//에러컬럼들
	private String errorMsg;		// 유효성체크 에러 메세지
	
	// 코드들.
	private String svcTyp;			// 서비스유형
	private String grp1Cd;				// 그룹1
	private String grp2Cd;				// 그룹2	
	private String grp3Cd;				// 그룹3	
	
	
	/**
     * 엑셀 업로드 처리 객체
     * @param row
     * @return
     */
    public static ExcelUploadModelSample from(Row row) {
        return ExcelUtil.setObjectMapping(new ExcelUploadModelSample(), row);
    }

}
