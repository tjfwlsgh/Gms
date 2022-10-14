package com.lgl.gms.webapi.sample.persistence.model;

//import javax.persistence.Entity;
//import javax.persistence.Id;

//import org.hibernate.annotations.DynamicInsert;

import lombok.Data;

//@DynamicInsert
//@Entity(name = "tb_excel")
@Data
public class SampleExcelModel {

//	@Id
	private Integer num;
	private String col1;
	private String col2;
	private String col3;

}
