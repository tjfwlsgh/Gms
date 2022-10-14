package com.lgl.gms.webapi.inc.dto.response;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.apache.poi.ss.usermodel.Row;

import com.lgl.gms.webapi.common.constant.Constants;
import com.lgl.gms.webapi.common.util.ExcelUtil;

import lombok.Data;

/**
 * 손익>법인매출계획 엑셀 등록
 * @author jokim
 * @date 2022.02.23
 */
@Data
public class IncSaleRetExcelResponse {
	
//	@Pattern(regexp=Constants.PATTERN_REGEX.NUM, message="{validation.onlynumber}")
//	@NotBlank
//	private String num;				// 순번
	
	@NotBlank(message="{validation.notblank}")
	@Pattern(regexp=Constants.PATTERN_REGEX.MONTH, message="{validation.month}")
	private String incMon;			// 월
	
	private String boCustCd;		// 거래처코드
	
	private String custNm;		// 거래처명
	
	@NotBlank(message="{validation.notblank}")
	private String grp1Cd;				// 그룹1
	
	@NotBlank(message="{validation.notblank}")
	private String svcTyp;			// 서비스유형명	
	
	@NotBlank(message="{validation.notblank}")@NotBlank(message="{validation.notblank}")
	private String grp2Cd;				// 그룹2
	
	private String grp3Cd;				// 그룹3
	
	@Pattern(regexp=Constants.PATTERN_REGEX.FLOAT, message="{validation.onlyfloat}")
	@NotBlank(message="{validation.notblank}")
	private String salAmt;			// 매출액
	
	@Pattern(regexp=Constants.PATTERN_REGEX.FLOAT, message="{validation.onlyfloat}")
	@NotBlank(message="{validation.notblank}")
	private String salCst;			// 매출원가
	
//	@Pattern(regexp=Constants.PATTERN_REGEX.FLOAT, message="{validation.onlyfloat}")
	private String seaeAmt;		// 판매관리비
	
	@NotBlank(message="{validation.notblank}")
	@Pattern(regexp=Constants.PATTERN_REGEX.FLOAT, message="{validation.onlyfloat}")
	private String lbrCost;			// 인건비
	
	@NotBlank(message="{validation.notblank}")
	@Pattern(regexp=Constants.PATTERN_REGEX.FLOAT, message="{validation.onlyfloat}")
	private String expAmt;			// 경비
	
//	@Pattern(regexp=Constants.PATTERN_REGEX.FLOAT, message="{validation.onlyfloat}")	
	private String ifoAmt;			// 영업이익
	
//	@NotBlank(message="{validation.notblank}")
	private String bizClCd;			// 영업구분
	
//	@NotBlank(message="{validation.notblank}")
	private String bizObjCd;			// 영업주체
	
	private String frArea;			// From_Area
	private String frSbuArea;		// From SBU Area
	private String toSbuArea;			// To SBU Area
	
//	private String incNote;			// 비고
	
	private List<String> errorColumns;	//에러컬럼들
	private String errorMsg;			// 유효성체크 에러 메세지
	
	// 헤더정보
	private Integer boId;			// 법인 ID
	private String incYymm;				// 손익실적 년월
	private String defCl;			// 확정 구분 (8일, 15일)
	
	// 코드들.
	private String svcTypc;			// 서비스유형
	private Integer grp1Cdc;		// 그룹1
	private Integer grp2Cdc;		// 그룹2	
	private Integer grp3Cdc;		// 그룹3	

	/**
     * 엑셀 업로드 처리 객체
     * @param row
     * @return
     */
    public static IncSaleRetExcelResponse from(Row row) {
    	return ExcelUtil.setObjectMapping(new IncSaleRetExcelResponse(), row);
    }
}
