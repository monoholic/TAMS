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
import kr.co.trito.tams.web.asset.regist.invest.dto.AssetDtlDto;
import kr.co.trito.tams.web.asset.regist.invest.dto.AssetMasDto;
import kr.co.trito.tams.web.asset.regist.invest.dto.InvestExcelDto;
import kr.co.trito.tams.web.asset.regist.invest.dto.InvestRegistDto;
import kr.co.trito.tams.web.asset.regist.invest.service.InvestRegistService;
import kr.co.trito.tams.web.common.dto.ComCodeDto;
import kr.co.trito.tams.web.common.dto.ComCodeParamDto;
import kr.co.trito.tams.web.common.service.CommonService;
import kr.co.trito.tams.web.user.dto.UserInfo;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/asset/regist/invest")
public class InvestRegistController {

	@Autowired
	ResponseService responseService;
	
	@Autowired
	CommonService commonService;

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
			view.addObject("menuId"    , params.get("menuId").toString());
			view.addObject("menuNm"    , params.get("menuNm").toString());
			view.addObject("menuDesc"  , params.get("menuDesc").toString());			
		} else {
			view.addObject("menuId"    , request.getParameter("menuId"));
			view.addObject("menuNm"    , request.getParameter("menuNm"));
			view.addObject("menuDesc"  , request.getParameter("menuDesc"));
		}
		
		view.addObject("loadParams", StringUtils.defaultIfBlank(request.getParameter("loadParams"), "N"));
		view.addObject("url", request.getParameter("url"));
		view.setViewName("/content/asset/regist/invest/investRegist");
		
		return view;
	}

	/** 투자자산등록(투자정보조회) 화면 : 조회 */
	@GetMapping(value = "/investReg/investRegList")
	@ResponseBody
	@ApiOperation(value = "투자자산등록(투자정보조회) 화면 / 리스트", notes = "전체 투자정보를 조회한다")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> investRegList(
			@ApiParam(value = "필터 / 페이징 값", required = true) @RequestParam Map<String, Object> params) {

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
			@ApiParam(value = "필터 / 페이징 값", required = true) @RequestParam Map<String, Object> params) {

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

	/** 등록자산목록 화면 */
	@GetMapping(value = "/regAset/regAsetList")
	@ResponseBody
	@ApiOperation(value = "투자자산등록(팝업 : 등록자산목록) 화면 / 리스트", notes = "투자에 해당하는 자산목록 리스트를 조회한다")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> regAsetList(
			@ApiParam(value = "필터 / 페이징 값", required = true) @RequestParam Map<String, Object> params) {

		SearchCondition condition = new SearchCondition(params.get("currentPage").toString(),
				params.get("numOfRows").toString(), params);
		condition.pageSetup(investRegistService.selectCountAsetList(condition));

		List<AsetListDto> list = investRegistService.selectAsetList(condition);
		List<InvestRegistDto> list2 = investRegistService.selectInvestReg(condition);

		return responseService.success(condition, list, list2);
	}

	/** 신규자산 등록 화면 */
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
		view.addObject("assetNo", request.getParameter("assetNo"));

		view.setViewName("/content/asset/regist/invest/newAsetRegist");

		return view;
	}

	/** 신규자산 저장 */
	@PostMapping("/saveNewAset")
	@ResponseBody
	@ApiOperation(value = "투자자산등록(팝업 : 신규자산 등록) 화면 / 저장버튼", notes = "신규자산을 등록한다")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> saveNewAset(
			@ApiParam(value = "필터 / 페이징 값", required = false) @RequestBody List<Map<String, Object>> params,
			@AuthenticationPrincipal UserDetails userDetail) {		
		String userId = ((UserInfo)userDetail).getDto().getUserId();		
		investRegistService.registAset(userId, params);
		return responseService.success(null);
	}

	/** 신규자산 삭제 */
	@GetMapping("/deleteNewAset")
	@ResponseBody
	@ApiOperation(value = "투자자산등록(팝업 : 신규자산 등록) 화면 / 삭제버튼", notes = "등록된 신규자산을 삭제한다")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })	
	public ResponseEntity<? extends Response> deleteNewAset(
			@ApiParam(value = "PO/자산번호", required = true) @RequestParam Map<String, Object> param){
		log.info(param.toString());
		investRegistService.deleteNewAset(param);		
		return responseService.success(null);
	}
	
	
	
	/** 개조자산 등록 팝업 */
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

	
	/** 개조자산 저장 */
	@PostMapping("/saveRemodelAset")
	@ResponseBody
	@ApiOperation(value = "투자자산등록(팝업 : 개조자산 등록) 화면 / 저장버튼", notes = "개조자산 정보을 등록한다")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> saveRemodelAset(
			@ApiParam(value = "필터 / 페이징 값", required = false) @RequestBody List<Map<String, Object>> params,
			@AuthenticationPrincipal UserDetails userDetail) {				
		String userId = ((UserInfo)userDetail).getDto().getUserId();
		investRegistService.saveRemodelAset(userId, params);		
		return responseService.success(null);
	}	
	
	
	/** 자산유형 특성정보 조회 */
	@GetMapping(value = "/asetTypeInfo")
	@ResponseBody
	@ApiOperation(value = "자산유형3 특성정보 조회", notes = "자산유형3에 해당하는 자산유형특성정보를 조회한다")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> asetTypeInfo(
			@ApiParam(value = "자산유형3", required = true) @RequestParam(value="asetType") String asetType) {
		List<ComCodeDto> list = investRegistService.selectAsetTypeInfo(asetType);
		return responseService.success(list);
	}

	
	/** 자산유형 특성정보 조회 */
	@GetMapping(value = "/assetDetailInfo")
	@ResponseBody
	@ApiOperation(value = "자산 상세 정보", notes = "자산번호로 자산의 상세정보를 조회한다.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> assetDetailInfo(
			@ApiParam(value = "자산유형3", required = true) @RequestParam(value="assetNo") String assetNo) {
		
		List<ComCodeDto> assetType1 = new ArrayList<>();
		List<ComCodeDto> assetType2 = new ArrayList<>();
		List<ComCodeDto> assetType3 = new ArrayList<>();
		
		AssetMasDto assetMasDto = investRegistService.selectAssetMasPoInfo(assetNo);		
		AssetDtlDto assetDtlDto = investRegistService.selectAssetDtl(assetNo);		
		
		ComCodeParamDto codeDto = ComCodeParamDto
					.builder()
					.codeGrpId("AS_CLASS")
					.codeLvl("1")
					.upperCodeId("")
					.build();
		assetType1 = commonService.commSelectBox(codeDto);
		
		if(StringUtils.isNotEmpty(assetMasDto.getAsetType1())) {
			codeDto = ComCodeParamDto
						.builder()
						.codeGrpId("AS_CLASS")						
						.upperCodeId(assetMasDto.getAsetType1())
						.codeLvl("")						
						.build();
			assetType2 = commonService.commSelectBox(codeDto);
		}
		
		if(StringUtils.isNotEmpty(assetMasDto.getAsetType2())) {
			codeDto = ComCodeParamDto
						.builder()
						.codeGrpId("AS_CLASS")						
						.upperCodeId(assetMasDto.getAsetType2())
						.codeLvl("")
						.build();
			assetType3 = commonService.commSelectBox(codeDto);
		}		
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mas", assetMasDto);
		map.put("dtl", assetDtlDto);
		map.put("assetType1", assetType1);
		map.put("assetType2", assetType2);
		map.put("assetType3", assetType3);
		
		return responseService.success(map);
	}
	
	
	
	/** 투자자산등록(팝업) 등록자산목록 일괄업로드 팝업 화면 : 일괄업로드 */
	@GetMapping("/popup/regAsetUploadPopup")
	public ModelAndView invUploadPopupView(HttpServletRequest request) {

		ModelAndView view = new ModelAndView();
		view.setViewName("/content/asset/regist/invest/regAsetUploadPopup");

		return view;
	}


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
