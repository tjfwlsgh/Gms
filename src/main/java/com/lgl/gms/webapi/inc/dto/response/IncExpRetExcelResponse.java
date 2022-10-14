package com.lgl.gms.webapi.inc.dto.response;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.apache.poi.ss.usermodel.Row;

import com.lgl.gms.webapi.common.constant.Constants;
import com.lgl.gms.webapi.common.util.ExcelUtil;

import lombok.Data;

/**
 * 손익>법인수익/비용 실적 엑셀 등록
 * @author jokim
 * @date 2022.03.14
 */
@Data
public class IncExpRetExcelResponse {

//	private String num;			// 순번
	
	@NotBlank(message="{validation.notblank}")
	@Pattern(regexp=Constants.PATTERN_REGEX.MONTH, message="{validation.month}")
	private String incMon;			// 월
	
	@NotBlank(message="{validation.notblank}")
	private String typCd;			// 유형
	
	@NotBlank(message="{validation.notblank}")
	private String cl1Cd;				// 구분1
	
	@NotBlank(message="{validation.notblank}")
	private String cl2Cd;				// 구분2
	
	private String cl3Cd;				// 구분3
	
//	@NotBlank(message="{validation.notblank}")
	private String	grp1Cd;		// 그룹1 코드
	
	@Pattern(regexp=Constants.PATTERN_REGEX.FLOAT, message="{validation.onlyfloat}")
	private String rvnAmt;		// 수익 금액
	
	@Pattern(regexp=Constants.PATTERN_REGEX.FLOAT, message="{validation.onlyfloat}")
	private String expAmt;			// 비용 금액
	
	private String incNote;			// 비고
	
	private List<String> errorColumns;	//에러컬럼들
	private String errorMsg;			// 유효성체크 에러 메세지
	
	// 헤더정보
	private Integer boId;			// 법인 ID
	private String incYymm;				// 손익실적 년월
	private String defCl;			// 확정 구분 (8일, 15일)
	
	// 코드들
	private String typCdc;			// 유형 코드
	private String cl1Cdc;			// 구분1 코드
	private String cl2Cdc;			// 구분2 코드
	private String cl3Cdc;			// 구분3 코드
	private String grp1Cdc;			// 그룹1 코드
	
	private Integer incItmId;			// 손익 항목  ID
	private Integer incItmDetId;		// 손익 항목 상세 ID
	
	// 집계
	private String aggYn;				// 집계 YN
	
	// seq
	private Integer seq;
	
	/**
     * 엑셀 업로드 처리 객체
     * @param row
     * @return
     */
    public static IncExpRetExcelResponse from(Row row) {
    	return ExcelUtil.setObjectMapping(new IncExpRetExcelResponse(), row);
    }
}
