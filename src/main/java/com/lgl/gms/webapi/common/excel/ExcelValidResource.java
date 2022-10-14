package com.lgl.gms.webapi.common.excel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.lgl.gms.webapi.inc.dto.response.SvcTypResponse;
import com.lgl.gms.webapi.inc.dto.response.TccResponse;
import com.lgl.gms.webapi.inc.persistence.model.BoCustModel;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * X 현재사용안하는 객체임
 * 추후를 위하여 남겨놓음
 * @author jokim *
 */
@Data
@AllArgsConstructor
public class ExcelValidResource {

	public ExcelValidResource(List<TccResponse> tccCodeList, List<BoCustModel> boCustList) {
		
		if(boCustList != null && !boCustList.isEmpty() ) {
			setCustNoMap(boCustList);
			setCustNmMap(boCustList);
			setCustEngNmMap(boCustList);
		}
		
		// 배열(첫번째 배열 : 그룹1, 두번째배열 : 그룹2, 세번째배열 : 그룹3)
		if(tccCodeList != null && !tccCodeList.isEmpty() ) {
			setTccCodeList(tccCodeList);
		}
		
	}
	
	public ExcelValidResource(List<TccResponse> tccCodeList, List<BoCustModel> boCustList, List<SvcTypResponse> svcTypList) {
		
		if(boCustList != null && !boCustList.isEmpty() ) {
			setCustNoMap(boCustList);
			setCustNmMap(boCustList);
			setCustEngNmMap(boCustList);
		}
		
		// 배열(첫번째 배열 : 그룹1, 두번째배열 : 그룹2, 세번째배열 : 그룹3)
		if(tccCodeList != null && !tccCodeList.isEmpty() ) {
			setTccCodeList(tccCodeList);
		}
		
		// 서비스유형
		if(svcTypList != null && !svcTypList.isEmpty() ) {
			setSvcTypList(svcTypList);
		}
		
	}
	
	private List<TccResponse> tccCodeList = null;			// 공통코드
	private List<SvcTypResponse> svcTypList = null;		// 서비스유형
	private Map<String, BoCustModel> custNoMap = null;	// 해당법인에 대한 거래처(거래처코드별)
	private Map<String, String> custNmMap = null;	// 해당법인에 대한 거래처(거래처명 별)
	private Map<String, String> custNmEngMap = null;	// 해당법인에 대한 거래처(거래처명-CUST_NM_ENG 별)
	
	/**
	 * 해당법인에 대한 거래처 - 엑셀업로드 시 거래처코드별로 저장
	 * @param boCustList
	 */
	public void setCustNoMap(List<BoCustModel> boCustList) {
		Map<String, BoCustModel> custMap =
				boCustList.stream().collect(Collectors.toMap(BoCustModel::getBoCustCd,
							Function.identity(),
							(o,n) -> o, 
							HashMap::new));
		custNoMap = custMap;
	}
	
	/**
	 * 해당법인에 대한 거래처 - 엑셀업로드 시 거래처명(CUST_NM)별로 저장
	 * @param boCustList
	 */
	private void setCustNmMap(List<BoCustModel> boCustList) {
		Map<String, String> custMap =
				boCustList.stream().collect(Collectors.toMap(BoCustModel::getCustNm,
						BoCustModel::getBoCustCd,
							(o,n) -> o, 
							HashMap::new));
		this.custNmMap = custMap;
	}
	
	/**
	 * 해당법인에 대한 거래처 - 엑셀업로드 시 거래처명(CUST_NM_ENG)별로 저장
	 * @param boCustList
	 */
	private void setCustEngNmMap(List<BoCustModel> boCustList) {
		Map<String, String> custMap =
				boCustList.stream().collect(Collectors.toMap(BoCustModel::getCustNmEng,
							BoCustModel::getBoCustCd,
							(o,n) -> o, 
							HashMap::new));
		this.custNmEngMap = custMap;
	}
	
	/**
	 * 코드
	 * @param tccCodeList
	 */
//	public void setTccCodeMaps(List<TccResponse> tccCodeList) {
////		List<Map<Integer, SvcTypResponse>> tCodeMaps = new ArrayList<Map<Integer, SvcTypResponse>>();
////		for( int idx  =0; idx < tccCodeList.size(); idx++ ) {
//////			Map<String, TccValResponse> tMap = new HashMap<String, TccValResponse>(); 
////			List<TccVal> tccs = tccCodeList.get(idx).getTccVals();
////			Map<Integer, SvcTypResponse> tMap =
////					tccs.stream().collect(Collectors.toMap(SvcTypResponse::getTccvId,
////							Function.identity(),
////								(o,n) -> o, 
////								HashMap::new));
////			tCodeMaps.add(tMap);
////		}
//		this.tccCodeList = tccCodeList;
//	}
	
	
	
}
