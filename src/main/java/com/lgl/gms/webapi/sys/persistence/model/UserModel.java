package com.lgl.gms.webapi.sys.persistence.model;

import java.util.Date;

import com.lgl.gms.webapi.common.model.BaseModel;

import lombok.Data;

/**
 * Model객체는 Insert/Update/Delete를 위한 객체로 사용
 * cf) Select의 결과집합은 Response객체에서 바로 받아서 처리
 */
@Data
//public class UserModel {
 public class UserModel extends BaseModel{
		
	private Integer userNo;	
	private String userId;	// 사용자 id
	private String userNm;
	private String userNmEng;
	
	private String useLang;	// 사용 언어
	private String userTyp;	// 사용자 타입(S:관리자, H:본사, B: 법인, X:기타)
	
	private String userPwd;
	
	private Integer compId;	// 회사 id
//	private String compNm;
//	private String compNmEng;
	
	private Integer boId;	// 법인 id
//	private String boNm;
//	private String boNmEng;
	
	private String authCd;
	private String cntryCd;
	
	private String delYn;

// 2022.03.20 yhlee baseModel을 상속받아서 사용하므로 반드시 제거 필요
// 회사id는 사용자정보 조회시 사용되므로 제거가 안되는데...
//	private String regNo;  // varchar type, userId 저장
//	private String updNo;  	
//	private Date regDt;
//	private Date updDt;
//	private String workIp;

	// --- 비밀번호 변경 관련 필드
	private String pwdChgDt;		// 비밀번호 변경(예정)일
	private String pwdChgMustYn;	// 비밀번호 변경 강제 여부

	private String refToken;	// refresh Token
	private Integer loginFailCnt;	// 로그인 실패 횟수
	private String lockYn;			// 계정 잠금 여부
	private Date lockDt;			// 계정 잠금 일자

	private String hqYn;			// 본사 여부

//	private String salt;	// salt(미사용)
	
//	private Date finlPwdUpdDt;	// 최종 비밀번호 변경일
//	private Date pwdChgDt; // 비밀번호 변경 예정일
	
}