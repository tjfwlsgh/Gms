package com.lgl.gms.webapi.sample.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lgl.gms.config.PropConfig;
import com.lgl.gms.webapi.common.dto.code.ResponseCode;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.common.util.FileUtil;
import com.lgl.gms.webapi.sample.dto.request.FileRequest;
import com.lgl.gms.webapi.sample.dto.response.FileResponse;
import com.lgl.gms.webapi.sample.persistence.dao.FileDao;
import com.lgl.gms.webapi.sample.persistence.model.FileModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

	@Autowired
	private FileDao fileDao;

	@Autowired
	public PropConfig prop;

	@Override
	public BaseResponse upload(MultipartFile file, FileRequest body) {

		try {

			FileModel param = saveFile(file);
			fileDao.insertFileInfo(param);

			FileResponse fRes = new FileResponse(param.getId());
			fRes.setExt(param.getExt());
			fRes.setFilename(param.getFilename());
			fRes.setOrgname(param.getOrgname());
			fRes.setUrl("/v1/files/" + param.getId());

			return new BaseResponse(ResponseCode.C200, fRes);
		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}
	}

	@Override
	public ResponseEntity<?> downloadFile(String id, HttpServletResponse response) {

		try {

			FileModel fileModel = fileDao.selectFileInfo(id);
			if (fileModel == null) {
				return ResponseEntity.notFound().build();
			}

			String filePath = fileModel.getFilepath();
			String fileName = fileModel.getOrgname();
			Path file = Paths.get(filePath);
			if (Files.exists(file)) {
				long contentLength = file.toFile().length();
				Resource resource = new InputStreamResource(Files.newInputStream(file));
				String contentType = Files.probeContentType(file);
				HttpHeaders headers = new HttpHeaders();

				headers.add(HttpHeaders.CONTENT_TYPE, contentType);
				headers.add(HttpHeaders.CONTENT_LENGTH, Long.toString(contentLength));
				headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

				return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
			}

			return ResponseEntity.notFound().build();

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return ResponseEntity.notFound().build();
		}
	}

	@Override
	public FileModel saveFile(MultipartFile file) {

		try {

			FileModel param = new FileModel();

			String[] exts = file.getOriginalFilename().split("\\.");
			String ext = exts[exts.length - 1];

			String fileName = UUID.randomUUID().toString().replace("-", "") + "." + ext;
			String loc = prop.getDocumentPath() + "/" + fileName;
			Path path = Paths.get(loc);

			param.setExt(ext);
			param.setFilename(fileName);
			param.setOrgname(file.getOriginalFilename());
			param.setFilepath(loc);

			FileUtil.createDirectory(path.getParent().toString());
			FileUtil.saveToFile(file.getInputStream(), path.toString());

			file.getInputStream().close();

			return param;
		} catch (Exception e) {
			log.warn(e.toString(), e);
			return null;
		}
	}

	/**
	 * Excel workbook
	 */
	@Override
	public Workbook getExcelWorkbook(MultipartFile file) {

		try {

			String[] exts = file.getOriginalFilename().split("\\.");
			String ext = exts[exts.length - 1];

			Workbook workbook = null;

			if (ext.equals("xlsx")) {
				workbook = new XSSFWorkbook(file.getInputStream());
			} else if (ext.equals("xls")) {
				workbook = new HSSFWorkbook(file.getInputStream());
			}

			return workbook;
		} catch (Exception e) {
			log.warn(e.toString(), e);
			return null;
		}
	}

}
