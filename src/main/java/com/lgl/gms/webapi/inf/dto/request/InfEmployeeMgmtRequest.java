package com.lgl.gms.webapi.inf.dto.request;

import java.math.BigDecimal;

import com.lgl.gms.webapi.common.context.UserInfo;

import lombok.Data;

@Data
public class InfEmployeeMgmtRequest {
	
	private Integer boEmpId;	// 법인 직원 ID

	//조회 파라미터
	private Integer boId; 		// 법인 ID	
	private Integer subBoId;	// 지사 ID
	private Integer empTypId;	// 직원 유형 ID
	
	
	private String baseDate; 	// 기간
	private String baseDateStart;	// 기간 (시작일)
	private String baseDateEnd; 	// 기간 (종료일)
	
	
	private String searchOpt;	// 검색 조건
	private String startDate;	// 주재원 근무기간 시작일
	private String endDate;		// 주재원 근무기간 종료일
	
	
	private String assDt;		// 부임일
	private String rtnDt;		// 귀임일
	
	private Integer compId = UserInfo.getCompId();
	
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
	
	private Integer sheetNum;	// 업로드 시트 번호
	
	private String locale = UserInfo.getLocale();	
	
}
