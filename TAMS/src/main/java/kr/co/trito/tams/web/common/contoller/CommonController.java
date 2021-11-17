package kr.co.trito.tams.web.common.contoller;

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
import kr.co.trito.tams.comm.util.file.FileDto;
import kr.co.trito.tams.comm.util.res.Response;
import kr.co.trito.tams.comm.util.res.ResponseService;
import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.common.dto.DeptDto;
import kr.co.trito.tams.web.common.service.CommonService;
import kr.co.trito.tams.web.system.user.dto.UserMngDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/common")
public class CommonController {
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	ResponseService responseService;		
	
	
	/** 부서팝업 화면 */
	@GetMapping("/popup/deptPopup")
	public ModelAndView deptPopupView(HttpServletRequest request) {
		
		ModelAndView view = new ModelAndView();
//		view.addObject("menuId", request.getParameter("menuId"));
//		view.addObject("menuNm", request.getParameter("menuNm"));
//		view.addObject("menuDesc", request.getParameter("menuDesc"));
		view.setViewName("/content/common/popup/deptPopup");
		    
		return view;
	}
	
	/** 부서팝업 조회 */
	@GetMapping("/popup/deptPopupList")
	@ResponseBody
	@ApiOperation(value = "Web API Common test", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response>  deptPopupList(
			@ApiParam(value = "검색 조건(부서명, 부서코드)", required = false) @RequestParam(value="searchText",required = false) String searchText) {
			
		Map<String, Object> params = new HashMap<>();
		if (!StringUtils.isEmpty(searchText))
		params.put("searchText", searchText);
//		
		
		SearchCondition condition = new SearchCondition("0","0",params);
		
		List<DeptDto> list = commonService.selectDeptPopupList(condition);
		condition.pageSetup(list.size());
		
		return responseService.success(condition, list);
		
	}
	
	/** 사용자 팝업 화면 */
	@GetMapping("/popup/userPopup")
	public ModelAndView userPopupView(HttpServletRequest request) {
		
		ModelAndView view = new ModelAndView();
		view.setViewName("/content/common/popup/userPopup");
		
		return view;
	}
	
	/** 부서팝업 조회 */
	@GetMapping("/popup/userPopupList")
	@ResponseBody
	@ApiOperation(value = "Web API Common test", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response>  userPopupList(
			@ApiParam(value = "검색 조건(사용자명, 사용자ID)", required = false) @RequestParam(value="searchText",required = false) String searchText) {
		
		Map<String, Object> params = new HashMap<>();
		if (!StringUtils.isEmpty(searchText))
			params.put("searchText", searchText);
//		
		
		SearchCondition condition = new SearchCondition("0","0",params);
		
		List<UserMngDto> list = commonService.selectUserPopupList(condition);
		condition.pageSetup(list.size());
		
		return responseService.success(condition, list);
		
	}
	
	
	/** 첨부파일 목록 조회 */
	@GetMapping("/file/selectFileList")
	@ResponseBody
	@ApiOperation(value = "Web API Common test", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response>  selectFileList(
			@ApiParam(value = "검색 조건(참조키)", required = false) @RequestParam(value="searchText",required = false) String searchText) {
		
		Map<String, Object> params = new HashMap<>();
		if (!StringUtils.isEmpty(searchText))
			params.put("searchText", searchText);
		
		SearchCondition condition = new SearchCondition("0","0",params);
		
		List<FileDto> list = commonService.selectFileList(condition);
		condition.pageSetup(list.size());
		
		return responseService.success(condition, list);
		
	}
	
	
	
	/** 파일정보 저장 */
	@PostMapping("/file/saveFiles")
	@ResponseBody
	@ApiOperation(value = "Web API Common test", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public String saveFiles(
			@ApiParam(value = "검색 조건(파일정보)", required = false) @RequestBody(required = false) List<FileDto> files) {
		
		String code = "202";
		
		int idx = 0;
		int cnt = 0;
		for(FileDto dto : files) {
			
			dto.setSortOdr(idx);
			dto.setDwldCnt(0);
			dto.setUseYn("Y");
			
			dto.setRegr("system");
			dto.setUpdr("system");
			
			if( commonService.saveFiles(dto) > 0 ) cnt++ ;
			
			idx++;
		}
		
		if( cnt > 0) code = "200";
		
		return code;
		
	}
	
	/** 파일정보 저장 */
	@PostMapping("/file/updateDwldCnt")
	@ResponseBody
	@ApiOperation(value = "Web API Common test", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public String updateDwldCnt(
			@ApiParam(value = "검색 조건(파일정보)", required = false) @RequestBody(required = false) FileDto file) {
		
		String code = "202";
		
		file.setUpdr("system");
			
		if( commonService.updateDwldCnt(file) > 0 ) code = "200";
		
		return code;
		
	} 	
	
}

