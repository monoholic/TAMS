package kr.co.trito.tams.web.asset.insp.manage.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kr.co.trito.tams.comm.util.res.Response;
import kr.co.trito.tams.comm.util.res.ResponseService;
import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.asset.insp.manage.dto.InspMasterDto;
import kr.co.trito.tams.web.asset.insp.manage.service.InspManageService;
import kr.co.trito.tams.web.common.dto.ComCodeParamDto;
import kr.co.trito.tams.web.common.service.CommonService;
import kr.co.trito.tams.web.user.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@RequestMapping("/asset/insp/manage")
@Slf4j
public class InspManageController {	
	@Autowired
	InspManageService inspManageService; 
	
	@Autowired
	ResponseService responseService;
	
	@Autowired
	CommonService commonService;
	
	/** 실사관리화면 */
	@PostMapping("list")
	public ModelAndView inspManageView(HttpServletRequest request, 
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
		
		view.addObject("loadParams", StringUtils.defaultIfBlank(request.getParameter("loadParams"), "N"));
		view.addObject("url", request.getParameter("url"));
		
		view.setViewName("/content/asset/insp/manage/InspManage");
		return view;
	}		
	
	/** 실사관리화면 리스트 조회 */
	@GetMapping(value="/selectInspManageList")
	@ResponseBody
	@ApiOperation(value = "자산실사 조회", notes = "자산실사 조회")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> selectInspManageList(
			@ApiParam(value = "검색 조건 파리미터 Dto", required = true) @RequestParam Map<String, Object> params,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail) {

		log.info("[controller][selectInspManageList]");
		
		SearchCondition condition = new SearchCondition(params.get("currentPage").toString(), params.get("numOfRows").toString(), params);
		condition.pageSetup1(inspManageService.selectCountInspManageList(condition));
		List<InspMasterDto> list = inspManageService.selectInspManageList(condition);
		
		return responseService.success(condition, list);
	}	
	
	/** 실사관리화면 실사명 코드 리스트 조회 */
	@GetMapping(value="/selectInspNmList")
	@ResponseBody
	@ApiOperation(value = "자산실사명 조회", notes = "자산실사명 조회")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> selectInspNmList(
			@ApiParam(value = "검색 조건 파리미터 Dto", required = true) @RequestParam Map<String, Object> params,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail) { 
		
		log.info("[controller][selectInspNmList]");
		UserInfo userInfo = (UserInfo)userDetail;
		String userId = userInfo.getDto().getUserId();

		params.put("userId", userId);
		SearchCondition condition = new SearchCondition(params.get("currentPage").toString(), params.get("numOfRows").toString(), params);
		List<InspMasterDto> list = inspManageService.selectInspNmList(condition);		
		return responseService.success(condition, list);
	}
	
	/** 실사관리화면 삭제*/
	@PostMapping(value="/deleteInspList")
	@ResponseBody
	@ApiOperation(value = "자산정보수정 화면 : 삭제", notes = "자산정보수정 화면 : 삭제")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> deleteInspList( 
			@ApiParam(value = "실사 번호", required = true) @RequestBody Map<String, Object> items) { 
		
		log.info("[controller][deleteInspList]");
		List<String> asetList = (List<String>) items.get("items");

		for(String inspNo : asetList) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("inspNo", inspNo);
			inspManageService.deleteInspMaster(param);
			inspManageService.deleteInspAsetList(param);
			inspManageService.deleteInspMngList(param);
		}
		
		return responseService.success(null);
	}
	
	/** 실사관리화면 수정*/
	@PostMapping(value="/updateInspMaster")
	@ResponseBody
	@ApiOperation(value = "자산정보수정 화면 : 수정", notes = "자산정보수정 화면 : 수정")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> updateInspMaster( 
			@ApiParam(value = "실사 번호", required = true) @RequestBody Map<String, Object> items,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail) { 
		
		log.info("[controller][updateInspMaster]");
		
		items.put("userId", userDetail.getUsername());
		inspManageService.updateInspMaster(items);
		
		return responseService.success(null);
	}
	
	/** 실사생성 팝업 */
	@GetMapping(value="inspManagePopup")
	public ModelAndView inspManagePopupView(HttpServletRequest request,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail) {
		
		log.info("[controller][inspManagePopup]");
		
		ModelAndView view = new ModelAndView();
		
		view.addObject("regr", userDetail.getUsername());
		Date now = new Date(System.currentTimeMillis());
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		view.addObject("regDt", fmt.format(now));
		
		view.setViewName("/content/asset/insp/manage/inspManagePopup");
		
		
		return view;
	}

	
	/** 실사생성 저장 */
	@PostMapping(value="insertInspMaster")
	@ResponseBody
	@ApiOperation(value = "자산실사 등록화면 : 입력", notes = "자산실사 등록화면 : 입력")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> insertInspMaster(
			@ApiParam(value = "파라미터 Dto", required = true) @RequestBody Map<String, Object> params){ 
		
		log.info("[controller][insertInspMaster]");
		
		inspManageService.insertInspMaster(params);
		
		return responseService.success(null);
	}
	
	
	/** 실사관리 디테일 화면 */
	@PostMapping("/inspManageDetail")
	@ResponseBody
	public ModelAndView inspManageDetailView(HttpServletRequest request) {
		
		log.info("[controller][inspManageDetailView]");
		ModelAndView view = new ModelAndView();
		String inspNo = request.getParameter("inspNo");
		
		view.addObject("inspNo", inspNo);
		view.setViewName("/content/asset/insp/manage/inspManageDetail");
		return view;
	}
	
	
	
	/** 실사관리 디테일 조회 */
	@GetMapping("/selectInspAsetList")
	@ResponseBody
	@ApiOperation(value = "실사관리 자산리스트 조회", notes = "실사관리 자산리스트 조회")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> selectInspAsetList(
			@ApiParam(value = "검색 조건 파리미터 Dto", required = true) @RequestParam Map<String, Object> params,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail) { 
		log.info("[controller][selectInspAsetList]");
		
		String inspNo = (String) params.get("inspNo");
		
		
		SearchCondition condition = new SearchCondition(params.get("currentPage").toString(), params.get("numOfRows").toString(), params);
		condition.pageSetup1(inspManageService.selectCountInspAsetList(condition));
		
		// 실사 <> 자산 정보 리스트 조회 
		List<InspMasterDto> list = inspManageService.selectInspAsetList(condition);
		
		
		return responseService.success(condition, list);
		
	}
	
	
	/** 자산추가 팝업 */
	@GetMapping(value="inspManageAsetAddPopup")
	public ModelAndView inspManageAsetAddPopupView(HttpServletRequest request,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail) {
		
		log.info("[controller][inspManageAsetAddPopupView]");
		
		ModelAndView view = new ModelAndView();
		
		view.setViewName("/content/asset/insp/manage/inspManageAsetAddPopup");
		
		
		return view;
	}
	
	/** 실사관리 자산추가팝업 자산조회 */
	@GetMapping("/selectInspAsetAddList")
	@ResponseBody
	@ApiOperation(value = "실사관리 자산추가팝업 자산조회", notes = "실사관리 자산추가팝업 자산조회")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> selectInspAsetAddList(
			@ApiParam(value = "검색 조건 파리미터 Dto", required = true) @RequestParam Map<String, Object> params,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail) { 
		
		log.info("[controller][selectInspAsetAddList]");
		
		
		SearchCondition condition = new SearchCondition(params.get("currentPage").toString(), params.get("numOfRows").toString(), params);
		condition.pageSetup1(inspManageService.selectInspAsetAddCoutList(condition));
		
		// 실사 <> 자산 정보 리스트 조회 
		List<InspMasterDto> list = inspManageService.selectInspAsetAddList(condition);
		
		
		return responseService.success(condition, list);
		
	}
	

	/** 실사관리화면 자산정보 추가 */
	@PostMapping(value="/insertInspAssetList")
	@ResponseBody
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "실사관리화면 자산추가팝업 저장", notes = "실사관리화면 자산추가팝업 저장")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> insertInspAssetList(
			@ApiParam(value = "자산번호", required = true) @RequestBody Map<String, Object> items,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail) { 
		
		log.info("[controller][insertInspAssetList]");
		InspMasterDto dto = new InspMasterDto();

		String inspNo = (String) items.get("inspNo");
		String arr = (String) items.get("items");
		String[] asetNoList = arr.split(",");
		
		for (String asetNo : asetNoList) {
			dto.setRegr(userDetail.getUsername());
			dto.setAsetNo(asetNo);
			dto.setInspNo(inspNo);
			inspManageService.insertInspAssetList(dto);
		}
		
		
		return responseService.success(null);
	}
	
	/** 실사관리화면 자산목록 코드 조회 */
	@GetMapping("/selectInspAsetCodeList")
	@ResponseBody
	@ApiOperation(value = "실사관리 자산추가팝업 자산조회", notes = "실사관리 자산추가팝업 자산조회")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> selectInspAsetCodeList(
			@ApiParam(value = "검색 조건 파리미터 Dto", required = true) @RequestParam Map<String, Object> params,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail) { 
		
		log.info("[controller][selectInspAsetCodeList]");
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		ComCodeParamDto param = new ComCodeParamDto();
		
		// 실사 <> 자산 정보 리스트 조회 
		param.setCodeLvl("1");
		param.setUpperCodeId("");
		
		param.setCodeGrpId("INSP_STATUS");
		result.put("inspStusArr", commonService.commSelectBox(param));
		
		param.setCodeGrpId("INSP_METHOD");
		result.put("inspMtdArr", commonService.commSelectBox(param));
		
		param.setCodeGrpId("INSP_NOTE");
		result.put("inspPtclArr", commonService.commSelectBox(param));
		
		
		return responseService.success(result);
		
	}
	
	
	/** 실사관리화면 자산정보 수정 */
	@PostMapping(value="/updateInspAsetList")
	@ResponseBody
	@ApiOperation(value = "실사관리화면 자산정보 수정", notes = "실사관리화면 자산정보 수정")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> updateInspAsetList(
			@ApiParam(value = "자산정보 수정 데이터", required = true) @RequestBody Map<String, Object> items,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail) { 
		
		UserInfo userInfo = (UserInfo)userDetail;
		String userId = userInfo.getDto().getUserId();
		
		items.put("regr", userId);
		
		int cnt = inspManageService.updateInspAsetList(items);
		
		return responseService.success(null);
	}
	
	/** 실사관리화면 자산정보 삭제 */
	@PostMapping(value="/deleteInspAsetList")
	@ResponseBody
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "실사관리화면 자산정보 삭제", notes = "실사관리화면 자산정보 삭제")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> deleteInspAsetList(
			@ApiParam(value = "자산정보 수정 데이터", required = true) @RequestBody Map<String, Object> items,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail) { 
		
		//UserInfo userInfo = (UserInfo)userDetail;
		//String userId = userInfo.getDto().getUserId();
		
		List<String> asetList = (List<String>) items.get("asetNo");
		
		for (String asetNo : asetList) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("asetNo", asetNo);
			param.put("inspNo", items.get("inspNo"));
			inspManageService.deleteInspAsetList(param);
		}
		
		
		//int cnt = inspManageService.deleteInspAsetList(items);
		
		return responseService.success(null);
	}
	
	
	
	/** 실사관리 대상자 화면 */
	@PostMapping("/inspManageTarget")
	@ResponseBody
	public ModelAndView inspManageTargetView(HttpServletRequest request) {
		
		log.info("[controller][inspManageTargetView]");
		ModelAndView view = new ModelAndView();
		String inspNo = request.getParameter("inspNo");
		
		view.addObject("inspNo", inspNo);
		view.setViewName("/content/asset/insp/manage/inspManageTarget");
		return view;
	}
	
	/** 실사관리 디테일 조회 */
	@GetMapping("/selectInspTargetList")
	@ResponseBody
	@ApiOperation(value = "실사관리 자산리스트 조회", notes = "실사관리 자산리스트 조회")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> selectInspTargetList(
			@ApiParam(value = "검색 조건 파리미터 Dto", required = true) @RequestParam Map<String, Object> params,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail) { 
		log.info("[controller][selectInspTargetList]");
		
		
		SearchCondition condition = new SearchCondition(params.get("currentPage").toString(), params.get("numOfRows").toString(), params);
		condition.pageSetup1(inspManageService.selectCountInspMngList(condition));
		
		// 실사 <> 자산 정보 리스트 조회 
		List<InspMasterDto> list = inspManageService.selectInspMngList(condition);
		
		
		return responseService.success(condition, list);
		
	}
	
	
	/** 실사관리화면 실사담당자 수정 */
	@PostMapping(value="/updateInspMngList")
	@ResponseBody
	@ApiOperation(value = "실사관리화면 실사담당자 수정", notes = "실사관리화면 실사담당자 수정")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> updateInspMngList(
			@ApiParam(value = "자산정보 수정 데이터", required = true) @RequestBody Map<String, Object> items,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail) { 
		
		UserInfo userInfo = (UserInfo)userDetail;
		String userId = userInfo.getDto().getUserId();
		items.put("userId", userId);
		
		inspManageService.updateInspMngList(items);
		
		return responseService.success(null);
	}
	
	
	/** 실사관리 진행상황조회 및 마감 화면 */
	@PostMapping("/inspManageProgress")
	@ResponseBody
	public ModelAndView inspManageProgressView(HttpServletRequest request) {
		
		log.info("[controller][inspManageProgressView]");
		ModelAndView view = new ModelAndView();
		String inspNo = request.getParameter("inspNo");
		
		view.addObject("inspNo", inspNo);
		view.setViewName("/content/asset/insp/manage/inspManageProgress");
		return view;
	}
	
	/** 실사관리 진행상황조회 */
	@GetMapping("/selectInspProgressList")
	@ResponseBody
	@ApiOperation(value = "실사관리 자산리스트 조회", notes = "실사관리 자산리스트 조회")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> selectInspProgressList(
			@ApiParam(value = "검색 조건 파리미터 Dto", required = true) @RequestParam Map<String, Object> params,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail) { 
		log.info("[controller][selectInspTargetList]");
		
		
		SearchCondition condition = new SearchCondition(params.get("currentPage").toString(), params.get("numOfRows").toString(), params);
		condition.pageSetup1(inspManageService.selectCountInspProgressList(condition));
		
		// 실사 <> 자산 정보 리스트 조회 
		List<InspMasterDto> list = inspManageService.selectInspProgressList(condition);
		
		return responseService.success(condition, list);
		
	}
	
	/** 실사관리화면 실사상태 수정 */
	@PostMapping(value="/updateInspStus")
	@ResponseBody
	@ApiOperation(value = "실사관리화면 실사상태 수정", notes = "실사관리화면 실사상태 수정")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> updateInspStus(
			@ApiParam(value = "자산정보 수정 데이터", required = true) @RequestBody Map<String, Object> items,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail) { 
		log.info("[controller][updateInspStus]");
		UserInfo userInfo = (UserInfo)userDetail;
		String userId = userInfo.getDto().getUserId();
		items.put("userId", userId);
		
		inspManageService.updateInspStus(items);
		
		return responseService.success(null);
	}
	
}
