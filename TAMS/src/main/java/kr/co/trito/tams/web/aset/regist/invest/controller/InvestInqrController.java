package kr.co.trito.tams.web.aset.regist.invest.controller;

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
import kr.co.trito.tams.web.aset.regist.invest.dto.InvestInqrDto;
import kr.co.trito.tams.web.aset.regist.invest.service.InvestInqrService;
import kr.co.trito.tams.web.standard.invest.dto.InvestDto;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/aset/regist/invest")
public class InvestInqrController {
	@Autowired
	ResponseService responseService;
	
	@Autowired
	InvestInqrService asetinqrService;
	
	/** 투자정보관리 화면 */
	@PostMapping("/investInqr")
	public ModelAndView asetinqrView(HttpServletRequest request) {
	
		ModelAndView view = new ModelAndView();
		view.addObject("menuId", request.getParameter("menuId"));
		view.addObject("menuNm", request.getParameter("menuNm"));
		view.addObject("menuDesc", request.getParameter("menuDesc"));
		view.addObject("url", request.getParameter("url"));
		
		view.setViewName("/content/aset/regist/invest/investInqr");
		
		return view;
	}
	
	/** 투자정보관리 화면 : 조회 */
	@GetMapping(value="/investInqr/investInqrList")
	@ResponseBody
	@ApiOperation(value = "Web API Menu Mgr test", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> asetinqrList(
				@ApiParam(value = "조회 페이지 번호", required = true) @RequestParam(value = "currentPage",required = true) String currentPage,
				@ApiParam(value = "페이지별 조회 출력수", required = true) @RequestParam(value = "numOfRows", required = true) String numOfRows,
				@ApiParam(value = "검색 조건(투자번호, 투자명, 품명, 부서, 담당자)", required = false) @RequestParam(value = "searchType", required = false) String searchType,
				@ApiParam(value = "검색 값", required = false) @RequestParam(value = "searchText", required = false) String searchText,
				@ApiParam(value = "검색 조건(투자번호, 투자명, 품명, 부서, 담당자)", required = false) @RequestParam(value = "searchType2", required = false) String searchType2,
				@ApiParam(value = "검색 값", required = false) @RequestParam(value = "searchText2", required = false) String searchText2,
				@ApiParam(value = "검색항목 선택(메뉴ID, 메뉴명)", required = false) @RequestParam(value = "searchOption",required = false) String searchOption,
				@ApiParam(value = "사용 여부", required = false) @RequestParam(value = "useYnOption",required = false) String useYnOption,
				@ApiParam(value = "정렬 필드", required = false) @RequestParam(value = "sortField", required = false) String sortField,
				@ApiParam(value = "정렬 타입", required = false) @RequestParam(value = "sortOrder", required = false) String sortOrder
            ) { 

		if (StringUtils.isEmpty(currentPage) || StringUtils.isEmpty(numOfRows))
		throw new IllegalArgumentException("Request parameter error.");
		
		Map<String, Object> params = new HashMap<>();
		
		if (!StringUtils.isEmpty(searchType))
		params.put("searchType", searchType);
		
		if (!StringUtils.isEmpty(searchText))
		params.put("searchText", searchText);
		
		if (!StringUtils.isEmpty(searchType2))
		params.put("searchType2", searchType2);
		
		if (!StringUtils.isEmpty(searchText2))
		params.put("searchText2", searchText2);
		
		if (!StringUtils.isEmpty(searchOption))
		params.put("searchOption", searchOption);
		
		if (!StringUtils.isEmpty(useYnOption))
		params.put("useYnOption", useYnOption);
		
		if (!StringUtils.isEmpty(sortField))
		params.put("sortField", sortField);
		
		if (!StringUtils.isEmpty(sortOrder))
		params.put("sortOrder", sortOrder);
		
		SearchCondition condition = new SearchCondition(currentPage, numOfRows, params);
		int total = asetinqrService.selectCountInvest(condition);
		condition.pageSetup(total);
		
		List<InvestInqrDto> list = null; //asetinqrService.selectInvestList(condition);
		return responseService.success(condition, list);
	}
}
