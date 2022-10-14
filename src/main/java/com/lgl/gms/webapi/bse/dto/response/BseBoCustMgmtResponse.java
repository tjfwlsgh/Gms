package com.lgl.gms.webapi.bse.dto.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.lgl.gms.webapi.bse.dto.request.BseBranchOfficeMgmtRequest;

import lombok.Data;

@Data
public class BseBoCustMgmtResponse {
	
	@JsonInclude(Include.NON_NULL)
	private Integer boId;		// 법인 ID
	@JsonInclude(Include.NON_NULL)
	private Integer subBoId;	// 지사 ID
	@JsonInclude(Include.NON_NULL)
	private String boCustCd;	// 법인 거래처 코드
	@JsonInclude(Include.NON_NULL)
	private String custNm;		// 거래처 명
	@JsonInclude(Include.NON_NULL)
	private String custSnm;		// 거래처 단축명
	@JsonInclude(Include.NON_NULL)
	private String custNmEng;	// 거래처 명 영어
	@JsonInclude(Include.NON_NULL)
	private String custSnmEng;	// 거래처 단축명 영어
	@JsonInclude(Include.NON_NULL)
	private String delYn;		// 삭제 Yn
	@JsonInclude(Include.NON_NULL)
	private Date regDt;			// 등록 일자
	@JsonInclude(Include.NON_NULL)
	private Date updDt;			// 수정 일자
	
	private String boNmH;	// 법인 명 (boCl = 'H')
	private String subBoNm;	// 지사 명 (boCl = 'B')
	
	public BseBoCustMgmtResponse() {}
}
