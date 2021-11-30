package kr.co.trito.tams.web.common.contoller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kr.co.trito.tams.comm.util.res.ResponseService;
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
	@GetMapping("/approvalPopup")
	public ModelAndView approvalPopupView(HttpServletRequest request) {
		
		ModelAndView view = new ModelAndView();
		
		view.addObject("asset_no", request.getParameter("asset_no"));
		
		view.setViewName("/content/common/popup/approvalPopup");
		
		return view;
	}
	
	
	/** 결재상신 저장 */
	@SuppressWarnings("unchecked")
	@PostMapping("/saveApproval")
	@ResponseBody
	@ApiOperation(value = "Web API Approval", notes = "Web API")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public String saveApproval(HttpServletRequest request
			                       , @ApiParam(value = "결재상신 정보 (결재,합의,통보,본문)", required = true) @RequestBody(required=true) String datas
			                       , @ApiParam(value = "결재자 정보", required = true) @AuthenticationPrincipal UserDetails userDetail  
			                        ) {
		String code = "202";
		
		
		Map<String,Object> map = new HashMap<>();
		
		//상신자
		UserInfo userDto = (UserInfo)userDetail;
		String userId = userDto.getDto().getUserId();
		
		JSONParser parser = new JSONParser(datas);
		
		
		String assetNo 		= null;         
		String status 		= null;           
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
			appvTtl = (String) obj.get("appvTtl");
			appvType = (String) obj.get("appvType");
			apprContent = (String) obj.get("apprContent");
			
			appr = (List<String>)obj.get("appr");
			agree = (List<String>)obj.get("agree");
			noti = (List<String>)obj.get("noti");
			
			log.error("assetNo : "+assetNo);
			log.error("appvTtl : "+appvTtl);
			log.error("content "+apprContent);
			log.error("appvType : " + appvType);
			log.error("userId : " + userId);
			
		}catch(ParseException pe) {}
		
			AppvDto appvDto = new AppvDto();
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
			
			map.put("appvDto"	, appvDto);
			map.put("appr"		, appr);
			map.put("agree"		, agree);
			map.put("noti"		, noti);

			approvalService.regAppvInfo(map);
		
		//if( insCnt > 0 && lineCnt > 0 ) code = "200";
		
		return code;
	}
	
	
}

