package kr.co.trito.tams.web.standard.invest.controller;

import java.util.HashMap;
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
import kr.co.trito.tams.web.common.dto.ComCodeDto;
import kr.co.trito.tams.web.standard.invest.dto.InvestDto;
import kr.co.trito.tams.web.standard.invest.service.InvestService;
import kr.co.trito.tams.web.user.dto.UserInfo;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/standard/invest")
public class InvestController {
	@Autowired
	ResponseService responseService;
	
	@Autowired
	InvestService investService;
	
	/** 투자정보관리 화면 */
	@PostMapping("/investMng")
	public ModelAndView investMngView(HttpServletRequest request) {
	
		ModelAndView view = new ModelAndView();
		view.addObject("menuId", request.getParameter("menuId"));
		view.addObject("menuNm", request.getParameter("menuNm"));
		view.addObject("menuDesc", request.getParameter("menuDesc"));
		view.addObject("url", request.getParameter("url"));
		
		view.setViewName("/content/standard/invest/investMng");
		
		return view;
	}
	
	/** 투자정보관리 화면 : 조회 */
	@GetMapping(value="/investMng/investMngList")
	@ResponseBody
	@ApiOperation(value = "투자정보관리 화면 / 조회", notes = "투자정보들을 조회한다")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> investMngList(
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
		int total = investService.selectCountInvest(condition);
		condition.pageSetup(total);
		
		List<InvestDto> list = investService.selectInvestMngList(condition);
		return responseService.success(condition, list);
	}

	/** 투자정보관리 화면 : 저장 */
	   @PostMapping(value="/investMng/investMngSave")
	   @ResponseBody
	   @ApiOperation(value = "투자정보관리 화면 / (저장/수정)", notes = "투자정보를 새로 저장하거나 기존 정보를 수정한다")
	   @ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	   @ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	   public String investMngSave( 
			   @ApiParam(value = "메뉴권한", required = false) @RequestBody(required = false) List<InvestDto> data,
			   @AuthenticationPrincipal UserDetails userDetail) { 
	      
	       String code = "202";
	       
	       UserInfo userInfo = (UserInfo)userDetail;
	       String userId = userInfo.getDto().getUserId();
	       
		   int cnt = 0;
		   
		   for(InvestDto inv : data) {
		 	  inv.setRegr(userId);
		 	  inv.setUpdr(userId);
		 	  log.error("@@@ "+ inv.toString());
		 	  if(investService.saveInvestInfo(inv) > 0 && investService.savePoInfo(inv) > 0) cnt++;
		   }
			 
		   if(cnt > 0) code = "200";
	       
	       return code;
	} 
	
	/** 투자정보관리 화면 : 삭제 */
	@PostMapping(value="/investMng/investMngDelete")
	@ResponseBody
	@ApiOperation(value = "투자정보관리 화면 / 삭제", notes = "투자정보를 삭제한다(일괄삭제가능)")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public String investMngDelete( 
			@ApiParam(value = "Row값", required = false) @RequestBody(required = false) List<InvestDto> items
			) { 
		
		String code = "202";
		
		int cnt = 0;
		
		for (InvestDto poNo : items) {
			System.out.println("@@@@DELETE " + poNo);
			if (investService.deletePoInfo(poNo) > 0)
				cnt++;
		}
		 
		if( cnt > 0) code = "200";
		
		return code;
	}
}
