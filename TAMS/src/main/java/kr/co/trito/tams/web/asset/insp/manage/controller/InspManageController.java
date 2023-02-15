package kr.co.trito.tams.web.asset.insp.manage.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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
	
	/** 실사관리화면 */
	@PostMapping("list")
	public ModelAndView inspManageView(HttpServletRequest request, @ApiParam(value = "필터 / 페이징 값", required = true) @RequestParam Map<String, Object> params) {
		
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

		log.info("[selectInspManageList]");
		
		SearchCondition condition = new SearchCondition(params.get("currentPage").toString(), params.get("numOfRows").toString(), params);
		condition.pageSetup(inspManageService.selectCountInspManageList(condition));
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
	@GetMapping(value="/deleteInspList")
	@ResponseBody
	@ApiOperation(value = "자산정보수정 화면 : 삭제", notes = "자산정보수정 화면 : 삭제")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> deleteInspList( 
			@ApiParam(value = "실사 번호", required = true) @RequestParam(value = "items", required = true) List<String> items) { 
		
		log.info("[controller][deleteInspList]");
		InspMasterDto dto = new InspMasterDto();

		for(String inspNo : items) {
			dto.setInspNo(inspNo);
			inspManageService.deleteInspList(dto);
		}
		
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
		ModelAndView view = new ModelAndView();
		String reqNo = request.getParameter("reqNo");
		
		view.addObject("reqNo", reqNo);
		view.setViewName("/content/asset/insp/manage/inspManageDetail");
		return view;
	}
	
}
