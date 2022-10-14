package com.lgl.gms.webapi.fin.persistence.model;

import com.lgl.gms.webapi.common.model.BaseModel;

import lombok.Data;

/**
 * 양식 정보 법인 tb_frm_info_bo
 * @author jokim
 * 2022.04.16
 */
@Data
public class FrmInfoBoModel extends BaseModel {
	
	private Integer frmId;
	private Integer colSeq;
	private Integer boId;

}