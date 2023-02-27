package kr.co.trito.tams.web.standard.tag.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
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
import kr.co.trito.tams.web.common.service.CommonService;
import kr.co.trito.tams.web.standard.tag.dto.IssueTagDto;
import kr.co.trito.tams.web.standard.tag.service.IssueTagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@RequestMapping("/standard/tag")
@Slf4j
public class IssueTagController {	
	@Autowired
	IssueTagService issueTagService; 
	
	@Autowired
	ResponseService responseService;
	
	@Autowired
	CommonService commonService;
	
	/** 실사대상자산 화면 */
	@PostMapping("list")
	public ModelAndView issueTagView(HttpServletRequest request, 
			@ApiParam(value = "필터 / 페이징 값", required = true) @RequestParam Map<String, Object> params) {
		
		log.info("[controller][issueTagView]");
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
		
		view.setViewName("/content/standard/tag/issueTag");
		return view;
	}		
	
	/** 실사대상자산 조회 */
	@PostMapping(value="/selectIssueTagList")
	@ResponseBody
	@ApiOperation(value = "태그발행 조회", notes = "태그발행 조회")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> selectIssueTagList(
			@ApiParam(value = "검색 조건 파리미터 Dto", required = true) @RequestBody Map<String, Object> items,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail) {

		log.info("[controller][selectInspSearchList]");
		
		SearchCondition condition = new SearchCondition(items.get("currentPage").toString(), items.get("numOfRows").toString(), items);
		condition.pageSetup1(issueTagService.selectCountIssueTagList(condition));
		List<IssueTagDto> list = issueTagService.selectIssueTagList(condition);
		
		return responseService.success(condition, list);
	}	
	
}
