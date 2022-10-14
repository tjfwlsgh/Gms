package com.lgl.gms.webapi.sample.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lgl.gms.config.Auth;
import com.lgl.gms.config.Auth.Role;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.sample.dto.request.UserSampleRequest;
import com.lgl.gms.webapi.sample.service.AdminService;

/**
 * Admin 컨트롤러매핑
 */
@CrossOrigin
@RestController
public class AdminController {
	@Autowired
	public AdminService adminService;

	@Auth(role = Role.ADMIN)
	@RequestMapping(value = { "/v1/admin/allUsers" }, method = RequestMethod.GET)
	public BaseResponse list(HttpServletRequest request,
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
		return adminService.allUsers(body, request.getHeader("Authorization"));
	}

}
