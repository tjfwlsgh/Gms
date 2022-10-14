package com.lgl.gms.webapi.bse.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.lgl.gms.webapi.bse.dto.request.BseCloseRequest;
import com.lgl.gms.webapi.bse.dto.response.BseCloseResponse;
import com.lgl.gms.webapi.bse.persistence.dao.BseCloseDao;
import com.lgl.gms.webapi.cmm.dto.request.BoRequest;
import com.lgl.gms.webapi.cmm.dto.response.BoResponse;
import com.lgl.gms.webapi.common.context.UserInfo;
import com.lgl.gms.webapi.common.dto.code.ResponseCode;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.inc.dto.request.BoIncRetRequest;
import com.lgl.gms.webapi.inc.persistence.model.BoIncPlnModel;
import com.lgl.gms.webapi.inc.persistence.model.BoIncRetHopayModel;
import com.lgl.gms.webapi.inc.persistence.model.BoIncRetModel;
import com.lgl.gms.webapi.inc.service.IncExpPlanService;
import com.lgl.gms.webapi.inc.service.IncExpRetService;
import com.lgl.gms.webapi.inc.service.IncRetHopayService;
import com.lgl.gms.webapi.inc.service.IncSalesPlanService;
import com.lgl.gms.webapi.inc.service.IncSalesRetService;

import lombok.extern.slf4j.Slf4j;

/**
 * @FileName    : BseCloseServiceImpl.java
 * @Date        : 22.04.01
 * @Author      : hj.Chung
 * @Description : ServiceImpl
 * @History     : 마감 현황 ServiceImpl
 */                  
@Transactional(transactionManager = "transactionManagerBatis")
@Slf4j
@Service
public class BseCloseServiceImpl implements BseCloseService {
  
  @Autowired
  private BseCloseDao bcDao;
  
  @Autowired
  private IncSalesPlanService ispService;
  
  @Autowired
  private IncExpPlanService iepService;
  
  @Autowired
  private IncSalesRetService isrService;
  
  @Autowired
  private IncExpRetService ierService;
  
  @Autowired
  private IncRetHopayService irhService;
  

  @Override
  public BaseResponse getCloseList(BseCloseRequest paramDto) {
    try {
      // 마감 현황 List
      List<BseCloseRequest> list = (List<BseCloseRequest>)bcDao.selectCloseList(paramDto);
      
      BaseResponse res = new BaseResponse(ResponseCode.C200);
      res.setData(list);

      return res;
      
    } catch (Exception e) {
      log.warn(e.toString(), e);
      return new BaseResponse(ResponseCode.C589);
    }
  }

  @Override
  public BaseResponse selectBoList(BoRequest param) {
    try {
      
      List<BoResponse> list = (List<BoResponse>) bcDao.selectBoList(param);
      
      return new BaseResponse(ResponseCode.C200, list);

    } catch (Exception e) {
      log.error(e.toString(), e);
      return new BaseResponse(ResponseCode.C589);
    }
  }

  @Override
  public BaseResponse updateBoClear(Map<String, Object> IUDObj) {
    try {
      Map<String, Object> chMap = (Map<String, Object>) IUDObj.get("checkedList");
      
      if (chMap == null) {
        return new BaseResponse(ResponseCode.C781);
      }
      
      BseCloseRequest bcRequest = new BseCloseRequest();
      bcRequest.setClsFlg("N");
      
      commonCloseService(IUDObj, chMap, bcRequest);
      
      return new BaseResponse(ResponseCode.C200);
      
    } catch (Exception e) {
      log.error(e.toString(), e);
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      return new BaseResponse(ResponseCode.C589);
    }
  }

  @Override
  public BaseResponse updateCheckedCloser(Map<String, Object> IUDObj) {
    try {
    	
      Map<String, Object> chMap = (Map<String, Object>) IUDObj.get("checkedList");
      
      if (chMap == null) {
        return new BaseResponse(ResponseCode.C781);
      }
      
      BseCloseRequest bcRequest = new BseCloseRequest();
      bcRequest.setClsFlg("Y");
      commonCloseService(IUDObj, chMap, bcRequest);
      
      return new BaseResponse(ResponseCode.C200);
      
    } catch (Exception e) {
      log.error(e.toString(), e);
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      return new BaseResponse(ResponseCode.C589);
    }
  }

	private void commonCloseService(Map<String, Object> IUDObj, Map<String, Object> chMap, BseCloseRequest bcRequest) {
		// 일괄 마감 및 일괄 해제 프로시저 수행
		// clsFlg가 Y이면 일괄 마감 수행, N이면 일괄 마감 해제 수행
		
	    Integer boId = (Integer) chMap.get("boId");
	    String yymm = (String) IUDObj.get("yymm");
	    String retCl = IUDObj.get("pgm").toString().equals("CLS0P") ? "P" : "R";
	    String defCl = IUDObj.get("definit").toString();
	    String clsPgm = (String) chMap.get("clsPgm");
	    String yy = (String) IUDObj.get("yy");
	    
	    
	    bcRequest.setBoId(boId);
	    bcRequest.setIncYymm(yymm);
	    bcRequest.setPlnRetCl(retCl);
	    bcRequest.setDefCl(defCl);
	    bcRequest.setClsPgm(clsPgm);
	    bcRequest.setWorkIp(UserInfo.getWorkIp());
	    bcRequest.setUpdNo(UserInfo.getUserId());
	    
	    switch(clsPgm) {
	      case "BOINC001":  
	        bcRequest.setJobCl("PLN_CLS1");
	        break;
	      case "BOINC002":
	        bcRequest.setJobCl("PLN_CLS2");
	        break;
	      case "BOINC003":  
	        bcRequest.setJobCl("RET_CLS1");
	        break;
	      case "BOINC004":
	        bcRequest.setJobCl("RET_CLS2");
	        break;
	      default:  // "BOINC005"
	        bcRequest.setJobCl("RET_CLS3");
	        break;
	      }
	    	
	      bcDao.procBseUpdClsMgmt(bcRequest);
	
	}
  

}
