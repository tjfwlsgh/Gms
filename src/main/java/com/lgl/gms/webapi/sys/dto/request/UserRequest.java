package com.lgl.gms.webapi.sys.dto.request;

import java.util.Date;

import lombok.Data;

@Data
public class UserRequest {

	// 조회파라미터
	private String userTyp;	// 사용자 타입(S:관리자, H:본사, B: 법인, X:기타)
	public String userId;
	public String userNm;
	
	public String sDate;	// 추후 사용을 위해 정의
	public String eDate;

	// --- 아래는 UserModel과 동일
	private Integer userNo;	
	private String userNmEng;
	
	private String useLang;	// 사용 언어

	// 사용자가 수정한 pwd(암호화되기 전의 평문 pwd)
	private String userPwd;
//	private String prePw;	// 이전 Pwd로 필요할 때 사용
	
	private Integer compId;	// 회사 id
//	private String compNm;
//	private String compNmEng;
	
	private Integer boId;	// 법인 id
//	private String boNm;
//	private String boNmEng;
	
	private String authCd;
	private String cntryCd;
	
	private String delYn;
	private String lockYn;			// 계정 잠금 여부

	// ---- 아래는 변경 가능성 없거나 시스템적으로 변경됨
//	private String workIp;
//	private String regNo;  // varchar type, userId 저장
//	private String updNo;  	
//	private Date regDt;
//	private Date updDt;
//	
//	private String refToken;	// refresh Token
//	private Integer loginFailCnt;	// 로그인 실패 횟수
//	private Date lockDt;			// 계정 잠금 일자
//
//	private Date finlPwdUpdDt;	// 최종 비밀번호 변경일
//	private Date pwdChgDt; // 비밀번호 변경 예정일

}
