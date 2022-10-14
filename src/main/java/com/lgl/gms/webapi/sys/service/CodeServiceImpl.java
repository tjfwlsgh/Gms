package com.lgl.gms.webapi.sys.service;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgl.gms.webapi.common.context.UserInfo;
import com.lgl.gms.webapi.common.dto.code.ResponseCode;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.sys.dto.request.SvcTypRequest;
import com.lgl.gms.webapi.sys.dto.request.TccRequest;
import com.lgl.gms.webapi.sys.dto.request.TccValRequest;
import com.lgl.gms.webapi.sys.dto.response.SvcTypResponse;
import com.lgl.gms.webapi.sys.dto.response.TccResponse;
import com.lgl.gms.webapi.sys.dto.response.TccValResponse;
import com.lgl.gms.webapi.sys.persistence.dao.CodeDao;
import com.lgl.gms.webapi.sys.persistence.model.SvcTypModel;
import com.lgl.gms.webapi.sys.persistence.model.TccModel;
import com.lgl.gms.webapi.sys.persistence.model.TccValModel;

import lombok.extern.slf4j.Slf4j;

@Transactional(transactionManager = "transactionManagerBatis")
@Slf4j
@Service
public class CodeServiceImpl implements CodeService {
	
	@Autowired
	private CodeDao codeDao;
	
	@Override
	public BaseResponse getCodeTypeList(TccRequest body) {
		
		try {
			HashMap<String, Object> param = new HashMap<>();

			// param에 값이 없어도 키가 존재하면 mybatis 조건에서 구문이 제거 되지 않으므로 
			// 아래와 같이 값이 있는 경우에만 HashMap에 키값을 설정해야 함
			if (StringUtils.isNotBlank(body.getBseCd())) {	// 유형코드
				param.put("bseCd", body.getBseCd());
			}
			if (StringUtils.isNotBlank(body.getTypNm())) {	// 유형명
				param.put("typNm", body.getTypNm());
			}
			if (StringUtils.isNotBlank(body.getUserCl())) {	// 사용자구분(MNGR:관리자, USER:사용자)
				param.put("userCl", body.getUserCl());
			}
			if (StringUtils.isNotBlank(body.getDelYn())) {	// 삭제구분
				param.put("delYn", body.getDelYn());
			}

			// 로그인 사용자 compId 설정
			param.put("compId", UserInfo.getCompId());  // 회사ID
			
			System.out.println("===>>> CodeServiceImpl > getCodeTypeList() > param : "+ param);
			
			List<TccResponse> list = (List<TccResponse>) codeDao.selectCodeTypeList(param);

			BaseResponse res = new BaseResponse(ResponseCode.C200);
			res.setData(list);
			return res;

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}

	}

	@Override
	public BaseResponse checkCodeVal(TccRequest body) {
		// tccId로 코드값 존재여부 체크
		try {
			System.out.println("===>>> CodeServiceImpl > checkCodeVal() > body : "+ body);
			
			// 필수값 TccId 존재여부 체크
			if (body.getTccId() == null || body.getTccId() == 0) {
				// 필수항목이 유효하지 않습니다 : 731
				return new BaseResponse(ResponseCode.C731);
			}
			
			int cnt =  codeDao.selectCodeValCnt(body);
			if (cnt > 0) {
				// 이미 존재하는 항목입니다. : 733
				return new BaseResponse(ResponseCode.C733);
			}

			return new BaseResponse(ResponseCode.C200);

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}
	}
	
	@Override
	public BaseResponse addCodeType(TccRequest body) {

		try {
			System.out.println("===>>> UserServiceImpl > addUser() > body : "+ body);
			
			// 필수입력값(bseCd, TypNm) 체크
			if (StringUtils.isBlank(body.getBseCd()) ||  
					StringUtils.isBlank(body.getTypNm())
				) {
				// 필수항목이 유효하지 않습니다 : 731
				return new BaseResponse(ResponseCode.C731);
			}
			
			// 로그인 사용자 compId를 body에 추가해서 중복 체크
			body.setCompId(UserInfo.getCompId());
			
			// 중복 체크
			TccResponse tcc = codeDao.selectCodeTypeOne(body);
			if (tcc != null) {
				// 이미 존재하는 항목입니다. : 733
				return new BaseResponse(ResponseCode.C733);
			}
			TccModel param = new TccModel();
			
			// 신규이므로 로그인 사용자 compId추가
			param.setCompId(UserInfo.getCompId());	
			param.setBseCd(body.getBseCd());
			param.setTypNm(body.getTypNm());
			param.setTypNmEng(body.getTypNmEng());
			param.setCdCl(body.getCdCl());
			param.setUserCl(body.getUserCl());
			param.setCdLv(body.getCdLv());
			
			System.out.println("===>>> CodeServiceImpl > addCodeType() > param : "+ param);
			
			// --- 사용자 insert ---
			int updCnt = codeDao.insertCodeType(param);
			
			if (updCnt < 1) {
				// 내부 에러가 발생하였습니다 : 589
				return new BaseResponse(ResponseCode.C589);
			}
			
			return new BaseResponse(ResponseCode.C200);

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}

	}

	@Override
	public BaseResponse modifyCodeType(TccRequest body) {
		try {
			System.out.println("===>>> UserServiceImpl > modifyCodeType() > body : "+ body);
			
			// 필수입력값(tccId, bseCd, TypNm) 체크
			if ((body.getTccId() == null || body.getTccId() == 0) ||  
					StringUtils.isBlank(body.getBseCd()) ||  
					StringUtils.isBlank(body.getTypNm())  
				) {
				// 필수항목이 유효하지 않습니다 : 731
				return new BaseResponse(ResponseCode.C731);
			}
			
			TccModel param = new TccModel();
			
			param.setTccId(body.getTccId());	// pk
			param.setBseCd(body.getBseCd());	
			param.setTypNm(body.getTypNm());
			param.setTypNmEng(body.getTypNmEng());
//			param.setCompId(body.getCompId());
			param.setCdCl(body.getCdCl());
			param.setUserCl(body.getUserCl());
			param.setCdLv(body.getCdLv());
			param.setDelYn(body.getDelYn());		// 삭제여부 수정 가능
			
			System.out.println("===>>> CodeServiceImpl > modifyCodeType() > param : "+ param);
			
			// --- 사용자 Update ---
			int updCnt = codeDao.updateCodeType(param);
			
			if (updCnt < 1) {
				// 내부 에러가 발생하였습니다 : 589
				return new BaseResponse(ResponseCode.C589);
			}
			
			return new BaseResponse(ResponseCode.C200);

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}
	}

	@Override
	public BaseResponse deleteCodeType(TccRequest body) {
		try {
			System.out.println("===>>> UserServiceImpl > deleteCodeType() > body : "+ body);
			
			// 필수입력값(bseCd, TypNm) 체크
			if (body.getTccId() == null || body.getTccId() == 0) {
				// 필수항목이 유효하지 않습니다 : 731
				return new BaseResponse(ResponseCode.C731);
			}
			
			TccModel param = new TccModel();
			
			param.setTccId(body.getTccId());	// 필요할때 query에서 지정해서 사용
//			param.setCompId(body.getCompId());	
//			param.setBseCd(body.getBseCd());	

			System.out.println("===>>> CodeServiceImpl > deleteCodeType() > param : " + param);
			System.out.println("===>>> CodeServiceImpl > deleteCodeType() > authCd : "+ UserInfo.getAuthCd());
			
			// 시스템 관리자이면 실제 데이터 삭제, 아니면 삭제구분 flag만 Y로 
			int updCnt = 0;
			
			if("LGL-000".equals(UserInfo.getAuthCd())) {		// 시스템 관리자(LGL마스터)
				updCnt = codeDao.deleteCodeTypeReal(param);
			} else {
				updCnt = codeDao.deleteCodeType(param);
			}	
			
			if (updCnt < 1) {
				// 내부 에러가 발생하였습니다 : 589
				return new BaseResponse(ResponseCode.C589);
			}
			
			return new BaseResponse(ResponseCode.C200);

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}
	}

	@Override
	public BaseResponse getCodeValList(TccValRequest body) {
		
		try {
			HashMap<String, Object> param = new HashMap<>();
			
			// tccId가 선택되어 조회 파라미터로 전달
			if (body.getTccId() != null && body.getTccId() != 0) {	// 유형코드
				param.put("tccId", body.getTccId());
			}
			if (StringUtils.isNotBlank(body.getDelYn())) {	// 삭제구분
				param.put("delYn", body.getDelYn());
			}
			
			System.out.println("===>>> CodeServiceImpl > getCodeValList() > param : "+ param);
			
			List<TccValResponse> list = (List<TccValResponse>) codeDao.selectCodeValList(param);

			BaseResponse res = new BaseResponse(ResponseCode.C200);
			res.setData(list);
			return res;

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}

	}

	@Override
	public BaseResponse addCodeVal(TccValRequest body) {

		try {
			System.out.println("===>>> UserServiceImpl > addCodeVal() > body : "+ body);
			
			// 필수입력값(tccId, bseCd, TypNm) 체크
			if ((body.getTccId() == null  || body.getTccId() == 0) ||
					StringUtils.isBlank(body.getStdCd()) ||  
					StringUtils.isBlank(body.getStdCdNm())
				) {
				// 필수항목이 유효하지 않습니다 : 731
				return new BaseResponse(ResponseCode.C731);
			}
			
			// 중복 체크
			TccValResponse tccVal = codeDao.selectCodeValOne(body);
			if (tccVal != null) {
				// 이미 존재하는 항목입니다. : 733
				return new BaseResponse(ResponseCode.C733);
			}
			TccValModel param = new TccValModel();
			
			param.setTccId(body.getTccId());
			param.setStdCd(body.getStdCd());
			param.setStdCdNm(body.getStdCdNm());
			param.setStdCdNmEng(body.getStdCdNmEng());
			param.setCdVal(body.getCdVal());
			param.setViewSeq(body.getViewSeq());
			
			System.out.println("===>>> CodeServiceImpl > addCodeVal() > param : "+ param);
			
			// --- 사용자 insert ---
			int updCnt = codeDao.insertCodeVal(param);
			
			if (updCnt < 1) {
				// 내부 에러가 발생하였습니다 : 589
				return new BaseResponse(ResponseCode.C589);
			}
			
			return new BaseResponse(ResponseCode.C200);

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}

	}

	@Override
	public BaseResponse modifyCodeVal(TccValRequest body) {
		try {
			System.out.println("===>>> UserServiceImpl > modifyCodeVal() > body : "+ body);
			
			// 필수입력값(tccvId, stdCd, stdCdNm) 체크
			if ((body.getTccvId() == null  || body.getTccvId() == 0) ||  
					StringUtils.isBlank(body.getStdCd()) ||  
					StringUtils.isBlank(body.getStdCdNm())  
				) {
				// 필수항목이 유효하지 않습니다 : 731
				return new BaseResponse(ResponseCode.C731);
			}
			
			TccValModel param = new TccValModel();
			
			param.setTccvId(body.getTccvId());
//			param.setTccId(body.getTccId());
			param.setStdCd(body.getStdCd());
			param.setStdCdNm(body.getStdCdNm());
			param.setStdCdNmEng(body.getStdCdNmEng());
			param.setCdVal(body.getCdVal());
			param.setViewSeq(body.getViewSeq());
			param.setDelYn(body.getDelYn());		// 삭제여부 수정 가능
			
			System.out.println("===>>> CodeServiceImpl > modifyCodeVal() > param : "+ param);
			
			// --- 사용자 update ---
			int updCnt = codeDao.updateCodeVal(param);
			
			if (updCnt < 1) {
				// 내부 에러가 발생하였습니다 : 589
				return new BaseResponse(ResponseCode.C589);
			}
			
			return new BaseResponse(ResponseCode.C200);

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}
	}

	@Override
	public BaseResponse deleteCodeVal(TccValRequest body) {
		try {
			System.out.println("===>>> UserServiceImpl > deleteCodeVal() > body : "+ body);
			
			// 필수입력값(tccvId 체크
			if (body.getTccvId() == null || body.getTccvId() == 0
				) {
				// 필수항목이 유효하지 않습니다 : 731
				return new BaseResponse(ResponseCode.C731);
			}
			
			TccValModel param = new TccValModel();
			
			param.setTccvId(body.getTccvId());

			System.out.println("===>>> CodeServiceImpl > deleteCodeVal() > param : "+ param);
			
			// 시스템 관리자이면 실제 데이터 삭제, 아니면 삭제구분 flag만 Y로 
			int updCnt = 0;
			if(UserInfo.getAuthCd() == "LGL-000") {		// 시스템 관리자(LGL마스터)
				updCnt = codeDao.deleteCodeValReal(param);
			} else {
				updCnt = codeDao.deleteCodeVal(param);
			}		
			
			if (updCnt < 1) {
				// 내부 에러가 발생하였습니다 : 589
				return new BaseResponse(ResponseCode.C589);
			}
			
			return new BaseResponse(ResponseCode.C200);

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}
	}

	@Override
	public BaseResponse getSvcTypeList(SvcTypRequest body) {
		try {
			HashMap<String, Object> param = new HashMap<>();
			
			// tccId가 선택되어 조회 파라미터로 전달
			if (body.getTccvId() != null && body.getTccvId() != 0) {	// 유형코드
				param.put("tccvId", body.getTccvId());
			}
			if (StringUtils.isNotBlank(body.getDelYn())) {	// 삭제구분
				param.put("delYn", body.getDelYn());
			}
			
			System.out.println("===>>> CodeServiceImpl > getSvcTypeList() > param : "+ param);
			
			List<SvcTypResponse> list = (List<SvcTypResponse>) codeDao.selectSvcTypeList(param);

			BaseResponse res = new BaseResponse(ResponseCode.C200);
			res.setData(list);
			return res;

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}

	}

	@Override
	public BaseResponse checkSvcType(TccRequest body) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public BaseResponse addSvcType(SvcTypRequest body) {
		try {
			System.out.println("===>>> UserServiceImpl > addSvcType() > body : "+ body);
			
			// 필수입력값(svcTyp, svcTypNm) 체크
			if ((body.getTccvId() == null && body.getTccvId() == 0) || 
					StringUtils.isBlank(body.getSvcTyp()) ||  
					StringUtils.isBlank(body.getSvcNm())
				) {
				// 필수항목이 유효하지 않습니다 : 731
				return new BaseResponse(ResponseCode.C731);
			}
			
			// 중복 체크
			SvcTypResponse svcTyp =  codeDao.selectSvcTypeOne(body);
			if (svcTyp != null) {
				// 이미 존재하는 항목입니다. : 733
				return new BaseResponse(ResponseCode.C733);
			}
			
			SvcTypModel param = new SvcTypModel();
			
			param.setTccvId(body.getTccvId());
			param.setSvcTyp(body.getSvcTyp());
			param.setCl1Cd(body.getCl1Cd());
			param.setSvcNm(body.getSvcNm());
			param.setSvcSnm(body.getSvcSnm());
			param.setSvcNmEng(body.getSvcNmEng());
			param.setSvcSnmEng(body.getSvcSnmEng());
			param.setViewSeq(body.getViewSeq());
			
			System.out.println("===>>> CodeServiceImpl > addSvcType() > param : "+ param);
			
			// --- 사용자 insert ---
			int updCnt = codeDao.insertSvcType(param);
			
			if (updCnt < 1) {
				// 내부 에러가 발생하였습니다 : 589
				return new BaseResponse(ResponseCode.C589);
			}
			
			return new BaseResponse(ResponseCode.C200);

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}

	}

	@Override
	public BaseResponse modifySvcType(SvcTypRequest body) {
		try {
			System.out.println("===>>> UserServiceImpl > modifySvcType() > body : "+ body);
			
			// 필수입력값(bseCd, TypNm) 체크
			if ((body.getTccvId() == null && body.getTccvId() == 0) ||
					StringUtils.isBlank(body.getSvcTyp()) ||  
					StringUtils.isBlank(body.getSvcNm())
				) {
				// 필수항목이 유효하지 않습니다 : 731
				return new BaseResponse(ResponseCode.C731);
			}
			
			SvcTypModel param = new SvcTypModel();
				
			param.setTccvId(body.getTccvId());
			param.setSvcTyp(body.getSvcTyp());
			param.setCl1Cd(body.getCl1Cd());
			param.setSvcNm(body.getSvcNm());
			param.setSvcSnm(body.getSvcSnm());
			param.setSvcNmEng(body.getSvcNmEng());
			param.setSvcSnmEng(body.getSvcSnmEng());
			param.setViewSeq(body.getViewSeq());
			param.setDelYn(body.getDelYn());		// 삭제여부 수정 가능
			
			System.out.println("===>>> CodeServiceImpl > modifySvcType() > param : "+ param);
			
			// --- 사용자 update ---
			int updCnt = codeDao.updateSvcType(param);
			
			if (updCnt < 1) {
				// 내부 에러가 발생하였습니다 : 589
				return new BaseResponse(ResponseCode.C589);
			}
			
			return new BaseResponse(ResponseCode.C200);

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}
	}

	@Override
	public BaseResponse deleteSvcType(SvcTypRequest body) {
		try {
			System.out.println("===>>> UserServiceImpl > deleteSvcType() > body : "+ body);
			
			// 필수입력값(svcTyp, tccvId) 체크
			if ((body.getTccvId() == null && body.getTccvId() == 0) || 
					StringUtils.isBlank(body.getSvcTyp())
					) {
				// 필수항목이 유효하지 않습니다 : 731
				return new BaseResponse(ResponseCode.C731);
			}
			
			SvcTypModel param = new SvcTypModel();
			
			param.setTccvId(body.getTccvId());
			param.setSvcTyp(body.getSvcTyp());

			System.out.println("===>>> CodeServiceImpl > deleteSvcType() > param : "+ param);
			
			// 시스템 관리자이면 실제 데이터 삭제, 아니면 삭제구분 flag만 Y로 
			int updCnt = 0;
			
			if(UserInfo.getAuthCd() == "LGL-000") {		// 시스템 관리자(LGL마스터)
				updCnt = codeDao.deleteSvcTypeReal(param);
			} else {
				updCnt = codeDao.deleteSvcType(param);
			}
			
			if (updCnt < 1) {
				// 내부 에러가 발생하였습니다 : 589
				return new BaseResponse(ResponseCode.C589);
			}
			
			return new BaseResponse(ResponseCode.C200);

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}
	}









}
