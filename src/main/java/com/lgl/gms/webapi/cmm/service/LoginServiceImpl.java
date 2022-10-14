package com.lgl.gms.webapi.cmm.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgl.gms.config.PropConfig;
import com.lgl.gms.webapi.cmm.dto.request.CommonAuthRequest;
import com.lgl.gms.webapi.cmm.dto.request.CommonMenuRequest;
import com.lgl.gms.webapi.cmm.dto.request.LoginRequest;
import com.lgl.gms.webapi.cmm.dto.response.CommonAuthResponse;
import com.lgl.gms.webapi.cmm.dto.response.CommonMenuResponse;
import com.lgl.gms.webapi.cmm.dto.response.LoginFailResponse;
import com.lgl.gms.webapi.cmm.dto.response.LoginResponse;
import com.lgl.gms.webapi.cmm.dto.response.LoginUserInfo;
import com.lgl.gms.webapi.cmm.persistence.dao.CommonMenuDao;
import com.lgl.gms.webapi.cmm.persistence.dao.LoginLogDao;
import com.lgl.gms.webapi.cmm.persistence.model.LoginLogModel;
import com.lgl.gms.webapi.common.dto.TokenInfo;
import com.lgl.gms.webapi.common.dto.code.ResponseCode;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.common.util.CommonUtil;
import com.lgl.gms.webapi.common.util.HMacUtil;
import com.lgl.gms.webapi.common.util.JWTUtil;
import com.lgl.gms.webapi.sys.dto.request.PwdChangeRequest;
import com.lgl.gms.webapi.sys.dto.response.PwdChangeResponse;
import com.lgl.gms.webapi.sys.persistence.dao.UserDao;
import com.lgl.gms.webapi.sys.persistence.model.UserModel;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("LoginService")
public class LoginServiceImpl implements LoginService {
	@Autowired
	public UserDao userDao;

	@Autowired
	public LoginLogDao loginLogDao;
	
	@Autowired
	public CommonMenuDao cmmMenuDao;

	@Autowired
	public PropConfig prop;

	@Autowired
	public JWTUtil jwtUtil;

	/**
	 * 로그인 
	 */
	public BaseResponse loginUser(LoginRequest loginReq, HttpServletRequest request, HttpServletResponse response) {
		LoginFailResponse failData = new LoginFailResponse();
		failData.setLockYn("N");
		failData.setFailCnt("0");
		try {
			log.debug("===>>> LoginServiceImpl > loginUser() > loginRequest : "+ loginReq); 
			// 필수 param없으면 에러 발생
			if (loginReq.getUserId() == null || loginReq.getUserPwd() == null) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				// 유효하지 않은 파라미터
				return new BaseResponse(ResponseCode.C781, failData);
			}
			
			HashMap<String, Object> loginParam = new HashMap<>();
			loginParam.put("userId", loginReq.getUserId());
			loginParam.put("compId", loginReq.getCompId());	// 로그인 화면에서 compId를 지정 필요
			// UserId, compId로 DB의 사용자 정보 조회
			UserModel user = userDao.selectUserById(loginParam);
			
			log.debug("===>>> LoginServiceImpl > loginUser() > user : " + user);
			if (user == null) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				// C701:사용자가 존재하지 않음
				return new BaseResponse(ResponseCode.C701, failData);
			}

			if (user.getLockYn() != null && user.getLockYn().equals("Y")) {
				// 계정잠금 시간을 확인해서 현재시간보다 이전이면 lockYn을 Y로 조회
				// lockYn이 Y이면 로그인 진행
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				failData.setLockYn(user.getLockYn());
				failData.setFailCnt(user.getLoginFailCnt() + "");
				// C709: 계정 잠금 처리
				return new BaseResponse(ResponseCode.C709, failData);
			}

//			String workIp = request.getRemoteAddr();
			String workIp = CommonUtil.getClientIP(request);
			log.debug("===>>> LoginServiceImpl > loginUser() > workIp : " + workIp);

			// 로그인관련 정보를 DB 사용자 정보에 저장하기 위한 파라미터 객체
			UserModel param = new UserModel();
			param.setUserId(user.getUserId());	// 키 항목이므로 필수
			param.setUpdNo(user.getUserId());	// 사용자 id 설정
			param.setWorkIp(workIp);
			user.setWorkIp(workIp);	// 뒤의 토큰 생성 시 현재 로그인 work ip 필요

			// 로그인 로그 정보 저장용 파라미터 객체
			LoginLogModel logParam = new LoginLogModel();
			logParam.setLoginId(user.getUserId());	// 
			logParam.setLogTyp("I");	// I: Log-In
			logParam.setLoginIp(workIp);	

			HMacUtil hmac = new HMacUtil();

			log.debug("===>>> LoginServiceImpl > loginUser() > login.pwd >  : "+ loginReq.getUserPwd());
			log.debug("===>>> LoginServiceImpl > loginUser() > user.pwd >  : "+ user.getUserPwd());
			log.debug("===>>> LoginServiceImpl > loginUser() > hmac.getEncHmacSha(loginReq.getUserPwd() >  : "+ hmac.getEncHmacSha(loginReq.getUserPwd()));

			// 로그인 실패횟수 초기화
			// 기존 값이 5이면 잠금됐다가 다시 로그인하는 경우이므로 0로 초기화
			int loginFailCnt = user.getLoginFailCnt();
			if (loginFailCnt == 5) {
				loginFailCnt = 0;
			}
			// 요청의 사용자 pw를 사용자의 pw와 비교한 결과 틀리면  
			if (!hmac.getEncHmacSha(loginReq.getUserPwd()).equals(user.getUserPwd())) {
				// Login Fail Count를 DB에 저장
				// fail count가 5이면 계정 잠금 처리도 같이 처리
				loginFailCnt = loginFailCnt + 1;
				if (loginFailCnt == 5) {
					// DB 저장할 때 잠금일자도 30분뒤로 설정 -> 다음 로그인시 현재시간으로 비교 처리
					param.setLockYn("Y");	
				}
				param.setLoginFailCnt(loginFailCnt);
				// *** 로그인 실패 카운트 저장
				
				userDao.updateUser(param);
				
				logParam.setErrYn("Y");
				if (loginFailCnt < 5) {
					logParam.setErrRsn("Login Failed! fail count : " + loginFailCnt);
				} else {
					logParam.setErrRsn("Login Locked!!!, fail count : " + loginFailCnt);
				}
				loginLogDao.insertLoginLog(logParam);	// 로그 저장

				// fail cnt를 DB에서 읽어서 설정
				failData.setFailCnt(Integer.toString(param.getLoginFailCnt()));
				// HttpResponse.status : 401 리턴
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				log.warn("===>>> LoginServiceImpl > loginUser() > failData >  : "+ failData);
				// C703: 로그인 실패
				BaseResponse res = new BaseResponse(ResponseCode.C703);
				res.setData(failData);
				log.warn("===>>> LoginServiceImpl > loginFail > res : "+ res);
				return res;
			}

			// --- JWT Access토큰과 Refresh토큰 발행 ---
			// 현재 yml에 정의된 유효기간은 AT:60분, RT:1주
			String tokenKey = jwtUtil.generateToken(user);
			String tokenKeyRef = jwtUtil.generateRefreshToken(user);
			// Refresh 토큰 저장
			param.setRefToken(tokenKeyRef);
			
			// 로그인 성공 시 LoginFaliCnt, lockYn, lockDt 초기화
			param.setLoginFailCnt(0);	
			param.setLockYn("N");
			param.setLockDt(null);
			log.debug("===>>> LoginServiceImpl > loginUser() > tokenKey, tokenKeyRef : "+ tokenKey + ", " +tokenKeyRef);
			log.debug("===>>> LoginServiceImpl > loginUser() > param : "+ param);
			// *** 로그인 성공 => RT정보 저장
			userDao.updateUser(param);
			
			loginLogDao.insertLoginLog(logParam);	// 로그 저장
			
			// --- 로그인 사용자 정보 구성 ---
			LoginUserInfo userInfo = new LoginUserInfo();
			userInfo.setUserId(user.getUserId());
			userInfo.setUserNm(user.getUserNm());
			userInfo.setUserNmEng(user.getUserNmEng());
			userInfo.setUserTyp(user.getUserTyp());
			userInfo.setCompId(user.getCompId());
			userInfo.setBoId(user.getBoId());
			userInfo.setCntryCd(user.getCntryCd());
			userInfo.setAuthCd(user.getAuthCd());
			userInfo.setUseLang(user.getUseLang());
			userInfo.setPwdChgDt(user.getPwdChgDt());
			userInfo.setPwdChgMustYn(user.getPwdChgMustYn());
			userInfo.setHqYn(user.getHqYn());
//			log.debug("===>>> LoginServiceImpl > loginUser() > userInfo : "+ userInfo);

			// --- 사용권한을 가진 메뉴 정보 구성 ---
			CommonMenuRequest menuReq = new CommonMenuRequest();
			menuReq.setCompId(user.getCompId());
			menuReq.setAuthCd(user.getAuthCd());
//			log.debug("===>>> LoginServiceImpl > loginUser() > menuReq : "+ menuReq);
			// 메뉴 정보 조회
			List<CommonMenuResponse> menuRes = (List<CommonMenuResponse>) cmmMenuDao.selectMenuList(menuReq);			
//			log.debug("===>>> LoginServiceImpl > loginUser() > menuRes : "+ menuRes);

			// --- 메뉴별 사용권한 정보 구성 ---
			CommonAuthRequest authReq = new CommonAuthRequest();
			authReq.setAuthCd(user.getAuthCd());
			authReq.setCompId(user.getCompId());
//			log.debug("===>>> LoginServiceImpl > loginUser() > authReq : "+ authReq);
			// 메뉴 정보 조회
			List<CommonAuthResponse> authRes = (List<CommonAuthResponse>) cmmMenuDao.selectAuthList(authReq);			
//			log.debug("===>>> LoginServiceImpl > loginUser() > authRes : "+ authRes);
			
			// --- response 정보 구성 ---
			// res의 code를 성공으로 설정하고 data에 로그인 결과를 저장하고 리턴
			BaseResponse res = new BaseResponse(ResponseCode.C200);
			LoginResponse loginRes = new LoginResponse();
			loginRes.setAccessToken(tokenKey);
			loginRes.setRefToken(tokenKeyRef);
			// loginResponse에 로그인 사용자 정보 설정
			loginRes.setUserInfo(userInfo);
			loginRes.setMenus(menuRes);
			loginRes.setAuths(authRes);
			
			//log.debug("===>>> LoginServiceImpl > loginUser() > loginRes : "+ loginRes);
			
			res.setData(loginRes);
			log.debug("===>>> LoginServiceImpl > loginUser() > res : "+ res);
			
			return res;

		} catch (Exception e) {
			log.warn(e.toString(), e);
			// HttpResponse.status : 401 리턴
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);	
			// 에러가 발생하면 내부 에러로 리턴
			return new BaseResponse(ResponseCode.C589, failData);
		}
	}

	/**
	 * 로그아웃
	 * AT가 만료되어 에러가 발생하면 RT리셋 및 로그처리가 안되는 경우가 발생
	 * 로그아웃의 경우 예외적인(비정상) 종료일 경우가 많으므로 에러는 무시
	 * ex) 브라우저 탭 닫기 또는 브라우저 종료, 시스템 비정상 종료, 10분이상 미사용 중 로그아웃(AT만료) 등 
	 */
	@Override
	public BaseResponse logout(String auth, HttpServletResponse response) {
		
		// MvcInterceptor를 거치지 않으므로 직접 request header의 토큰 정보를 취득
		log.debug("===>>> LoginServiceImpl > logout() exec..."+ auth);
//		if (auth == null || !auth.startsWith("Bearer ")) {
		if (!auth.startsWith("Bearer ")) {
			System.out.println("===>>> LoginServiceImpl > logout() > xxxx");
			// 리소스 접근 권한 없음
			return new BaseResponse(ResponseCode.C402);
		}
		auth = auth.substring("Bearer ".length());
		log.debug("===>>> LoginServiceImpl > logout() > auth : " + auth);
		
		try {
	
			// AT에서 사용자id를 취득해서 DB의 저장된 RT를 리셋 
			// AT는 10분이 만료기한이므로 로그아웃시 만료 에러가 발생할 가능성이 높음
			// 토큰 만료 에러가 발생했다 하더라도 RT리셋만 못하는 것이므로 무시 
			TokenInfo tokenInfo = (TokenInfo) jwtUtil.getTokenInfo(auth);
			log.debug("===>>> LoginServiceImpl > logout() > tokenInfo : " + tokenInfo);
						
			UserModel user = new UserModel();
			String userId = tokenInfo.getUserId();
			user.setUserId(userId);
			user.setUpdNo(userId);
			user.setWorkIp(tokenInfo.getWorkIp());
			user.setRefToken("");
			log.debug("===>>> LoginServiceImpl > logout() > user : " + user);
			// *** 로그아웃 시 RT 정보 리셋
			userDao.updateUser(user);
			
			// 로그인 로그 정보 저장용 파라미터 객체
			LoginLogModel logParam = new LoginLogModel();
			logParam.setLoginId(tokenInfo.getUserId());	// 
			logParam.setLogTyp("O");	// O: Log-Out
			logParam.setLoginIp(tokenInfo.getWorkIp());	
			
			log.debug("===>>> LoginServiceImpl > logout() > logParam : " + logParam);
			loginLogDao.insertLoginLog(logParam);	// 로그 저장
			
			return new BaseResponse(ResponseCode.C705);	// 로그아웃 
			
		} catch (Exception e) {
			log.warn(e.toString(), e);
			// 로그 아웃에 대한 에러는 무시하고 로그아웃 상태코드(705)리턴
//			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//			return new BaseResponse(ResponseCode.C589);	// 서버에러
			return new BaseResponse(ResponseCode.C705);	// 로그아웃
		}
	}

	/**
	 * RT가 만료되지 않았으면 AT 재발행
	 *  AT가 만료된 경우 front-end의 response interceptor에서 다시 요청이 발생하고
	 *  이 요청을 처리하는 메소드
	 *  *** RT가 만료되지 않았고 RT와 DB의 RT가 일치할때 재발행
	 *      만약 중복 로그인인 경우 가장 최근 로그인자의 RT가 저장되므로 이전 로그인자의 RT와 불일치
	 * 
	 */
	@Override
	public BaseResponse refreshToken(HttpServletRequest request, HttpServletResponse response) {

		String tokenKey = "";
		String tokenKeyRef = "";
		// request header의 Authorization에 AT값이 존재
		String token = request.getHeader("Authorization");
		String accessToken = token.substring("Bearer ".length());
		String refreshToken = request.getHeader("refreshToken");

		log.debug("===>>> LoginServiceImpl > refreshToken() > AT : " + accessToken);
		log.debug("===>>> LoginServiceImpl > refreshToken() > RT : " + refreshToken);
		
		try {
			// RT내의 token 정보를 취득
			// RT가 만료되었으면 ExpiredJwtException 발생
			TokenInfo tokenInfo = (TokenInfo) jwtUtil.getRefreshTokenInfo(refreshToken);
			
			HashMap<String, Object> loginParam = new HashMap<>();
			loginParam.put("userId", tokenInfo.getUserId());
			loginParam.put("compId", tokenInfo.getCompId());
			// token정보의 userId, compId로 사용자 정보 취득
			UserModel user = userDao.selectUserById(loginParam);

			// refreshToken과 DB의 refreshToken이 같으면 AT, RT 토큰 재발행
			if (user.getRefToken().equals(refreshToken)) {
				log.debug("=============>>> LoginServiceImpl > refreshToken() > refreshToken is valid!!! ");
				tokenKey = jwtUtil.generateToken(user);	// AT 재발행
				log.debug("===>>> LoginServiceImpl > refreshToken() > tokenKey : " + tokenKey);
//				tokenKeyRef = user.getRefToken();
				tokenKeyRef = jwtUtil.generateRefreshToken(user);	// RT 재발행
				log.debug("===>>> LoginServiceImpl > refreshToken() > tokenKeyRef : " + tokenKeyRef);
			} else {
				// refreshToken 일치하지 않는 경우 Exception발생시킴 
				// => 아래의 catch (Exception e)에서 처리 
				log.debug("===>>> LoginServiceImpl > refreshToken() > throw Exception > RefreshToken don't match!!! ");
				throw new Exception("RefreshToken do not match.");
			}
			
//			String workIp = request.getRemoteAddr();
			String workIp = CommonUtil.getClientIP(request);
			log.debug("===>>> LoginServiceImpl > refreshToken() > workIp : " + workIp);
	
			// refreshToken 저장
			UserModel param = new UserModel();
			param.setUserId(user.getUserId());	// 키 항목이므로 필수
			param.setRefToken(tokenKeyRef);
			//*** 변경자를 직접 설정(MvcInterceptor를 우회하므로 UserInfo가 없음)
			param.setUpdNo(user.getUserId());	 
			param.setWorkIp(workIp);	
			
			log.debug("===>>> LoginServiceImpl > refreshToken() > param : "+ param);
			userDao.updateUser(param);
			
		} catch (ExpiredJwtException e) {
			// refresh token 만료 시 , 401 처리
			log.warn("===>>> LoginServiceImpl > refreshToken() > ExpiredJwtException");
			response.setStatus(ResponseCode.C401.getCode());
			return new BaseResponse(ResponseCode.C401);
		} catch (Exception e) {
			// >>>> throw new Exception을 catch하는지 확인 필요
			// 어쨋든 둘 다 401 리턴해서 RT 만료 처리
			log.warn("===>>> LoginServiceImpl > refreshToken() > Exception");
			response.setStatus(ResponseCode.C401.getCode());
			return new BaseResponse(ResponseCode.C401);
		}

		LoginResponse data = new LoginResponse();
		data.setAccessToken(tokenKey);
		data.setRefToken(tokenKeyRef);
		BaseResponse res = new BaseResponse(ResponseCode.C200);
		res.setData(data);
		return res;
	}

//	@Override
//	public BaseResponse changeUserPwd(PwdChangeRequest body, 
//			HttpServletRequest request, HttpServletResponse response) {
//
//		try {
//			log.debug("===>>> LoginServiceImpl > changeUserPwd() > body : " + body);
//
//			// 사용자 id로 사용자 no 조회
//			if (body.getUserId() == null || body.getUserPwd() == null || 
//					body.getNewPwd() == null) {
//				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);	// 401
//				// 유효하지 않은 파라미터
//				return new BaseResponse(ResponseCode.C781);
//			}
//			
//			HashMap<String, Object> param = new HashMap<>();
//			param.put("userId", body.getUserId());
//			param.put("compId", body.getCompId());	
//			// UserId, compId로 DB의 사용자 정보 조회
//			UserModel user = userDao.selectUserById(param);
//			
//			if (user == null) {
//				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//				// C701:사용자가 존재하지 않음
//				return new BaseResponse(ResponseCode.C701);
//			}
//	
//			// new pwd를 암호화
//			HMacUtil hmac = new HMacUtil();
//			String newPwdEnc = hmac.getEncHmacSha(body.getNewPwd());
//			
//			// 요청의 비밀번호와 DB 비밀번호가 같은지 확인, 틀리면 에러 
//			if (!hmac.getEncHmacSha(body.getUserPwd()).equals(user.getUserPwd())) {
//				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//				// 현재 비밀번호가 유효하지 않습니다.
//				return new BaseResponse(ResponseCode.C784);
//			}
//			
//	//		String workIp = request.getRemoteAddr();
//			String workIp = CommonUtil.getClientIP(request);
//			log.debug("===>>> LoginServiceImpl > changeUserPwd() > workIp : " + workIp);
//			
//			// 비번 변경이력의 pwd1, pwd2, pwd3 조회해서 최근 3회까지의 비번인지 확인
//			// 같은게 있으면 에러코드와 함께 리턴 처리
//			body.setUserNo(user.getUserNo());	// pk
//			
//			PwdChangeResponse userPwdHist = userDao.selectUserPwdHistory(body);
//			
//			if (userPwdHist == null) {
//				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//				// C711 : 사용자의 비밀번호 변경이력이 존재하지 않습니다.
//				return new BaseResponse(ResponseCode.C711);
//			}
//			
//			log.debug("===>>> LoginServiceImpl > changeUserPwd() > userPwdHist : " + userPwdHist);
//			if (newPwdEnc.equals(userPwdHist.getUserPwd1()) || 
//					newPwdEnc.equals(userPwdHist.getUserPwd2()) || 
//					newPwdEnc.equals(userPwdHist.getUserPwd3()) 
//				) {
//				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//				// 최근 3회까지 이전 비밀번호는 사용할 수 없습니다.
//				return new BaseResponse(ResponseCode.C785);
//			}
//			// 문제 없으면 new pwd로 사용자 테이블의 pwd 변경
//			user.setUserPwd(newPwdEnc);	
//			user.setWorkIp(workIp);
//			user.setUpdNo(body.getUserId());
//			
//			// --- 사용자 정보 update ---
//			int updCnt = userDao.updateUserPwd(user);
//
//			if (updCnt < 1) {
//				// 내부 에러가 발생하였습니다 : 589
//				return new BaseResponse(ResponseCode.C589);
//			}
//			
//			// pwd 변경 이력 업데이트
//			// user에 신규 비밀번호도 set된 상태이고 user를 그대로 사용
//			// 비밀번호 변경 이력 update
//			updCnt = userDao.updateUserPwdHistory(user);				
//
//			if (updCnt < 1) {
//				// 내부 에러가 발생하였습니다 : 589
//				return new BaseResponse(ResponseCode.C589);
//			}
//			
//			// 정상 처리 상태 리턴
//			BaseResponse res = new BaseResponse(ResponseCode.C200);
//			
//			return res;
//
//			
//		} catch (Exception e) {
//			log.warn(e.toString(), e);
//			// HttpResponse.status : 401 리턴
//			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);	
//			// 에러가 발생하면 내부 에러로 리턴
//			return new BaseResponse(ResponseCode.C589);
//		}
//	}
//	@Override
//	public BaseResponse delayUserPwd(PwdChangeRequest body, 
//			HttpServletRequest request, HttpServletResponse response) {
//
//		try {
//			log.debug("===>>> LoginServiceImpl > delayUserPwd() > body : " + body);
//
//			// 사용자 id로 사용자 no 조회
//			if (body.getUserId() == null || body.getCompId() == null)  {
//				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);	// 401
//				// 유효하지 않은 파라미터
//				return new BaseResponse(ResponseCode.C781);
//			}
//
//			// String workIp = request.getRemoteAddr();
//			String workIp = CommonUtil.getClientIP(request);
//			log.debug("===>>> LoginServiceImpl > changeUserPwd() > workIp : " + workIp);
//			
//			HashMap<String, Object> param = new HashMap<>();
//			param.put("userId", body.getUserId());
//			param.put("compId", body.getCompId());	
//			param.put("updNo", body.getUserId());	
//			param.put("workIp", workIp);	
//	
//			// pwd 90일 연장
//			int updCnt = userDao.updateUserPwdChgDelay(param);				
//
//			if (updCnt < 1) {
//				// 내부 에러가 발생하였습니다 : 589
//				return new BaseResponse(ResponseCode.C589);
//			}
//			
//			// 정상 처리 상태 리턴
//			BaseResponse res = new BaseResponse(ResponseCode.C200);
//			
//			return res;
//
//			
//		} catch (Exception e) {
//			log.warn(e.toString(), e);
//			// HttpResponse.status : 401 리턴
//			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);	
//			// 에러가 발생하면 내부 에러로 리턴
//			return new BaseResponse(ResponseCode.C589);
//		}
//	}
}
