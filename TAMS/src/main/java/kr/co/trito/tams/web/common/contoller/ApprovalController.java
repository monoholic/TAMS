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
	
	/** 결재상신 저장 
	@SuppressWarnings("unchecked")
	@PostMapping("/saveApproval")
	@ResponseBody
	@ApiOperation(value = "Web API Approval", notes = "Web API")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public String saveApproval(HttpServletRequest request
           , @ApiParam(value = "결재상신 정보 (결재,합의,통보,본문)", required = true) @RequestBody(required=true) String datas
           , @ApiParam(value = "결재자 정보", required = true) @AuthenticationPrincipal UserDetails userDetail  ) {
		
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		System.out.println(datas);
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		
		Map<String,Object> map = new HashMap<>();
		
		//상신자
		UserInfo userDto = (UserInfo)userDetail;
		String userId = userDto.getDto().getUserId();
		
		JSONParser parser = new JSONParser(datas);
		
		String assetNo 		= null;         
		String status 		= null;           
		String appvId 		= null;         
		String appvTtl 		= null;         
		String appvType 	= null;       
		String apprContent 	= null; 
		                                                      
		List<String> appr 	= null;    
		List<String> agree 	= null;  
		List<String> noti 	= null;
		
		try {
			
			LinkedHashMap<?,?> obj = (LinkedHashMap<?,?>)parser.parse();
			
			assetNo = (String) obj.get("assetNo");
			status = (String) obj.get("status");
			appvId = (String) obj.get("appvId");
			appvTtl = (String) obj.get("appvTtl");
			appvType = (String) obj.get("appvType");
			apprContent = (String) obj.get("apprContent");
			
			appr = (List<String>)obj.get("appr");
			agree = (List<String>)obj.get("agree");
			noti = (List<String>)obj.get("noti");
			
		}catch(ParseException pe) {}
		
			AppvDto appvDto = new AppvDto();
			appvDto.setAppvId(appvId);
			appvDto.setAppvTtl(appvTtl);
			appvDto.setAppvTtl(appvTtl);
			appvDto.setAppvCn(apprContent);
			appvDto.setAppvType(appvType);
			appvDto.setAppvUserId(userId);
			
			if( "F".equals(status) ) {
				appvDto.setAppvStus("ING");
			} else {
				appvDto.setAppvStus("TMP");
			}
			appvDto.setAppvStep("SUBMIT"); //기안
			appvDto.setRegr(userId);
			
			log.error("======================================================");
			log.error(appvDto.toString());
			log.error("======================================================");
			
			map.put("appvDto"	, appvDto);
			map.put("appr"		, appr);
			map.put("agree"		, agree);
			map.put("noti"		, noti);

		String rst = approvalService.regAppvInfo(map, status);
		
		return rst;
	}*/
	
	/** 결재상신 저장 */
	@SuppressWarnings("unchecked")
	@PostMapping("/saveApproval")
	@ResponseBody
	@ApiOperation(value = "저장 및 결재상신", notes = "저장 및 결재 상신")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> saveApproval(
           @ApiParam(value = "결재상신 정보 (결재,합의,통보,본문)", required = true) @RequestBody(required=true) Map<String, Object> items,
           @ApiParam(value = "결재자 정보", required = true) @AuthenticationPrincipal UserDetails userDetail) {
		
		//상신자
		UserInfo userDto = (UserInfo)userDetail;
		String userId = userDto.getDto().getUserId();
		
		// tb_req_appv 테이블에서 자산의뢰 번호가 있는지 확인.
		// 있다면, appv_doc_id 값을 가지고 업데이트
		// 없다면, appv_doc_id 만들기 → 임시저장임
		
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
		System.out.println(items);
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
		// 자산의뢰 결재 테이블 유무 확인.
		int reqNoChk = approvalService.reqNoCheck(items);
		
		if(reqNoChk > 0) {
			
		}
		else {
			
		}
			
		
		/*
		try {
			
			LinkedHashMap<?,?> obj = (LinkedHashMap<?,?>)parser.parse();
			
			assetNo = (String) obj.get("assetNo");
			status = (String) obj.get("status");
			appvId = (String) obj.get("appvId");
			appvTtl = (String) obj.get("appvTtl");
			appvType = (String) obj.get("appvType");
			apprContent = (String) obj.get("apprContent");
			
			appr = (List<String>)obj.get("appr");
			agree = (List<String>)obj.get("agree");
			noti = (List<String>)obj.get("noti");
			
		}catch(ParseException pe) {}
		
			AppvDto appvDto = new AppvDto();
			appvDto.setAppvId(appvId);
			appvDto.setAppvTtl(appvTtl);
			appvDto.setAppvTtl(appvTtl);
			appvDto.setAppvCn(apprContent);
			appvDto.setAppvType(appvType);
			appvDto.setAppvUserId(userId);
			
			if( "F".equals(status) ) {
				appvDto.setAppvStus("ING");
			} else {
				appvDto.setAppvStus("TMP");
			}
			appvDto.setAppvStep("SUBMIT"); //기안
			appvDto.setRegr(userId);
			
			log.error("======================================================");
			log.error(appvDto.toString());
			log.error("======================================================");
			
			map.put("appvDto"	, appvDto);
			map.put("appr"		, appr);
			map.put("agree"		, agree);
			map.put("noti"		, noti);

		String rst = approvalService.regAppvInfo(map, status);
		
		return rst;
		*/
		return responseService.success(null);
	}
}

