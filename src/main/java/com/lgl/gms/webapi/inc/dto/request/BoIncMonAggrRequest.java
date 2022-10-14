package com.lgl.gms.webapi.inc.dto.request;

import java.util.List;

import com.lgl.gms.webapi.common.context.UserInfo;
import com.lgl.gms.webapi.inc.dto.response.CommCodeResponse;

import lombok.Data;

/**
 * 손익집계 조회 Request
 * @author jokim
 * @date 2022.03.24
 */
@Data
public class BoIncMonAggrRequest {

	private Integer boId;			// 법인 ID
	private List<Integer> boIds;	// 법인 ID 배열
	private List<String> incItm2s;			// 사업구분
	private List<String> inc10s;			// 행선택(파라미터)
//	private List<CommCodeResponse> inc10s;	// 행선택
	private String incYymm;			// 손익 년월
	private String defCl;			// 확정 구분 (8일, 15일)
	private String plnRetCl;		// 계획_실적 구분	
	private String untDp;			// 화폐
	private String stYymm;		// 년월(시작일)
	private String endYymm;		// 년월(마지막일)
	private Integer compId = UserInfo.getCompId();
	private String locale = UserInfo.getLocale();
	
	private List<CommCodeResponse> inc10Lvl1s;	// 행선택(level3)

}
