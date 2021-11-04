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
import kr.co.trito.tams.comm.util.res.Response;
import kr.co.trito.tams.comm.util.res.ResponseService;
import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.sample.dto.BoardDto;
import kr.co.trito.tams.web.sample.service.BoardService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/sample")
public class SampleController {

	@Autowired
	ResponseService responseService;

	@Autowired
	BoardService boardService;

	@GetMapping(value = "/menu1")
	public String menu1() {
		return "/sample/00-main_메뉴1";
	}
	
	@GetMapping(value = "/menu2")
	public String menu2() {
		return "/sample/00-main_메뉴2";
	}
	
	@GetMapping(value = "/menu3")
	public String menu3() {
		return "/sample/00-메뉴1";
	}
	
	@GetMapping(value = "/menu4")
	public String menu4() {
		return "/sample/00-메뉴2_1";
	}
	
	@GetMapping(value = "/menu5")
	public String menu5() {
		return "/sample/00-메뉴2";
	}
	
	@GetMapping(value = "/screen")
	public String screen() {
		return "/sample/00-화면구성";
	}
}
