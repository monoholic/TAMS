package kr.co.trito.tams.web.common.contoller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kr.co.trito.tams.comm.util.file.FileDto;
import kr.co.trito.tams.comm.util.file.excel.ExcelReader;
import kr.co.trito.tams.comm.util.file.excel.InvDto;
import kr.co.trito.tams.comm.util.res.Response;
import kr.co.trito.tams.comm.util.res.ResponseService;
import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.common.dto.AsetMasDto;
import kr.co.trito.tams.web.common.dto.CodeTreeDto;
import kr.co.trito.tams.web.common.dto.ComCodeDto;
import kr.co.trito.tams.web.common.dto.ComCodeParamDto;
import kr.co.trito.tams.web.common.dto.DeptDto;
import kr.co.trito.tams.web.common.dto.MenuRoleCheckDto;
import kr.co.trito.tams.web.common.service.CommonService;
import kr.co.trito.tams.web.standard.code.dto.CodeDto;
import kr.co.trito.tams.web.standard.invest.dto.InvestDto;
import kr.co.trito.tams.web.system.user.dto.UserMngDto;
import kr.co.trito.tams.web.user.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@Slf4j
@RequestMapping("/common")
public class CommonController {
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	ResponseService responseService;		
	
	@Autowired
	private ExcelReader excelReader;

	
	/** 부서팝업 화면 */
	@GetMapping("/popup/deptPopup")
	public ModelAndView deptPopupView1(HttpServletRequest request) {		
		ModelAndView view = new ModelAndView();
		view.setViewName("/content/common/popup/deptPopup");		    
		return view;
	}
	
	/** 부서팝업 조회 */
	@GetMapping("/popup/deptPopupList")
	@ResponseBody
	@ApiOperation(value = "Web API Common test", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response>  deptPopupList(
			@ApiParam(value = "검색 조건(부서명, 부서코드)", required = false) @RequestParam(value="searchText",required = false) String searchText) {
			
		Map<String, Object> params = new HashMap<>();
		if (!StringUtils.isEmpty(searchText))
		params.put("searchText", searchText);
		
		SearchCondition condition = new SearchCondition("0","0",params);
		
		List<DeptDto> list = commonService.selectDeptPopupList(condition);
		condition.pageSetup(list.size());
		
		return responseService.success(condition, list);
	}
	
	/** 부서팝업 화면 */
	@GetMapping("/popup/deptTreePopup")
	public ModelAndView deptPopupView2(HttpServletRequest request) {		
		ModelAndView view = new ModelAndView();
		view.setViewName("/content/common/popup/deptTreeList");		    
		return view;
	}
	
	/** 공통코드(트리) 조회 */
	@GetMapping("/popup/deptTreeList")
	@ResponseBody
	@ApiOperation(value = "Web API Common test", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response>  deptTreePopupList(
        @ApiParam(value = "조회 페이지 번호", required = false) @RequestParam(value = "searchType",required = false) String searchType) {		
		List<CodeTreeDto> list = commonService.selectDeptTree(searchType);		
		return responseService.success(list);
	}
	
	/** 사용자 팝업 화면 */
	@GetMapping("/popup/userPopup")
	public ModelAndView userPopupView(HttpServletRequest request) {
		
		ModelAndView view = new ModelAndView();
		view.setViewName("/content/common/popup/userPopup");
		
		return view;
	}
	
	/** 부서팝업 조회 */
	@GetMapping("/popup/userPopupList")
	@ResponseBody
	@ApiOperation(value = "Web API Common test", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response>  userPopupList(
			@ApiParam(value = "검색 조건(사용자명, 사용자ID)", required = false) @RequestParam(value="searchText",required = false) String searchText) {
		
		Map<String, Object> params = new HashMap<>();
		if (!StringUtils.isEmpty(searchText))
			params.put("searchText", searchText);
		
		SearchCondition condition = new SearchCondition("0","0",params);
		
		List<UserMngDto> list = commonService.selectUserPopupList(condition);
		condition.pageSetup(list.size());
		
		return responseService.success(condition, list);
		
	}
	
	/** 사용자 팝업 화면 */
	@GetMapping("/popup/userFilterPopup")
	public ModelAndView userFilterPopupView(HttpServletRequest request) {
		
		ModelAndView view = new ModelAndView();
		view.setViewName("/content/common/popup/userFilterPopup");
		
		return view;
	}
	
	/** 메뉴권한관리 화면 : 공통 셀렉트박스 */
	@GetMapping(value="/comm/selectBox")
	@ResponseBody
	@ApiOperation(value = "Web API Menu Mgr test", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> commSelectBox(
			@ApiParam(value = "공통코드 조회 조건", required = false) @ModelAttribute ComCodeParamDto params) { 

		List<ComCodeDto> list = commonService.commSelectBox(params);
		
		return responseService.success(list);
	}	
	
	
	/** 부서팝업 조회 */
	@GetMapping("/popup/userFilterPopupList")
	@ResponseBody
	@ApiOperation(value = "Web API Common test", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response>  userFilterPopupList(
			@ApiParam(value = "검색 조건(사용자ID, 사용자명, 부서코드, 부서명, 이메일, 전화번호)", required = false) @RequestParam(value="searchType",required = false) String searchType,
			@ApiParam(value = "검색 키워드", required = false) @RequestParam(value="searchText",required = false) String searchText,
			@ApiParam(value = "검색 조건(사용자ID, 사용자명, 부서코드, 부서명, 이메일, 전화번호)", required = false) @RequestParam(value="searchType2",required = false) String searchType2,
			@ApiParam(value = "검색 키워드", required = false) @RequestParam(value="searchText2",required = false) String searchText2
			) {
		
		Map<String, Object> params = new HashMap<>();
		if (!StringUtils.isEmpty(searchType))
			params.put("searchType", searchType);
		
		if (!StringUtils.isEmpty(searchText))
			params.put("searchText", searchText);
		
		if (!StringUtils.isEmpty(searchType2))
			params.put("searchType2", searchType2);
			
		if (!StringUtils.isEmpty(searchText2))
			params.put("searchText2", searchText2);
		
		SearchCondition condition = new SearchCondition("0", "0", params);
		
		List<UserMngDto> list = commonService.selectUserFilterPopupList(condition);
		condition.pageSetup(list.size());
		
		return responseService.success(condition, list);
		
	}		
	
	/** 첨부파일 목록 조회 */
	@GetMapping("/file/selectFileList")
	@ResponseBody
	@ApiOperation(value = "Web API Common test", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response>  selectFileList(
			@ApiParam(value = "검색 조건(참조키)", required = false) @RequestParam(value="searchText",required = false) String searchText) {
		
		Map<String, Object> params = new HashMap<>();
		if (!StringUtils.isEmpty(searchText))
			params.put("searchText", searchText);
		
		SearchCondition condition = new SearchCondition("0","0",params);
		
		List<FileDto> list = commonService.selectFileList(condition);
		condition.pageSetup(list.size());
		
		return responseService.success(condition, list);
		
	}
	
	
	
	/** 파일정보 저장 */
	@PostMapping("/file/saveFiles")
	@ResponseBody
	@ApiOperation(value = "Web API Common test", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public String saveFiles(
			@ApiParam(value = "검색 조건(파일정보)", required = false) @RequestBody(required = false) List<FileDto> files) {
		
		String code = "202";
		
		int idx = 0;
		int cnt = 0;
		for(FileDto dto : files) {
			
			dto.setSortOdr(idx);
			dto.setDwldCnt(0);
			dto.setUseYn("Y");
			
			dto.setRegr("system");
			dto.setUpdr("system");
			
			if( commonService.saveFiles(dto) > 0 ) cnt++ ;
			
			idx++;
		}
		
		if( cnt > 0) code = "200";
		
		return code;
		
	}
	
	/** 파일정보 저장 */
	@PostMapping("/file/updateDwldCnt")
	@ResponseBody
	@ApiOperation(value = "Web API Common test", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public String updateDwldCnt(
			@ApiParam(value = "검색 조건(파일정보)", required = false) @RequestBody(required = false) FileDto file) {
		
		String code = "202";
		
		file.setUpdr("system");
			
		if( commonService.updateDwldCnt(file) > 0 ) code = "200";
		
		return code;
		
	} 	
	
	/** 화면 권한 여부 체크 */
	@GetMapping("/menu/menuRoleCheck")
	@ResponseBody
	@ApiOperation(value = "Web API Common test", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> menuRoleCheck(HttpServletRequest request,
			@AuthenticationPrincipal UserDetails userDetail,
			@ApiParam(value = "메뉴ID", required = false) @RequestParam(required = false) String menuId
			){
		
		List<GrantedAuthority> auths = new ArrayList<>(userDetail.getAuthorities());
	      log.error("@@@@@@@ : "+ auths.get(0).getAuthority());
	      
		MenuRoleCheckDto dto = new MenuRoleCheckDto();
		
		dto.setRoleId(auths.get(0).getAuthority());
		dto.setMenuId(menuId);
		
		HttpSession session = request.getSession();
		
		MenuRoleCheckDto role = commonService.selectMenuRoleCheck(dto);
		log.error("@@@@ "+role);
		session.setAttribute("MenuRoleCheck", role);		
//		session.setAttribute("inqrYn", role.getInqrYn());		
//		session.setAttribute("updYn", role.getUpdYn());
//		session.setAttribute("regYn", role.getRegYn());
//		session.setAttribute("delYn", role.getDelYn());
		
		return responseService.success(role);
		
	}
	
	
	/** 투자정보 일괄업로드 팝업 화면 */
	@GetMapping("/popup/invUploadPopup")
	public ModelAndView invUploadPopupView(HttpServletRequest request) {
		
		ModelAndView view = new ModelAndView();
		view.setViewName("/content/common/popup/investUploadPopup");
		    
		return view;
	}
	
	
	@PostMapping("/invest/readExcel")
	@ResponseBody
	public List<InvestDto> readExcel(@RequestParam("file") MultipartFile multipartFile) throws IOException, InvalidFormatException {
		List<InvestDto> result = null;
		
		InvestDto dto = new InvestDto();
		
		result = excelReader.readFileToList(multipartFile, dto::row);
		
		//첫번째 열 헤더 제거...??!!
		result.remove(0);
		
		/** 유효성 검증 */
		for(InvestDto inv : result) {
			
			String chkResult = "";
			
			if( StringUtils.isEmpty(inv.getInvNo()) ) {
				chkResult += "투자번호가 누락되었습니다";
			}
			if( StringUtils.isEmpty(inv.getInvTtl()) ) {
				chkResult += "투자명이 누락되었습니다";
			}
			if( StringUtils.isEmpty(inv.getInvDt()) ) {
				chkResult += "투자일자가 누락되었습니다";
			}
			if( StringUtils.isEmpty(inv.getInvQty()) ) {
				chkResult += "투자수량이 누락되었습니다";
			} else {
				//숫자값만 입력 가능...
				if( !StringUtils.isNumeric(inv.getInvQty()) ) {
					chkResult += "숫자만 입력 가능합니다.";
				}
			}
			if( StringUtils.isEmpty(inv.getInvAmt()) ) {
				chkResult += "투자금액이 누락되었습니다";
			} else {
				//숫자값만 입력 가능...
				if( !StringUtils.isNumeric(inv.getInvAmt()) ) {
					chkResult += "숫자만 입력 가능합니다.";
				}
			}
			if( StringUtils.isEmpty(inv.getActgYear()) ) {
				chkResult += "회계연도가 누락되었습니다";
			}
			if( StringUtils.isEmpty(inv.getPoNo()) ) {
				chkResult += "PO번호가 누락되었습니다";
			}
			if( StringUtils.isEmpty(inv.getMfgdNm()) ) {
				chkResult += "품명이 누락되었습니다";
			}
			if( StringUtils.isEmpty(inv.getQty()) ) {
				chkResult += "수량이 누락되었습니다";
			} else {
				//숫자값만 입력 가능...
				if( !StringUtils.isNumeric(inv.getQty()) ) {
					chkResult += "숫자만 입력 가능합니다.";
				}
			}
			if( StringUtils.isEmpty(inv.getPoAmt()) ) {
				chkResult += "PO금액이 누락되었습니다";
			} else {
				//숫자값만 입력 가능...
				if( !StringUtils.isNumeric(inv.getPoAmt()) ) {
					chkResult += "숫자만 입력 가능합니다.";
				}
			}
			if( StringUtils.isEmpty(inv.getInvReqr()) ) {
				chkResult += "담당자가 누락되었습니다";
			} else {
				// 담장자 id 로 정보 찾기...
				
				  Map<String,String> map = new HashMap<>(); 
				  map.put("userId", inv.getInvReqr()); 
				  Map<String,String> deptInfo = commonService.selectUserDeptInfo(map);
				  
				  //inv.set(deptInfo.get("upDeptNm"));
				  inv.setDeptNm(deptInfo.get("deptNm"));
				  inv.setInvReqr(deptInfo.get("userNm"));
				 
			}
			if( StringUtils.isEmpty(inv.getVendNm()) ) {
				chkResult += "업체가 누락되었습니다";
			}
			if( StringUtils.isEmpty(inv.getReqDt()) ) {
				chkResult += "구매요청일자가 누락되었습니다";
			}
			if( StringUtils.isEmpty(inv.getDlvDt()) ) {
				chkResult += "납품일자가 누락되었습니다";
			}
			
			//검증 결과 
			inv.setChkResult(chkResult);
			if( !StringUtils.isEmpty(chkResult) ) inv.setChkFlag("Y");
		}
		
		return result;
	}
	
	
	@PostMapping("/invest/saveExcel")
	@ResponseBody
	public String saveExcel(@RequestBody List<InvDto> datas, @AuthenticationPrincipal UserDetails userDetail) throws IOException, InvalidFormatException {
		
		String code = "202";
		
		UserInfo userInfo = (UserInfo)userDetail;
	    String userId = userInfo.getDto().getUserId();         
		
	    int cnt = 0;
		for(InvDto inv : datas) {
			inv.setRegr(userId);
			inv.setUpdr(userId);
			log.error("@@@ "+inv);
			if( commonService.saveInvestInfo(inv) > 0 ) cnt++;
		}
		
		cnt = 0;
		cnt = commonService.savePoInfo(datas);
		
		if(cnt > 0) code = "200";
		
		return code;
	}
	
	
	
	/** 공통코드(트리) 팝업 화면 */
	@GetMapping("/popup/commCodePopup")
	public ModelAndView commCodePopupView(HttpServletRequest request) {
		
		ModelAndView view = new ModelAndView();
		view.setViewName("/content/common/popup/commCodePopup");
		    
		return view;
	}
	

	
	/** 챠트 조회 */
	@GetMapping("/chartData")
	@ResponseBody
	@ApiOperation(value = "Web API Chart test", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response>  getChartData() {
		
		Map<String, Object> rst = new HashMap<String, Object>();
		
		List<Map<String,String>> chart1 = commonService.selectChartData("chart1");
		rst.put("chart1", chart1);
		
		List<Map<String,String>> chart2 = commonService.selectChartData("chart2");
		rst.put("chart2", chart2);
		
		return responseService.success(rst);
		
	}
	
	/** 공통코드(트리) 조회 */
	@GetMapping("/popup/codeTreeList")
	@ResponseBody
	@ApiOperation(value = "Web API Common test", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response>  commCodeTreePopupList(
			@ApiParam(value = "검색 조건(코드그룹)", required = false) @RequestParam(value="codeGrpId",required = false) String codeGrpId) {
		
		Map<String, String> params = new HashMap<>();
		if (!StringUtils.isEmpty(codeGrpId))
			params.put("codeGrpId", codeGrpId);
		
		List<CodeTreeDto> list = commonService.selectCodeTree(params);
		
		return responseService.success(list);
		
	}
	
	/** 달력 팝업 화면 */
	@GetMapping("/popup/calendarPopup")
	public ModelAndView calendarPopupView(HttpServletRequest request) {
		
		ModelAndView view = new ModelAndView();
		view.setViewName("/content/common/popup/calendarPopup");
		
		return view;
	}
	
	/** 자산 선택(팝업-공통) 화면 */
	@GetMapping("/popup/asetMasPopup")
	public ModelAndView asetMasPopupView(HttpServletRequest request) {
		
		ModelAndView view = new ModelAndView();
		view.setViewName("/content/common/popup/asetMasPopup");
		    
		return view;
	}
	
	/** 자산 선택(팝업-공통) : 조회 */
	@GetMapping(value="/popup/asetMasPopupList")
	@ResponseBody
	@ApiOperation(value = "Web API Menu Mgr test", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> asetMasPopupList(
				@ApiParam(value = "조회 페이지 번호", required = true) @RequestParam(value = "currentPage",required = true) String currentPage,
				@ApiParam(value = "페이지별 조회 출력수", required = true) @RequestParam(value = "numOfRows", required = true) String numOfRows,
				@ApiParam(value = "검색 조건(메뉴ID, 메뉴명)", required = false) @RequestParam(value = "searchText", required = false) String searchText,
				@ApiParam(value = "시작일", required = false) @RequestParam(value = "fromDate", required = false) String fromDate,
				@ApiParam(value = "종료일", required = false) @RequestParam(value = "toDate", required = false) String toDate,
				@ApiParam(value = "자산유형1", required = false) @RequestParam(value = "asetType1", required = false) String asetType1,
				@ApiParam(value = "자산유형2", required = false) @RequestParam(value = "asetType2", required = false) String asetType2,
				@ApiParam(value = "자산유형3", required = false) @RequestParam(value = "asetType3", required = false) String asetType3,
				@ApiParam(value = "사업부 코드", required = false) @RequestParam(value = "deptCd1", required = false) String deptCd1,
				@ApiParam(value = "부서 코드", required = false) @RequestParam(value = "deptCd2", required = false) String deptCd2,
				@ApiParam(value = "담당자", required = false) @RequestParam(value = "chrgr", required = false) String chrgr,
				@ApiParam(value = "정렬 필드", required = false) @RequestParam(value = "sortField", required = false) String sortField,
				@ApiParam(value = "정렬 타입", required = false) @RequestParam(value = "sortOrder", required = false) String sortOrder
                              ) { 

		if (StringUtils.isEmpty(currentPage) || StringUtils.isEmpty(numOfRows))
		throw new IllegalArgumentException("Request parameter error.");
		
		Map<String, Object> params = new HashMap<>();
		
		if (!StringUtils.isEmpty(searchText))
		params.put("searchText", searchText);
		
		
		if (!StringUtils.isEmpty(fromDate))
		params.put("fromDate", fromDate);
		
		if (!StringUtils.isEmpty(toDate))
		params.put("toDate", toDate);
		
		
		if (!StringUtils.isEmpty(asetType1))
		params.put("asetType1", asetType1);
		
		if (!StringUtils.isEmpty(asetType2))
		params.put("asetType2", asetType2);
		
		if (!StringUtils.isEmpty(asetType3))
		params.put("asetType3", asetType3);

		
		if (!StringUtils.isEmpty(deptCd1))
		params.put("deptCd1", deptCd1);
		
		if (!StringUtils.isEmpty(deptCd2))
		params.put("deptCd2", deptCd2);
		
		
		if (!StringUtils.isEmpty(chrgr))
		params.put("chrgr", chrgr);
		
		
		if (!StringUtils.isEmpty(sortField))
		params.put("sortField", sortField);
		
		if (!StringUtils.isEmpty(sortOrder))
		params.put("sortOrder", sortOrder);
		
		SearchCondition condition = new SearchCondition(currentPage, numOfRows, params);
		int total = commonService.selectCountAsetMas(condition);
		condition.pageSetup(total);
		
		List<AsetMasDto> list = commonService.selectAsetMasList(condition);
		return responseService.success(condition, list);
	}
	
	/** 자산 선택(팝업-공통) : 1레벨 조회 */
	@PostMapping("/popup/asetType")
	@ResponseBody
	public ResponseEntity<? extends Response> asetTypeList(String str) { 
		
		List<CodeDto> list = commonService.selectAsetType("");
		return responseService.success(list);
	}
	
	/** 엑셀다운로드 */
	
	/*
	 * @PostMapping("/excel/InvestDl") public void excelDownload(HttpServletResponse
	 * response,
	 * 
	 * @ApiParam(value = "투자정보리스트", required = false) @RequestParam(required =
	 * false) List<InvestInqrDto> items ) throws IOException {
	 * 
	 * Workbook wb = new XSSFWorkbook(); Sheet sheet = wb.createSheet("첫번째시트"); Row
	 * row = null; Cell cell = null; int rowNum = 0;
	 * 
	 * // Header row = sheet.createRow(rowNum++); cell = row.createCell(0);
	 * cell.setCellValue("투자번호"); cell = row.createCell(1);
	 * cell.setCellValue("투자명"); cell = row.createCell(2);
	 * cell.setCellValue("투자일자"); cell = row.createCell(3);
	 * cell.setCellValue("PO번호"); cell = row.createCell(4); cell.setCellValue("품명");
	 * cell = row.createCell(5); cell.setCellValue("수량"); cell = row.createCell(6);
	 * cell.setCellValue("PO금액"); cell = row.createCell(7); cell.setCellValue("부서");
	 * cell = row.createCell(8); cell.setCellValue("담당자"); cell = row.createCell(9);
	 * cell.setCellValue("자산등록"); cell = row.createCell(10);
	 * cell.setCellValue("자산미등록");
	 * 
	 * for (InvestInqrDto dto : items) { row = sheet.createRow(rowNum++); cell =
	 * row.createCell(0); cell.setCellValue(dto.getInvNo()); cell =
	 * row.createCell(1); cell.setCellValue(dto.getInvTtl()); cell =
	 * row.createCell(2); cell.setCellValue(dto.getInvDt()); cell =
	 * row.createCell(3); cell.setCellValue(dto.getPoNo()); cell =
	 * row.createCell(4); cell.setCellValue(dto.getMfgdNm()); cell =
	 * row.createCell(5); cell.setCellValue(dto.getQty()); cell = row.createCell(6);
	 * cell.setCellValue(dto.getPoAmt()); cell = row.createCell(7);
	 * cell.setCellValue(dto.getDeptNm()); cell = row.createCell(8);
	 * cell.setCellValue(dto.getInvReqr()); cell = row.createCell(9);
	 * cell.setCellValue(dto.getAsetQty()); cell = row.createCell(10);
	 * cell.setCellValue(dto.getNoAsetQty()); }
	 * 
	 * // 컨텐츠 타입과 파일명 지정 response.setContentType("ms-vnd/excel"); //
	 * response.setHeader("Content-Disposition", "attachment;filename=example.xls");
	 * response.setHeader("Content-Disposition",
	 * "attachment;filename=example.xlsx");
	 * 
	 * // Excel File Output wb.write(response.getOutputStream()); wb.close(); }
	 */
}

