package com.lgl.gms.webapi.inc.dto.request;

import java.util.List;

import org.springframework.context.i18n.LocaleContextHolder;

import com.lgl.gms.webapi.common.context.UserInfo;

import lombok.Data;

/**
 * 손익당월실적 조회 Request
 * @author jokim
 * @date 2022.03.31
 */
@Data
public class BoIncDataInqRequest {

	private List<Integer> boIds;	// 법인 ID 배열
	private String searchSalYn;		// 매출실적 검색 여부( Y/N )
	private String searchExpYn;		// 비용실적 검색 여부( Y/N )
	private String searchHopayYn;	// 본사지급분 검색 여부( Y/N )
	
	private List<String> cl1Cds;		// 구분1
	private List<String> cl2Cds;		// 구분2
	private List<String> cl3Cds;		// 구분3
	
	private List<String> grp01s;	// 그룹1
	private List<String> grp02s;	// 그룹2
	private List<String> grp03s;	// 그룹3
	private List<String> svcTpys;	// 서비스
	private List<String> inc01s; 	// 유형
	
	private String stYymm;			// 손익 년월(시작)
	private String endYymm;			// 손익 년월(마지막)
	private String defCl;			// 확정구분
	private String untDp;			// 화폐
	private String inc10;			// 유형(매출/비용/본사지급분)
	private String custNm;			// 거래처

	private Integer compId = UserInfo.getCompId();	// 회사ID
	private String locale = UserInfo.getLocale();
}
