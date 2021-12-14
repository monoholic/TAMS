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
	
	/* 공통코드 관리 화면 */
	@PostMapping("/codeMng")
	public ModelAndView codeMngView(HttpServletRequest request) {
		
		ModelAndView view = new ModelAndView();
		view.addObject("menuId", request.getParameter("menuId"));
		view.addObject("menuNm", request.getParameter("menuNm"));
		view.addObject("menuDesc", request.getParameter("menuDesc"));
		view.addObject("url", request.getParameter("url"));
		
		view.setViewName("/content/standard/code/codeMng");
		    
		return view;
	}
	
	/* 공통코드 관리 화면 : 공통코드 그룹 조회 */
	@PostMapping("/codeGrpList")
	@ResponseBody
	public ResponseEntity<? extends Response> menulist(String str) { 
		
		List<CodeDto> list = codeService.selectCodeGrpList("");
		return responseService.success(list);
	}
	
	/* 공통코드 관리 화면 : 조회 */
	@GetMapping(value="/codeMng/codeList")
	@ResponseBody
	@ApiOperation(value = "공통코드 관리 화면 : 조회", notes = "공통코드 관리 화면 : 조회")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> codeMngList(
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
		int total = codeService.selectCountCode(condition);
		condition.pageSetup(total);
		
		List<CodeDto> list = codeService.selectCodeList(condition);
		return responseService.success(condition, list);
	}
	
	/* 공통코드 관리 화면 : 등록 */  
	@GetMapping(value="/codeMng/codeInsert")
	@ResponseBody
	@ApiOperation(value = "공통코드 관리 화면 : 등록", notes = "공통코드 관리 화면 : 등록")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public String codeMngInsert(
			@ApiParam(value = "공통코드 ID", required = true) @RequestParam(value = "codeId", required = true) String codeId,
			@ApiParam(value = "공통코드 그룹ID", required = true) @RequestParam(value = "codeGrpId", required = true) String codeGrpId,
			@ApiParam(value = "공통코드명", required = true) @RequestParam(value = "codeNm", required = true) String codeNm,
			@ApiParam(value = "공통코드 영문명", required = true) @RequestParam(value = "codeEngNm", required = true) String codeEngNm,
			@ApiParam(value = "상위 공통코드 ID", required = true) @RequestParam(value = "uppCodeId", required = true) String uppCodeId,
			@ApiParam(value = "코드 레벨", required = true) @RequestParam(value = "codeLvl", required = true) String codeLvl,
			@ApiParam(value = "공통코드 설명", required = true) @RequestParam(value = "codeDesc", required = true) String codeDesc,
			@ApiParam(value = "정렬 순서", required = true) @RequestParam(value = "sortOdr", required = true) String sortOdr,
			@ApiParam(value = "예비1", required = true) @RequestParam(value = "resv1", required = true) String resv1,
			@ApiParam(value = "예비2", required = true) @RequestParam(value = "resv2", required = true) String resv2,
			@ApiParam(value = "예비3", required = true) @RequestParam(value = "resv3", required = true) String resv3,
			@ApiParam(value = "사용 여부", required = true) @RequestParam(value = "useYn", required = true) String useYn,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail
			) { 
		
		String code = "202";
		
		UserInfo userInfo = (UserInfo)userDetail;
		String userId = userInfo.getDto().getUserId();
		
		CodeDto dto = new CodeDto();
		
		if (StringUtils.isNotEmpty(codeId)) 
			dto.setCodeId(codeId);
		
	    if (StringUtils.isNotEmpty(codeGrpId)) 
	    	dto.setCodeGrpId(codeGrpId);
	    
	    if (StringUtils.isNotEmpty(codeNm)) 
	    	dto.setCodeNm(codeNm);
	    
	    dto.setCodeEngNm(codeEngNm);
	    dto.setUppCodeId(uppCodeId);
	    dto.setCodeLvl(codeLvl);
	    dto.setCodeDesc(codeDesc);
	    dto.setSortOdr(sortOdr);
	    dto.setResv1(resv1);
	    dto.setResv2(resv2);
	    dto.setResv3(resv3);
	    dto.setUseYn(useYn);
		dto.setRegr(userId);
		 
		int cnt = codeService.codeMngInsert(dto); 
		
		if( cnt > 0)
			code = "200";
		
		return code;
	}
	
	/* 공통코드 관리 화면 : 수정 */
	@GetMapping(value="/codeMng/codeUpdate")
	@ResponseBody
	@ApiOperation(value = "공통코드 관리 화면 : 수정", notes = "공통코드 관리 화면 : 수정")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public String codegrpMngUpdate( 
			@ApiParam(value = "공통코드 ID", required = true) @RequestParam(value = "codeId", required = true) String codeId,
			@ApiParam(value = "공통코드 그룹ID", required = true) @RequestParam(value = "codeGrpId", required = true) String codeGrpId,
			@ApiParam(value = "공통코드명", required = true) @RequestParam(value = "codeNm", required = true) String codeNm,
			@ApiParam(value = "공통코드 영문명", required = true) @RequestParam(value = "codeEngNm", required = true) String codeEngNm,
			@ApiParam(value = "상위 공통코드 ID", required = true) @RequestParam(value = "uppCodeId", required = true) String uppCodeId,
			@ApiParam(value = "코드 레벨", required = true) @RequestParam(value = "codeLvl", required = true) String codeLvl,
			@ApiParam(value = "공통코드 설명", required = true) @RequestParam(value = "codeDesc", required = true) String codeDesc,
			@ApiParam(value = "정렬 순서", required = true) @RequestParam(value = "sortOdr", required = true) String sortOdr,
			@ApiParam(value = "예비1", required = true) @RequestParam(value = "resv1", required = true) String resv1,
			@ApiParam(value = "예비2", required = true) @RequestParam(value = "resv2", required = true) String resv2,
			@ApiParam(value = "예비3", required = true) @RequestParam(value = "resv3", required = true) String resv3,
			@ApiParam(value = "사용 여부", required = true) @RequestParam(value = "useYn", required = true) String useYn,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail
			) { 
		
		String code = "202";
		
		UserInfo userInfo = (UserInfo)userDetail;
		String userId = userInfo.getDto().getUserId();
		
		CodeDto dto = new CodeDto();
		
		if (StringUtils.isNotEmpty(codeId)) 
			dto.setCodeId(codeId);
		
	    if (StringUtils.isNotEmpty(codeGrpId)) 
	    	dto.setCodeGrpId(codeGrpId);
	    
	    if (StringUtils.isNotEmpty(codeNm)) 
	    	dto.setCodeNm(codeNm);
	    
	    dto.setCodeEngNm(codeEngNm);
	    dto.setUppCodeId(uppCodeId);
	    dto.setCodeLvl(codeLvl);
	    dto.setCodeDesc(codeDesc);
	    dto.setSortOdr(sortOdr);
	    dto.setResv1(resv1);
	    dto.setResv2(resv2);
	    dto.setResv3(resv3);
	    dto.setUseYn(useYn);
		dto.setUpdr(userId);
		
		int cnt = codeService.codeMngUpdate(dto);
		if( cnt > 0) code = "200";
		
		return code;
	}
	
	/* 공통코드 화면 : 삭제 */
	@PostMapping(value="/codeMng/codeDelete")
	@ResponseBody
	@ApiOperation(value = "공통코드 화면 : 삭제", notes = "공통코드 화면 : 삭제")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public String codegrpMngDelete( 
			@ApiParam(value = "공통코드 ID", required = true) @RequestBody(required = true) List<CodeDto> items
			) { 
		
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
