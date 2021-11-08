package kr.co.trito.tams.web.system.role.controller;

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
import kr.co.trito.tams.comm.util.res.Response;
import kr.co.trito.tams.comm.util.res.ResponseService;
import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.sample.dto.BoardDto;
import kr.co.trito.tams.web.sample.service.BoardService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/role")
public class RoleController {

	@Autowired
	ResponseService responseService;

	@Autowired
	BoardService boardService;

	@GetMapping(value = "/roleList")
	@ResponseBody
	@ApiOperation(value = "Web API Board test", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> webBoardList(
			@ApiParam(value = "조회 페이지 번호", required = true) @RequestParam(value = "currentPage", required = true) String currentPage,
			@ApiParam(value = "페이지별 조회 출력수", required = true) @RequestParam(value = "numOfRows", required = true) String numOfRows,
			@ApiParam(value = "검색 조건(타이틀)", required = false) @RequestParam(value = "searchTitle", required = false) String searchTitle,
			@ApiParam(value = "정렬 필드", required = false) @RequestParam(value = "sortField", required = false) String sortField,
			@ApiParam(value = "정렬 타입", required = false) @RequestParam(value = "sortOrder", required = false) String sortOrder) {

		if (StringUtils.isEmpty(currentPage) || StringUtils.isEmpty(numOfRows))
			throw new IllegalArgumentException("Request parameter error.");

		Map<String, Object> params = new HashMap<>();
		if (!StringUtils.isEmpty(searchTitle))
			params.put("searchTitle", searchTitle);
		
		if (!StringUtils.isEmpty(sortField))
			params.put("sortField", sortField);
		
		if (!StringUtils.isEmpty(sortOrder))
			params.put("sortOrder", sortOrder);
		
		SearchCondition condition = new SearchCondition(currentPage, numOfRows, params);
		int total = boardService.webCountBoard(condition);
		condition.pageSetup(total);

		List<BoardDto> list = boardService.webSelectBoard(condition);
		return responseService.success(condition, list);
	}

	@GetMapping(value = "/roleUpdate")
	@ResponseBody
	@ApiOperation(value = "Web API Board test", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public String webUpdateList(
			@ApiParam(value = "게시글번호", required = true) @RequestParam(value = "id", required = true) String id,
			@ApiParam(value = "게시글제목", required = true) @RequestParam(value = "title", required = true) String title) {

		String code = "202";

		BoardDto board = new BoardDto();

		if (StringUtils.isNotEmpty(id))
			board.setId(id);

		if (StringUtils.isNotEmpty(title))
			board.setTitle(title);

		if (boardService.webUpdateBoard(board) > 0)
			code = "200";

		return code;
	}

	@GetMapping(value = "/roleDelete")
	@ResponseBody
	@ApiOperation(value = "Web API Board test", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public String webDeleteList(
			@ApiParam(value = "게시글번호", required = true) @RequestParam(value = "id", required = true) String id,
			@ApiParam(value = "게시글제목", required = true) @RequestParam(value = "title", required = true) String title) {

		String code = "202";

		BoardDto board = new BoardDto();

		if (StringUtils.isNotEmpty(id))
			board.setId(id);

		if (StringUtils.isNotEmpty(title))
			board.setTitle(title);

		if (boardService.webDeleteBoard(board) > 0)
			code = "200";

		return code;
	}

	@GetMapping(value = "/roleInsert")
	@ResponseBody
	@ApiOperation(value = "Web API Board test", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public String webInsertList(
			@ApiParam(value = "게시글번호", required = true) @RequestParam(value = "id", required = true) String id,
			@ApiParam(value = "게시글제목", required = true) @RequestParam(value = "title", required = true) String title) {

		String code = "202";

		BoardDto board = new BoardDto();

		if (StringUtils.isNotEmpty(id))
			board.setId(id);

		if (StringUtils.isNotEmpty(title))
			board.setTitle(title);

		if (boardService.webInsertBoard(board) > 0)
			code = "200";

		return code;
	}

	@RequestMapping(value = "/role_management")
	public ModelAndView role_management() {
		ModelAndView mav = new ModelAndView("/content/system/role/role");
		return mav;
	}
}
