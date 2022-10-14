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
import com.lgl.gms.webapi.sys.dto.request.PgmRequest;
import com.lgl.gms.webapi.sys.dto.response.PgmResponse;
import com.lgl.gms.webapi.sys.persistence.dao.PgmDao;
import com.lgl.gms.webapi.sys.persistence.model.PgmModel;
import com.lgl.gms.webapi.sys.persistence.model.TccModel;

import lombok.extern.slf4j.Slf4j;

@Transactional(transactionManager = "transactionManagerBatis")
@Slf4j
@Service
public class PgmServiceImpl implements PgmService {

	@Autowired
	private PgmDao pgmDao;
	
	// 프로그램 리스트 취득
	@Override
	public BaseResponse getPgmList(PgmRequest body) {
		try {
			HashMap<String, Object> param = new HashMap<>();

			// param에 값이 없어도 키가 존재하면 mybatis 조건에서 구문이 제거 되지 않으므로 
			// 아래와 같이 값이 있는 경우에만 HashMap에 키값을 설정해야 함
			String pgmTyp = body.getPgmTyp();
			
			if (StringUtils.isNotBlank(pgmTyp)) {	// 프로그램 타입
				param.put("pgmTyp", pgmTyp);
				
				if (StringUtils.isNotBlank(body.getTopMenuCd())) {	// 메뉴명
					param.put("topMenuCd", body.getTopMenuCd());
				}
				if (StringUtils.isNotBlank(body.getPgmId())) {	// 메뉴코드/프로그램id
					param.put("pgmId", body.getPgmId());
				}
				if (StringUtils.isNotBlank(body.getPgmNm())) {	// 메뉴/프로그램명
					param.put("pgmNm", body.getPgmNm());
				}
			}
			// 로그인 사용자 compId 설정
			param.put("compId", UserInfo.getCompId());  // 회사ID

			System.out.println("===>>> PgmServiceImpl > getPgmList() > param : "+ param);
			
			List<PgmResponse> list = null;
			
			if ("PGM".equals(pgmTyp)) {
				list = (List<PgmResponse>) pgmDao.selectPgmList(param);
			} else {
				list = (List<PgmResponse>) pgmDao.selectMenuList(param);
			}
		
			BaseResponse res = new BaseResponse(ResponseCode.C200);
			res.setData(list);
			return res;

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}

	}

	@Override
	public BaseResponse getPgmOne(PgmRequest body) {
		try {
			HashMap<String, Object> param = new HashMap<>();

			if (StringUtils.isNotBlank(body.getPgmId())) {	// 사용자구분(MNGR:관리자, USER:사용자)
				param.put("pgmId", body.getPgmId());
			}

			// 로그인 사용자 compId 설정
			param.put("compId", UserInfo.getCompId());  // 회사ID
			
			System.out.println("===>>> PgmServiceImpl > getPgmOne() > param : "+ param);
			
			PgmResponse pgmResp =  pgmDao.selectPgmOne(param);

			BaseResponse res = new BaseResponse(ResponseCode.C200, pgmResp);
			return res;

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}
	}

	@Override
	public BaseResponse addPgm(PgmRequest body) {
		try {
			System.out.println("===>>> PgmServiceImpl > addPgm() > body : "+ body);
			
			// 필수입력값(pgmId, pgmNm, pgmTyp, topMenuCd) 체크
			if (StringUtils.isBlank(body.getPgmId()) ||  
					StringUtils.isBlank(body.getPgmNm()) ||
					StringUtils.isBlank(body.getPgmTyp()) ||
					StringUtils.isBlank(body.getTopMenuCd())
				) {
				// 필수항목이 유효하지 않습니다 : 731
				return new BaseResponse(ResponseCode.C731);
			}
			
			HashMap<String, Object> hm = new HashMap<>();
			hm.put("pgmId", body.getPgmId());

			// 중복 체크
			PgmResponse pgm = pgmDao.selectPgmOne(hm);
			if (pgm != null) {
				// 이미 존재하는 항목입니다. : 733
				return new BaseResponse(ResponseCode.C733);
			}
			
			PgmModel paramModel = new PgmModel();
			
			// 신규이므로 로그인 사용자 compId추가
			paramModel.setPgmId(body.getPgmId());
			paramModel.setPgmNm(body.getPgmNm());
			paramModel.setPgmSnm(body.getPgmSnm());
			paramModel.setPgmNmEng(body.getPgmNmEng());
			paramModel.setPgmSnmEng(body.getPgmSnmEng());
			paramModel.setTopMenuCd(body.getTopMenuCd());
//			paramModel.setViewNm(body.getViewNm());
			paramModel.setPgmTyp(body.getPgmTyp());
			paramModel.setLinkFile(body.getLinkFile());
			paramModel.setRmrk(body.getRmrk());
			paramModel.setCompId(UserInfo.getCompId());	// 회사 id
			paramModel.setViewSeq(body.getViewSeq());
			
			System.out.println("===>>> PgmServiceImpl > addPgm() > paramModel : "+ paramModel);
			
			// --- 사용자 insert ---
			int updCnt = pgmDao.insertPgm(paramModel);
			
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
	public BaseResponse modifyPgm(PgmRequest body) {
		try {
			System.out.println("===>>> PgmServiceImpl > modifyPgm() > body : "+ body);
			
			// 필수값(pgmId, pgmNm, pgmTyp, topMenuCd) 체크
			if (StringUtils.isBlank(body.getPgmId()) ||  
					StringUtils.isBlank(body.getPgmNm()) ||
					StringUtils.isBlank(body.getPgmTyp()) ||
					StringUtils.isBlank(body.getTopMenuCd())
				) {
				// 필수항목이 유효하지 않습니다 : 731
				return new BaseResponse(ResponseCode.C731);
			}
			
			PgmModel paramModel = new PgmModel();
			
			paramModel.setPgmId(body.getPgmId());	// PK
			paramModel.setPgmNm(body.getPgmNm());
			paramModel.setPgmSnm(body.getPgmSnm());
			paramModel.setPgmNmEng(body.getPgmNmEng());
			paramModel.setPgmSnmEng(body.getPgmSnmEng());
			paramModel.setTopMenuCd(body.getTopMenuCd());
//			paramModel.setViewNm(body.getViewNm());
			paramModel.setPgmTyp(body.getPgmTyp());
			paramModel.setLinkFile(body.getLinkFile());
			paramModel.setRmrk(body.getRmrk());
			paramModel.setViewSeq(body.getViewSeq());
			paramModel.setDelYn(body.getDelYn());		// 삭제여부 수정 가능
			
			System.out.println("===>>> PgmServiceImpl > modifyPgm() > paramModel : "+ paramModel);
			
			// --- 사용자 Update ---
			int updCnt = pgmDao.updatePgm(paramModel);
			
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
	public BaseResponse deletePgm(PgmRequest body) {
		try {
			System.out.println("===>>> PgmServiceImpl > deletePgm() > body : "+ body);
			
			// 필수값(pgmId, pgmNm, pgmTyp, topMenuCd) 체크
			if (StringUtils.isBlank(body.getPgmId()) 
				) {
				// 필수항목이 유효하지 않습니다 : 731
				return new BaseResponse(ResponseCode.C731);
			}
			
			HashMap<String, Object> param = new HashMap<>();
			param.put("pgmId", body.getPgmId());
			
			// --- 사용자 Update ---
			int updCnt = pgmDao.deletePgm(param);
			
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
	public BaseResponse getMenuCodeList(PgmRequest body) {
		try {
			HashMap<String, Object> param = new HashMap<>();

			String pgmTyp = body.getPgmTyp();
			
			if (StringUtils.isNotBlank(pgmTyp)) {	// 프로그램 타입
				param.put("pgmTyp", pgmTyp);
				
				if (StringUtils.isNotBlank(body.getDelYn())) {	// 메뉴명
					param.put("delYn", body.getDelYn());
				}
				if (StringUtils.isNotBlank(body.getPgmId())) {	// 메뉴명
					param.put("pgmId", body.getPgmId());
				}
			}
			// 로그인 사용자 compId 설정
			param.put("compId", UserInfo.getCompId());  // 회사ID

			System.out.println("===>>> PgmServiceImpl > getMenuCodeList() > param : "+ param);
			
			List<PgmResponse> list = (List<PgmResponse>) pgmDao.selectMenuList(param);

			BaseResponse res = new BaseResponse(ResponseCode.C200);
			res.setData(list);
			return res;

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}

	}
}
