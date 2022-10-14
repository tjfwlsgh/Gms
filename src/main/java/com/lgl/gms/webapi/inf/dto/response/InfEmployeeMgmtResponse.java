package com.lgl.gms.webapi.inf.dto.response;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
public class InfEmployeeMgmtResponse {
	
	@JsonInclude(Include.NON_NULL)
	private Integer boEmpId;	// 법인 인원 ID
	@JsonInclude(Include.NON_NULL)
	private Integer boId; 		// 법인 ID 	
	private Integer subBoId;	// 지사 ID
	private String boNm; 		// 법인 명 (법인 + 지사)
	private String subBoNm;		// 지사 명 (법인 코드 'B'만)
	private String boNmH;		// 법인 명 (법인 코드 'H'만)

	private Integer empTypId;	// 직업 유형 ID
	private String empTypNm;	// 직업 유형 이름
	private String baseDate; 	// 기준일
	private String searchOpt;	// 검색 조건
	private String startDate;	// 주재원 근무기간 시작일
	private String endDate;		// 주재원 근무기간 종료일
	
	private String assDt;		// 부임일
	private String rtnDt;		// 귀임일
	
	private BigDecimal seq;		// 순서

	private String empNm;		// 직원 명
	private String sex;			// 성별

	private String dpt;			// 부서
	private String pst;			// 직위
	private String hoPst;		// 본사 직위
	
	private String cty;			// 도시
	private String job;			// 업무
	private String doe;			// 입사일
	private String rgd;			// 퇴사일
	
	private String delYn;		// 삭제 YN
	
	private BigDecimal accFmlyWife;	// 동반가족 - 처
	private BigDecimal accFmlyChild; // 동반가족 - 자
	private String hsngContStd;	// 주택 계약 시작일
	private String hsngContEtd;	// 주택 계약 종료일
	
	private String hsngContDocYn; // 주택 계약 서류 YN
	private BigDecimal hre;		// 임차료
	private String crncyCd;		// 통화코드
	
	private String boCl;		// 통화코드
	private String remainDt;	// 잔여임기
	
	private String period;		// 근속기간

	public InfEmployeeMgmtResponse() {}
}
