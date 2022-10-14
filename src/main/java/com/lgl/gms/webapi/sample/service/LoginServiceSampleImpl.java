package com.lgl.gms.webapi.sample.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgl.gms.config.PropConfig;
import com.lgl.gms.webapi.common.dto.code.ResponseCode;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.common.util.HMacUtil;
import com.lgl.gms.webapi.common.util.JWTUtil;
import com.lgl.gms.webapi.sample.dto.request.LoginRequestSample;
import com.lgl.gms.webapi.sample.dto.response.LoginFailResponseSample;
import com.lgl.gms.webapi.sample.dto.response.LoginResponseSample;
import com.lgl.gms.webapi.sample.persistence.dao.UserSampleDao;
import com.lgl.gms.webapi.sample.persistence.model.UserSampleModel;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("LoginServiceSample")
public class LoginServiceSampleImpl implements LoginServiceSample {
	@Autowired
	public UserSampleDao userDao;

	@Autowired
	public PropConfig prop;

	@Autowired
	public JWTUtil jwtUtil;

	/**
	 * 로그인 
	 */
	public BaseResponse loginUser(LoginRequestSample req, HttpServletRequest request, HttpServletResponse response) {
		LoginFailResponseSample failData = new LoginFailResponseSample();
		failData.setLockYn("N");
		failData.setFailCnt("0");
		try {
			// 필수 param없으면 에러 발생
			if (req.getEmail() == null || req.getPw() == null) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				// 유효하지 않은 파라미터
				return new BaseResponse(ResponseCode.C781, failData);
			}

			// 이메일로 사용자 정보 조회
			UserSampleModel user = userDao.selectUserByEmail(req.getEmail());
			if (user == null) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				// 사용자가 존재하지 않음
				return new BaseResponse(ResponseCode.C701, failData);
			}

			if (user.getLockYn() != null && user.getLockYn().equals("Y")) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				failData.setLockYn(user.getLockYn());
				failData.setFailCnt(user.getLoginFailCnt() + "");
				// 계정 잠금 처리
				return new BaseResponse(ResponseCode.C709, failData);
			}

			HMacUtil hmac = new HMacUtil();
			UserSampleModel param = new UserSampleModel();
			param.setId(user.getId());

			// 요청의 사용자 pw를 사용자의 pw와 비교한 결과 틀리면 
			if (!hmac.getEncHmacShaWithSalt(req.getPw(), user.getSalt()).equals(user.getPw())) {
				// Login Fail Count를 DB에 저장
				param.setLoginFailCnt(user.getLoginFailCnt() + 1);
				userDao.updateUser(param);
				// fail cnt를 DB에서 읽어서 설정
				failData.setFailCnt(Integer.toString(param.getLoginFailCnt()));
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				// 로그인 실패
				return new BaseResponse(ResponseCode.C703, failData);
			}

			user.setUserType(user.getUserType());  // ?? 
			// JWT Access토큰과 Refresh토큰 발행
			// 현재 yml에 정의된 유효기간은 AT:60분, RT:1주
// 2022.03.03 jwtUtil의 파라미터가 변경되어 주석처리
//			String tokenKey = jwtUtil.generateToken(user);
//			String tokenKeyRef = jwtUtil.generateRefreshToken(user);
			// Refresh 토큰 저장
//			param.setRefreshToken(tokenKeyRef);
			userDao.updateUser(param);
			
			// res의 code를 성공으로 설정하고 data에 로그인 결과를 저장하고 리턴
			BaseResponse res = new BaseResponse(ResponseCode.C200);
			LoginResponseSample data = new LoginResponseSample();
// 2022.03.03 jwtUtil의 파라미터가 변경되어 주석처리			
//			data.setAccessToken(tokenKey);
//			data.setRefreshToken(tokenKeyRef);
			data.setName(user.getUsername());
			data.setEmail(user.getEmail());
			data.setUserType(user.getUserType());
			data.setId(user.getId());
			res.setData(data);

			return res;

		} catch (Exception e) {
			log.warn(e.toString(), e);
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			// 에러가 발생하면 내부 에러로 리턴
			return new BaseResponse(ResponseCode.C589, failData);
		}
	}

	/**
	 * 로그아웃
	 */
	@Override
	public BaseResponse logout(String auth, HttpServletResponse response) {

		if (!auth.startsWith("Bearer ")) {
			// 리소스 접근 권한 없음
			return new BaseResponse(ResponseCode.C402);
		}
		auth = auth.substring("Bearer ".length());
		try {
			// AT에서 사용자id를 취득해서 DB의 사용자 정보중 RT를 리셋 

			// 2022.03.04 토큰 사용 샘플용도, 에러나서 주석처리
//			TokenSampleInfo tokenInfo = (TokenSampleInfo) jwtUtil.getTokenInfo(auth);
			UserSampleModel user = new UserSampleModel();
//			user.setId(tokenInfo.getId());
			user.setRefreshToken("");
			userDao.updateUser(user);
			
			return new BaseResponse(ResponseCode.C705);	// 로그아웃 
		} catch (Exception e) {
			log.warn(e.toString(), e);
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return new BaseResponse(ResponseCode.C589);	// 서버에러
		}
	}

	/**
	 * RT가 만료되지 않았으면 AT 재발행
	 *  AT가 만료된 경우 front-end의 response interceptor에서 다시 요청이 발생하고
	 *  이 요청을 처리하는 메소드
	 * 
	 */
	@Override
	public BaseResponse refreshToken(HttpServletRequest request, HttpServletResponse response) {

		String tokenKey = "";
		String tokenKeyRef = "";
		// request header의 refreshToken에 RT값이 존재
		String refreshToken = request.getHeader("refreshToken");

		try {
			// RT내의 token 정보를 취득
//			TokenInfo tokenInfo = (TokenInfo) jwtUtil.getTokenInfo(refreshToken);
			// token정보의 email로 사용자 정보 취득
//			UserModel user = userDao.selectUserByEmail(tokenInfo.getEmail());
//			user.setUserType(user.getUserType());
//			if (user.getRefreshToken().equals(refreshToken)) {
//				tokenKey = jwtUtil.generateToken(user);
//				tokenKeyRef = user.getRefreshToken();
//			} else {
//				// refreshToken 일치하지 않는 경우 refreshToken 만료로 처리
//				throw new Exception("RefreshToken do not match.");
//			}
		} catch (ExpiredJwtException e) {
			// refresh token 만료 시 , 401 처리
			response.setStatus(ResponseCode.C401.getCode());
			return new BaseResponse(ResponseCode.C401);
		} catch (Exception e) {
			// ===>>>>>>>>>>>>>> 에러처리 확인 필요
			response.setStatus(ResponseCode.C401.getCode());
			return new BaseResponse(ResponseCode.C401);
		}

		LoginResponseSample data = new LoginResponseSample();
		data.setAccessToken(tokenKey);
		data.setRefreshToken(tokenKeyRef);
		BaseResponse res = new BaseResponse(ResponseCode.C200);
		res.setData(data);
		return res;
	}
}
