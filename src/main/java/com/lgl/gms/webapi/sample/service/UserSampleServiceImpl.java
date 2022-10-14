package com.lgl.gms.webapi.sample.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import com.lgl.gms.config.PropConfig;
import com.lgl.gms.webapi.common.dto.code.ResponseCode;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.common.dto.response.PagingListResponse;
import com.lgl.gms.webapi.common.util.ExcelUtil;
import com.lgl.gms.webapi.common.util.PagingUtil;
import com.lgl.gms.webapi.sample.dto.request.FileRequest;
import com.lgl.gms.webapi.sample.dto.request.UserSampleRequest;
import com.lgl.gms.webapi.sample.dto.response.FileResponse;
import com.lgl.gms.webapi.sample.dto.response.UserSampleResponse;
import com.lgl.gms.webapi.sample.persistence.dao.ExcelDao;
import com.lgl.gms.webapi.sample.persistence.dao.FileDao;
import com.lgl.gms.webapi.sample.persistence.dao.UserSampleDao;
import com.lgl.gms.webapi.sample.persistence.model.FileModel;
import com.lgl.gms.webapi.sample.persistence.model.SampleExcelModel;
import com.lgl.gms.webapi.sample.persistence.model.UserSampleModel;

import lombok.extern.slf4j.Slf4j;

@Transactional(transactionManager = "transactionManagerBatis")
@Slf4j
@Service
public class UserSampleServiceImpl implements UserSampleService {

	@Autowired
	private UserSampleDao userDao;
	@Autowired
	private FileDao fileDao;

	@Autowired
	private ExcelDao excelDao;

	@Autowired
	public PropConfig prop;

	@Autowired
	public FileService fileService;

	@Override
	public BaseResponse add(UserSampleRequest body) {

		try {
			// 필수입력값 체크
			if (body.getUsername() == null
					|| body.getUsername().trim().isEmpty() 
//					|| body.getPw() == null || body.getPw().trim().isEmpty() 
					|| body.getEmail() == null || body.getEmail().trim().isEmpty() 
				) {
				return new BaseResponse(ResponseCode.C781);
			}

			UserSampleModel user = userDao.selectUserByEmail(body.getEmail());
			if (user != null) {
				return new BaseResponse(ResponseCode.C805);
			}

			UserSampleModel param = new UserSampleModel();
			
			// 사용자 추가할 때 pwd가 있으면 암호화하는 처리
			// 여기서는 필요없으므로 주석처리
//			HMacUtil hmac = new HMacUtil();
//			String salt = hmac.getSalt();
//			param.setPw(hmac.getEncHmacShaWithSalt(body.getPw(), salt));
//			param.setSalt(salt);

			param.setEmail(body.getEmail());
			param.setUsername(body.getUsername());
			param.setPhone(body.getPhone());
			param.setGender(body.getGender());
			param.setCompany(body.getCompany());
			param.setAddress(body.getAddress());
			userDao.insertUser(param);

			UserSampleResponse userRes = new UserSampleResponse();
			userRes.setUsername(param.getUsername());
			userRes.setEmail(param.getEmail());
			userRes.setId(param.getId());

			return new BaseResponse(ResponseCode.C200, userRes);

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}
	}

	@Override
	public BaseResponse delete(UserSampleRequest body) {

		try {
			// 필수입력값 체크
			if (body.getId() == null || body.getId().trim().isEmpty()) {
				return new BaseResponse(ResponseCode.C781);
			}

			int isDeleted = userDao.deleteUser(body.getId());

			if (isDeleted < 1) {
				return new BaseResponse(ResponseCode.C589);
			}

			return new BaseResponse(ResponseCode.C200);

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}
	}

	@Override
	public BaseResponse modify(UserSampleRequest body) {

		try {
			// 필수입력값 체크
			if (body.getId() == null
					|| body.getId().trim().isEmpty()) {
				return new BaseResponse(ResponseCode.C781);
			}

			UserSampleModel param = new UserSampleModel();
			param.setId(Integer.parseInt(body.getId()));
			param.setUsername(body.getUsername());
			param.setPhone(body.getPhone());
			param.setPw(body.getPw());
			param.setGender(body.getGender());
			param.setCompany(body.getCompany());
			param.setAddress(body.getAddress());
			userDao.updateUser(param);

			UserSampleResponse userRes = new UserSampleResponse();
			userRes.setUsername(param.getUsername());
			userRes.setEmail(param.getEmail());
			userRes.setPhone(param.getPhone());
			userRes.setId(param.getId());

			return new BaseResponse(ResponseCode.C200, userRes);

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}
	}

	/**
	 * 트랜잭션 처리 샘플로 롤백처리 포함한 샘플
	 */
	@Override
	public BaseResponse modifyWithTransaction(UserSampleRequest body) {

		try {
			// 필수입력값 체크
			if (body.getId() == null
					|| body.getId().trim().isEmpty()) {
				return new BaseResponse(ResponseCode.C781);
			}

			FileModel fileModel = new FileModel();
			fileModel.setFilename("test");
			fileDao.insertFileInfo(fileModel);

			UserSampleModel param = new UserSampleModel();
			param.setId(Integer.parseInt(body.getId()));
			param.setUsername(body.getUsername());
			param.setPhone(body.getPhone());
			param.setCompany(body.getCompany());
			param.setAddress(body.getAddress());
			param.setGender("error"); // 강제로 데이터저장 오류 발생(Data too long)
			userDao.updateUser(param);

			UserSampleResponse userRes = new UserSampleResponse();
			userRes.setUsername(param.getUsername());
			userRes.setEmail(param.getEmail());
			userRes.setPhone(param.getPhone());
			userRes.setId(param.getId());

			return new BaseResponse(ResponseCode.C200, userRes);
		} catch (DataAccessException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new BaseResponse(ResponseCode.C589);
		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}
	}

	@Override
	public BaseResponse get(UserSampleRequest body) {

		try {
			// 필수입력값 체크
			if (body.getEmail() == null || body.getEmail().trim().isEmpty()) {
				return new BaseResponse(ResponseCode.C781);
			}

			UserSampleModel user = userDao.selectUserByEmail(body.getEmail());
			if (user == null) {
				return new BaseResponse(ResponseCode.C701);
			}
			UserSampleResponse userRes = new UserSampleResponse();
			userRes.setId(user.getId());
			userRes.setUsername(user.getUsername());
			userRes.setEmail(user.getEmail());
			userRes.setPhone(user.getPhone());
			userRes.setGender(user.getGender());
			userRes.setCompany(user.getCompany());
			userRes.setAddress(user.getAddress());
			BaseResponse res = new BaseResponse(ResponseCode.C200.getCode(), ResponseCode.C200.getMsg());
			res.setData(user);

			return new BaseResponse(ResponseCode.C200, userRes);

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}
	}

	@Override
	public BaseResponse list(UserSampleRequest body) {

		try {

			PagingUtil pagingUtil = new PagingUtil(body.getPage(), body.getListSize());
			HashMap<String, Object> param = pagingUtil.getParam();

			if (body.getSearchKey() != null && !body.getSearchKey().trim().equals("")) {
				param.put("searchKey", body.getSearchKey());
				param.put(body.getSearchKey(), body.getKeyword());
			}

			List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) userDao.selectUserList(param);

			int totalCount = userDao.selectUserListCount(param);
			int totalPage = pagingUtil.getTotalPage(totalCount);

			PagingListResponse resData = new PagingListResponse(totalCount, totalPage);

			List<UserSampleResponse> rows = new ArrayList<UserSampleResponse>();
			for (HashMap<String, Object> hashMap : list) {
				UserSampleResponse data = new UserSampleResponse();
				data.setData(hashMap);
				rows.add(data);
			}
			resData.setRows(rows);

			BaseResponse res = new BaseResponse(ResponseCode.C200);
			res.setData(resData);
			return res;

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}
	}

	@Override
	public BaseResponse upload(MultipartFile file, FileRequest body) {

		try {

			FileModel param = fileService.saveFile(file);
			fileDao.insertFileInfo(param);

			FileResponse fRes = new FileResponse(param.getId());
			fRes.setExt(param.getExt());
			fRes.setFilename(param.getFilename());
			fRes.setOrgname(param.getOrgname());
			fRes.setUrl("/v1/files/" + param.getId());

			return new BaseResponse(ResponseCode.C200, fRes);
		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}
	}

	@Override
	public BaseResponse uploadExcel(MultipartFile file, FileRequest body) {

		try {
			FileModel savedModel = fileService.saveFile(file);
			Workbook workbook = fileService.getExcelWorkbook(file);
			Sheet worksheet = workbook.getSheetAt(0);
			List<SampleExcelModel> list = new ArrayList<SampleExcelModel>();

			for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {

				Row row = worksheet.getRow(i);

				SampleExcelModel model = new SampleExcelModel();
				model.setNum(Integer.parseInt(ExcelUtil.getValue(row.getCell(0))));
				model.setCol1(ExcelUtil.getValue(row.getCell(1)));
				model.setCol2(ExcelUtil.getValue(row.getCell(2)));
				model.setCol3(ExcelUtil.getValue(row.getCell(3)));
				list.add(model);
				excelDao.insertExcelInfo(model);
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
	public BaseResponse getUserById(String id) {

		try {

			UserSampleModel user = userDao.selectUserById(id);
			if (user == null) {
				return new BaseResponse(ResponseCode.C701);
			}
			UserSampleResponse userRes = new UserSampleResponse();
			userRes.setId(user.getId());
			userRes.setUsername(user.getUsername());
			userRes.setEmail(user.getEmail());
			userRes.setPhone(user.getPhone());
			userRes.setGender(user.getGender());
			userRes.setCompany(user.getCompany());
			userRes.setAddress(user.getAddress());
			BaseResponse res = new BaseResponse(ResponseCode.C200.getCode(), ResponseCode.C200.getMsg());
			res.setData(user);

			return new BaseResponse(ResponseCode.C200, userRes);

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}
	}
}
