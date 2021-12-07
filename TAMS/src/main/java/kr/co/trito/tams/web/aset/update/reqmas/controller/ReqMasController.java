package kr.co.trito.tams.web.aset.update.reqmas.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kr.co.trito.tams.comm.util.file.excel.ExcelConstant;
import kr.co.trito.tams.comm.util.res.Response;
import kr.co.trito.tams.comm.util.res.ResponseService;
import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.aset.update.reqmas.dto.ReqMasDto;
import kr.co.trito.tams.web.aset.update.reqmas.dto.ReqMasExcelDto;
import kr.co.trito.tams.web.aset.update.reqmas.mapper.ReqMasMapper;
import kr.co.trito.tams.web.aset.update.reqmas.service.ReqMasService;
import kr.co.trito.tams.web.standard.code.dto.CodeDto;
import kr.co.trito.tams.web.standard.codegrp.dto.CodegrpDto;
import kr.co.trito.tams.web.system.user.dto.UserMngDto;
import kr.co.trito.tams.web.system.user.dto.UserMngExcelDto;
import kr.co.trito.tams.web.system.user.service.UserMngService;
import kr.co.trito.tams.web.user.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@RequestMapping("/aset/update/reqmas")
@Slf4j
public class ReqMasController {
	@Autowired
	ReqMasService reqmasService; 
	
	@Autowired
	ResponseService responseService;
	
	@Autowired
	BCryptPasswordEncoder encoder;
	
	/** 자산정보수정 화면 */
	@PostMapping("/reqInqrView")
	public ModelAndView reqInqrView(@RequestParam(value="menuId"  , required=true) String menuId
			                      , @RequestParam(value="menuNm"  , required=true) String menuNm
			                      , @RequestParam(value="menuDesc", required=true) String menuDesc) {
		ModelAndView view = new ModelAndView();
		view.addObject("menuId"  , menuId);
		view.addObject("menuNm"  , menuNm);
		view.addObject("menuDesc", menuDesc);
		view.setViewName("/content/aset/update/request/reqInqr");
		return view;
	}
	
	/** 자산정보수정 화면 : 공통코드(의뢰구분) 조회 */
	@PostMapping("/selectReqTypeList")
	@ResponseBody
	public ResponseEntity<? extends Response> reqTypeList(String str) { 
		
		List<CodeDto> list = reqmasService.selectReqTypeList("");
		return responseService.success(list);
	}
	
	/** 자산정보수정 화면 : 공통코드(의뢰구분) 조회 */
	@PostMapping("/selectReqStusList")
	@ResponseBody
	public ResponseEntity<? extends Response> selectReqStusList(String str) { 
		
		List<CodeDto> list = reqmasService.selectReqStusList("");
		return responseService.success(list);
	}
	
	/** 자산정보수정 화면 : 조회 */
	@GetMapping(value="/reqInqrList")
	@ResponseBody
	@ApiOperation(value = "Web API Menu Mgr test", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> reqInqrList(
		@ApiParam(value = "조회 페이지 번호", required = true) @RequestParam(value = "currentPage",required = true) String currentPage,
		@ApiParam(value = "페이지별 조회 출력수", required = true) @RequestParam(value = "numOfRows", required = true) String numOfRows,
		@ApiParam(value = "의뢰번호", required = false) @RequestParam(value = "reqNo", required = false) String reqNo,
		@ApiParam(value = "시작일", required = false) @RequestParam(value = "fromDate", required = false) String fromDate,
		@ApiParam(value = "종료일", required = false) @RequestParam(value = "toDate", required = false) String toDate,
		@ApiParam(value = "의뢰명", required = false) @RequestParam(value = "reqNm", required = false) String reqNm,
		@ApiParam(value = "사업부 코드", required = false) @RequestParam(value = "deptCd1", required = false) String deptCd1,
		@ApiParam(value = "부서 코드", required = false) @RequestParam(value = "deptCd2", required = false) String deptCd2,
		@ApiParam(value = "의뢰자", required = false) @RequestParam(value = "reqtr", required = false) String reqtr,
		@ApiParam(value = "자산번호", required = false) @RequestParam(value = "asetNo", required = false) String asetNo,
		@ApiParam(value = "의뢰상태", required = false) @RequestParam(value = "reqStus", required = false) String reqStus,
		@ApiParam(value = "정렬 필드", required = false) @RequestParam(value = "sortField", required = false) String sortField,
		@ApiParam(value = "정렬 타입", required = false) @RequestParam(value = "sortOrder", required = false) String sortOrder,
		@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail) {
		
		UserInfo userInfo = (UserInfo)userDetail;
		String userId = userInfo.getDto().getUserId();

		if (StringUtils.isEmpty(currentPage) || StringUtils.isEmpty(numOfRows))
		throw new IllegalArgumentException("Request parameter error.");
		
		Map<String, Object> params = new HashMap<>();
		
		if (!StringUtils.isEmpty(userId))
		params.put("userId", userId);
		
		if (!StringUtils.isEmpty(reqNo))
		params.put("reqNo", reqNo);
		
		if (!StringUtils.isEmpty(fromDate))
		params.put("fromDate", fromDate);
		
		if (!StringUtils.isEmpty(toDate))
		params.put("toDate", toDate);
		
		if (!StringUtils.isEmpty(reqNm))
		params.put("reqNm", reqNm);
		
		if (!StringUtils.isEmpty(deptCd1))
		params.put("deptCd1", deptCd1);
		
		if (!StringUtils.isEmpty(deptCd2))
		params.put("deptCd2", deptCd2);
		
		if (!StringUtils.isEmpty(reqtr))
		params.put("reqtr", reqtr);
		
		if (!StringUtils.isEmpty(asetNo))
		params.put("asetNo", asetNo);
		
		if (!StringUtils.isEmpty(reqStus))
		params.put("reqStus", reqStus);
		
		if (!StringUtils.isEmpty(sortField))
		params.put("sortField", sortField);
		
		if (!StringUtils.isEmpty(sortOrder))
		params.put("sortOrder", sortOrder);
		
		SearchCondition condition = new SearchCondition(currentPage, numOfRows, params);
		int total = reqmasService.selectCountReqInqr(condition);
		condition.pageSetup(total);
		
		System.out.println(total);
		
		List<ReqMasDto> list = reqmasService.selectReqInqrList(condition);
		
		return responseService.success(condition, list);
	} 	
	
	/** 자산정보수정 화면 : 등록 */  
	@GetMapping(value="/reqmasInsert")
	@ResponseBody
	@ApiOperation(value = "Web API Menu Mgr Insert", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public String reqmasInsert(
			@ApiParam(value = "공통코드(의뢰구분)", required = true) @RequestParam(value = "reqType", required = true) String reqType,
			@ApiParam(value = "의뢰명", required = true) @RequestParam(value = "reqNm", required = true) String reqNm,
			@ApiParam(value = "의뢰자", required = true) @RequestParam(value = "reqtr", required = true) String reqtr,
			@ApiParam(value = "결재문서 ID", required = false) @RequestParam(value = "appvDocId", required = false) String appvDocId,
			@ApiParam(value = "의뢰사유", required = false) @RequestParam(value = "reqRsn", required = false) String reqRsn,
			@ApiParam(value = "공통코드(의뢰상태)", required = true) @RequestParam(value = "reqStus", required = true) String reqStus,
			@ApiParam(value = "이동목적", required = false) @RequestParam(value = "movePupos", required = false) String movePupos,
			@ApiParam(value = "이동일자", required = false) @RequestParam(value = "moveDt", required = false) String moveDt,
			@ApiParam(value = "반출예정여부", required = false) @RequestParam(value = "tkoutScheYn", required = false) String tkoutScheYn,
			@ApiParam(value = "반출예정일자", required = false) @RequestParam(value = "tkoutScheDt", required = false) String tkoutScheDt,
			@ApiParam(value = "반출여부", required = false) @RequestParam(value = "tkoutYn", required = false) String tkoutYn,
			@ApiParam(value = "반출일자", required = false) @RequestParam(value = "tkoutDt", required = false) String tkoutDt,
			@ApiParam(value = "재반입여부", required = false) @RequestParam(value = "rtkinYn", required = false) String rtkinYn,
			@ApiParam(value = "재반입일자", required = false) @RequestParam(value = "rtKinDt", required = false) String rtKinDt,
			@ApiParam(value = "진행중단여부", required = false) @RequestParam(value = "prgsDcontYn", required = true) String prgsDcontYn,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail
			) { 
		
		String code = "202";
		
		UserInfo userInfo = (UserInfo)userDetail;
		String userId = userInfo.getDto().getUserId();
		
		ReqMasDto dto = new ReqMasDto();
		
		if (StringUtils.isNotEmpty(reqType)) 
			dto.setReqType(reqType);
		
	    if (StringUtils.isNotEmpty(reqNm)) 
	    	dto.setReqNm(reqNm);
	    
	    if (StringUtils.isNotEmpty(reqtr)) 
	    	dto.setReqtr(reqtr);
	    
	    if (StringUtils.isNotEmpty(appvDocId)) 
	    	dto.setAppvDocId(appvDocId);
	    
	    dto.setReqRsn(reqRsn);
	    dto.setReqStus(reqStus);
	    dto.setMovePupos(movePupos);
	    dto.setMoveDt(moveDt);
	    dto.setTkoutScheYn(tkoutScheYn);
	    dto.setTkoutScheDt(tkoutScheDt);
	    dto.setTkoutYn(tkoutYn);
	    dto.setTkoutDt(tkoutDt);
	    dto.setRtkinYn(rtkinYn);
	    dto.setRtKinDt(rtKinDt);
	    dto.setPrgsDcontYn(prgsDcontYn);
		dto.setRegr(userId);
		 
		int cnt = reqmasService.reqInqrInsert(dto); 
		
		if( cnt > 0)
			code = "200";
		
		return code;
	}
	
	/** 자산정보수정 화면 : 수정 */ 
	@GetMapping(value="/reqmasUpdate")
	@ResponseBody
	@ApiOperation(value = "Web API Menu Mgr Insert", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public String reqmasUpdate(
			@ApiParam(value = "의뢰번호", required = true) @RequestParam(value = "reqNo", required = true) String reqNo,
			@ApiParam(value = "공통코드(의뢰구분)", required = true) @RequestParam(value = "reqType", required = true) String reqType,
			@ApiParam(value = "의뢰명", required = true) @RequestParam(value = "reqNm", required = true) String reqNm,
			@ApiParam(value = "의뢰자", required = true) @RequestParam(value = "reqtr", required = true) String reqtr,
			@ApiParam(value = "결재문서 ID", required = true) @RequestParam(value = "appvDocId", required = true) String appvDocId,
			@ApiParam(value = "의뢰사유", required = true) @RequestParam(value = "reqRsn", required = true) String reqRsn,
			@ApiParam(value = "공통코드(의뢰상태)", required = true) @RequestParam(value = "reqStus", required = true) String reqStus,
			@ApiParam(value = "이동목적", required = true) @RequestParam(value = "movePupos", required = true) String movePupos,
			@ApiParam(value = "이동일자", required = true) @RequestParam(value = "moveDt", required = true) String moveDt,
			@ApiParam(value = "반출예정여부", required = true) @RequestParam(value = "tkoutScheYn", required = true) String tkoutScheYn,
			@ApiParam(value = "반출예정일자", required = true) @RequestParam(value = "tkoutScheDt", required = true) String tkoutScheDt,
			@ApiParam(value = "반출여부", required = true) @RequestParam(value = "tkoutYn", required = true) String tkoutYn,
			@ApiParam(value = "반출일자", required = true) @RequestParam(value = "tkoutDt", required = true) String tkoutDt,
			@ApiParam(value = "재반입여부", required = true) @RequestParam(value = "rtkinYn", required = true) String rtkinYn,
			@ApiParam(value = "재반입일자", required = true) @RequestParam(value = "rtKinDt", required = true) String rtKinDt,
			@ApiParam(value = "진행중단여부", required = true) @RequestParam(value = "prgsDcontYn", required = true) String prgsDcontYn,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail
			) { 
		
		String code = "202";
		
		UserInfo userInfo = (UserInfo)userDetail;
		String userId = userInfo.getDto().getUserId();
		
		ReqMasDto dto = new ReqMasDto();
		
		if (StringUtils.isNotEmpty(reqNo)) 
			dto.setReqNo(reqNo);
		
		if (StringUtils.isNotEmpty(reqType)) 
			dto.setReqType(reqType);
		
	    if (StringUtils.isNotEmpty(reqNm)) 
	    	dto.setReqNm(reqNm);
	    
	    if (StringUtils.isNotEmpty(reqtr)) 
	    	dto.setReqtr(reqtr);
	    
	    dto.setAppvDocId(appvDocId);
	    dto.setReqRsn(reqRsn);
	    dto.setReqStus(reqStus);
	    dto.setMovePupos(movePupos);
	    dto.setMoveDt(moveDt);
	    dto.setTkoutScheYn(tkoutScheYn);
	    dto.setTkoutScheDt(tkoutScheDt);
	    dto.setTkoutYn(tkoutYn);
	    dto.setTkoutDt(tkoutDt);
	    dto.setRtkinYn(rtkinYn);
	    dto.setRtKinDt(rtKinDt);
	    dto.setPrgsDcontYn(prgsDcontYn);
		dto.setUpdr(userId);
		 
		int cnt = reqmasService.reqInqrUpdate(dto); 
		
		if( cnt > 0)
			code = "200";
		
		return code;
	}
	
	/** 자산정보수정 화면 : 삭제 */
	@GetMapping(value="/reqmasDelete")
	@ResponseBody
	@ApiOperation(value = "Web API Menu Mgr Delete", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public String reqmasDelete( 
			@ApiParam(value = "의뢰 번호", required = true) @RequestParam(value = "items", required = true) List<String> items
			) { 
		
		String code = "202";
		
		int cnt = 0;
		
		for(String reqNo : items) {
			ReqMasDto dto = new ReqMasDto();
			
			dto.setReqNo(reqNo);
			
			if( reqmasService.reqInqrDelete(dto) > 0 )
				cnt++;
		}
		
		if(cnt > 0) 
			code = "200";
		
		return code;
	}
	
	// 사용자 관리 화면 : 조회 - 엑셀다운 
	@PostMapping(value="/reqInqrExcel")
	@ApiOperation(value = "Web API Menu Mgr test", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ModelAndView reqMasListExcel(
			@ApiParam(value = "의뢰번호", required = false) @RequestParam(value = "req_no", required = false) String req_no,
			@ApiParam(value = "시작일", required = false) @RequestParam(value = "fromDate", required = false) String fromDate,
			@ApiParam(value = "종료일", required = false) @RequestParam(value = "toDate", required = false) String toDate,
			@ApiParam(value = "의뢰명", required = false) @RequestParam(value = "req_nm", required = false) String req_nm,
			@ApiParam(value = "사업부 코드", required = false) @RequestParam(value = "dept_cd1", required = false) String dept_cd1,
			@ApiParam(value = "부서 코드", required = false) @RequestParam(value = "dept_cd2", required = false) String dept_cd2,
			@ApiParam(value = "의뢰자", required = false) @RequestParam(value = "Reqtr", required = false) String Reqtr,
			@ApiParam(value = "자산번호", required = false) @RequestParam(value = "aset_no", required = false) String aset_no,
			@ApiParam(value = "의뢰상태", required = false) @RequestParam(value = "req_stus", required = false) String req_stus,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail
			) { 
		
		Map<String, Object> params = new HashMap<>();
		
		UserInfo userInfo = (UserInfo)userDetail;
		String userId = userInfo.getDto().getUserId();
		
		if (!StringUtils.isEmpty(userId))
		params.put("userId", userId);
		
		if (!StringUtils.isEmpty(req_no))
		params.put("reqNo", req_no);
			
		if (!StringUtils.isEmpty(fromDate))
		params.put("fromDate", fromDate);
		
		if (!StringUtils.isEmpty(toDate))
		params.put("toDate", toDate);
		
		if (!StringUtils.isEmpty(req_nm))
		params.put("reqNm", req_nm);
		
		if (!StringUtils.isEmpty(dept_cd1))
		params.put("deptCd1", dept_cd1);
		
		if (!StringUtils.isEmpty(dept_cd2))
		params.put("deptCd2", dept_cd2);
		
		if (!StringUtils.isEmpty(Reqtr))
		params.put("reqtr", Reqtr);
		
		if (!StringUtils.isEmpty(aset_no))
		params.put("asetNo", aset_no);
		
		if (!StringUtils.isEmpty(req_stus))
		params.put("reqStus", req_stus);
		
		SearchCondition condition = new SearchCondition("0", "0", params);
		int total = reqmasService.selectCountReqInqr(condition);
		System.out.println(total);
		condition.pageSetup(total);
		
		List<ReqMasExcelDto> list = reqmasService.selectReqInqrExcelList(condition);
		
		System.out.println(list);
		
		return new ModelAndView("excelXlsxView",makeExcelData(list)) ;
	} 	
	
	
	// 엑셀 다운로드용 데이터 생성 
	private Map<String, Object> makeExcelData(List<ReqMasExcelDto> list) {
		Map<String, Object> map = new HashMap<>();
		map.put(ExcelConstant.FILE_NAME, "자산의뢰 리스트");
		// map.put(ExcelConstant.HEAD, Arrays.asList("의뢰번호", "의뢰구분", "의뢰구분명", "의뢰명", "의뢰일자","의뢰자", "의뢰자 부서", "결재문서 ID", "의뢰사유", "의뢰상태", "의뢰상태명", "이동목적", "이동일자", "반출예정여부", "반출예정일자", "반출여부", "반출일자", "재반입여부", "재반입일자", "진행중단여부", "수정자", "수정일자", "등록자", "등록일자", "부서코드", "자산 수"));
		map.put(ExcelConstant.HEAD, Arrays.asList("의뢰번호", "의뢰구분", "의뢰명", "의뢰자 부서", "의뢰자", "의뢰일자", "의뢰상태", "자산 수"));
		
		ObjectMapper objectMapper = new ObjectMapper();
		List<List<String>> rows = new ArrayList<List<String>>();
		
		for( ReqMasExcelDto req: list) {
			Map<String, String> m = objectMapper.convertValue(req, Map.class);
			List<String> l = new ArrayList<>(m.values());
			
			rows.add(l);
		}
		//log.error("@@@@ "+ rows.toString());
		map.put(ExcelConstant.BODY, rows);
		return map;
	}
}

