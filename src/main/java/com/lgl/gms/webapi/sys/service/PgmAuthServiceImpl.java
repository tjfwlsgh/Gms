package com.lgl.gms.webapi.sys.service;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgl.gms.webapi.common.dto.code.ResponseCode;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.sys.dto.request.AuthRequest;
import com.lgl.gms.webapi.sys.dto.request.PgmAuthRequest;
import com.lgl.gms.webapi.sys.dto.response.AuthResponse;
import com.lgl.gms.webapi.sys.dto.response.PgmAuthResponse;
import com.lgl.gms.webapi.sys.persistence.dao.PgmAuthDao;
import com.lgl.gms.webapi.sys.persistence.model.AuthModel;
import com.lgl.gms.webapi.sys.persistence.model.PgmAuthModel;

import lombok.extern.slf4j.Slf4j;

@Transactional(transactionManager = "transactionManagerBatis")
@Slf4j
@Service
public class PgmAuthServiceImpl implements PgmAuthService {

	@Autowired
	private PgmAuthDao pgmAuthDao;
	
	// 프로그램 리스트 취득
	@Override
	public BaseResponse getAuthList(AuthRequest body) {
		try {
			HashMap<String, Object> param = new HashMap<>();

			// param에 값이 없어도 키가 존재하면 mybatis 조건에서 구문이 제거 되지 않으므로 
			// 아래와 같이 값이 있는 경우에만 HashMap에 키값을 설정해야 함
			String authCd = body.getAuthCd();
			
			if (StringUtils.isNotBlank(authCd)) {	// 프로그램 타입
				param.put("authCd", authCd);
			}
			
			System.out.println("===>>> PgmAuthServiceImpl > getAuthList() > param : "+ param);
			
			List<AuthResponse> list = (List<AuthResponse>) pgmAuthDao.selectAuthList(param);
		
			BaseResponse res = new BaseResponse(ResponseCode.C200);
			res.setData(list);
			return res;

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}

	}

	@Override
	public BaseResponse getAuthOne(AuthRequest body) {
		try {
			HashMap<String, Object> param = new HashMap<>();

			if (StringUtils.isNotBlank(body.getAuthCd())) {	
				param.put("authCd", body.getAuthCd());
			}


			
			System.out.println("===>>> PgmServiceImpl > getPgmOne() > param : "+ param);
			
			AuthResponse pgmResp =  pgmAuthDao.selectAuthOne(param);

			BaseResponse res = new BaseResponse(ResponseCode.C200, pgmResp);
			return res;

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}
	}

	@Override
	public BaseResponse addAuth(AuthRequest body) {
		try {
			System.out.println("===>>> PgmAuthServiceImpl > addAuth() > body : "+ body);
			
			// 필수입력값(pgmId, pgmNm, pgmTyp, topMenuCd) 체크
			if (StringUtils.isBlank(body.getAuthCd()) ||  
					StringUtils.isBlank(body.getAuthNm()) 
				) {
				// 필수항목이 유효하지 않습니다 : 731
				return new BaseResponse(ResponseCode.C731);
			}
			
			HashMap<String, Object> hm = new HashMap<>();
			hm.put("authCd", body.getAuthCd());

			// 중복 체크
			AuthResponse auth = pgmAuthDao.selectAuthOne(hm);
			if (auth != null) {
				// 이미 존재하는 항목입니다. : 733
				return new BaseResponse(ResponseCode.C733);
			}
			
			AuthModel paramModel = new AuthModel();
			
			// 신규이므로 로그인 사용자 compId추가
			paramModel.setAuthCd(body.getAuthCd());
			paramModel.setAuthNm(body.getAuthNm());
			paramModel.setAuthNmEng(body.getAuthNmEng());
						
			System.out.println("===>>> PgmAuthServiceImpl > addAuth() > paramModel : "+ paramModel);
			
			// --- 사용자 insert ---
			int updCnt = pgmAuthDao.insertAuth(paramModel);
			
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
	public BaseResponse modifyAuth(AuthRequest body) {
		try {
			System.out.println("===>>> PgmAuthServiceImpl > modifyAuth() > body : "+ body);
			
			// 필수값(pgmId, pgmNm, pgmTyp, topMenuCd) 체크
			if (StringUtils.isBlank(body.getAuthCd()) ||  
					StringUtils.isBlank(body.getAuthNm())
				) {
				// 필수항목이 유효하지 않습니다 : 731
				return new BaseResponse(ResponseCode.C731);
			}
			
			AuthModel paramModel = new AuthModel();
			
			paramModel.setAuthCd(body.getAuthCd());	// PK
			paramModel.setAuthNm(body.getAuthNm());
			paramModel.setAuthNmEng(body.getAuthNmEng());
			paramModel.setDelYn(body.getDelYn());		// 삭제여부 수정 가능
			
			System.out.println("===>>> PgmServiceImpl > modifyPgm() > paramModel : "+ paramModel);
			
			// --- 사용자 Update ---
			int updCnt = pgmAuthDao.updateAuth(paramModel);
			
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
	public BaseResponse deleteAuth(AuthRequest body) {
		try {
			System.out.println("===>>> PgmAuthServiceImpl > deleteAuth() > body : "+ body);
			
			// 필수값(pgmId, pgmNm, pgmTyp, topMenuCd) 체크
			if (StringUtils.isBlank(body.getAuthCd()) 
				) {
				// 필수항목이 유효하지 않습니다 : 731
				return new BaseResponse(ResponseCode.C731);
			}
			
			HashMap<String, Object> param = new HashMap<>();
			param.put("authCd", body.getAuthCd());
			
			// 중복 체크
			int cnt = pgmAuthDao.selectPgmAuthCnt(param);
			if (cnt > 0) {
				// 하위 항목이 존재하므로 삭제할 수 없습니다.. : 737
				return new BaseResponse(ResponseCode.C737);
			}

			// --- 사용자 Update ---
			int updCnt = pgmAuthDao.deleteAuth(param);
			
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
	public BaseResponse getPgmAuthList(PgmAuthRequest body) {
		try {
			HashMap<String, Object> param = new HashMap<>();

			String authCd = body.getAuthCd();
			String topMenuCd = body.getTopMenuCd();
			
			if (StringUtils.isNotBlank(authCd)) {	// 권한코드
				param.put("authCd", authCd);
			}
			if (StringUtils.isNotBlank(topMenuCd)) {	// 대메뉴 코드
				param.put("topMenuCd", body.getTopMenuCd());
			}
			
			System.out.println("===>>> PgmAuthServiceImpl > getPgmAuthList() > param : "+ param);
			
			List<PgmAuthResponse> list = (List<PgmAuthResponse>) pgmAuthDao.selectPgmAuthList(param);
		
			BaseResponse res = new BaseResponse(ResponseCode.C200);
			res.setData(list);
			
			return res;

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}

	}

	@Override
	public BaseResponse getPgmAuthOne(PgmAuthRequest body) {
		// TODO Auto-generated method stub
		return null;
	}

	/* 프로그램-권한 정보 저장(기존 추가 처리와 다르게 처리)
	 * authCd와 topMenuCd를 조건으로 프로그램-권한 정보를 저장처리
	   저장처리는 해당 조건을 만족하는 프로그램 권한이 없으면 생성하고 
	   존재하지만 추가된 신규 프로그램이 있으면 추가 */
	@Override
	public BaseResponse addPgmAuth(PgmAuthRequest body) {

		try {
			System.out.println("===>>> PgmAuthServiceImpl > addPgmAuth() > body : "+ body);
			
			// 필수입력값(authCd, topMenuCd) 체크
			if (StringUtils.isBlank(body.getAuthCd()) ||  
				StringUtils.isBlank(body.getTopMenuCd()) 
				) {
				// 필수항목이 유효하지 않습니다 : 731
				return new BaseResponse(ResponseCode.C731);
			}
			
			PgmAuthModel paramModel = new PgmAuthModel();
			
			//조건 항목
			paramModel.setAuthCd(body.getAuthCd());
			paramModel.setTopMenuCd(body.getTopMenuCd());
						
			System.out.println("===>>> PgmAuthServiceImpl > addPgmAuth() > paramModel : "+ paramModel);
			
			// --- 사용자 insert ---
			int updCnt = pgmAuthDao.insertPgmAuth(paramModel);
			
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
	public BaseResponse modifyPgmAuth(PgmAuthRequest body) {

		try {
			System.out.println("===>>> PgmAuthServiceImpl > addPgmAuth() > body : "+ body);
			
			// 필수입력값(pgmId, pgmNm, pgmTyp, topMenuCd) 체크
			if (StringUtils.isBlank(body.getAuthCd()) ||  
				StringUtils.isBlank(body.getTopMenuCd()) ||
				StringUtils.isBlank(body.getPgmId())
				) {
				// 필수항목이 유효하지 않습니다 : 731
				return new BaseResponse(ResponseCode.C731);
			}
			
			PgmAuthModel paramModel = new PgmAuthModel();
			
			// pk조건(authCd+topMenuCd+PgmId)
			paramModel.setAuthCd(body.getAuthCd());
			paramModel.setTopMenuCd(body.getTopMenuCd());
			paramModel.setPgmId(body.getPgmId());
			paramModel.setViewSeq(body.getViewSeq());	// 순서
			paramModel.setDelYn(body.getDelYn());	// 삭제여부
			
			paramModel.setViewAuth(body.getViewAuth());	// View권한
			paramModel.setAddAuth(body.getAddAuth());	// 추가
			paramModel.setChgAuth(body.getChgAuth());	// 수정
			paramModel.setDelAuth(body.getDelAuth());	// 삭제
			paramModel.setDwlAuth(body.getDwlAuth());	// 다운로드
			paramModel.setUplAuth(body.getUplAuth());	// 업로드
			paramModel.setSveAuth(body.getSveAuth());	// 저장
			paramModel.setClsAuth(body.getClsAuth());	// 마감
			
			System.out.println("===>>> PgmAuthServiceImpl > modifyPgmAuth() > paramModel : "+ paramModel);
			
			// --- 사용자 insert ---
			int updCnt = pgmAuthDao.updatePgmAuth(paramModel);
			
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
	public BaseResponse deletePgmAuth(PgmAuthRequest body) {

		try {
			System.out.println("===>>> PgmAuthServiceImpl > deletePgmAuth() > body : "+ body);
			
			// 필수입력값(authCd, topMenuCd) 체크
			if (StringUtils.isBlank(body.getAuthCd()) ||  
				StringUtils.isBlank(body.getTopMenuCd()) 
				) {
				// 필수항목이 유효하지 않습니다 : 731
				return new BaseResponse(ResponseCode.C731);
			}
			
			PgmAuthModel paramModel = new PgmAuthModel();
			
			//조건 항목
			paramModel.setAuthCd(body.getAuthCd());
			paramModel.setTopMenuCd(body.getTopMenuCd());
						
			System.out.println("===>>> PgmAuthServiceImpl > deletePgmAuth() > paramModel : "+ paramModel);
			
			// --- 사용자 insert ---
			int updCnt = pgmAuthDao.deletePgmAuthReal(paramModel);
			
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
