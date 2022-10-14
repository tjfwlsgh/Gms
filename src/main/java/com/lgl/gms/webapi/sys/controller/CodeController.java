package com.lgl.gms.webapi.sys.controller;

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
import com.lgl.gms.webapi.common.context.UserInfo;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.sample.dto.request.FileRequest;
import com.lgl.gms.webapi.sys.dto.request.SvcTypRequest;
import com.lgl.gms.webapi.sys.dto.request.TccRequest;
import com.lgl.gms.webapi.sys.dto.request.TccValRequest;
import com.lgl.gms.webapi.sys.dto.request.UserRequest;
import com.lgl.gms.webapi.sys.service.CodeService;
import com.lgl.gms.webapi.sys.service.UserService;

/**
 * 공통코드관리 Controller
 */
@CrossOrigin
@RequestMapping("/sys")
@RestController
public class CodeController {

	// MyBatis 사용
	@Autowired
	public CodeService codeService;

	// POST/PUT/GET/DELETE method 방식

	// 코드유형 리스트 조회(n건)
	@RequestMapping(value = { "/code-types" }, method = RequestMethod.GET)
	public BaseResponse getCodeTypes(HttpServletRequest request,
					@RequestParam(defaultValue = "") String bseCd,
					@RequestParam(defaultValue = "") String typNm,
					@RequestParam(defaultValue = "") String userCl,
					@RequestParam(defaultValue = "") String delYn
			) throws java.lang.Exception {

		TccRequest body = new TccRequest();
		body.setBseCd(bseCd);
		body.setTypNm(typNm);
		body.setUserCl(userCl);
		body.setDelYn(delYn);
//		System.out.println("===>>> CodeController > getCodes() > UserInfo.getCompId() : "+ UserInfo.getCompId());
		
		return codeService.getCodeTypeList(body);
	}
	
	// 하위 코드 존재여부 체크 => 코드유형 삭제 시 사용
	// UI화면 단에서 체크가 가능하므로  실제 사용되지는 않음
	@RequestMapping(value = { "/code-vals/{tccId}/check" }, method = RequestMethod.GET)
	public BaseResponse checkCodeVal(HttpServletRequest request, 
			@PathVariable Integer tccId)
		throws java.lang.Exception {
		
		TccRequest body = new TccRequest();
		body.setTccId(tccId);
		
		return codeService.checkCodeVal(body);
	}
	
	// 코드유형 추가
	@RequestMapping(value = { "/code-types" }, method = RequestMethod.POST)
	public BaseResponse addCodeTypes(HttpServletRequest request, 
				@RequestBody TccRequest body) 
		throws java.lang.Exception {
		
		return codeService.addCodeType(body);
	}
	
	// 코드유형 정보 수정
	@RequestMapping(value = { "/code-types/{tccId}" }, method = RequestMethod.PUT)
	public BaseResponse modifyCodeTypes(HttpServletRequest request, 
				@PathVariable Integer tccId, @RequestBody TccRequest body)	
					throws java.lang.Exception {
		
		// 이미 있지만 path variable로 받은 tccId를 다시 명확하게 설정
		body.setTccId(tccId);
		
		return codeService.modifyCodeType(body);
	}

	// 코드유형 삭제
	@RequestMapping(value = { "/code-types/{tccId}" }, method = RequestMethod.DELETE)
	public BaseResponse deleteCodeType(HttpServletRequest request, 
				@PathVariable Integer tccId)
			throws java.lang.Exception {
		
		TccRequest body = new TccRequest();
		body.setTccId(tccId);
		
		return codeService.deleteCodeType(body);
	}

	// ------------------------------------------------------------------
	// 코드값 리스트 조회(n건)
	@RequestMapping(value = { "/code-vals" }, method = RequestMethod.GET)
	public BaseResponse getCodeVals(HttpServletRequest request,
					@RequestParam(defaultValue = "") Integer tccId,
					@RequestParam(defaultValue = "") String delYn
			) throws java.lang.Exception {

		TccValRequest body = new TccValRequest();
		body.setTccId(tccId);
		body.setDelYn(delYn);
		
		return codeService.getCodeValList(body);
	}

//	// 코드값 리스트 조회(1건) => 필요시 사용
//	@RequestMapping(value = { "/code-vals/{tccId}/{stdCd}" }, method = RequestMethod.GET)
//	public BaseResponse getCodeValOne(HttpServletRequest request,
//			@PathVariable Integer tccId,
//			@PathVariable String stdCd
//			) throws java.lang.Exception {
//
//		System.out.println("===>>> CodeController > getCodeValOne() > tccvId, stdCd : " + tccId.toString() + "," + stdCd);
//		TccValRequest body = new TccValRequest();
//		
//		body.setTccId(tccId);
//		body.setStdCd(stdCd);
//		
//		return codeService.getCodeValList(body);
//	}	

	// 코드값 추가
	@RequestMapping(value = { "/code-vals" }, method = RequestMethod.POST)
	public BaseResponse addCodeVal(HttpServletRequest request, 
				@RequestBody TccValRequest body) 
		throws java.lang.Exception {
		
		return codeService.addCodeVal(body);
	}
	
	// 코드값 정보 수정
	@RequestMapping(value = { "/code-vals/{tccvId}" }, method = RequestMethod.PUT)
	public BaseResponse modifyCodeVal(HttpServletRequest request, 
				@PathVariable Integer tccvId, @RequestBody TccValRequest body)	
					throws java.lang.Exception {
		
		body.setTccvId(tccvId);
		
		return codeService.modifyCodeVal(body);
	}

	// 코드값 삭제
	@RequestMapping(value = { "/code-vals/{tccvId}" }, method = RequestMethod.DELETE)
	public BaseResponse deleteCodeVal(HttpServletRequest request, 
				@PathVariable Integer tccvId)
			throws java.lang.Exception {
		
		TccValRequest body = new TccValRequest();
		body.setTccvId(tccvId);
		
		return codeService.deleteCodeVal(body);
	}

	// ------------------------------------------------------------------
	// 서비스유형 리스트 조회(n건)
	@RequestMapping(value = { "/svc-types" }, method = RequestMethod.GET)
	public BaseResponse getSvcTyps(HttpServletRequest request,
					@RequestParam(defaultValue = "") Integer tccvId,
					@RequestParam(defaultValue = "") String delYn
			) throws java.lang.Exception {

		SvcTypRequest body = new SvcTypRequest();
		
		body.setTccvId(tccvId);
		body.setDelYn("N");

		return codeService.getSvcTypeList(body);
	}
		
	// 서비스유형 추가
	@RequestMapping(value = { "/svc-types" }, method = RequestMethod.POST)
	public BaseResponse addSvcTyp(HttpServletRequest request, 
				@RequestBody SvcTypRequest body) 
		throws java.lang.Exception {
		
		return codeService.addSvcType(body);
	}
	
	// 서비스유형 정보 수정
	@RequestMapping(value = { "/svc-types/{tccvId}/{svcTyp}" }, method = RequestMethod.PUT)
	public BaseResponse modifySvcTyp(HttpServletRequest request, 
			@PathVariable Integer tccvId,
			@PathVariable String svcTyp, 
			@RequestBody SvcTypRequest body)	
					throws java.lang.Exception {
		
		body.setTccvId(tccvId);
		body.setSvcTyp(svcTyp);
		
		return codeService.modifySvcType(body);
	}

	// 서비스유형 삭제
	@RequestMapping(value = { "/svc-types/{tccvId}/{svcTyp}" }, method = RequestMethod.DELETE)
	public BaseResponse deleteSvcTyp(HttpServletRequest request, 
			@PathVariable Integer tccvId,
			@PathVariable String svcTyp)
			throws java.lang.Exception {
		
		SvcTypRequest body = new SvcTypRequest();
		
		body.setTccvId(tccvId);
		body.setSvcTyp(svcTyp);
		
		return codeService.deleteSvcType(body);
	}
	
}
