package com.lgl.gms.webapi.inf.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import com.lgl.gms.webapi.common.context.UserInfo;
import com.lgl.gms.webapi.common.dto.code.ResponseCode;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.common.util.ExcelUtil;
import com.lgl.gms.webapi.fin.dto.request.ArIncreAnalysisRequest;
import com.lgl.gms.webapi.fin.dto.response.ArIncreAnalysisExcelResponse;
import com.lgl.gms.webapi.fin.dto.response.ArIncreAnalysisResponse;
import com.lgl.gms.webapi.fin.dto.response.BoCustResponse;
import com.lgl.gms.webapi.inc.dto.response.SvcTypResponse;
import com.lgl.gms.webapi.inc.dto.response.TccResponse;
import com.lgl.gms.webapi.inc.persistence.model.BoCustModel;
import com.lgl.gms.webapi.inf.dto.request.InfEmployeeMgmtRequest;
import com.lgl.gms.webapi.inf.dto.response.InfEmployeeMgmtExcelResponse;
import com.lgl.gms.webapi.inf.dto.response.InfEmployeeMgmtResponse;
import com.lgl.gms.webapi.inf.persistence.dao.InfEmployeeMgmtDao;
import com.lgl.gms.webapi.inf.persistence.model.InfraBoEmpModel;

import lombok.extern.slf4j.Slf4j;

/**
 * @FileName    : InfEquipmentMgmtServiceImpl.java
 * @Date        : 22.03.14
 * @Author      : hj.Chung
 * @Description : Controller
 * @History     : 법인 장치현황 관리 ServiceImpl
 */
@Transactional(transactionManager = "transactionManagerBatis")
@Slf4j
@Service
public class InfEmployeeMgmtServiceImpl implements InfEmployeeMgmtService {
	
	@Autowired
	public InfEmployeeMgmtDao empDao;

	
	@Override
	public BaseResponse getEmployeeMgmtList(InfEmployeeMgmtRequest paramDto) {
		try {
			
			// 직원 현황 List
			List<InfEmployeeMgmtResponse> list = (List<InfEmployeeMgmtResponse>)empDao.selectInfraEmployeeMgmtList(paramDto);
			
			BaseResponse res = new BaseResponse(ResponseCode.C200);
			res.setData(list);
			
			log.debug("employeeList ====> {}" , res);
			
			return res;
			
		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}
	}

	@Override
	public BaseResponse addEmployeeMgmt(InfEmployeeMgmtRequest paramDto) {
		try {
			
			InfraBoEmpModel empModel = empDao.selectInfraEmployeeByEmpId(paramDto.getBoEmpId());
				
			if (empModel != null) {
				return new BaseResponse(ResponseCode.C805);
			}
			
			InfraBoEmpModel param = new InfraBoEmpModel();
			
			param.setBoEmpId(paramDto.getBoEmpId());
			param.setBoId(paramDto.getBoId());
			param.setEmpTypId(paramDto.getEmpTypId());
			param.setBaseDate(paramDto.getBaseDate());
			param.setSearchOpt(paramDto.getSearchOpt());
			param.setAssDt(paramDto.getAssDt());
			param.setRtnDt(paramDto.getRtnDt());
			param.setCompId(paramDto.getCompId());
			param.setEmpNm(paramDto.getEmpNm());
			param.setSex(paramDto.getSex());
			param.setDpt(paramDto.getDpt());
			param.setPst(paramDto.getPst());
			param.setHoPst(paramDto.getHoPst());
			param.setCty(paramDto.getCty());
			param.setJob(paramDto.getJob());
			param.setDoe(paramDto.getDoe());
			param.setRgd(paramDto.getRgd());
			
			param.setAccFmlyWife(paramDto.getAccFmlyWife());
			param.setAccFmlyChild(paramDto.getAccFmlyChild());
			param.setHsngContStd(paramDto.getHsngContStd());
			param.setHsngContEtd(paramDto.getHsngContEtd());
			param.setHsngContDocYn(paramDto.getHsngContDocYn());
			param.setHre(paramDto.getHre());
			param.setCrncyCd(paramDto.getCrncyCd());
			
			// 직원 현황 employee 추가
			empDao.insertEmployeeMgmt(param);
			
			InfEmployeeMgmtResponse empResponse = new InfEmployeeMgmtResponse();
			empResponse.setBoEmpId(param.getBoEmpId());
			empResponse.setBoId(param.getBoId());
			empResponse.setEmpTypId(param.getEmpTypId());
			empResponse.setBaseDate(param.getBaseDate());
			empResponse.setSearchOpt(param.getSearchOpt());
			empResponse.setAssDt(param.getAssDt());
			empResponse.setRtnDt(param.getRtnDt());
			empResponse.setEmpNm(param.getEmpNm());
			empResponse.setSex(param.getSex());
			empResponse.setDpt(param.getDpt());
			empResponse.setPst(param.getPst());
			empResponse.setHoPst(param.getHoPst());
			empResponse.setCty(param.getCty());
			empResponse.setJob(param.getJob());
			empResponse.setDoe(param.getDoe());
			empResponse.setRgd(param.getRgd());
			
			empResponse.setAccFmlyWife(param.getAccFmlyWife());
			empResponse.setAccFmlyChild(param.getAccFmlyChild());
			empResponse.setHsngContStd(param.getHsngContStd());
			empResponse.setHsngContEtd(param.getHsngContEtd());
			empResponse.setHsngContDocYn(param.getHsngContDocYn());
			empResponse.setHre(param.getHre());
			empResponse.setCrncyCd(param.getCrncyCd());
			
			
			return new BaseResponse(ResponseCode.C200, empResponse);
			
			
		} catch (Exception e) {
			log.warn(e.toString(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new BaseResponse(ResponseCode.C589);
		}
	}

	@Override
	public BaseResponse deleteEmployeeMgmt(InfEmployeeMgmtRequest paramDto) {
		try {
			// 필수입력값 체크
			if (StringUtils.isBlank(paramDto.getBoEmpId().toString())) {
				return new BaseResponse(ResponseCode.C781);
			}
			
			int isDeleted = empDao.deleteEmployeeMgmt(paramDto.getBoEmpId());
			
			if (isDeleted < 1) {
				return new BaseResponse(ResponseCode.C589);
			}

			return new BaseResponse(ResponseCode.C200);
			
		} catch (Exception e) {
			log.warn(e.toString(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new BaseResponse(ResponseCode.C589);
		}
	}

	@Override
	public BaseResponse modifyEmployeeMgmt(InfraBoEmpModel paramDto) {
		try {
			
			InfraBoEmpModel param = new InfraBoEmpModel();
			
			param.setBoEmpId(paramDto.getBoEmpId());
			param.setBoId(paramDto.getBoId());
			param.setSubBoId(paramDto.getSubBoId());
			param.setEmpTypId(paramDto.getEmpTypId());
			param.setBaseDate(paramDto.getBaseDate());
			param.setSearchOpt(paramDto.getSearchOpt());
			param.setAssDt(paramDto.getAssDt());
			param.setRtnDt(paramDto.getRtnDt());
			param.setCompId(paramDto.getCompId());
			param.setEmpNm(paramDto.getEmpNm());
			param.setSex(paramDto.getSex());
			param.setDpt(paramDto.getDpt());
			param.setPst(paramDto.getPst());
			param.setHoPst(paramDto.getHoPst());
			param.setCty(paramDto.getCty());
			param.setJob(paramDto.getJob());
			param.setDoe(paramDto.getDoe());
			param.setRgd(paramDto.getRgd());
			
			param.setAccFmlyWife(paramDto.getAccFmlyWife());
			param.setAccFmlyChild(paramDto.getAccFmlyChild());
			param.setHsngContStd(paramDto.getHsngContStd());
			param.setHsngContEtd(paramDto.getHsngContEtd());
			param.setHsngContDocYn(paramDto.getHsngContDocYn());
			param.setHre(paramDto.getHre());
			param.setCrncyCd(paramDto.getCrncyCd());
			
			// 직원 현황 employee 추가
			empDao.updateEmployeeMgmt(param);
			
			InfEmployeeMgmtResponse empResponse = new InfEmployeeMgmtResponse();
			empResponse.setBoEmpId(param.getBoEmpId());
			empResponse.setBoId(param.getBoId());
			empResponse.setSubBoId(param.getSubBoId());
			empResponse.setEmpTypId(param.getEmpTypId());
			empResponse.setBaseDate(param.getBaseDate());
			empResponse.setSearchOpt(param.getSearchOpt());
			empResponse.setAssDt(param.getAssDt());
			empResponse.setRtnDt(param.getRtnDt());
//			empResponse.setCompId(param.getCompId());
			empResponse.setEmpNm(param.getEmpNm());
			empResponse.setSex(param.getSex());
			empResponse.setDpt(param.getDpt());
			empResponse.setPst(param.getPst());
			empResponse.setHoPst(param.getHoPst());
			empResponse.setCty(param.getCty());
			empResponse.setJob(param.getJob());
			empResponse.setDoe(param.getDoe());
			empResponse.setRgd(param.getRgd());
			
			empResponse.setAccFmlyWife(param.getAccFmlyWife());
			empResponse.setAccFmlyChild(param.getAccFmlyChild());
			empResponse.setHsngContStd(param.getHsngContStd());
			empResponse.setHsngContEtd(param.getHsngContEtd());
			empResponse.setHsngContDocYn(param.getHsngContDocYn());
			empResponse.setHre(param.getHre());
			empResponse.setCrncyCd(param.getCrncyCd());
			
			return new BaseResponse(ResponseCode.C200, empResponse);
			
		} catch (Exception e) {
			log.warn(e.toString(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new BaseResponse(ResponseCode.C589);
		}
	}

	@Override
	public BaseResponse getRegidentMgmtList(InfEmployeeMgmtRequest paramDto) {
		try {
			// 주재원 List
			List<InfEmployeeMgmtResponse> list = (List<InfEmployeeMgmtResponse>)empDao.selectRegidentMgmtList(paramDto);
			
			BaseResponse res = new BaseResponse(ResponseCode.C200);
			res.setData(list);
			
			return res;
			
		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}
	}

	@Override
	public BaseResponse excelUpload(MultipartFile file, Object body) {
		InfEmployeeMgmtRequest req = (InfEmployeeMgmtRequest)body;
		List<InfEmployeeMgmtExcelResponse> empList;	// 엑셀 내용 담을 리스트 객체
		
		try {
			empList = ExcelUtil.getObjectList(file, InfEmployeeMgmtExcelResponse::from, 1, null, req.getSheetNum());
			
			
			for (int idx = 0; idx < empList.size(); idx++) {
				InfEmployeeMgmtExcelResponse emp = empList.get(idx);
				
				log.debug("emp ===> {}", emp);
			}
			
			return new BaseResponse(ResponseCode.C200, empList);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("InfEmployeeMgmtService excelUpload Exception => {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
		}
	}

	@Override
	public BaseResponse doSaveEmployeeExcel(Map<String, Object> IUDObj) {
		try {
			List<InfEmployeeMgmtResponse> empList = (List<InfEmployeeMgmtResponse>) IUDObj.get("empList");
			// empList.get(i)는 linkedHashMap이므로 새로운 Map에 담아줌
			Map<String, Object> map =  (Map<String, Object>) empList.get(0);
			
			if (empList == null || empList.size() == 0) {
				return new BaseResponse(ResponseCode.C781);
			}
			
			Map<String, Object> emp = null;
			
			for (int i = 0; i < empList.size(); i++) {
				
				emp = (Map<String, Object>) empList.get(i);
				
				for (String key : emp.keySet()) {
					
					String boKey = (emp.get("subBoNm") != null && emp.get("subBoNm") != "") ? (String) emp.get("subBoNm") : (String) emp.get("boNmH");
					Integer boId = empDao.selectBoIdByBoKey(boKey);
					
					emp.put("boId", boId);
					
					String empTypKey = (String) emp.get("empTypNm");
					Integer empTypId = empDao.selectEmpTypIdByEmpTypKey(empTypKey);
					
					emp.put("empTypId", empTypId);
					
					
				}
			}
			
//			log.debug("empList ===> {}", empList);
			
//			IUDObj.put("empList", empList);
//			log.debug("IUDObj ===> {}", IUDObj);
			
//			log.debug("empList.size() ===> {}", empList.size());
			
			for (int i = 0; i < empList.size(); i++) {
				
				emp = (Map<String, Object>) empList.get(i);
				
				int isInserted = empDao.insertExcelEmployeeMgmt(emp);
				
				if (isInserted < 1) {
//					엑셀 저장을 실패하였습니다. 다시 시도해 주세요. : 601
					return new BaseResponse(ResponseCode.C601);
				}
				
				isInserted = 0;
			
			}
			
			return new BaseResponse(ResponseCode.C200);
			
		} catch (Exception e) {
			log.error("doSaveEmployeeExcel==> {}", e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new BaseResponse(ResponseCode.C589);
		}
	}

}
