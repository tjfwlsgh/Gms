package com.lgl.gms.webapi.sample.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lgl.gms.config.Auth;
import com.lgl.gms.config.Auth.Role;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.sample.dto.request.FileRequest;
import com.lgl.gms.webapi.sample.dto.request.UserSampleRequest;
import com.lgl.gms.webapi.sample.service.UserSampleService;

/**
 * 사용자 관련 컨트롤러로 MyBatis 사용
 */
@CrossOrigin
@RestController
public class UserSampelController {

	// MyBatis 사용
	@Autowired
	public UserSampleService userService;

	// POST/PUT/GET/DELETE method 방식

	/**
	 * 사용자 추가
	 *
	 * @param request
	 * @param body
	 * @return
	 * @throws java.lang.Exception
	 */
	@Auth(role = { Role.USER })
	@RequestMapping(value = { "/v1/users" }, method = RequestMethod.POST)
	public BaseResponse addUsers(HttpServletRequest request, @RequestBody UserSampleRequest body)
			throws java.lang.Exception {
		return userService.add(body);
	}

	/**
	 * 사용자 정보수정
	 *
	 * @param request
	 * @param id
	 * @param body
	 * @return
	 * @throws java.lang.Exception
	 */
	@Auth(role = { Role.USER })
	@RequestMapping(value = { "/v1/users/{id}" }, method = RequestMethod.PUT)
	public BaseResponse modifyUsers(HttpServletRequest request, @PathVariable String id, @RequestBody UserSampleRequest body)
			throws java.lang.Exception {
		body.setId(id);
		return userService.modify(body);
	}

	/**
	 * 사용자 정보수정 Transaction 처리 샘플
	 *
	 * @param request
	 * @param id
	 * @param body
	 * @return
	 * @throws java.lang.Exception
	 */
	@Auth(role = { Role.USER })
	@RequestMapping(value = { "/v1/users/transaction/{id}" }, method = RequestMethod.PUT)
	public BaseResponse modifyUsersWithTransaction(HttpServletRequest request, @PathVariable String id,
			@RequestBody UserSampleRequest body)
			throws java.lang.Exception {
		body.setId(id);
		return userService.modifyWithTransaction(body);
	}

	/**
	 * 사용자 목록 요청(검색) - 페이징
	 *
	 * @param request
	 * @param searchKey
	 * @param keyword
	 * @param page
	 * @param listSize
	 * @return
	 * @throws java.lang.Exception
	 */
	@Auth(role = { Role.USER })
	@RequestMapping(value = { "/v1/users" }, method = RequestMethod.GET)
	public BaseResponse getUsers(HttpServletRequest request,
			@RequestParam(defaultValue = "") String searchKey,
			@RequestParam(defaultValue = "") String keyword,
			@RequestParam(defaultValue = "0") String page,
			@RequestParam(defaultValue = "15") String listSize)
			throws java.lang.Exception {
		UserSampleRequest body = new UserSampleRequest();
		body.setSearchKey(searchKey);
		body.setKeyword(keyword);
		body.setPage(page);
		body.setListSize(listSize);
		return userService.list(body);
	}

	/**
	 * 사용자 삭제
	 *
	 * @param request
	 * @param id
	 * @return
	 * @throws java.lang.Exception
	 */
	@Auth(role = { Role.USER })
	@RequestMapping(value = { "/v1/users/{id}" }, method = RequestMethod.DELETE)
	public BaseResponse deleteUserOne(HttpServletRequest request, @PathVariable String id)
			throws java.lang.Exception {
		UserSampleRequest body = new UserSampleRequest();
		body.setId(id);
		return userService.delete(body);
	}

	/**
	 * 사용자 정보요청
	 *
	 * @param request
	 * @param id
	 * @return
	 * @throws java.lang.Exception
	 */
	@Auth(role = { Role.USER })
	@RequestMapping(value = { "/v1/users/{id}" }, method = RequestMethod.GET)
	public BaseResponse getUserOne(HttpServletRequest request, @PathVariable String id)
			throws java.lang.Exception {
		return userService.getUserById(id);
	}

	/**
	 * 사용자 파일업로드
	 *
	 * @param request
	 * @param file
	 * @param body
	 * @return
	 * @throws java.lang.Exception
	 */
	@Auth(role = { Role.USER })
	@RequestMapping(value = { "/v1/user/upload" }, method = RequestMethod.POST)
	public BaseResponse upload(HttpServletRequest request, @RequestPart(value = "file") MultipartFile file,
			@RequestPart(value = "body", required = false) FileRequest body) throws java.lang.Exception {
		return userService.upload(file, body);
	}

	/**
	 * 엑셀파일 업로드 및 파싱
	 *
	 * @param request
	 * @param file
	 * @param body
	 * @return
	 * @throws java.lang.Exception
	 */
	@Auth(role = { Role.USER })
	@RequestMapping(value = { "/v1/user/uploadExcel" }, method = RequestMethod.POST)
	public BaseResponse uploadExcel(HttpServletRequest request, @RequestPart(value = "file") MultipartFile file,
			@RequestPart(value = "body", required = false) FileRequest body) throws java.lang.Exception {
		return userService.uploadExcel(file, body);
	}

}
