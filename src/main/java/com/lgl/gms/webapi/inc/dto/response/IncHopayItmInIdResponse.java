package com.lgl.gms.webapi.inc.dto.response;

import lombok.Data;

/**
 * 본사지급분 항목
 * @author jokim
 * @date 2022.03.15
 */
@Data
public class IncHopayItmInIdResponse {
	
	private String	typCd;				// 유형 CD
	private String	cl1Cd;				// 구분1 코드
	private String	cl3Cd;				// 구분3 코드
	
	private String payItmCl2;			// 급여항목 구분2
	private String rentItmCl2;			//주택임차료 구분2
	private String tuitionItmCl2;		// 자녀학자금 구분2
	private String etcItmCl2;			// 기타 항목 구분2
	
	private Integer payItmId;			// 급여항목 ID
	private Integer rentItmId;			//주택임차료 ID
	private Integer tuitionItmId;		// 자녀학자금 ID
	private Integer etcItmId;			// 기타 항목 ID
	
	private Integer payItmDetId;			// 급여항목 ID
	private Integer rentItmDetId;		//주택임차료 ID
	private Integer tuitionItmDetId;	// 자녀학자금 ID
	private Integer etcItmDetId;			// 기타 상세항목ID
	
	

}
