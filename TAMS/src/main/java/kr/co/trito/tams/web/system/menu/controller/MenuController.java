package kr.co.trito.tams.web.system.menu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
import kr.co.trito.tams.web.system.menu.dto.MenuDto;
import kr.co.trito.tams.web.system.menu.service.MenuService;
import kr.co.trito.tams.web.user.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@Slf4j
@RequestMapping("/system/menu")
public class MenuController {
	@Autowired
	MenuService menuService;
	
	@Autowired
	ResponseService responseService;		

	/** 메뉴관리 화면 */
	@PostMapping("/list")
	public ModelAndView menumngView(HttpServletRequest request) {
		
		ModelAndView view = new ModelAndView();
		view.addObject("menuId", request.getParameter("menuId"));
		view.addObject("menuNm", request.getParameter("menuNm"));
		view.addObject("menuDesc", request.getParameter("menuDesc"));
		view.setViewName("/content/system/menu/menuMng");
		    
		return view;
	} 		
	
	
	/** 메뉴 - 상단 메뉴 조회 */
	@PostMapping("/menuList")
	@ResponseBody
	public ResponseEntity<? extends Response> menulist(
			@AuthenticationPrincipal UserDetails userDetail) { 
		
		List<GrantedAuthority> auths = new ArrayList<>(userDetail.getAuthorities());
	      log.error("@@@@@@@ : "+ auths.get(0).getAuthority());
	      
		/*
		 * String role = userRole.substring(userRole.indexOf("=") + 1);
		 * 
		 * log.info("테스트2" + role);
		 */
	      
		String roleId = auths.get(0).getAuthority();
	      
		List<MenuDto> list = menuService.selectMenuList(roleId);
		return responseService.success(list);
	}

	
	/** 메뉴관리 화면 : 조회 */
	@GetMapping(value="/menuMng/menuList")
	@ResponseBody
	@ApiOperation(value = "Web API Menu Mgr test", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> menumngList(
				@ApiParam(value = "조회 페이지 번호", required = true) @RequestParam(value = "currentPage",required = true) String currentPage,
				@ApiParam(value = "페이지별 조회 출력수", required = true) @RequestParam(value = "numOfRows", required = true) String numOfRows,
				@ApiParam(value = "검색 조건(메뉴ID, 메뉴명)", required = false) @RequestParam(value = "searchText", required = false) String searchText,
				@ApiParam(value = "정렬 필드", required = false) @RequestParam(value = "sortField", required = false) String sortField,
				@ApiParam(value = "정렬 타입", required = false) @RequestParam(value = "sortOrder", required = false) String sortOrder
                              ) { 

		if (StringUtils.isEmpty(currentPage) || StringUtils.isEmpty(numOfRows))
		throw new IllegalArgumentException("Request parameter error.");
		
		Map<String, Object> params = new HashMap<>();
		if (!StringUtils.isEmpty(searchText))
		params.put("searchText", searchText);
		
		if (!StringUtils.isEmpty(sortField))
		params.put("sortField", sortField);
		
		if (!StringUtils.isEmpty(sortOrder))
		params.put("sortOrder", sortOrder);
		
		SearchCondition condition = new SearchCondition(currentPage, numOfRows, params);
		int total = menuService.selectCountMenu(condition);
		condition.pageSetup(total);
		
		List<MenuDto> list = menuService.selectMenuMngList(condition);
		return responseService.success(condition, list);
	} 	
	
	
	/** 메뉴관리 화면 : 등록 */
	   @GetMapping(value="/menuMng/menuInsert")
	   @ResponseBody
	   @ApiOperation(value = "메뉴 등록", notes = "신규 메뉴를 등록한다.")
	   @ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	   @ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	   public ResponseEntity<? extends Response> menuMgrInsert( 
				@ApiParam(value = "메뉴 등록 데이터", required = true) @RequestParam Map<String, Object> items,
				@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail			   
	         ) {
		   	UserInfo userInfo = (UserInfo)userDetail;
			String userId = userInfo.getDto().getUserId();
			items.put("regr", userId);
			items.put("updr", userId);	      	      
			menuService.menuMngInsert(items);
			return responseService.success(null);
	   } 
	
	/** 메뉴관리 화면 : 수정 */
	@GetMapping(value="/menuMng/menuUpdate")
	@ResponseBody
	   @ApiOperation(value = "메뉴 수정", notes = "메뉴 정보를 업데이트 한다.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response>  menuMgrUpdate( 
			@ApiParam(value = "메뉴 등록 데이터", required = true) @RequestParam Map<String, Object> items,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail	
			) { 
	   	UserInfo userInfo = (UserInfo)userDetail;
		String userId = userInfo.getDto().getUserId();
		items.put("regr", userId);
		items.put("updr", userId);	      	 
		menuService.menuMngUpdate(items);
		return responseService.success(null);
	} 	
	
	
	/** 메뉴관리 화면 : 삭제 */
	@GetMapping(value="/menuMng/menuDelete")
	@ResponseBody
	@ApiOperation(value = "Web API Menu Mgr Delete", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response>  menumngDelete( 
			@ApiParam(value = "메뉴ID", required = true) @RequestParam(value = "items", required = true) List<String> items
			) { 
		
		for(String menuId : items) {
			MenuDto dto = new MenuDto();
			dto.setMenuId(menuId);
			 menuService.menuMngDelete(dto); 
		}
		
		return responseService.success(null);
	}

}

