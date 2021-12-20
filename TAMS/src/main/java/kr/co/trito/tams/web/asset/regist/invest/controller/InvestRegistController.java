package kr.co.trito.tams.web.asset.regist.invest.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
import kr.co.trito.tams.comm.util.file.excel.ExcelReader;
import kr.co.trito.tams.comm.util.res.Response;
import kr.co.trito.tams.comm.util.res.ResponseService;
import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.asset.regist.invest.dto.AsetListDto;
import kr.co.trito.tams.web.asset.regist.invest.dto.InvestExcelDto;
import kr.co.trito.tams.web.asset.regist.invest.dto.InvestRegistDto;
import kr.co.trito.tams.web.asset.regist.invest.service.InvestRegistService;
import kr.co.trito.tams.web.common.dto.ComCodeDto;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/asset/regist/invest")
public class InvestRegistController {

	@Autowired
	ResponseService responseService;

	@Autowired
	InvestRegistService investRegistService;

	@Autowired
	private ExcelReader excelReader;

	/** 투자자산등록 화면 */
	@PostMapping("/investReg")
	public ModelAndView asetinqrView(HttpServletRequest request,
			@ApiParam(value = "필터 / 페이징 값", required = true) @RequestParam Map<String, Object> params) {

		ModelAndView view = new ModelAndView();
		if (params != null) {
			view.addObject("menuId", params.get("menuId").toString());
			view.addObject("menuNm", params.get("menuNm").toString());
			view.addObject("menuDesc", params.get("menuDesc").toString());
		} else {
			view.addObject("menuId", request.getParameter("menuId"));
			view.addObject("menuNm", request.getParameter("menuNm"));
			view.addObject("menuDesc", request.getParameter("menuDesc"));
		}
		view.addObject("url", request.getParameter("url"));

		view.setViewName("/content/asset/regist/invest/investRegist");

		return view;
	}

	/** 투자자산등록(투자정보조회) 화면 : 조회 */
	@PostMapping(value = "/investReg/investRegList")
	@ResponseBody
	@ApiOperation(value = "투자자산등록(투자정보조회) 화면 / 리스트", notes = "전체 투자정보를 조회한다")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> investRegList(
			@ApiParam(value = "필터 / 페이징 값", required = true) @RequestBody Map<String, Object> params) {

		SearchCondition condition = new SearchCondition(params.get("currentPage").toString(),
				params.get("numOfRows").toString(), params);
		int total = investRegistService.selectCountInvest(condition);
		condition.pageSetup(total);

		List<InvestRegistDto> list = investRegistService.selectInvestRegList(condition);
		return responseService.success(condition, list);
	}

	/** 투자자산등록 화면 : 조회 - 엑셀다운 */
	@PostMapping(value = "/invListExcel")
	@ApiOperation(value = "투자정보조회 화면 엑셀다운로드", notes = "엑셀다운로드")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ModelAndView invListExcel(
			@ApiParam(value = "필터 / 페이징 값", required = true) @RequestBody Map<String, Object> params) {

		SearchCondition condition = new SearchCondition(params.get("currentPage").toString(),
				params.get("numOfRows").toString(), params);
		condition.pageSetup(investRegistService.selectCountInvest(condition));
		List<InvestExcelDto> list = investRegistService.selectInvInqrExcelList(condition);

		List<List<String>> rows = new ArrayList<List<String>>();

		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> map = null;
		List<String> row = null;

		for (InvestExcelDto inv : list) {
			map = objectMapper.convertValue(inv, Map.class);
			row = new ArrayList<>(map.values());
			rows.add(row);
		}

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ExcelConstant.FILE_NAME, "투자정보리스트");
		paramMap.put(ExcelConstant.HEAD,
				Arrays.asList("투자번호", "투자명", "투자일자", "PO번호", "품명", "수량", "PO금액", "부서", "담당자", "자산등록", "자산미등록"));
		paramMap.put(ExcelConstant.BODY, rows);

		return new ModelAndView("excelXlsxView", paramMap);
	}

	/** 투자자산등록(팝업) 등록자산목록 화면 */
	@PostMapping("/regAset")
	public ModelAndView regAsetMain(HttpServletRequest request,
			@ApiParam(value = "필터 / 페이징 값", required = true) @RequestParam Map<String, Object> params) {

		ModelAndView view = new ModelAndView();
		view.addObject("menuId", params.get("menuId").toString());
		view.addObject("poNo", params.get("poNo").toString());

		view.setViewName("/content/asset/regist/invest/regAset");

		return view;
	}

	/** 투자자산등록(팝업) 등록자산목록 화면 : 조회 */
	@PostMapping(value = "/regAset/regAsetList")
	@ResponseBody
	@ApiOperation(value = "투자자산등록(팝업 : 등록자산목록) 화면 / 리스트", notes = "투자에 해당하는 자산목록 리스트를 조회한다")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> regAsetList(
			@ApiParam(value = "필터 / 페이징 값", required = true) @RequestBody Map<String, Object> params) {

		SearchCondition condition = new SearchCondition(params.get("currentPage").toString(),
				params.get("numOfRows").toString(), params);
		int total = investRegistService.selectCountAsetList(condition);
		condition.pageSetup(total);

		List<AsetListDto> list = investRegistService.selectAsetList(condition);
		List<InvestRegistDto> list2 = investRegistService.selectInvestReg(condition);

		return responseService.success(condition, list, list2);
	}

	/** 신규자산등록(팝업) 화면 */
	@GetMapping("/newAsetReg")
	public ModelAndView newAsetRegView(HttpServletRequest request,
			@ApiParam(value = "메뉴ID", required = false) @RequestParam(value = "menuId", required = false) String menuId,
			@ApiParam(value = "PO번호", required = false) @RequestParam(value = "poNo", required = false) String poNo) {

		ModelAndView view = new ModelAndView();
		if (menuId != null) {
			view.addObject("menuId", menuId);
		} else {
			view.addObject("menuId", request.getParameter("menuId"));
		}
		view.addObject("menuNm", request.getParameter("menuNm"));
		view.addObject("menuDesc", request.getParameter("menuDesc"));
		view.addObject("url", request.getParameter("url"));
		view.addObject("poNo", poNo);

		view.setViewName("/content/asset/regist/invest/newAsetRegist");

		return view;
	}

	/** 신규자산등록(팝업) 저장 */
	@PostMapping("/saveNewAset")
	@ResponseBody
	@ApiOperation(value = "투자자산등록(팝업 : 신규자산 등록) 화면 / 저장버튼", notes = "신규자산을 등록한다")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public String saveNewAset(
			@ApiParam(value = "필터 / 페이징 값", required = false) @RequestBody List<Map<String, Object>> params,
			@AuthenticationPrincipal UserDetails userDetail) {

		String code = "202";

		code = investRegistService.registAset(userDetail, params, code);

		return code;
	}

	/** 신규자산등록(팝업) 화면 */
	@GetMapping("/remodelAsetReg")
	public ModelAndView remodelAsetRegView(HttpServletRequest request,
			@ApiParam(value = "PO번호", required = false) @RequestParam(value = "poNo", required = false) String poNo) {

		ModelAndView view = new ModelAndView();
		view.addObject("menuId", request.getParameter("menuId"));
		view.addObject("menuNm", request.getParameter("menuNm"));
		view.addObject("menuDesc", request.getParameter("menuDesc"));
		view.addObject("url", request.getParameter("url"));
		view.addObject("poNo", poNo);

		view.setViewName("/content/asset/regist/invest/remodelAsetRegist");

		return view;
	}

	/** 투자자산등록(팝업) 등록자산목록 화면 : 조회 */
	@PostMapping(value = "/asetTypeInfo")
	@ResponseBody
	@ApiOperation(value = "투자자산등록(팝업 : 신규자산 등록) 화면 / 자산유형3 셀렉트박스 체인지 이벤트", notes = "자산유형3에 해당하는 자산유형특성정보를 조회한다")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> asetTypeInfo(
			@ApiParam(value = "필터 / 페이징 값", required = true) @RequestBody Map<String, Object> params) {

		SearchCondition condition = new SearchCondition("0", "0", params);

		List<ComCodeDto> list = investRegistService.selectAsetTypeInfo(condition);

		return responseService.success(condition, list);
	}

	/** 투자자산등록(팝업) 등록자산목록 일괄업로드 팝업 화면 : 일괄업로드 */
	@GetMapping("/popup/regAsetUploadPopup")
	public ModelAndView invUploadPopupView(HttpServletRequest request) {

		ModelAndView view = new ModelAndView();
		view.setViewName("/content/asset/regist/invest/regAsetUploadPopup");

		return view;
	}

	/*
	 * @PostMapping("/readExcel")
	 * 
	 * @ResponseBody
	 * 
	 * @ApiOperation(value = "등록자산목록 화면 / 일괄 업로드 엑셀파일 읽기", notes =
	 * "선택한 엑셀파일을 읽어 그리드에 뿌려준다")
	 * 
	 * @ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	 * 
	 * @ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") }) public
	 * List<InvestDto> readExcel(@RequestParam("file") MultipartFile multipartFile)
	 * throws IOException, InvalidFormatException { List<InvestDto> result = null;
	 * 
	 * InvestDto dto = new InvestDto();
	 * 
	 * result = excelReader.readFileToList(multipartFile, dto::row);
	 * 
	 * //첫번째 열 헤더 제거...??!! result.remove(0);
	 * 
	 *//** 유효성 검증 *//*
					 * for(InvestDto inv : result) {
					 * 
					 * String chkResult = "";
					 * 
					 * if( StringUtils.isEmpty(inv.getInvNo()) ) { chkResult += "투자번호가 누락되었습니다"; }
					 * if( StringUtils.isEmpty(inv.getInvTtl()) ) { chkResult += "투자명이 누락되었습니다"; }
					 * if( StringUtils.isEmpty(inv.getInvDt()) ) { chkResult += "투자일자가 누락되었습니다"; }
					 * if( StringUtils.isEmpty(inv.getInvQty()) ) { chkResult += "투자수량이 누락되었습니다"; }
					 * else { //숫자값만 입력 가능... if( !StringUtils.isNumeric(inv.getInvQty()) ) {
					 * chkResult += "숫자만 입력 가능합니다."; } } if( StringUtils.isEmpty(inv.getInvAmt()) )
					 * { chkResult += "투자금액이 누락되었습니다"; } else { //숫자값만 입력 가능... if(
					 * !StringUtils.isNumeric(inv.getInvAmt()) ) { chkResult += "숫자만 입력 가능합니다."; } }
					 * if( StringUtils.isEmpty(inv.getActgYear()) ) { chkResult += "회계연도가 누락되었습니다";
					 * } if( StringUtils.isEmpty(inv.getPoNo()) ) { chkResult += "PO번호가 누락되었습니다"; }
					 * if( StringUtils.isEmpty(inv.getMfgdNm()) ) { chkResult += "품명이 누락되었습니다"; }
					 * if( StringUtils.isEmpty(inv.getQty()) ) { chkResult += "수량이 누락되었습니다"; } else
					 * { //숫자값만 입력 가능... if( !StringUtils.isNumeric(inv.getQty()) ) { chkResult +=
					 * "숫자만 입력 가능합니다."; } } if( StringUtils.isEmpty(inv.getPoAmt()) ) { chkResult +=
					 * "PO금액이 누락되었습니다"; } else { //숫자값만 입력 가능... if(
					 * !StringUtils.isNumeric(inv.getPoAmt()) ) { chkResult += "숫자만 입력 가능합니다."; } }
					 * if( StringUtils.isEmpty(inv.getInvReqr()) ) { chkResult += "담당자가 누락되었습니다"; }
					 * else { // 담장자 id 로 정보 찾기...
					 * 
					 * Map<String,String> map = new HashMap<>(); map.put("userId",
					 * inv.getInvReqr()); Map<String,String> deptInfo =
					 * commonService.selectUserDeptInfo(map);
					 * 
					 * //inv.set(deptInfo.get("upDeptNm")); inv.setDeptNm(deptInfo.get("deptNm"));
					 * inv.setInvReqr(deptInfo.get("userNm"));
					 * 
					 * } if( StringUtils.isEmpty(inv.getVendNm()) ) { chkResult += "업체가 누락되었습니다"; }
					 * if( StringUtils.isEmpty(inv.getReqDt()) ) { chkResult += "구매요청일자가 누락되었습니다"; }
					 * if( StringUtils.isEmpty(inv.getDlvDt()) ) { chkResult += "납품일자가 누락되었습니다"; }
					 * 
					 * //검증 결과 inv.setChkResult(chkResult); if( !StringUtils.isEmpty(chkResult) )
					 * inv.setChkFlag("Y"); }
					 * 
					 * return result; }
					 * 
					 * 
					 * @PostMapping("/saveExcel")
					 * 
					 * @ResponseBody public String saveExcel(@RequestBody List<InvDto>
					 * datas, @AuthenticationPrincipal UserDetails userDetail) throws IOException,
					 * InvalidFormatException {
					 * 
					 * String code = "202";
					 * 
					 * UserInfo userInfo = (UserInfo)userDetail; String userId =
					 * userInfo.getDto().getUserId();
					 * 
					 * int cnt = 0; for(InvDto inv : datas) { inv.setRegr(userId);
					 * inv.setUpdr(userId); log.error("@@@ "+inv); if(
					 * investRegistService.saveInvestInfo(inv) > 0 ) cnt++; }
					 * 
					 * cnt = 0; cnt = investRegistService.savePoInfo(datas);
					 * 
					 * if(cnt > 0) code = "200";
					 * 
					 * return code; }
					 */

	/** 투자자산등록 화면 : 조회 - 엑셀다운 */
	@PostMapping(value = "/regAsetListExcel")
	@ApiOperation(value = "투자정보조회 화면 엑셀다운로드", notes = "엑셀다운로드")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ModelAndView regAsetListExcel(
			@ApiParam(value = "필터 / 페이징 값", required = true) @RequestParam Map<String, Object> params) {

		SearchCondition condition = new SearchCondition(params.get("currentPage").toString(),
				params.get("numOfRows").toString(), params);
		condition.pageSetup(investRegistService.selectCountAsetList(condition));
		List<AsetListDto> list = investRegistService.selectAsetList(condition);

		List<List<String>> rows = new ArrayList<List<String>>();

		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> map = null;
		List<String> row = null;

		for (AsetListDto aset : list) {
			map = objectMapper.convertValue(aset, Map.class);
			row = new ArrayList<>(map.values());
			rows.add(row);
		}

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ExcelConstant.FILE_NAME, "등록자산리스트");
		paramMap.put(ExcelConstant.HEAD, Arrays.asList("자산번호", "자산유형1", "자산유형2", "자산유형3", "제조사", "모델", "S/N", "사업부",
				"부서", "담당자", "태깅여부", "등록상태"));
		paramMap.put(ExcelConstant.BODY, rows);

		return new ModelAndView("excelXlsxView", paramMap);
	}
}
