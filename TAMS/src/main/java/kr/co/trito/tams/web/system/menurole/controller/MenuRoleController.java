package kr.co.trito.tams.web.system.menurole.controller;

import java.util.HashMap;
import java.util.Iterator;
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
import kr.co.trito.tams.web.system.menu.dto.MenuDto;
import kr.co.trito.tams.web.system.menurole.dto.MenuRoleDto;
import kr.co.trito.tams.web.system.menurole.dto.RoleGroupDto;
import kr.co.trito.tams.web.system.menurole.service.MenuRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@Slf4j
@RequestMapping("/system/menurole")
public class MenuRoleController {
	@Autowired
	MenuRoleService menuroleService;
	
	@Autowired
	ResponseService responseService;
	
	/** 메뉴관리 화면 */
	@PostMapping("/menuRoleMng")
	public ModelAndView menuRoleMngView(HttpServletRequest request) {
		
		ModelAndView view = new ModelAndView();
		view.addObject("menuId", request.getParameter("menuId"));
		view.addObject("menuNm", request.getParameter("menuNm"));
		view.addObject("menuDesc", request.getParameter("menuDesc"));
		view.setViewName("/content/system/menurole/menuRoleMng");
		    
		return view;
	} 	

	/** 메뉴권한관리 화면 : 셀렉트박스 */
	@GetMapping(value="/menuRoleMng/roleGroup")
	@ResponseBody
	@ApiOperation(value = "Web API Menu Mgr test", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> roleGroup() { 

		List<RoleGroupDto> list = menuroleService.selectRoleGroup();
		
		return responseService.success(list);
	}
	
	/** 메뉴권한관리 화면 : 조회 */
	@GetMapping(value="/menuRoleMng/menuRoleMngList")
	@ResponseBody
	@ApiOperation(value = "Web API Menu Role Mng test", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> menuRoleMngList(
				@ApiParam(value = "조회 페이지 번호", required = true) @RequestParam(value = "currentPage",required = true) String currentPage,
				@ApiParam(value = "페이지별 조회 출력수", required = true) @RequestParam(value = "numOfRows", required = true) String numOfRows,
				@ApiParam(value = "검색 조건(메뉴ID, 메뉴명)", required = false) @RequestParam(value = "searchType", required = false) String searchType,
				@ApiParam(value = "정렬 필드", required = false) @RequestParam(value = "sortField", required = false) String sortField,
				@ApiParam(value = "정렬 타입", required = false) @RequestParam(value = "sortOrder", required = false) String sortOrder
                              ) { 

		if (StringUtils.isEmpty(currentPage) || StringUtils.isEmpty(numOfRows))
		throw new IllegalArgumentException("Request parameter error.");
		
		Map<String, Object> params = new HashMap<>();
		if (!StringUtils.isEmpty(searchType))
		params.put("roleId", searchType);
		
		if (!StringUtils.isEmpty(sortField))
		params.put("sortField", sortField);
		
		if (!StringUtils.isEmpty(sortOrder))
		params.put("sortOrder", sortOrder);
		
		SearchCondition condition = new SearchCondition(currentPage, numOfRows, params);
		int total = menuroleService.selectCountMenu(condition);
		condition.pageSetup(total);
		
		List<MenuRoleDto> list = menuroleService.selectMenuRoleMngList(condition);
		return responseService.success(condition, list);
	} 	
	
	
	/** 메뉴권한관리 화면 : 저장 */
	   @PostMapping(value="/menuRoleMng/menuRoleSave")
	   @ResponseBody
	   @ApiOperation(value = "Web API Menu Mgr Save", notes = "Web API Test")
	   @ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	   @ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	   public String menuRoleMngSave( 
			   @ApiParam(value = "메뉴권한", required = false) @RequestBody(required = false) List<MenuRoleDto> items
		 ) { 
	      
	      String code = "202";
	      
		  int cnt = 0;
		  int itemCnt = 0;
		  String grpId = "";
		  
		  for(MenuRoleDto dto : items) {
			  
			  if(itemCnt == 0) {
				  grpId = dto.getGroupId();
			  }else{
				  if(StringUtils.isEmpty(dto.getGroupId()) || "none".equals(dto.getGroupId()))
				  dto.setGroupId(grpId);
			  }
			  dto.setRegr("System");
			  dto.setUpdr("System");
			  
			  if(menuroleService.menuRoleMngRoleSave(dto) > 0) cnt++;
			  
			  itemCnt++;
		  }
		  
		  if( cnt > 0) code = "200";
	      
	      return code;
	   } 
	
	/** 메뉴관리 화면 : 삭제 */
	@GetMapping(value="/menuMng/menuDelete")
	@ResponseBody
	@ApiOperation(value = "Web API Menu Mgr Delete", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public String menuRoleMngDelete( 
			@ApiParam(value = "메뉴ID", required = true) @RequestParam(value = "items", required = true) List<String> items
			) { 
		
		String code = "202";
		
		int cnt = 0;
		
		for(String menuId : items) {
			System.out.println("@@@@@@@@@@ "+menuId);
			MenuDto dto = new MenuDto();
			dto.setMenuId(menuId);
			if( menuroleService.menuMngDelete(dto) > 0 ) cnt++;
		}
		
		if( cnt > 0) code = "200";
		
		return code;
	}

}

