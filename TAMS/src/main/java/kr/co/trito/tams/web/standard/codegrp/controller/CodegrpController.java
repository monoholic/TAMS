package kr.co.trito.tams.web.standard.codegrp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.SystemOutLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
import kr.co.trito.tams.web.standard.codegrp.dto.CodegrpDto;
import kr.co.trito.tams.web.standard.codegrp.service.CodegrpService;
import kr.co.trito.tams.web.user.dto.UserInfo;

@Controller
@RequestMapping("/standard/codegrp")
public class CodegrpController {
	
	@Autowired
	ResponseService responseService;
	
	@Autowired
	CodegrpService codegrpService;
	
	/* 공통코드 그룹 관리 화면 */
	@PostMapping("/codegrpMng")
	public ModelAndView codegrpMngView(HttpServletRequest request) {
		
		ModelAndView view = new ModelAndView();
		view.addObject("menuId", request.getParameter("menuId"));
		view.addObject("menuNm", request.getParameter("menuNm"));
		view.addObject("menuDesc", request.getParameter("menuDesc"));
		view.setViewName("/content/standard/codegrp/codegrpMng");
		    
		return view;
	}
	
	/* 공통코드 그룹 관리 화면 : 조회 */
	@GetMapping(value="/codegrpMng/codegrpList")
	@ResponseBody
	@ApiOperation(value = " 공통코드 그룹 관리 화면 : 조회", notes = " 공통코드 그룹 관리 화면 : 조회")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> codegrpMngList(
				@ApiParam(value = "조회 페이지 번호", required = true) @RequestParam(value = "currentPage",required = true) String currentPage,
				@ApiParam(value = "페이지별 조회 출력수", required = true) @RequestParam(value = "numOfRows", required = true) String numOfRows,
				@ApiParam(value = "검색 조건(메뉴ID, 메뉴명)", required = false) @RequestParam(value = "searchText", required = false) String searchText,
				@ApiParam(value = "검색항목 선택(메뉴ID, 메뉴명)", required = false) @RequestParam(value = "searchOption",required = false) String searchOption,
				@ApiParam(value = "사용 여부", required = false) @RequestParam(value = "useYnOption",required = false) String useYnOption,
				@ApiParam(value = "정렬 필드", required = false) @RequestParam(value = "sortField", required = false) String sortField,
				@ApiParam(value = "정렬 타입", required = false) @RequestParam(value = "sortOrder", required = false) String sortOrder
                              ) { 

		if (StringUtils.isEmpty(currentPage) || StringUtils.isEmpty(numOfRows))
		throw new IllegalArgumentException("Request parameter error.");
		
		Map<String, Object> params = new HashMap<>();
		
		if (!StringUtils.isEmpty(searchText))
		params.put("searchText", searchText);
		
		if (!StringUtils.isEmpty(searchOption))
		params.put("searchOption", searchOption);
		
		if (!StringUtils.isEmpty(useYnOption))
		params.put("useYnOption", useYnOption);
		
		if (!StringUtils.isEmpty(sortField))
		params.put("sortField", sortField);
		
		if (!StringUtils.isEmpty(sortOrder))
		params.put("sortOrder", sortOrder);
		
		SearchCondition condition = new SearchCondition(currentPage, numOfRows, params);
		int total = codegrpService.selectCountCodegrp(condition);
		condition.pageSetup(total);
		
		List<CodegrpDto> list = codegrpService.selectCodegrpList(condition);
		return responseService.success(condition, list);
	}
	
	/* 공통코드 그룹 관리 화면 : 등록 */ 
	@GetMapping(value="/codegrpMng/codegrpInsert")
	@ResponseBody
	@ApiOperation(value = "공통코드 그룹 관리 화면 : 등록", notes = "공통코드 그룹 관리 화면 : 등록")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public String codegrpMngInsert( 
			@ApiParam(value = "공통코드 그룹ID", required = true) @RequestParam(value = "codeGrpId", required = true) String codeGrpId,
			@ApiParam(value = "공통코드 그룹명", required = true) @RequestParam(value = "codeGrpNm", required = true) String codeGrpNm,
			@ApiParam(value = "공통코드 영문명", required = true) @RequestParam(value = "codeGrpEngNm", required = true) String codeGrpEngNm,
			@ApiParam(value = "공통코드 그룹 설명", required = true) @RequestParam(value = "codeGrpDesc", required = true) String codeGrpDesc,
			@ApiParam(value = "정렬순서", required = true) @RequestParam(value = "sortOdr", required = true) String sortOdr,
			@ApiParam(value = "사용여부", required = true) @RequestParam(value = "useYn", required = true) String useYn,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail
			) { 
		
		String code = "202";
		
		CodegrpDto dto = new CodegrpDto();
		
		if (StringUtils.isNotEmpty(codeGrpId)) dto.setCodeGrpId(codeGrpId);
	    if (StringUtils.isNotEmpty(codeGrpNm)) dto.setCodeGrpNm(codeGrpNm);
	    
	    UserInfo userInfo = (UserInfo)userDetail;
		String userId = userInfo.getDto().getUserId();
	    
	    dto.setCodeGrpId(codeGrpId);
	    dto.setCodeGrpNm(codeGrpNm);
	    dto.setCodeGrpEngNm(codeGrpEngNm);
	    dto.setCodeGrpDesc(codeGrpDesc);
	    dto.setSortOdr(sortOdr);
	    dto.setUseYn(useYn);
	    dto.setRegr(userId);
	    
	    int cnt = codegrpService.codegrpMngInsert(dto);
	    if( cnt > 0) code = "200";
		
		return code;
	}
	
	/* 공통코드 그룹 관리 화면 : 수정 */
	@GetMapping(value="/codegrpMng/codegrpUpdate")
	@ResponseBody
	@ApiOperation(value = "공통코드 그룹 관리 화면 : 수정", notes = "공통코드 그룹 관리 화면 : 수정")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public String codegrpMngUpdate( 
			@ApiParam(value = "공통코드 그룹ID", required = true) @RequestParam(value = "codeGrpId", required = true) String codeGrpId,
			@ApiParam(value = "공콩코드 그룹명", required = true) @RequestParam(value = "codeGrpNm", required = true) String codeGrpNm,
			@ApiParam(value = "공통코드 영문명", required = false) @RequestParam(value = "codeGrpEngNm", required = true) String codeGrpEngNm,
			@ApiParam(value = "공통코드 그룹 설명", required = false) @RequestParam(value = "codeGrpDesc", required = true) String codeGrpDesc,
			@ApiParam(value = "정렬순서", required = false) @RequestParam(value = "sortOdr", required = true) String sortOdr,
			@ApiParam(value = "사용구분", required = false) @RequestParam(value = "useYn", required = true) String useYn,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail
			) { 
		
		String code = "202";
		
		UserInfo userInfo = (UserInfo)userDetail;
		String userId = userInfo.getDto().getUserId();
		
		System.out.println(userId);
		
		CodegrpDto dto = new CodegrpDto();
		
		if (StringUtils.isNotEmpty(codeGrpId)) 
			dto.setCodeGrpId(codeGrpId);
		
		if (StringUtils.isNotEmpty(codeGrpNm)) 
			dto.setCodeGrpEngNm(codeGrpNm);
		
		dto.setCodeGrpId(codeGrpId);
		dto.setCodeGrpNm(codeGrpNm);
		dto.setCodeGrpEngNm(codeGrpEngNm);
		dto.setCodeGrpDesc(codeGrpDesc);
		dto.setSortOdr(sortOdr);
		dto.setUseYn(useYn);
		dto.setUpdr(userId);
		
		int cnt = codegrpService.codegrpMngUpdate(dto);
		if( cnt > 0) code = "200";
		
		return code;
	}
	
	/* 공통코드 그룹 화면 : 삭제 */
	@GetMapping(value="/codegrpMng/codegrpDelete")
	@ResponseBody
	@ApiOperation(value = "공통코드 그룹 화면 : 삭제", notes = "공통코드 그룹 화면 : 삭제")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public String codegrpMngDelete( 
			@ApiParam(value = "공통코드 그룹ID", required = true) @RequestParam(value = "items", required = true) List<String> items
			) { 
		
		String code = "202";
		
		int cnt = 0;
		
		for(String codeGrpId : items) {
			System.out.println("@@@@@@@@"+ codeGrpId);
			
			CodegrpDto dto = new CodegrpDto();
			
			dto.setCodeGrpId(codeGrpId);
			
			if( codegrpService.codegrpMngDelete(dto) > 0 ) 
				cnt++;
		}
		
		if(cnt > 0) 
			code = "200";
		
		return code;
	}
}
