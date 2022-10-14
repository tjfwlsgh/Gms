package com.lgl.gms.webapi.sys.dto.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * Response객체는 Select의 result를 바로 받아서 처리
 * 조회시 발생하는 부가정보(ex, 코드명)등의 추가 속성 및 연결된 하위 객체의 List를 정의 가능
 * cf) Model객체는 CUD를 위한 객체로 사용
 * 
 * 사용자 조회 결과 
 */
@Data
public class UserResponse {
	
	public UserResponse() {}

	// UserModel과 동일
	private Integer userNo;	
	private String userId;	// 사용자 id
	private String userNm;
	private String userNmEng;
	
	private String useLang;	// 사용 언어
	private String userTyp;	// 사용자 타입(S:관리자, H:본사, B: 법인, X:기타)
	private String userTypNm;
	
	// 사용자 pwd는 암호화된 문자열이고 복호화가 안되므로 
	// 서버 내부에서 비교용도로만 사용
//	private String userPwd;
	
	private Integer compId;	// 회사 id
	private String compNm;
	private String compNmEng;
	
	private Integer boId;	// 법인 id
	private String boNm;
	private String boNmEng;
	
	private String authCd;
	private String authNm;
	private String cntryCd;
	
	private String workIp;
	private String delYn;
	
	private String regNo;  // varchar type, userId 저장
	private String updNo;  	
	private Date regDt;
	private Date updDt;
	
	private String refToken;	// refresh Token
	private Integer loginFailCnt;	// 로그인 실패 횟수
	private String lockYn;			// 계정 잠금 여부
	private Date lockDt;			// 계정 잠금 일자

//	private String salt;	// salt(미사용)

	// pwd 변경 이력 테이블의 컬럼
	private String finlPwdUpdDt;	// 최종 비밀번호 변경일
	private String pwdChgDt; // 비밀번호 변경 예정일


}
