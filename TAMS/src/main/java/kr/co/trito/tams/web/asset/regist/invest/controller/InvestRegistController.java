package kr.co.trito.tams.web.asset.regist.invest.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
import kr.co.trito.tams.comm.util.msg.Message;
import kr.co.trito.tams.comm.util.res.Response;
import kr.co.trito.tams.comm.util.res.ResponseService;
import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.asset.regist.invest.dto.AsetListDto;
import kr.co.trito.tams.web.asset.regist.invest.dto.InvestExcelDto;
import kr.co.trito.tams.web.asset.regist.invest.dto.InvestRegistDto;
import kr.co.trito.tams.web.asset.regist.invest.service.InvestRegistService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/asset/regist/invest")
public class InvestRegistController {
	
	@Autowired
	ResponseService responseService;
	
	@Autowired
	InvestRegistService investRegistService;
	
	/** 투자자산등록 화면 */
	@PostMapping("/investReg")
	public ModelAndView asetinqrView(HttpServletRequest request) {
	
		ModelAndView view = new ModelAndView();
		view.addObject("menuId", request.getParameter("menuId"));
		view.addObject("menuNm", request.getParameter("menuNm"));
		view.addObject("menuDesc", request.getParameter("menuDesc"));
		view.addObject("url", request.getParameter("url"));
		
		view.setViewName("/content/asset/regist/invest/investRegist");
		
		return view;
	}
	
	/** 투자자산등록(투자정보조회) 화면 : 조회 */
	@GetMapping(value="/investReg/investRegList")
	@ResponseBody
	@ApiOperation(value = "투자자산등록(투자정보조회) 화면 / 리스트", notes = "전체 투자정보를 조회한다")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> investRegList(
				@ApiParam(value = "조회 페이지 번호", required = true) @RequestParam(value = "currentPage",required = true) String currentPage,
				@ApiParam(value = "페이지별 조회 출력수", required = true) @RequestParam(value = "numOfRows", required = true) String numOfRows,
				@ApiParam(value = "투자번호", required = false) @RequestParam(value = "invNo", required = false) String invNo,
				@ApiParam(value = "투자명", required = false) @RequestParam(value = "invTtl", required = false) String invTtl,
				@ApiParam(value = "PO번호", required = false) @RequestParam(value = "poNo", required = false) String poNo,
				@ApiParam(value = "품명", required = false) @RequestParam(value = "mfgdNm",required = false) String mfgdNm,
				@ApiParam(value = "부서코드", required = false) @RequestParam(value = "deptCd",required = false) String deptCd,
				@ApiParam(value = "사용자ID", required = false) @RequestParam(value = "userId", required = false) String userId,
				@ApiParam(value = "투자일자범위(from)", required = false) @RequestParam(value = "fromDate", required = false) String fromDate,
				@ApiParam(value = "투자일자범위(to)", required = false) @RequestParam(value = "toDate", required = false) String toDate,
				@ApiParam(value = "자산미등록체크", required = false) @RequestParam(value = "regYn", required = false) String regYn,
				@ApiParam(value = "정렬필드", required = false) @RequestParam(value = "sortField", required = false) String sortField,
				@ApiParam(value = "정렬순서번호", required = false) @RequestParam(value = "sortOrder", required = false) String sortOrder
            ) { 

		if (StringUtils.isEmpty(currentPage) || StringUtils.isEmpty(numOfRows))
		throw new IllegalArgumentException("Request parameter error.");
		
		Map<String, Object> params = new HashMap<>();
		
		if (!StringUtils.isEmpty(invNo))
		params.put("invNo", invNo);
		
		if (!StringUtils.isEmpty(invTtl))
		params.put("invTtl", invTtl);
		
		if (!StringUtils.isEmpty(poNo))
		params.put("poNo", poNo);
		
		if (!StringUtils.isEmpty(mfgdNm))
		params.put("mfgdNm", mfgdNm);
		
		if (!StringUtils.isEmpty(deptCd))
		params.put("deptCd", deptCd);
		
		if (!StringUtils.isEmpty(userId))
		params.put("userId", userId);
		
		if (!StringUtils.isEmpty(fromDate))
		params.put("fromDate", fromDate);
		
		if (!StringUtils.isEmpty(toDate))
		params.put("toDate", toDate);
		
		if (!StringUtils.isEmpty(regYn))
		params.put("regYn", regYn);
		
		if (!StringUtils.isEmpty(sortField))
		params.put("sortField", sortField);
		
		if (!StringUtils.isEmpty(sortOrder))
		params.put("sortOrder", sortOrder);
		
		SearchCondition condition = new SearchCondition(currentPage, numOfRows, params);
		int total = investRegistService.selectCountInvest(condition);
		condition.pageSetup(total);
		
		List<InvestRegistDto> list = investRegistService.selectInvestRegList(condition);
		return responseService.success(condition, list);
	}
	
	/** 투자자산등록 화면 : 조회 - 엑셀다운 */
	@PostMapping(value="/investReg/invListExcel")
	@ApiOperation(value = "투자정보조회 화면 엑셀다운로드", notes = "엑셀다운로드")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ModelAndView invListExcel(
			@ApiParam(value = "투자번호", required = false) @RequestParam(value = "invNo", required = false) String invNo,
			@ApiParam(value = "투자명", required = false) @RequestParam(value = "invTtl", required = false) String invTtl,
			@ApiParam(value = "PO번호", required = false) @RequestParam(value = "poNo", required = false) String poNo,
			@ApiParam(value = "품명", required = false) @RequestParam(value = "mfgdNm",required = false) String mfgdNm,
			@ApiParam(value = "부서코드", required = false) @RequestParam(value = "deptCd",required = false) String deptCd,
			@ApiParam(value = "사용자ID", required = false) @RequestParam(value = "userId", required = false) String userId,
			@ApiParam(value = "투자일자범위(from)", required = false) @RequestParam(value = "fromDate", required = false) String fromDate,
			@ApiParam(value = "투자일자범위(to)", required = false) @RequestParam(value = "toDate", required = false) String toDate,
			@ApiParam(value = "자산미등록체크", required = false) @RequestParam(value = "regYn", required = false) String regYn
			) { 
		
		Map<String, Object> params = new HashMap<>();
		
		if (!StringUtils.isEmpty(invNo))
		params.put("invNo", invNo);
		
		if (!StringUtils.isEmpty(invTtl))
		params.put("invTtl", invTtl);
		
		if (!StringUtils.isEmpty(poNo))
		params.put("poNo", poNo);
		
		if (!StringUtils.isEmpty(mfgdNm))
		params.put("mfgdNm", mfgdNm);
		
		if (!StringUtils.isEmpty(deptCd))
		params.put("deptCd", deptCd);
		
		if (!StringUtils.isEmpty(userId))
		params.put("userId", userId);
		
		if (!StringUtils.isEmpty(fromDate))
		params.put("fromDate", fromDate);
		
		if (!StringUtils.isEmpty(toDate))
		params.put("toDate", toDate);
		
		if (!StringUtils.isEmpty(regYn))
		params.put("regYn", regYn);	
		
		SearchCondition condition = new SearchCondition("0", "0", params);
		int total = investRegistService.selectCountInvest(condition);
		condition.pageSetup(total);
		
		List<InvestExcelDto> list = investRegistService.selectInvInqrExcelList(condition);
		
		return new ModelAndView("excelXlsxView", makeExcelData(list)) ;
	}
	
	/** 엑셀 다운로드용 데이터 생성 */
	private Map<String, Object> makeExcelData(List<InvestExcelDto> list) {
		Map<String, Object> map = new HashMap<>();
		map.put(ExcelConstant.FILE_NAME, "투자정보리스트");
		map.put(ExcelConstant.HEAD, Arrays.asList("투자번호", "투자명", "투자일자", "PO번호", "품명", "수량", "PO금액", "부서", "담당자", "자산등록", "자산미등록"));
		
		ObjectMapper objectMapper = new ObjectMapper();
		List<List<String>> rows = new ArrayList<List<String>>();
		for(InvestExcelDto inv: list) {
			Map<String, String> m = objectMapper.convertValue(inv, Map.class);
			List<String> l = new ArrayList<>(m.values());
			rows.add(l);
		}
		map.put(ExcelConstant.BODY, rows);
		return map;
	}	
	
	/** 투자자산등록(팝업) 등록자산목록 화면 */
	@PostMapping("/regAset")
	public ModelAndView regAsetMain(HttpServletRequest request,
			@ApiParam(value = "메뉴ID", required = false) @RequestParam(value = "menuId", required = false) String menuId,
			@ApiParam(value = "PO번호", required = false) @RequestParam(value = "poNo", required = false) String poNo) {
	
		ModelAndView view = new ModelAndView();
		view.addObject("menuId", request.getParameter("menuId"));
		view.addObject("menuNm", request.getParameter("menuNm"));
		view.addObject("menuDesc", request.getParameter("menuDesc"));
		view.addObject("poNo", poNo);
		
		view.setViewName("/content/asset/regist/invest/regAset");
		
		return view;
	}
	
	/** 투자자산등록(팝업) 등록자산목록 화면 : 조회 */
	@GetMapping(value="/regAset/regAsetList")
	@ResponseBody
	@ApiOperation(value = "투자자산등록(팝업 : 등록자산목록) 화면 / 리스트", notes = "투자에 해당하는 자산목록 리스트를 조회한다")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> regAsetList(
				@ApiParam(value = "조회 페이지 번호", required = true) @RequestParam(value = "currentPage",required = true) String currentPage,
				@ApiParam(value = "페이지별 조회 출력수", required = true) @RequestParam(value = "numOfRows", required = true) String numOfRows,
				@ApiParam(value = "PO번호", required = false) @RequestParam(value = "poNo", required = false) String poNo,
				@ApiParam(value = "정렬필드", required = false) @RequestParam(value = "sortField", required = false) String sortField,
				@ApiParam(value = "정렬순서번호", required = false) @RequestParam(value = "sortOrder", required = false) String sortOrder
            ) { 

		if (StringUtils.isEmpty(currentPage) || StringUtils.isEmpty(numOfRows))
		throw new IllegalArgumentException("Request parameter error.");
		
		Map<String, Object> params = new HashMap<>();
		
		if (!StringUtils.isEmpty(poNo))
		params.put("poNo", poNo);
		
		if (!StringUtils.isEmpty(sortField))
		params.put("sortField", sortField);
		
		if (!StringUtils.isEmpty(sortOrder))
		params.put("sortOrder", sortOrder);
		
		SearchCondition condition = new SearchCondition(currentPage, numOfRows, params);
		int total = investRegistService.selectCountInvest(condition);
		condition.pageSetup(total);
		
		List<AsetListDto> list = investRegistService.selectAsetList(condition);
		List<InvestRegistDto> list2 = investRegistService.selectInvestReg(condition);
		
		return responseService.success(condition, list, list2);
	}
	
	/** 신규자산등록(팝업) 화면 */
	@GetMapping("/newAsetReg")
	public ModelAndView newAsetRegView(HttpServletRequest request,
			@ApiParam(value = "PO번호", required = false) @RequestParam(value = "poNo", required = false) String poNo) {
	
		ModelAndView view = new ModelAndView();
		view.addObject("menuId", request.getParameter("menuId"));
		view.addObject("menuNm", request.getParameter("menuNm"));
		view.addObject("menuDesc", request.getParameter("menuDesc"));
		view.addObject("url", request.getParameter("url"));
		view.addObject("poNo", poNo);
		
		view.setViewName("/content/asset/regist/invest/newAsetRegist");
		
		return view;
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
}
