package kr.co.trito.tams.web.standard.code.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.json.JSONParser;
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
import kr.co.trito.tams.web.standard.code.dto.CodeDto;
import kr.co.trito.tams.web.standard.code.service.CodeService;
import kr.co.trito.tams.web.standard.codegrp.dto.CodegrpDto;
import kr.co.trito.tams.web.system.menu.dto.MenuDto;
import kr.co.trito.tams.web.user.dto.UserInfo;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/standard/code")
public class CodeController {
	@Autowired
	ResponseService responseService;
	
	@Autowired
	CodeService codeService;
	
	/** 공통코드관리 화면 */
	@PostMapping("/list")
	public ModelAndView codeMngView(HttpServletRequest request) {
		
		ModelAndView view = new ModelAndView();
		view.addObject("menuId", request.getParameter("menuId"));
		view.addObject("menuNm", request.getParameter("menuNm"));
		view.addObject("menuDesc", request.getParameter("menuDesc"));
		view.addObject("url", request.getParameter("url"));
		
		view.setViewName("/content/standard/code/codeMng");
		    
		return view;
	}
	
	/** 공통코드관리 화면 : 공통코드 그룹 조회 */
	@PostMapping("/codeGrpList")
	@ResponseBody
	public ResponseEntity<? extends Response> codeGrplist(String str) { 
		
		List<CodeDto> list = codeService.selectCodeGrpList("");
		return responseService.success(list);
	}
	
	/** 공통코드관리 화면 : 조회 */
	@GetMapping(value="/codeMng/codeList")
	@ResponseBody
	@ApiOperation(value = "공통코드 관리 화면 : 조회", notes = "공통코드 관리 화면 : 조회")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> codeMngList(
			@ApiParam(value = "검색 조건 파리미터 Dto", required = true) @RequestParam Map<String, Object> params) { 
		
		SearchCondition condition = new SearchCondition(params.get("currentPage").toString(), params.get("numOfRows").toString(), params);
		condition.pageSetup(codeService.selectCountCode(condition));
		List<CodeDto> list = codeService.selectCodeList(condition);
		return responseService.success(condition, list);
	}
	
	/** 공통코드관리 화면 : 등록 및 수정 */  
	@GetMapping(value="/codeMng/codeMerge")
	@ResponseBody
	@ApiOperation(value = "공통코드 관리 화면 : 등록 및 수정", notes = "공통코드 관리 화면 : 등록 및 수정")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public String codeMerge(
			@ApiParam(value = "공통코드관리 등록 및 수정 데이터", required = true) @RequestParam Map<String, Object> items,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail) { 
		
		String code = "202";
		
		UserInfo userInfo = (UserInfo)userDetail;
		String userId = userInfo.getDto().getUserId();
		
		items.put("regr", userId);
		items.put("updr", userId);
		 
		int cnt = codeService.codeMngMerge(items); 
		
		if( cnt > 0) code = "200";
		
		return code;
	}
	
	/** 공통코드 화면 : 삭제 */
	@PostMapping(value="/codeMng/codeDelete")
	@ResponseBody
	@ApiOperation(value = "공통코드 화면 : 삭제", notes = "공통코드 화면 : 삭제")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public String codegrpMngDelete( 
			@ApiParam(value = "공통코드 ID", required = true) @RequestBody(required = true) List<CodeDto> items) { 
		
		String code = "202";
		
		int cnt = 0;
		
		for(CodeDto item : items) {
			if( codeService.codeMngDelete(item) > 0 ) 
				cnt++;
		}
		
		if(cnt > 0) code = "200";
		
		return code;
	}
}
