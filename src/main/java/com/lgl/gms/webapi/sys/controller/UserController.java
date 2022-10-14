package com.lgl.gms.webapi.sys.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lgl.gms.webapi.common.context.UserInfo;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.sys.dto.request.PwdChangeRequest;
import com.lgl.gms.webapi.sys.dto.request.UserRequest;
import com.lgl.gms.webapi.sys.service.UserService;

/**
 * 사용자관리 Controller
 */
@CrossOrigin
@RequestMapping("/sys")
@RestController
public class UserController {

	// MyBatis 사용
	@Autowired
	public UserService userService;

	// POST/PUT/GET/DELETE method 방식

	/**
	 * 사용자 리스트 조회(n건)
	 */
	@RequestMapping(value = { "/users" }, method = RequestMethod.GET)
	public BaseResponse getUsers(HttpServletRequest request,
					@RequestParam(defaultValue = "") String userTyp,
					@RequestParam(defaultValue = "") String userId,
					@RequestParam(defaultValue = "") String userNm,
					@RequestParam(defaultValue = "") String delYn
			) throws java.lang.Exception {

		UserRequest body = new UserRequest();
		body.setUserTyp(userTyp);
		body.setUserId(userId);
		body.setUserNm(userNm);
		body.setDelYn(delYn);
		
		System.out.println("===>>> UserController > getUsers() > body : "+ body);

//		System.out.println("===>>> UserController > getUsers() > UserInfo.getCompId() : "+ UserInfo.getCompId());
		Locale locale = LocaleContextHolder.getLocale();	
		String locale2 = LocaleContextHolder.getLocale().getLanguage();	
		System.out.println("===>>> UserController > getUsers() > locale.toString() : "+ locale.toString());
		System.out.println("===>>> UserController > getUsers() > locale2 : "+ locale2);
		System.out.println("===>>> UserController > getUsers() > UserInfo.locale : "+ UserInfo.getLocale());
		
		return userService.getUserList(body);
	}
	
	/**
	 * 사용자 상세정보 조회(1건)
	 */
	@RequestMapping(value = { "/users/{id}" }, method = RequestMethod.GET)
	public BaseResponse getUserOne(HttpServletRequest request, 
			@PathVariable String id)
		throws java.lang.Exception {
		
		return userService.getUserOne(id);
	}
	
	
	/**
	 * 사용자 추가
	 */
	@RequestMapping(value = { "/users" }, method = RequestMethod.POST)
	public BaseResponse addUsers(HttpServletRequest request, 
				@RequestBody UserRequest body) 
		throws java.lang.Exception {
		
		return userService.addUser(body);
	}

	/**
	 * 사용자 id 존재여부 체크
	 */
	@RequestMapping(value = { "/users/{id}/check" }, method = RequestMethod.GET)
	public BaseResponse checkUsers(HttpServletRequest request, 
			@PathVariable String id) 
		throws java.lang.Exception {
		
		return userService.checkUserId(id);
	}
	
	/**
	 * 사용자 정보 수정
	 */
	@RequestMapping(value = { "/users/{userId}" }, method = RequestMethod.PUT)
	public BaseResponse modifyUsers(HttpServletRequest request, 
				@PathVariable String userId, @RequestBody UserRequest body)	
					throws java.lang.Exception {
		
		body.setUserId(userId);

		return userService.modifyUser(body);
	}

//	/**
//	 * 사용자 정보수정 Transaction 처리 샘플
//	 */
//	@Auth(role = { Role.USER })
//	@RequestMapping(value = { "/users/transaction/{id}" }, method = RequestMethod.PUT)
//	public BaseResponse modifyUsersWithTransaction(HttpServletRequest request, 
// 						@PathVariable String id,
//						@RequestBody UserRequest body)
//			throws java.lang.Exception {
//		body.setUserId(id);
//		return userService.modifyWithTransaction(body);
//	}

	/**
	 * 사용자 삭제
	 */
	@RequestMapping(value = { "/users/{id}" }, method = RequestMethod.DELETE)
	public BaseResponse deleteUserOne(HttpServletRequest request, 
				@PathVariable String id)
			throws java.lang.Exception {
		
		UserRequest body = new UserRequest();
		body.setUserId(id);
		
		return userService.deleteUser(body);
	}
	
	/**
	 * 비밀번호 수정
	 * @param request
	 * @param response
	 * @param body
	 * @return
	 * @throws java.lang.Exception
	 */
	@RequestMapping(value = { "/pwd" }, method = RequestMethod.POST)
	public BaseResponse changeUserPwd(HttpServletRequest request, HttpServletResponse response,
			@RequestBody PwdChangeRequest body) throws java.lang.Exception {
//			System.out.println("===>>> LoginController > changeUserPwd() exec.. ");
		return userService.changeUserPwd(body, request, response);
	}
	
	/**
	 * 비밀번호 연장
	 * @param request
	 * @param response
	 * @param body
	 * @return
	 * @throws java.lang.Exception
	 */
	@RequestMapping(value = { "/pwd-delay" }, method = RequestMethod.POST)
	public BaseResponse delayUserPwd(HttpServletRequest request, HttpServletResponse response,
			@RequestBody PwdChangeRequest body) throws java.lang.Exception {
			System.out.println("===>>> LoginController > delayUserPwd() exec.. ");
		return userService.delayUserPwd(body, request, response);
	}

//	/**
//	 * 사용자 파일업로드
//	 *
//	 * @param request
//	 * @param file
//	 * @param body
//	 * @return
//	 * @throws java.lang.Exception
//	 */
//	@Auth(role = { Role.USER })
//	@RequestMapping(value = { "/user/upload" }, method = RequestMethod.POST)
//	public BaseResponse upload(HttpServletRequest request, @RequestPart(value = "file") MultipartFile file,
//			@RequestPart(value = "body", required = false) FileRequest body) throws java.lang.Exception {
//		return userService.upload(file, body);
//	}
//
//	/**
//	 * 엑셀파일 업로드 및 파싱
//	 *
//	 * @param request
//	 * @param file
//	 * @param body
//	 * @return
//	 * @throws java.lang.Exception
//	 */
//	@Auth(role = { Role.USER })
//	@RequestMapping(value = { "/user/uploadExcel" }, method = RequestMethod.POST)
//	public BaseResponse uploadExcel(HttpServletRequest request, @RequestPart(value = "file") MultipartFile file,
//			@RequestPart(value = "body", required = false) FileRequest body) throws java.lang.Exception {
//		return userService.uploadExcel(file, body);
//	}

}
