package kr.co.trito.tams.web.common.contoller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
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
import kr.co.trito.tams.comm.util.res.Response;
import kr.co.trito.tams.comm.util.res.ResponseService;
import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.common.dto.AppvDto;
import kr.co.trito.tams.web.common.dto.AppvLineDto;
import kr.co.trito.tams.web.common.service.ApprovalService;
import kr.co.trito.tams.web.user.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@Slf4j
@RequestMapping("/approval")
public class ApprovalController {
	
	@Autowired
	ApprovalService approvalService;
	
	@Autowired
	ResponseService responseService;	
	
	
	/** 결재상신 팝업 화면 */
	@PostMapping("/approvalMng")
	public ModelAndView approvalMngView(HttpServletRequest request) {
		
		ModelAndView view = new ModelAndView();	
		view.addObject("menuId", request.getParameter("menuId"));
		view.addObject("menuNm", request.getParameter("menuNm"));
		view.addObject("menuDesc", request.getParameter("menuDesc"));
		view.setViewName("/content/common/approvalMng");
		
		return view;
	}
	
	/** 결재목록 조회 */
	@PostMapping("/approvalList")
	@ResponseBody
	@ApiOperation(value = "Web API Approval ", notes = "Web API")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> selectAppvList(HttpServletRequest request
			                        , @ApiParam(value = "검색 조건", required = false) @RequestParam(value="searchText",required = false) String searchText
			                        , @ApiParam(value = "사용자 정보", required = true) @AuthenticationPrincipal UserDetails userDetail ) {
		
		Map<String, Object> params = new HashMap<>();
		if (!StringUtils.isEmpty(searchText))
		params.put("searchText", searchText);
		
		SearchCondition condition = new SearchCondition("0","0",params);
		List<AppvDto> list = approvalService.selectAppvList(condition);
		condition.pageSetup(list.size());
		
		return responseService.success(condition, list);
	}
	
	/** 결재상신 팝업 화면 */
	@GetMapping("/approvalPopup")
	public ModelAndView approvalPopupView(HttpServletRequest request) {
		
		ModelAndView view = new ModelAndView();
		String appv_id = request.getParameter("appv_id");
		view.addObject("appv_id", appv_id);
		view.setViewName("/content/common/popup/approvalPopup");
		return view;
	}
	
	/** 결재상신 팝업 화면 */
	@GetMapping("/approvalPopup2")
	@ResponseBody
	public ResponseEntity<? extends Response>  approvalPopupView2(HttpServletRequest request) {
		
		Map<String,Object> result = new HashMap<>();
		
		String appv_id = request.getParameter("appv_id");
		Map<String,String> map = new HashMap<>();
		map.put("appv_id", appv_id);
		
		AppvDto appvDto = approvalService.selectAppvInfo(map);
		log.error(appvDto.toString());
		List<AppvLineDto> appvLines = approvalService.selectAppvLine(map);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = null;
		try { 
			jsonStr = mapper.writeValueAsString(appvLines);
		} catch(Exception e) {}
		
		result.put("appvDto", appvDto);
		result.put("appvLines", jsonStr);
		
		return responseService.success(result);
	}
	
	/** 결재상신 저장 및 수정 */
	@PostMapping("/saveApproval")
	@ResponseBody
	@ApiOperation(value = "저장 및 결재상신", notes = "저장 및 결재 상신")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> saveApproval(
           @ApiParam(value = "결재상신 정보 (결재,합의,통보,본문)", required = true) @RequestBody(required=true) Map<String, Object> items,
           @ApiParam(value = "결재자 정보", required = true) @AuthenticationPrincipal UserDetails userDetail) {
		
		// 결재상신 사용자 ID
		UserInfo userDto = (UserInfo)userDetail;
		String userId = userDto.getDto().getUserId();
		items.put("appvUserId", userId);
		
		// 자산의뢰 결재 테이블 유무 확인.
		String stus = items.get("status").toString();
		String appvId = items.get("appvId").toString();
		String maxAppvId = approvalService.maxAppvId(items);
		
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
		System.out.println(items);
		System.out.println(maxAppvId);
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
		
		// 결재 상신 버튼
		if("F".equals(stus)) {
			items.put("appvStus", "ING");
			items.put("appvStep", "1");
			if(StringUtils.isEmpty(appvId)) {
				items.put("appvId", maxAppvId);
				approvalService.insertAppv(items);
				approvalService.insertReqAppv(items);
			}
			else {
				approvalService.updateAppv(items);
			}
		}
		// 저장 버튼
		else {
			items.put("appvStus", "TMP");
			items.put("appvStep", "1");
			if(StringUtils.isEmpty(appvId)) {
				items.put("appvId", maxAppvId);
				approvalService.insertAppv(items);
				approvalService.insertReqAppv(items);
			}
			else {
				approvalService.updateAppv(items);
			}
		}
		
		approvalService.AppvLineInfo(items);	// 결재라인 테이블 INSERT
		approvalService.updateAppvId(items);	// 자산의뢰마스터 테이블 UPDATE
		
		return responseService.success(null);
	}
	
	/** 결재상신 팝업 화면 */
	@PostMapping("/approvalDeptMng")
	public ModelAndView approvalDeptMngView(HttpServletRequest request) {
		
		ModelAndView view = new ModelAndView();	
		view.addObject("menuId", request.getParameter("menuId"));
		view.addObject("menuNm", request.getParameter("menuNm"));
		view.addObject("menuDesc", request.getParameter("menuDesc"));
		view.setViewName("/content/common/approvalDeptMng");
		
		return view;
	}
	
	/** 결재상신 팝업 화면 */
	@GetMapping("/approvalDeptPopup")
	public ModelAndView approvalDeptPopupView(HttpServletRequest request) {
		
		ModelAndView view = new ModelAndView();
		String appv_id = request.getParameter("appv_id");
		view.addObject("appv_id", appv_id);
		view.setViewName("/content/common/popup/approvalDeptPopup");
		return view;
	}
}

