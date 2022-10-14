package com.lgl.gms.webapi.bse.persistence.model;

import com.lgl.gms.webapi.common.context.UserInfo;
import com.lgl.gms.webapi.common.model.BaseModel;

import lombok.Data;

@Data
public class FrmInfoModel extends BaseModel {
	
	private Integer frmId;		// 양식 ID
	private Integer frmCdId;	// 양식 코드 ID
	private String frmNm;		// 양식 명
	private String frmNmEng;	// 양식 명 영어
	private String frmTyp;		// 양식유형
	
	private String useYn;
	private Integer compId = UserInfo.getCompId();

}
