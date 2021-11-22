package kr.co.trito.tams.web.system.bbs.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import kr.co.trito.tams.web.standard.codegrp.dto.CodegrpDto;
import kr.co.trito.tams.web.system.bbs.dto.BbsDto;
import kr.co.trito.tams.web.system.bbs.service.BbsService;

@Controller
@RequestMapping("/system/bbs")
public class BbsController {
	@Autowired
	ResponseService responseService;
	
	@Autowired
	BbsService bbsService;

	/* 게시판 관리 화면 */
	@PostMapping("/bbsMng")
	public ModelAndView bbsMngView(HttpServletRequest request) {
		
		ModelAndView view = new ModelAndView();
		view.addObject("menuId", request.getParameter("menuId"));
		view.addObject("menuNm", request.getParameter("menuNm"));
		view.addObject("menuDesc", request.getParameter("menuDesc"));
		view.addObject("url", request.getParameter("url"));
		
		view.setViewName("/content/system/bbs/bbsMng");
		    
		return view;
	}
	
	/* 게시판 화면 */
	@PostMapping("/bbsList")
	public ModelAndView bbsMngList(HttpServletRequest request) {
		
		ModelAndView view = new ModelAndView();
		view.addObject("menuId", request.getParameter("menuId"));
		view.addObject("menuNm", request.getParameter("menuNm"));
		view.addObject("menuDesc", request.getParameter("menuDesc"));
		view.addObject("url", request.getParameter("url"));
		
		view.setViewName("/content/system/bbs/bbsList");
		    
		return view;
	}
	
	/* 게시판 상세 화면 */
	@PostMapping("/detailBbs")
	public ModelAndView detailBbs(HttpServletRequest request) {
		
		ModelAndView view = new ModelAndView();

		view.addObject("bbsId", request.getParameter("bbsId"));
		view.addObject("userId", request.getParameter("userId"));
		view.addObject("bbsGrpId", request.getParameter("bbsGrpId"));
		view.addObject("bbsDp", request.getParameter("bbsDp"));
		view.addObject("bbsTtl", request.getParameter("bbsTtl"));
		view.addObject("bbsCn", request.getParameter("bbsCn"));
		view.addObject("viewCnt", request.getParameter("viewCnt"));
		view.addObject("delYn", request.getParameter("delYn"));
		view.addObject("regDt", request.getParameter("regDt"));
		
		BbsDto dto = new BbsDto();
		
		String bbsId = request.getParameter("bbsId");
		dto.setBbsId(bbsId);
		bbsService.updateViewCnt(dto);
		
		view.setViewName("/content/system/bbs/detailBbs");
		return view;
	}
	
	/* 공통코드 관리 화면 : 공통코드 그룹 조회 */
	@PostMapping("/bbsMng/codeList")
	@ResponseBody
	public ResponseEntity<? extends Response> bbsCodeList(String str) { 
		
		List<CodeDto> list = bbsService.selectBbsCodeList("");
		return responseService.success(list);
	}
	
	/* 게시판 관리 화면 : 조회 */
	@GetMapping(value="/bbsMng/bbsMngList")
	@ResponseBody
	@ApiOperation(value = "Web API Menu Mgr test", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> bbsMngList(
				@ApiParam(value = "조회 페이지 번호", required = true) @RequestParam(value = "currentPage",required = true) String currentPage,
				@ApiParam(value = "페이지별 조회 출력수", required = true) @RequestParam(value = "numOfRows", required = true) String numOfRows,
				@ApiParam(value = "검색 조건", required = false) @RequestParam(value = "searchText", required = false) String searchText,
				@ApiParam(value = "검색항목 선택", required = false) @RequestParam(value = "searchOption",required = false) String searchOption,
				@ApiParam(value = "삭제 구분", required = false) @RequestParam(value = "useYnOption",required = false) String delYnOption,
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
		
		if (!StringUtils.isEmpty(delYnOption))
		params.put("delYnOption", delYnOption);
		
		if (!StringUtils.isEmpty(sortField))
		params.put("sortField", sortField);
		
		if (!StringUtils.isEmpty(sortOrder))
		params.put("sortOrder", sortOrder);
		
		SearchCondition condition = new SearchCondition(currentPage, numOfRows, params);
		int total = bbsService.selectCountBbsMng(condition);
		condition.pageSetup(total);
	    
		List<BbsDto> list = bbsService.selectBbsMngList(condition);
		return responseService.success(condition, list);
	}
	
	/* 게시판 관리 화면 : 등록 */  
	@GetMapping(value="/bbsMng/bbsInsert")
	@ResponseBody
	@ApiOperation(value = "Web API Menu Mgr Insert", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public String bbsMngInsert(
			@ApiParam(value = "게시글 그룹", required = true) @RequestParam(value = "bbsGrpId", required = true) String bbsGrpId,
			@ApiParam(value = "게시글 레벨", required = true) @RequestParam(value = "bbsDp", required = true) String bbsDp,
			@ApiParam(value = "게시글 제목", required = true) @RequestParam(value = "bbsTtl", required = true) String bbsTtl,
			@ApiParam(value = "게시글 내용", required = true) @RequestParam(value = "bbsCn", required = true) String bbsCn,
			@ApiParam(value = "삭제 구분", required = true) @RequestParam(value = "delYn", required = true) String delYn
			) { 
		
		String code = "202";
		
		BbsDto dto = new BbsDto();
		
		dto.setUserId("SuperMan");  //임시
		dto.setBbsGrpId(bbsGrpId);
		dto.setBbsDp(bbsDp);
		dto.setBbsTtl(bbsTtl);
		dto.setBbsCn(bbsCn);
		dto.setDelYn(delYn);
		dto.setViewCnt("0");
		dto.setRegr("system"); 		//임시
		 
		int cnt = bbsService.bbsMngInsert(dto);  
		
		if( cnt > 0 )
			code = "200";
		
		return code;
	}
	
	/* 공통코드 관리 화면 : 수정 */
	@GetMapping(value="/bbsMng/bbsUpdate")
	@ResponseBody
	@ApiOperation(value = "Web API Menu Mgr Update", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public String bbsMngUpdate( 
			@ApiParam(value = "게시판 ID", required = true) @RequestParam(value = "bbsId", required = true) String bbsId,
			@ApiParam(value = "게시판 그룹", required = true) @RequestParam(value = "bbsGrpId", required = true) String bbsGrpId,
			@ApiParam(value = "게시판 레벨", required = true) @RequestParam(value = "bbsDp", required = true) String bbsDp,
			@ApiParam(value = "게시판 제목", required = true) @RequestParam(value = "bbsTtl", required = true) String bbsTtl,
			@ApiParam(value = "게시판 내용", required = true) @RequestParam(value = "bbsCn", required = true) String bbsCn,
			@ApiParam(value = "삭제 구분", required = true) @RequestParam(value = "delYn", required = true) String delYn
			) { 
		String code = "202";
		
		BbsDto dto = new BbsDto();
		
		dto.setBbsId(bbsId);
		dto.setBbsGrpId(bbsGrpId);
		dto.setBbsDp(bbsDp);
		dto.setBbsTtl(bbsTtl);
		dto.setBbsCn(bbsCn);
		dto.setDelYn(delYn);
		dto.setUpdr("system"); 		//임시
			
		int cnt = bbsService.bbsMngUpdate(dto);
		if( cnt > 0) code = "200";
		
		return code;
	}
	
	/* 공통코드 그룹 화면 : 삭제 */
	@GetMapping(value="/bbsMng/bbsDelete")
	@ResponseBody
	@ApiOperation(value = "Web API Menu Mgr Delete", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public String bbsMngDelete( 
			@ApiParam(value = "공통코드 그룹ID", required = true) @RequestParam(value = "items", required = true) List<String> items
			) { 
		
		String code = "202";
		
		int cnt = 0;
		
		for(String bbsId : items) {
			BbsDto dto = new BbsDto();
			
			dto.setBbsId(bbsId);
			
			if( bbsService.bbsMngDelete(dto) > 0 ) 
				cnt++;
		}
		
		if(cnt > 0) 
			code = "200";
		
		return code;
	}
	
	/* 게시판 화면 : 조회 */
	@GetMapping(value="/bbsMng/bbsList")
	@ResponseBody
	@ApiOperation(value = "Web API Menu Mgr test", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> bbsList(
				@ApiParam(value = "조회 페이지 번호", required = true) @RequestParam(value = "currentPage",required = true) String currentPage,
				@ApiParam(value = "페이지별 조회 출력수", required = true) @RequestParam(value = "numOfRows", required = true) String numOfRows,
				@ApiParam(value = "검색 조건", required = false) @RequestParam(value = "searchText", required = false) String searchText,
				@ApiParam(value = "검색항목 선택", required = false) @RequestParam(value = "searchOption",required = false) String searchOption,
				@ApiParam(value = "삭제 구분", required = false) @RequestParam(value = "useYnOption",required = false) String delYnOption,
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
		
		if (!StringUtils.isEmpty(delYnOption))
		params.put("delYnOption", delYnOption);
		
		if (!StringUtils.isEmpty(sortField))
		params.put("sortField", sortField);
		
		if (!StringUtils.isEmpty(sortOrder))
		params.put("sortOrder", sortOrder);
		
		SearchCondition condition = new SearchCondition(currentPage, numOfRows, params);
		int total = bbsService.selectCountBbs(condition);
		condition.pageSetup(total);
	    
		List<BbsDto> list = bbsService.selectBbsList(condition);
		return responseService.success(condition, list);
	}
}
