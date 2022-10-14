package com.lgl.gms.webapi.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lgl.gms.webapi.common.dto.code.ResponseCode;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.common.service.ExcelUploadService;
import com.lgl.gms.webapi.common.util.ExcelUtil;
import com.lgl.gms.webapi.inc.persistence.dao.IncPlanDao;
import com.lgl.gms.webapi.sample.dto.response.ExcelUploadModelSample;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ExcelUploadSampleServiceImpl implements ExcelUploadService {
	
	@Autowired
	private IncPlanDao boIncPlnDao;
	
	@Override
	public BaseResponse excelUpload(MultipartFile file, Object body) {
		List<ExcelUploadModelSample> sampleExcelDtoList;		// 엑셀 내용 담을 리스트 객체
		try {
//			ExcelValidResource excelValidResource
//												= new ExcelValidResource(boIncPlnDao.selectIncPlnTccList()
//																						, boIncPlnDao.selectBoCustList(1000001)
//																						, boIncPlnDao.selectIncSvcTypList());
//			ExcelUtil.setExcelValidResource(excelValidResource);
		
			sampleExcelDtoList = ExcelUtil.getObjectList(file, ExcelUploadModelSample::from, 3);			
			return new BaseResponse(ResponseCode.C200, sampleExcelDtoList);
			
		} catch (Exception e) {
			log.error("ExcelUploadSampleServiceImpl excelUpload Exception => {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
		}

	}
	

}
