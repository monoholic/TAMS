package kr.co.trito.tams.web.asset.regist.outofbook.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
import kr.co.trito.tams.web.asset.regist.outofbook.dto.OutOfBookDto;
import kr.co.trito.tams.web.asset.regist.outofbook.service.OutOfBookService;
import kr.co.trito.tams.web.user.dto.UserDto;
import kr.co.trito.tams.web.user.dto.UserInfo;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/asset/regist/outofbook")
public class OutOfBookController {

	@Autowired
	ResponseService responseService;	
	
	@Autowired
	OutOfBookService outOfBookService;
	
	private String viewPath = "/content/asset/regist/outofbook/";
	
	@PostMapping("/list")
	public ModelAndView outOfBookView(Authentication authentication, HttpServletRequest request) {
		ModelAndView view = new ModelAndView();		
		UserDto userDto = ((UserInfo) authentication.getPrincipal()).getDto();		
		view.addObject("menuId"  , request.getParameter("menuId"));
		view.addObject("menuNm"  , request.getParameter("menuNm"));
		view.addObject("menuDesc", request.getParameter("menuDesc"));
		view.addObject("url"     , request.getParameter("url"));
		view.addObject("userDto" , userDto);		
		view.setViewName(viewPath.concat("outOfBook"));		
		return view;
	}		
	
	//신규자산 등록
	@GetMapping("/outOfBookRegist")
	public ModelAndView newAsetRegist(
			@ApiParam(value = "자산번호", required = true) @RequestParam Map<String, Object> params) {
		ModelAndView view = new ModelAndView();
		view.addObject("assetNo", params.get("assetNo"));
		view.setViewName(viewPath.concat("outOfBookRegist"));		
		return view;
	}
	
	
	@GetMapping(value = "/outOfBookList")
	@ResponseBody
	@ApiOperation(value = "부외자산등록 리스트", notes = "부외자산등록 리스트 조회한다")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> outOfBookList(
			@ApiParam(value = "조회 조건", required = true) @RequestParam Map<String, Object> params) {

		SearchCondition condition = new SearchCondition(params.get("currentPage").toString(),
				params.get("numOfRows").toString(), params);
		condition.pageSetup(outOfBookService.selectCountOutOfBook(condition));

		List<OutOfBookDto> list = outOfBookService.selectOutOfBookList(condition);
		return responseService.success(condition, list);
	}
	
	/** 부회자산 저장 */
	@PostMapping("/saveOutOfBookAset")
	@ResponseBody
	@ApiOperation(value = "부외자산 저장", notes = "부외자산 등록/수정 처리한다")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> saveOutOfBookAset(
			@ApiParam(value = "필터 / 페이징 값", required = false) @RequestBody List<Map<String, Object>> params,
			@AuthenticationPrincipal UserDetails userDetail) {		
		String userId = ((UserInfo)userDetail).getDto().getUserId();		
		outOfBookService.saveOutOfBookAset(userId, params);
		return responseService.success(null);
	}	

	/** 부회자산 삭제 */
	@GetMapping("/deleteOutOfBookAset")
	@ResponseBody
	@ApiOperation(value = "투자자산등록(팝업 : 신규자산 등록) 화면 / 삭제버튼", notes = "등록된 신규자산을 삭제한다")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })	
	public ResponseEntity<? extends Response> deleteOutOfBookAset(
			@ApiParam(value = "PO/자산번호", required = true) @RequestParam Map<String, Object> param){
		log.info(param.toString());
		outOfBookService.deleteOutOfBookAset(param);		
		return responseService.success(null);
	}
	
}
