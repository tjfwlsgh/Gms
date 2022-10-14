package com.lgl.gms.webapi.inc.persistence.model;

import com.lgl.gms.webapi.common.model.BaseModel;

//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;

//import org.hibernate.annotations.DynamicInsert;
//import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

/**
 * 법인 거래처
 * @author jokim
 * 2022.02.21
 */
@Data
public class BoCustModel extends BaseModel {
	
	private Integer boId;			// 법인 ID
	private String boCustCd;
	private String custNm;
	private String custSnm;
	private String custNmEng;
	private String custSnmEng;
	private String delYn;	

}