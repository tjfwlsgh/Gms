package com.lgl.gms.webapi.sample.persistence.model;

import java.util.Date;

//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;

import lombok.Data;

//@Entity(name = "tb_file")
@Data
public class FileModel {

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String filepath;
	private String filename;
	private String orgname;
	private String ext;
	private Date createdDt;
	private Date updatedDt;
}