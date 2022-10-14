package com.lgl.gms.webapi.sys.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgl.gms.webapi.common.context.UserInfo;
import com.lgl.gms.webapi.common.dto.code.ResponseCode;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.common.util.CommonUtil;
import com.lgl.gms.webapi.common.util.HMacUtil;
import com.lgl.gms.webapi.sys.dto.request.PwdChangeRequest;
import com.lgl.gms.webapi.sys.dto.request.UserRequest;
import com.lgl.gms.webapi.sys.dto.response.PwdChangeResponse;
import com.lgl.gms.webapi.sys.dto.response.UserResponse;
import com.lgl.gms.webapi.sys.persistence.dao.UserDao;
import com.lgl.gms.webapi.sys.persistence.model.UserModel;

import lombok.extern.slf4j.Slf4j;

@Transactional(transactionManager = "transactionManagerBatis")
@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
//	@Autowired
//	private FileDao fileDao;
//	@Autowired
//	private ExcelDao excelDao;
//	@Autowired
//	public FileService fileService;
//	@Autowired
//	public PropConfig prop;

	@Override
	public BaseResponse getUserList(UserRequest body) {

		try {

			HashMap<String, Object> param = new HashMap<>();

			if (StringUtils.isNotBlank(body.getUserTyp())) {
				param.put("userTyp", body.getUserTyp());
			}
			if (StringUtils.isNotBlank(body.getUserId())) {
				param.put("userId", body.getUserId());
			}
			if (StringUtils.isNotBlank(body.getUserNm())) {
				param.put("userNm", body.getUserNm());
			}
			if (StringUtils.isNotBlank(body.getDelYn())) {	// 삭제구분
				param.put("delYn", body.getDelYn());
			}
			// 로그인 사용자 compId 설정
			param.put("compId", UserInfo.getCompId());
			// 사용 lang
			param.put("lgngCd", UserInfo.getLocale());
			System.out.println("===>>> UserServiceImpl > getUserList() > param : "+ param);
			
			List<UserResponse> userList = (List<UserResponse>) userDao.selectUserList(param);

			BaseResponse res = new BaseResponse(ResponseCode.C200);
			res.setData(userList);
			return res;

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}
	}


	@Override
	public BaseResponse getUserOne(String id) {

		try {
			// 사용자 상세 정보 조회(1건)
			UserResponse userRes = userDao.selectUserOne(id);
			
			if (userRes == null) {
				// 사용자 id가 존재하지 않음 : 701
				return new BaseResponse(ResponseCode.C701);
			}

			return new BaseResponse(ResponseCode.C200, userRes);

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}
	}

	@Override
	public BaseResponse addUser(UserRequest body) {
		
		try {
			System.out.println("===>>> UserServiceImpl > addUser() > body : "+ body);
			// 필수입력값(id, pwd) 체크
			if (StringUtils.isBlank(body.getUserId()) ||  
					StringUtils.isBlank(body.getUserPwd())
				) {
				// ID 또는 Pwd가 유효하지 않습니다 : 781
				return new BaseResponse(ResponseCode.C781);
			}

			HashMap<String, Object> userParam = new HashMap<>();
			userParam.put("userId", body.getUserId());
			userParam.put("compId", UserInfo.getCompId());
			
			UserModel user = userDao.selectUserById(userParam);
			if (user != null) {
				// ID가 중복되었습니다 : 805
				return new BaseResponse(ResponseCode.C805);
			}

			UserModel param = new UserModel();
			
			// 사용자 추가할 때 pwd 암호화하는 처리
			HMacUtil hmac = new HMacUtil();
			param.setUserPwd(hmac.getEncHmacSha(body.getUserPwd()));

			param.setUserId(body.getUserId());
			param.setUserNm(body.getUserNm());
			param.setUserNmEng(body.getUserNmEng());
			param.setUseLang(body.getUseLang());
			
			// compId는 로그인 사용자의 compId로 설정
			param.setCompId(UserInfo.getCompId());

			param.setBoId(body.getBoId());
			
			param.setUserTyp(body.getUserTyp());
			param.setAuthCd(body.getAuthCd());
			param.setCntryCd(body.getCntryCd());
			param.setDelYn(body.getDelYn());

			System.out.println("===>>> UserServiceImpl > addUser() > param : "+ param);
			
			// --- 사용자 insert ---
			int updCnt = userDao.insertUser(param);
			
			if (updCnt < 1) {
				// 내부 에러가 발생하였습니다 : 589
				return new BaseResponse(ResponseCode.C589);
			}
			
			// --- 사용자 등록 후 동시에 비밀번호 변경이력 등록 ---
			// insert 후 자동 생성된 userNo를 param 객체에 받아오도록 설정했으므로 확인
			System.out.println("===>>> UserServiceImpl > addUser() > param.userNo : "+ param.getUserNo());
						
			updCnt = userDao.insertUserPwdHistory(param);
			
			if (updCnt < 1) {
				// 내부 에러가 발생하였습니다 : 589
				return new BaseResponse(ResponseCode.C589);
			}
			
			// return할 필요가 있는지 확인 필요
//			UserResponse userRes = new UserResponse();
//			userRes.setUserId(param.getUserId());
//			return new BaseResponse(ResponseCode.C200, userRes);
			return new BaseResponse(ResponseCode.C200);

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}
	}

	@Override
	public BaseResponse checkUserId(String userId) {
		
		try {
			System.out.println("===>>> UserServiceImpl > checkUserId() > userId : "+ userId);
			// 필수입력값 id 존재여부 체크(
			if (StringUtils.isBlank(userId)				) {
				// ID 또는 Pwd가 유효하지 않습니다 : 781
				return new BaseResponse(ResponseCode.C781);
			}

			HashMap<String, Object> userParam = new HashMap<>();
			userParam.put("userId", userId);
			userParam.put("compId", UserInfo.getCompId());
			
			UserModel user = userDao.selectUserById(userParam);
			if (user != null) {
				// ID가 중복되었습니다 : 805
				return new BaseResponse(ResponseCode.C805);
			}

			// svrCode가 200이 리턴이면 등록된 userId가 아님
			return new BaseResponse(ResponseCode.C200);

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}
	}
	
	@Override
	public BaseResponse modifyUser(UserRequest body) {

		try {
			// 필수입력값(id, pwd) 체크
			if (StringUtils.isBlank(body.getUserId())) {
				// ID 또는 Pwd가 유효하지 않습니다 : 781
				return new BaseResponse(ResponseCode.C781);
			}

			UserModel param = new UserModel();
			// PK에 해당하는 userId, compId 설정
			param.setUserId(body.getUserId());
			param.setCompId(body.getCompId());
			
			// 사용자 수정시 pwd가 있으면 암호화하고 파라미터에 추가
			// 수정 pwd가 기존 Pwd을 비교해서 같지 않으면 수정 처리
			HMacUtil hmac = new HMacUtil();
			UserModel user = null;
			String encPwd = "";
			HashMap<String, Object> userParam = new HashMap<>();
			userParam.put("userId", body.getUserId());
			userParam.put("compId", UserInfo.getCompId());
			
			// 수정 pwd가 존재하면 
			if (!StringUtils.isBlank(body.getUserPwd())) {
				encPwd = hmac.getEncHmacSha(body.getUserPwd());
				user = userDao.selectUserById(userParam);
				// 기존 pwd와 수정 pwd가 같으면 
				if (encPwd.equals(user.getUserPwd())) {
					//   C783: "기존 Password와 변경 Password가 동일합니다."
					return new BaseResponse(ResponseCode.C783);
				}
				param.setUserPwd(encPwd);	
			}
			
			param.setUserNm(body.getUserNm());
			param.setUserNmEng(body.getUserNmEng());
			param.setUseLang(body.getUseLang());
	
			param.setBoId(body.getBoId());
			param.setUserTyp(body.getUserTyp());
			param.setAuthCd(body.getAuthCd());
			param.setCntryCd(body.getCntryCd());
			param.setDelYn(body.getDelYn());

// 2022.03.20 yhlee
// updNo, regNo는 BaseModel에서 자동으로 설정되므로 아래 코드는 불필요
// 로그인 사용자 id가 필요한 경우 아래 코드와 같이 어디에서든 UserContext에서 꺼내서 사용 가능 
//			TokenInfo tokenInfo = UserContextHolder.getContext();
//			param.setUpdNo(tokenInfo.getUserId());
			
//			System.out.println("===>>> UserServiceImpl > modifyUser() > tokenInfo : "+ tokenInfo);
			System.out.println("===>>> UserServiceImpl > modifyUser() > param : "+ param);
			
			// --- 사용자 정보 update ---
			int updCnt = userDao.updateUser(param);

			if (updCnt < 1) {
				// 내부 에러가 발생하였습니다 : 589
				return new BaseResponse(ResponseCode.C589);
			}
			
			// --- 비밀번호 변경인 경우 변경이력 등록 ---
			if (!StringUtils.isBlank(body.getUserPwd())) {
				// 변경이력 테이블은 userNo가 키이므로 설정
				param.setUserNo(body.getUserNo());	
				System.out.println("===>>> UserServiceImpl > modifyUser() > userNo : "+ body.getUserNo());
				// 비밀번호 변경 이력 update
				userDao.updateUserPwdHistory(param);				
			}
		
			// return할 필요가 있는지 확인 필요
//			UserResponse userRes = new UserResponse();
//			userRes.setUserId(param.getUserId());
//			return new BaseResponse(ResponseCode.C200, userRes);
			return new BaseResponse(ResponseCode.C200);
		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}
	}
	
	@Override
	public BaseResponse deleteUser(UserRequest body) {

		try {
			// 필수입력값 체크
			if (StringUtils.isBlank(body.getUserId())) {
				// ID 또는 Pwd가 유효하지 않습니다 : 781
				return new BaseResponse(ResponseCode.C781);
			}

			System.out.println("===>>> UserServiceImpl > modifyUser() > userId : "+ body.getUserId());
			
			UserModel param = new UserModel();
			
			param.setUserId(body.getUserId());
			param.setCompId(UserInfo.getCompId());
			
			int updCnt = userDao.deleteUser(param);

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
	public BaseResponse changeUserPwd(PwdChangeRequest body, 
			HttpServletRequest request, HttpServletResponse response) {

		try {
			log.debug("===>>> LoginServiceImpl > changeUserPwd() > body : " + body);

			// 사용자 id로 사용자 no 조회
			if (body.getUserId() == null || body.getUserPwd() == null || 
					body.getNewPwd() == null) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);	// 401
				// 유효하지 않은 파라미터
				return new BaseResponse(ResponseCode.C781);
			}
			
			HashMap<String, Object> param = new HashMap<>();
			param.put("userId", body.getUserId());
			param.put("compId", body.getCompId());	
			// UserId, compId로 DB의 사용자 정보 조회
			UserModel user = userDao.selectUserById(param);
			
			if (user == null) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				// C701:사용자가 존재하지 않음
				return new BaseResponse(ResponseCode.C701);
			}
	
			// new pwd를 암호화
			HMacUtil hmac = new HMacUtil();
			String newPwdEnc = hmac.getEncHmacSha(body.getNewPwd());
			
			// 요청의 비밀번호와 DB 비밀번호가 같은지 확인, 틀리면 에러 
			if (!hmac.getEncHmacSha(body.getUserPwd()).equals(user.getUserPwd())) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				// 현재 비밀번호가 유효하지 않습니다.
				return new BaseResponse(ResponseCode.C784);
			}
			
	//		String workIp = request.getRemoteAddr();
			String workIp = CommonUtil.getClientIP(request);
			log.debug("===>>> LoginServiceImpl > changeUserPwd() > workIp : " + workIp);
			
			// 비번 변경이력의 pwd1, pwd2, pwd3 조회해서 최근 3회까지의 비번인지 확인
			// 같은게 있으면 에러코드와 함께 리턴 처리
			body.setUserNo(user.getUserNo());	// pk
			
			PwdChangeResponse userPwdHist = userDao.selectUserPwdHistory(body);
			
			if (userPwdHist == null) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				// C711 : 사용자의 비밀번호 변경이력이 존재하지 않습니다.
				return new BaseResponse(ResponseCode.C711);
			}
			
			log.debug("===>>> LoginServiceImpl > changeUserPwd() > userPwdHist : " + userPwdHist);
			if (newPwdEnc.equals(userPwdHist.getUserPwd1()) || 
					newPwdEnc.equals(userPwdHist.getUserPwd2()) || 
					newPwdEnc.equals(userPwdHist.getUserPwd3()) 
				) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				// 최근 3회까지 이전 비밀번호는 사용할 수 없습니다.
				return new BaseResponse(ResponseCode.C785);
			}
			// 문제 없으면 new pwd로 사용자 테이블의 pwd 변경
			user.setUserPwd(newPwdEnc);	
			user.setWorkIp(workIp);
			user.setUpdNo(body.getUserId());
			
			// --- 사용자 정보 update ---
			int updCnt = userDao.updateUserPwd(user);

			if (updCnt < 1) {
				// 내부 에러가 발생하였습니다 : 589
				return new BaseResponse(ResponseCode.C589);
			}
			
			// pwd 변경 이력 업데이트
			// user에 신규 비밀번호도 set된 상태이고 user를 그대로 사용
			// 비밀번호 변경 이력 update
			updCnt = userDao.updateUserPwdHistory(user);				

			if (updCnt < 1) {
				// 내부 에러가 발생하였습니다 : 589
				return new BaseResponse(ResponseCode.C589);
			}
			
			// 정상 처리 상태 리턴
			BaseResponse res = new BaseResponse(ResponseCode.C200);
			
			return res;

			
		} catch (Exception e) {
			log.warn(e.toString(), e);
			// HttpResponse.status : 401 리턴
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);	
			// 에러가 발생하면 내부 에러로 리턴
			return new BaseResponse(ResponseCode.C589);
		}
	}
	@Override
	public BaseResponse delayUserPwd(PwdChangeRequest body, 
			HttpServletRequest request, HttpServletResponse response) {

		try {
			log.debug("===>>> LoginServiceImpl > delayUserPwd() > body : " + body);

			// 사용자 id로 사용자 no 조회
			if (body.getUserId() == null || body.getCompId() == null)  {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);	// 401
				// 유효하지 않은 파라미터
				return new BaseResponse(ResponseCode.C781);
			}

			// String workIp = request.getRemoteAddr();
			String workIp = CommonUtil.getClientIP(request);
			log.debug("===>>> LoginServiceImpl > changeUserPwd() > workIp : " + workIp);
			
			HashMap<String, Object> param = new HashMap<>();
			param.put("userId", body.getUserId());
			param.put("compId", body.getCompId());	
			param.put("updNo", body.getUserId());	
			param.put("workIp", workIp);	
	
			// pwd 90일 연장
			int updCnt = userDao.updateUserPwdChgDelay(param);				

			if (updCnt < 1) {
				// 내부 에러가 발생하였습니다 : 589
				return new BaseResponse(ResponseCode.C589);
			}
			
			// 정상 처리 상태 리턴
			BaseResponse res = new BaseResponse(ResponseCode.C200);
			
			return res;

			
		} catch (Exception e) {
			log.warn(e.toString(), e);
			// HttpResponse.status : 401 리턴
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);	
			// 에러가 발생하면 내부 에러로 리턴
			return new BaseResponse(ResponseCode.C589);
		}
	}

	/**
	 * 트랜잭션 처리 샘플로 롤백처리 포함한 샘플
	 */
//	@Override
//	public BaseResponse modifyWithTransaction(UserRequest body) {
//
//		try {
//			// 필수입력값 체크
//			if (body.getUserId() == null
//					|| body.getUserId().trim().isEmpty()) {
//				return new BaseResponse(ResponseCode.C781);
//			}
//
//			FileModel fileModel = new FileModel();
//			fileModel.setFilename("test");
//			fileDao.insertFileInfo(fileModel);
//
//			UserModel param = new UserModel();
//			param.setUserId(Integer.parseInt(body.getUserId()));
//			param.setUsername(body.getUsername());
//			param.setPhone(body.getPhone());
//			param.setCompany(body.getCompany());
//			param.setAddress(body.getAddress());
//			param.setGender("error"); // 강제로 데이터저장 오류 발생(Data too long)
//			userDao.updateUser(param);
//
//			UserResponse userRes = new UserResponse();
//			userRes.setUsername(param.getUsername());
//			userRes.setEmail(param.getEmail());
//			userRes.setPhone(param.getPhone());
//			userRes.setUserId(param.getUserId());
//			return new BaseResponse(ResponseCode.C200, userRes);
//		} catch (DataAccessException e) {
//			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//			return new BaseResponse(ResponseCode.C589);
//		} catch (Exception e) {
//			log.warn(e.toString(), e);
//			return new BaseResponse(ResponseCode.C589);
//		}
//	}

	//	@Override
//	public BaseResponse upload(MultipartFile file, FileRequest body) {

//		try {
//
//			FileModel param = fileService.saveFile(file);
//			fileDao.insertFileInfo(param);
//
//			FileResponse fRes = new FileResponse(param.getId());
//			fRes.setExt(param.getExt());
//			fRes.setFilename(param.getFilename());
//			fRes.setOrgname(param.getOrgname());
//			fRes.setUrl("/v1/files/" + param.getId());

//			FileResponse fRes = null;
//			return new BaseResponse(ResponseCode.C200, fRes);
//		} catch (Exception e) {
//			log.warn(e.toString(), e);
//			return new BaseResponse(ResponseCode.C589);
//		}
//	}

//	@Override
//	public BaseResponse uploadExcel(MultipartFile file, FileRequest body) {

//		try {
//			FileModel savedModel = fileService.saveFile(file);
//			Workbook workbook = fileService.getExcelWorkbook(file);
//			Sheet worksheet = workbook.getSheetAt(0);
//			List<SampleExcelModel> list = new ArrayList<SampleExcelModel>();
//
//			for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
//
//				Row row = worksheet.getRow(i);
//
//				SampleExcelModel model = new SampleExcelModel();
//				model.setNum(Integer.parseInt(ExcelUtil.getValue(row.getCell(0))));
//				model.setCol1(ExcelUtil.getValue(row.getCell(1)));
//				model.setCol2(ExcelUtil.getValue(row.getCell(2)));
//				model.setCol3(ExcelUtil.getValue(row.getCell(3)));
//				list.add(model);
//				excelDao.insertExcelInfo(model);
//			}
		
//			List<SampleExcelModel> list = null;
//			BaseResponse res = new BaseResponse(ResponseCode.C200);
//			res.setData(list);
//			return res;
//		} catch (Exception e) {
//			log.warn(e.toString(), e);
//			return new BaseResponse(ResponseCode.C589);
//		}
//	}

}
