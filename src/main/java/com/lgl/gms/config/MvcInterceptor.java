
package com.lgl.gms.config;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.lgl.gms.webapi.common.context.UserContextHolder;
import com.lgl.gms.webapi.common.dto.TokenInfo;
import com.lgl.gms.webapi.common.util.CommonUtil;
import com.lgl.gms.webapi.common.util.JWTUtil;
import com.lgl.gms.webapi.common.util.MessageUtil;
import com.lgl.gms.webapi.sys.persistence.dao.UserDao;
import com.lgl.gms.webapi.sys.persistence.model.UserModel;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MvcInterceptor implements HandlerInterceptor {

	@Autowired
	public JWTUtil jwtUtil;
	
	@Autowired
	public UserDao userDao;
	
	public MvcInterceptor() {
	}

	@Override
	public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object arg2, Exception arg3)
			throws java.lang.Exception {
		// log.info("===>>> MvcInteceptor > afterCompletion() > arg2, arg3: " + arg2 +", "+ arg3);
		// ThreadLocal 객체 제거
		UserContextHolder.CONTEXT.remove();
	}

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object arg2, ModelAndView arg3)
			throws java.lang.Exception {
//		log.info("===>>> MvcInteceptor > postHandle() > arg2, arg3: " + arg2 +", "+ arg3);
	}

	/* Header Authorization token 체크.
	 * 2022.03.02 yhlee
	 * 호출된 컨트롤러의 권한 설정 체크 disable
	 *  */
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler)
			throws java.lang.Exception {
		// 컨트롤러 메소드가 아니면 다음으로 진행
		if (handler instanceof HandlerMethod == false) {
			return true;
		}
	
		// request header에서 AT를 취득해서 존재하면 토큰 유효성을 검사해서
		// 문제가 없으면(오류가 발생 안하면) Controller 메소드로 진행 처리
		String token = req.getHeader("Authorization");
		log.debug("===>>> MvcInteceptor > prehandler > Authorization : " + token);
		
		if (token != null) {
			// AT가 존재하는 경우
			token = token.substring("Bearer ".length());
			TokenInfo tokenInfo = null;
			try {
//				log.debug("===>>> MvcInteceptor > prehandler > token : " + token);
				// AT의 만료여부를 체크
				tokenInfo = jwtUtil.getTokenInfo(token);
				
			} catch (ExpiredJwtException e) {
				// ===  AT가 만료된 경우 ===
				// AT가 만료된 경우 code에 401을 리턴처리
				// => 클라이언트 Interceptor에서 reftoken url로 retry하게 되고 
				//    - RT가 유효하면 AT와 RT 재발행하여 유효기간 연장
				//    - RT가 만료되었으면 토큰 만료 메시지 표시 후 로그인 화면으로
				log.info("===>>> MvcInteceptor > prehandler > ExpiredJwtException : " + e);
				res.setContentType("application/json");
				res.setCharacterEncoding("UTF-8");
				// AT 만료 코드 리턴
				// Client에서 retr를 위해 Http상태코드는 200, svrCode를 401로 리턴
				res.setStatus(HttpServletResponse.SC_OK);	// 성공
				String errorMessage = MessageUtil.getResponseMessage("401"); // 토큰 만료
				res.getWriter().write("{\"code\":401,\"message\":\"" + errorMessage + "\"}");
				return false;
				
			} catch (Exception e) {
				log.info("===>>> MvcInteceptor > prehandler > Exception : " + e);
				return false;
			}

			// 토큰이 유효하면 Controller 메소드로 진행하기 전 아래 처리를 수행
			// === 1. 중복 로그인 체크
			// AT의 userId와 workIp를 읽어서 사용자 테이블의 로그인자 ip와 비교해서 틀리면 에러 리턴 	
			HashMap<String, Object> userParam = new HashMap<>();
			userParam.put("userId", tokenInfo.getUserId());
			userParam.put("compId", tokenInfo.getCompId());
			// token정보의 userId, compId로 사용자 정보 취득
			UserModel user = userDao.selectUserById(userParam);

			// AT의 workIP와 DB에 저장된 User의 workIp가 같지 않으면 에러 리턴
			// workIp가 유효한 경우에만 중복 로그인 체크 수행, 
			// 환경에 따라 로그인시 workIp를 정상 추출하지 못할 수 있으므로 이런 경우는 제외
			if (user.getWorkIp() != null && !user.getWorkIp().equals(tokenInfo.getWorkIp())) {
				// Exception발생시킴 
				// => 아래의 catch (Exception e)에서 처리 
				log.info("===>>> MvcInteceptor > prehandler() > throw Exception > duplicate Login!!! ");
				res.setContentType("application/json");
				res.setCharacterEncoding("UTF-8");
				// Http상태코드는 401, svrCode를 402로 리턴
				res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);	// SC_UNAUTHORIZED(401) : 비 인가된 접근
				String errorMessage = MessageUtil.getResponseMessage("402"); // 리소스 접근 권한 없음, 인증 필요
				res.getWriter().write("{\"code\":402,\"message\":\"" + errorMessage + "\"}");
				return false;	
			}
			
			// 2. 사용자 정보를 전역 ThreadLocal 변수에 저장
			// >>>>>>> AT에서 사용자 정보 추출하여 로그인 사용자 정보 생성 <<<<<<<
			// 세션을 사용하지 않으므로 매 접속시 토큰에서 로그인 사용자 정보를 ThreadLocal로 생성 
			log.debug("===>>> MvcInteceptor > prehandler > tokenInfo : " + tokenInfo);
//			String workIp = req.getRemoteAddr();
			String workIp = CommonUtil.getClientIP(req);
			log.debug("===>>> MvcInteceptor > prehandler > workIp : " + workIp);
			tokenInfo.setWorkIp(workIp);
			// 로그인 사용자 정보를 UserContextHolder에 저장
			// 프레임워크 전반에서 UserContextHolder를 통해 로그인 사용자 정보 접근 가능
			UserContextHolder.CONTEXT.set(tokenInfo);
			
			return true;
			
		} else {
			// token이 없으면 클라이언트로 에러를 리턴
			// 원래부터 token이 없는 경우인 로그인이나 로그아웃, 다른 접속 url은 
			// 제외 url에 추가해서 Interceptor를 우회하도록 처리
			log.info("===>>> MvcInteceptor > prehandler > token is null");
			res.setContentType("application/json");
			res.setCharacterEncoding("UTF-8");
			// Client에서 retr를 위해 Http상태코드는 401, svrCode를 402로 리턴
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);	// SC_UNAUTHORIZED(401) : 비 인가
			String errorMessage = MessageUtil.getResponseMessage("402"); // 리소스 접근 권한 없음, 인증 필요
			res.getWriter().write("{\"code\":402,\"message\":\"" + errorMessage + "\"}");
			return false;	
		}
	}

/* -----------------------------------------------------------------------------------------------
 * 2022.03.02 기존 로직 백업

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler)
			throws java.lang.Exception {
		// 컨트롤러 메소드가 아니면 그냥 넘어감.
		if (handler instanceof HandlerMethod == false) {
			return true;
		}
		
		// 호출된 메소드의 권한 설정을 취득
		// 2022.03.02 메소드의 권한 설정 disable
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		log.info("===>>> MvcInteceptor > prehandler > auth : " + auth);
		
		boolean canAccess = false;
		if (auth == null) {
			// 해당 메소드에 권한 설정이 없으면 인증처리 skip하고 그냥 통과
			canAccess = true;
		} else {
			// auth가 존재
		
			// request header에서 AT를 취득해서 존재하면 토큰 유효성을 검사해서
			// 문제가 없으면(오류가 발생 안하면) Controller 메소드로 진행 처리
			String token = req.getHeader("Authorization");
			log.info("===>>> MvcInteceptor > prehandler > token: " + token);
			
			if (token != null) {
				// AT가 존재하는 경우
				token = token.substring("Bearer ".length());
				TokenInfo tokenInfo = null;
				try {
					// AT의 만료여부를 체크
					tokenInfo = jwtUtil.getTokenInfo(token);
				} catch (ExpiredJwtException e) {
					// AT가 존재하지만 만료 exception이 발생한 경우
					// *** 만료 exception은 claim 추출시 에러가 발생하는 경우인데 이게 맞는가?
					res.setContentType("application/json");
					res.setCharacterEncoding("UTF-8");
					// AT 만료 코드 리턴
					String errorMessage = MessageUtil.getResponseMessage("401");
					res.getWriter().write("{\"code\":401,\"message\":\"" + errorMessage + "\"}");
					return false;
				} catch (Exception e) {
					log.info("===>>> MvcInteceptor > prehandler > Exception : " + e);
					return false;
				}

				Role[] roles = auth.role();

				// 토큰의 user 권한 그룹과 호출된 메소드에 정의된 권한 그룹들을 비교
				String userType = tokenInfo.getUserType();
				if (UserTypeCode.ADMIN.value().equals(userType)) {
					for (Role role : roles) {
						if ("ADMIN".equals(role.toString())) {
							return true;
						}
					}
				} else if (UserTypeCode.USER.value().equals(userType)) {
					for (Role role : roles) {
						if ("USER".equals(role.toString())) {
							return true;
						}
					}
				}
				// 토큰이 유효하면 Controller 메소드로 진행
				return true;
				
			} else {
				// token이 없으면 AT가 만료되었거나 로그인을 안 한 경우
				log.info("===>>> MvcInteceptor > prehandler > token is null");
				// 일단 AT가 없어도 진행되도록 처리
				// ★★★★★ TODO: 나중에 반드시 return false로 수정하고 메시지 처리
				// 로그인하도록 메시지 처리 필요
//				return false;
				return true;	
			}
		}

		if (!canAccess) {
			res.setContentType("application/json");
			res.setCharacterEncoding("UTF-8");
			// 리소스 접근 권한 없음
			// respose의 status는 402인데 리턴코드에 402는 버그인듯... 확인 필요
			res.setStatus(ResponseCode.C402.getCode());
			String errorMessage = MessageUtil.getResponseMessage("402");
			res.getWriter().write("{\"code\":402,\"message\":\"" + errorMessage + "\"}");
			return false;
		}

			return true;
	}
-------------------------------------------------------------------------------------------------- */
}
