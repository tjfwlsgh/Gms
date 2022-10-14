package com.lgl.gms.webapi.bse.persistence.model;

import java.math.BigDecimal;

import com.lgl.gms.webapi.common.context.UserInfo;
import com.lgl.gms.webapi.common.model.BaseModel;

import lombok.Data;

/**
 * S3 Obj 정보 Model
 * @author jokim
 * @Date 2022.06.21
 */
@Data
public class S3ObjInfoModel extends BaseModel {
	
	private Integer objId;		// 오프젝트 ID	
	private String objNm;		// 오프젝트 명
	private String objNmEng;	// 오프젝트 명(영문)
	private String objDet;		// 오프젝트 상세 (URL)
	private String objVal;		// 오프젝트 값
	private BigDecimal viewSeq;	// 순서
	private String useYn;		// 사용 YN
	
	private String useLng = UserInfo.getLocale();	// 사용 언어
	
	private Integer compId = UserInfo.getCompId();
		
}
