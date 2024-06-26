package kr.co.trito.tams.web.asset.insp.search.controller;

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
import kr.co.trito.tams.web.asset.insp.search.dto.InspSearchMasterDto;
import kr.co.trito.tams.web.asset.insp.search.service.InspSearchService;
import kr.co.trito.tams.web.common.service.CommonService;
import kr.co.trito.tams.web.user.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@RequestMapping("/asset/insp/search")
@Slf4j
public class InspSearchController {	
	@Autowired
	InspSearchService inspSearchService; 
	
	@Autowired
	ResponseService responseService;
	
	@Autowired
	CommonService commonService;
	
	/** 실사대상자산 화면 */
	@PostMapping("list")
	public ModelAndView inspSearchView(HttpServletRequest request, 
			@ApiParam(value = "필터 / 페이징 값", required = true) @RequestParam Map<String, Object> params) {
		
		log.info("[controller][inspSearchView]");
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
		
		view.setViewName("/content/asset/insp/search/InspSearch");
		return view;
	}		
	
	/** 실사대상자산 조회 */
	@PostMapping(value="/selectInspSearchList")
	@ResponseBody
	@ApiOperation(value = "실사대상자산 조회", notes = "실사대상자산 조회")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> selectInspSearchList(
			@ApiParam(value = "검색 조건 파리미터 Dto", required = true) @RequestBody Map<String, Object> items,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail) {

		log.info("[controller][selectInspSearchList]");
		
		List<InspSearchMasterDto> mastList = inspSearchService.selectInspMasterSearchList(items);
		
		SearchCondition condition = new SearchCondition(items.get("currentPage").toString(), items.get("numOfRows").toString(), items);
		condition.pageSetup1(inspSearchService.selectCountInspSearchList(condition));
		List<InspSearchMasterDto> list = inspSearchService.selectInspSearchList(condition);
		
		return responseService.success(condition, list, mastList);
	}	
	
	/** 실사대상조회화면 최근 실사연도 조회 */
	@PostMapping(value="/selectRecentInspYear")
	@ResponseBody
	@ApiOperation(value = "실사대상조회화면 최근 실사연도 조회", notes = "실사대상조회화면 최근 실사연도 조회")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> selectRecentInspYear(
			@ApiParam(value = "자산정보 데이터", required = true) @RequestBody Map<String, Object> items,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail) { 
		
		log.info("[controller][selectInspSearchList]");
		UserInfo userInfo = (UserInfo)userDetail;
		String userId = userInfo.getDto().getUserId();
		
		items.put("userId", userId);
		
		List<InspSearchMasterDto> list = inspSearchService.selectRecentInspYear(items);
		
		return responseService.success(list);
	}
	
	/** 실사대상조회화면 실사명 조회 */
	@PostMapping(value="/selectInspNmList")
	@ResponseBody
	@ApiOperation(value = "실사관리화면 자산정보 수정", notes = "실사관리화면 자산정보 수정")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> selectInspNmList(
			@ApiParam(value = "자산정보 데이터", required = true) @RequestBody Map<String, Object> items,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail) { 
		
		log.info("[controller][selectInspSearchList]");
		UserInfo userInfo = (UserInfo)userDetail;
		String userId = userInfo.getDto().getUserId();
		
		items.put("regr", userId);
		
		List<InspSearchMasterDto> list = inspSearchService.selectInspNmList(items);
		
		return responseService.success(list);
	}
	
	/** 실사대상조회화면 실사부서명 조회 */
	@PostMapping(value="/selectInspDeptNmList")
	@ResponseBody
	@ApiOperation(value = "실사관리화면 자산정보 수정", notes = "실사관리화면 자산정보 수정")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> selectInspDeptNmList(
			@ApiParam(value = "자산정보 수정 데이터", required = true) @RequestBody Map<String, Object> items,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail) { 
		
		log.info("[controller][selectInspDeptNmList]");
		UserInfo userInfo = (UserInfo)userDetail;
		String userId = userInfo.getDto().getUserId();
		String deptCd = userInfo.getDto().getDeptCd();
		items.put("userId", userId);
		items.put("deptCd", deptCd);
		
		List<InspSearchMasterDto> list = inspSearchService.selectInspDeptNmList(items);
		
		return responseService.success(list);
	}
	
	
	/** 실사대상조회화면 자산정보 수정 */
	@PostMapping(value="/updateInspAsetList")
	@ResponseBody
	@ApiOperation(value = "실사대상조회화면 자산정보 수정", notes = "실사대상조회화면 자산정보 수정")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> updateInspAsetList(
			@ApiParam(value = "자산정보 수정 데이터", required = true) @RequestBody Map<String, Object> items,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail) { 
		
		UserInfo userInfo = (UserInfo)userDetail;
		String userId = userInfo.getDto().getUserId();
		
		items.put("regr", userId);
		
		int cnt = inspSearchService.updateInspAsetList(items);
		
		return responseService.success(null);
	}
}
