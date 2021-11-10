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
import kr.co.trito.tams.web.common.dto.DeptDto;
import kr.co.trito.tams.web.common.service.CommonService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/common/popup")
public class CommonController {
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	ResponseService responseService;		
	
	
	/** 부서팝업 화면 */
	@GetMapping("/deptPopup")
	public ModelAndView deptPopupView(HttpServletRequest request) {
		
		ModelAndView view = new ModelAndView();
//		view.addObject("menuId", request.getParameter("menuId"));
//		view.addObject("menuNm", request.getParameter("menuNm"));
//		view.addObject("menuDesc", request.getParameter("menuDesc"));
		view.setViewName("/content/common/popup/deptPopup");
		    
		return view;
	}
	
	/** 부서팝업 조회 */
	@GetMapping("/deptPopupList")
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
	
}

