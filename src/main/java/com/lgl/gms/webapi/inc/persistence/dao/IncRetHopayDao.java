package com.lgl.gms.webapi.inc.persistence.dao;

import java.util.List;

import com.lgl.gms.webapi.inc.dto.request.BoIncRetHopayRequest;
import com.lgl.gms.webapi.inc.dto.request.BoIncRetRequest;
import com.lgl.gms.webapi.inc.dto.response.BoIncRetHopayResponse;
import com.lgl.gms.webapi.inc.dto.response.IncHopayItmInIdResponse;
import com.lgl.gms.webapi.inc.persistence.model.BoIncRetHopayModel;

/**
 * 손익 실적 본사지급
 * @author jokim
 *@date 2022.03.15
 */
public interface IncRetHopayDao {

	/**
	 * 손익 실적 본사지급 리스트 조회
	 * @param BoIncRetRequest
	 * @return
	*/
	public List<BoIncRetHopayResponse> selectIncRetHopayList(BoIncRetRequest param);
	
	/**
	 * 본사지급 저장
	 * @param retHopays
	 * @return
	 */
	public int insertIncRetHopay(BoIncRetHopayModel[] retHopays);
	
	/**
	 * 본사지급분 항목ID 조회
	 * @return
	 */
	public IncHopayItmInIdResponse selectIncHopayItmId();
	
	/**
	 * 본사지급분 수정
	 * @param retHopay
	 * @return
	 */
	public int updateIncRetHopay(BoIncRetHopayModel paramModel);
	
	/**
	 * 본사지급분 삭제
	 * @param seqs
	 * @return
	 */
	public int deleteIncRetHopay(BoIncRetHopayRequest param);
	
	/**
	 * 본사지급분 마감
	 * @param retHopay
	 * @return
	 */
	public int updateIncRetHopayCls(BoIncRetHopayModel paramModel);
	
}
