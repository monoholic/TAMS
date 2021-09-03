package kr.co.trito.tams.web.sample.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kr.co.trito.tams.comm.utils.SearchCondition;
import kr.co.trito.tams.comm.utils.res.Response;
import kr.co.trito.tams.comm.utils.res.ResponseService;
import kr.co.trito.tams.web.sample.dto.BoardDto;
import kr.co.trito.tams.web.sample.service.BoardService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value="/sample")
public class BoardController {

	@Autowired
	ResponseService responseService;
	
	@Autowired
	BoardService boardService;
	
	@GetMapping(value="/boardList")
	@ResponseBody	
	@ApiOperation(value="Web API Board test", notes="Web API Test")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "성공적으로 수행 됨"),
            @ApiResponse(code=500, message = "API 서버에 문제가 발생하였음")
    })		
	public ResponseEntity<? extends Response> webBoardList(
			@ApiParam(value = "조회 페이지 번호", required = true)
			@RequestParam(value="currentPage", required=true)String currentPage,
			@ApiParam(value = "페이지별 조회 출력수", required = true)
			@RequestParam(value="numOfRows", required=true)String numOfRows,
			@ApiParam(value = "검색 조건(타이틀)", required =false)
			@RequestParam(value="searchTitle", required=false)String searchTitle			
			) {			

		if (StringUtils.isEmpty(currentPage) || StringUtils.isEmpty(numOfRows))
			throw new IllegalArgumentException("Request parameter error.");		
		
		Map<String, Object> params = new HashMap<>();		
		if(!StringUtils.isEmpty(searchTitle)) 
			params.put("searchTitle", searchTitle);

		SearchCondition condition = new SearchCondition(params);
		int total = boardService.webCountBoard(condition);                            
		condition.pageSetup(total, Integer.parseInt(currentPage), Integer.parseInt(numOfRows));		

		List<BoardDto> list = boardService.webSelectBoard(condition);
		return responseService.success(condition, list);
	}		

	@RequestMapping(value = "/sampleView")	
	public ModelAndView sample() {
		ModelAndView mav = new ModelAndView("/sample");
		return mav;
	}	
}
