package com.lgl.gms.webapi.inf.dto.response;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.apache.poi.ss.usermodel.Row;

import com.lgl.gms.webapi.common.constant.Constants;
import com.lgl.gms.webapi.common.util.ExcelUtil;
import com.lgl.gms.webapi.fin.dto.response.ArIncreAnalysisExcelResponse;
import com.lgl.gms.webapi.inc.dto.response.IncSalePlanExcelResponse;

import lombok.Data;

/**
 * 인원현황관리 엑셀 업로드
 * @author hj.Chung
 * @date 2022.05.26
 * */
@Data
public class InfEmployeeMgmtExcelResponse {
	
	private String boNmH;	// 법인 명
	
	private String subBoNm;	// 지사 명
	
	private String empTypNm;	// 직업 유형 이름
	
	private String empNm;		// 직원 명
	private String dpt;			// 부서
	private String pst;			// 직위
	private String cty;			// 도시
	private String sex;			// 성별
	private String period;		// 근속기간
	private String doe;			// 입사일
	private String rgd;			// 퇴사일
	private String job;			// 업무
	private String hoPst;		// 본사 직위
	private String delYn;		// 삭제 여부
	

	
	private List<String> errorColumns;	//에러컬럼들
	private String errorMsg;			// 유효성체크 에러 메세지
	
	// 헤더 정보
	private Integer boId; 		// 법인 ID 	
	private Integer subBoId;	// 지사 ID
	private Integer empTypId;	// 직업 유형 ID

	
	/**
     * 엑셀 업로드 처리 객체
     * @param row
     * @return
     */
    public static InfEmployeeMgmtExcelResponse from(Row row) {
    	return ExcelUtil.setObjectMapping(new InfEmployeeMgmtExcelResponse(), row);
    }

}
