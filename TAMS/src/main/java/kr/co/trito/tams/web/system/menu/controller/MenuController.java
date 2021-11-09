package kr.co.trito.tams.web.system.menu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
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
import kr.co.trito.tams.web.system.menu.dto.MenuDto;
import kr.co.trito.tams.web.system.menu.service.MenuService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/system/menu")
public class MenuController {
	@Autowired
	MenuService menuService;
	
	@Autowired
	ResponseService responseService;		
	
	/** 메뉴 - 상단 메뉴 조회 */
	@PostMapping("/menuList")
	@ResponseBody
	public ResponseEntity<? extends Response> menulist(String str) { 
		
		List<MenuDto> list = menuService.selectMenuList("");
		return responseService.success(list);
	}
	
	/** 메뉴관리 화면 */
	@PostMapping("/menuMng")
	public ModelAndView menumngView(HttpServletRequest request) {
		
		ModelAndView view = new ModelAndView();
		view.addObject("menuId", request.getParameter("menuId"));
		view.addObject("menuNm", request.getParameter("menuNm"));
		view.addObject("menuDesc", request.getParameter("menuDesc"));
		view.setViewName("/content/system/menu/menuMng");
		    
		return view;
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
	   @ApiOperation(value = "Web API Menu Mgr Insert", notes = "Web API Test")
	   @ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	   @ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	   public String menuMgrInsert( 
	         @ApiParam(value = "메뉴ID", required = true) @RequestParam(value = "menuId", required = true) String menuId,
	         @ApiParam(value = "메뉴명", required = true) @RequestParam(value = "menuNm", required = true) String menuNm,
	         @ApiParam(value = "상위메뉴ID", required = false) @RequestParam(value = "uppMenuId", required = true) String uppMenuId,
	         @ApiParam(value = "메뉴설명", required = false) @RequestParam(value = "menuDesc", required = true) String menuDesc,
	         @ApiParam(value = "메뉴경로", required = false) @RequestParam(value = "url", required = true) String url,
	         @ApiParam(value = "메뉴레벨", required = false) @RequestParam(value = "lvl", required = true) String lvl,
	         @ApiParam(value = "사용여부", required = false) @RequestParam(value = "useYn", required = true) String useYn,
	         @ApiParam(value = "팝업구분", required = false) @RequestParam(value = "popupYn", required = true) String popupYn,
	         @ApiParam(value = "정렬순서", required = false) @RequestParam(value = "sortOdr", required = true) String sortOdr
	         ) { 
	      
	      String code = "202";
	      
	      MenuDto dto = new MenuDto();
	      
	      if (StringUtils.isNotEmpty(menuId)) dto.setMenuId(menuId);
	      if (StringUtils.isNotEmpty(menuNm)) dto.setMenuNm(menuNm);
	      
	      dto.setUppMenuId(uppMenuId);
	      dto.setMenuDesc(menuDesc);
	      dto.setUrl(url);
	      dto.setLvl(lvl);
	      dto.setUseYn(useYn);
	      dto.setPopupYn(popupYn);
	      dto.setSortOdr(sortOdr);
	      
	      dto.setRegr("system"); //임시
	      
	      int cnt = menuService.menuMngInsert(dto);
	      if( cnt > 0) code = "200";
	      
	      return code;
	   } 
	
	/** 메뉴관리 화면 : 수정 */
	@GetMapping(value="/menuMng/menuUpdate")
	@ResponseBody
	@ApiOperation(value = "Web API Menu Mgr Update", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public String menuMgrUpdate( 
			@ApiParam(value = "메뉴ID", required = true) @RequestParam(value = "menuId", required = true) String menuId,
			@ApiParam(value = "메뉴명", required = true) @RequestParam(value = "menuNm", required = true) String menuNm,
			@ApiParam(value = "상위메뉴ID", required = false) @RequestParam(value = "uppMenuId", required = true) String uppMenuId,
			@ApiParam(value = "메뉴설명", required = false) @RequestParam(value = "menuDesc", required = true) String menuDesc,
			@ApiParam(value = "메뉴경로", required = false) @RequestParam(value = "url", required = true) String url,
			@ApiParam(value = "메뉴레벨", required = false) @RequestParam(value = "lvl", required = true) String lvl,
			@ApiParam(value = "사용여부", required = false) @RequestParam(value = "useYn", required = true) String useYn,
			@ApiParam(value = "팝업구분", required = false) @RequestParam(value = "popupYn", required = true) String popupYn,
			@ApiParam(value = "정렬순서", required = false) @RequestParam(value = "sortOdr", required = true) String sortOdr
			) { 
		
		String code = "202";
		
		MenuDto dto = new MenuDto();
		
		if (StringUtils.isNotEmpty(menuId)) dto.setMenuId(menuId);
		if (StringUtils.isNotEmpty(menuNm)) dto.setMenuNm(menuNm);
		
		dto.setUppMenuId(uppMenuId);
		dto.setMenuDesc(menuDesc);
		dto.setUrl(url);
		dto.setLvl(lvl);
		dto.setUseYn(useYn);
		dto.setPopupYn(popupYn);
		dto.setSortOdr(sortOdr);
		
		dto.setUpdr("system"); //임시
		
		int cnt = menuService.menuMngUpdate(dto);
		if( cnt > 0) code = "200";
		
		return code;
	} 	
	
	
	/** 메뉴관리 화면 : 삭제 */
	@GetMapping(value="/menuMng/menuDelete")
	@ResponseBody
	@ApiOperation(value = "Web API Menu Mgr Delete", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public String menumngDelete( 
			@ApiParam(value = "메뉴ID", required = true) @RequestParam(value = "items", required = true) List<String> items
			) { 
		
		String code = "202";
		
		int cnt = 0;
		
		for(String menuId : items) {
			System.out.println("@@@@@@@@@@ "+menuId);
			MenuDto dto = new MenuDto();
			dto.setMenuId(menuId);
			if( menuService.menuMngDelete(dto) > 0 ) cnt++;
		}
		
		if( cnt > 0) code = "200";
		
		return code;
	}

}

