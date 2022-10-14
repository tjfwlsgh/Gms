package com.lgl.gms.webapi.inc.dto.request;

import java.util.List;

import org.springframework.context.i18n.LocaleContextHolder;

import com.lgl.gms.webapi.common.context.UserInfo;

import lombok.Data;

/**
 * 손익조회 Request
 * @author jokim
 * @date 2022.03.18
 */
@Data
public class BoIncPerfInqRequest {

	private Integer boId;		// 법인 ID
	private List<Integer> boIds;		// 법인 ID 배열
	private String incYymm;	// 손익 년월
	private String defCl;			// 확정 구분 (8일, 15일)
	private String plnRetCl;		// 계획_실적 구분	
	private String untDp;			// 화폐
	private String stYymm;		// 년월(시작일)
	private String endYymm;		// 년월(마지막일)
	
	// 메모포함여부
	private Boolean includeMemo = true;
	
	private Integer compId = UserInfo.getCompId();	// 회사ID
	private String locale = UserInfo.getLocale();

}
