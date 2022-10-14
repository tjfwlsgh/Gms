package com.lgl.gms.webapi.sample.persistence.dao;

import java.util.HashMap;
import java.util.List;

import com.lgl.gms.webapi.sample.persistence.model.FileModel;

public interface FileDao {

	public FileModel selectFileInfo(String id);

	public int selectFileListCount(HashMap<?, ?> param); // 파일리스트 조회

	public List<?> selectFileList(HashMap<?, ?> param); // 파일리스트 조회

	public int insertFileInfo(FileModel model);

	public int updateFileInfo(FileModel model);

	public int deleteFile(String id);

}
