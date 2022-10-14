package com.lgl.gms.webapi.bse.persistence.model;

import com.lgl.gms.webapi.common.context.UserInfo;
import com.lgl.gms.webapi.common.model.BaseModel;

import lombok.Data;

/**
 * @FileName    : BseBoModel.java
 * @Date        : 22.03.22
 * @Author      : hj.Chung
 * @Description : Model vo
 * @History     :
 */
 
@Data
public class BseBoModel extends BaseModel {
	 
	private Integer boId;		// 법인 ID
	private Integer subBoId;
	private Integer compId = UserInfo.getCompId();		// 회사 ID
	private String boCd;		// 법인 코드
	private String boCl;		// 법인 구분	
	private String boNm;		// 법인 명칭
	private String boSnm;		// 법인 단축명
	private String boNmEng;		// 법인 명칭 영어
	private String boSnmEng;	// 법인 단축명 영어
	private String cntryCd;		// 국가 코드
	private String crncyCd;		// 통화 코드
	private String std;			// 시작일
	private String etd;			// 종료일	
	private Integer pboId;		// 상위 법인 ID
	private Integer trrtId;		// 지역 ID
	private String useYn;		// 사용 여부		
//	private Date regDt;			// 등록 일자
//	private Date updDt;			// 수정 일자
	
}
