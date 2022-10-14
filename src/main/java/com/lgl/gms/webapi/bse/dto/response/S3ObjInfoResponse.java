package com.lgl.gms.webapi.bse.dto.response;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * 파일정보 Response
 * @author jokim
 * @Date @Date 2022.06.21
 */
@Data
public class S3ObjInfoResponse {
	
	private Integer no;			// rownum
	private Integer objId;		// 오프젝트 ID	
	private String objNm;		// 오프젝트 명
	private String objNmKr;		// 오프젝트 명(한글)
	private String objNmEng;	// 오프젝트 명(영문)
	private String objDet;		// 오프젝트 상세 (URL)
	private String objVal;		// 오프젝트 값
	private BigDecimal viewSeq;	// 순서
	private String useYn;		// 사용 YN
	
	private String workIp;		// 작업자_IP
	private String useLng;		// 사용 언어
	private String regNo;	
	private String updNo;
	private Date regDt;
	private Date updDt;	
	
}
