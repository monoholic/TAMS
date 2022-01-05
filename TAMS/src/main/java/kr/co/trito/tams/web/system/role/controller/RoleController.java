package kr.co.trito.tams.web.system.role.controller;

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
import kr.co.trito.tams.web.system.role.dto.RoleDto;
import kr.co.trito.tams.web.system.role.service.RoleService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/system/role")
public class RoleController {

	@Autowired
	ResponseService responseService;

	@Autowired
	RoleService roleService;

	/** 권한관리 화면 */
	@PostMapping("/list")
	public ModelAndView menumngView(HttpServletRequest request) {

		ModelAndView view = new ModelAndView();
		view.addObject("menuId", request.getParameter("menuId"));
		view.addObject("menuNm", request.getParameter("menuNm"));
		view.addObject("menuDesc", request.getParameter("menuDesc"));
		view.setViewName("/content/system/role/roleMng");

		return view;
	}

	/** 권한관리 화면 : 조회 */
	@GetMapping(value = "/roleMng/roleList")
	@ResponseBody
	@ApiOperation(value = "권한관리 화면 / 전체 권한 조회", notes = "권한코드나 권한명으로 권한을 조회한다")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> roleMngList(
			@ApiParam(value = "조회 페이지 번호", required = true) @RequestParam(value = "currentPage", required = true) String currentPage,
			@ApiParam(value = "페이지별 조회 출력수", required = true) @RequestParam(value = "numOfRows", required = true) String numOfRows,
			@ApiParam(value = "검색 타입(role_id, role_nm)", required = false) @RequestParam(value = "searchType", required = false) String searchType,
			@ApiParam(value = "검색 조건(권한코드, 권한명)", required = false) @RequestParam(value = "searchText", required = false) String searchText,
			@ApiParam(value = "정렬 필드", required = false) @RequestParam(value = "sortField", required = false) String sortField,
			@ApiParam(value = "정렬 타입", required = false) @RequestParam(value = "sortOrder", required = false) String sortOrder) {

		if (StringUtils.isEmpty(currentPage) || StringUtils.isEmpty(numOfRows))
			throw new IllegalArgumentException("Request parameter error.");

		Map<String, Object> params = new HashMap<>();
		if (!StringUtils.isEmpty(searchType))
			params.put("searchType", searchType);
		
		if (!StringUtils.isEmpty(searchText))
			params.put("searchText", searchText);

		if (!StringUtils.isEmpty(sortField))
			params.put("sortField", sortField);

		if (!StringUtils.isEmpty(sortOrder))
			params.put("sortOrder", sortOrder);

		SearchCondition condition = new SearchCondition(currentPage, numOfRows, params);
		int total = roleService.selectCountRole(condition);
		condition.pageSetup(total);
		
		List<RoleDto> list = roleService.selectRoleMngList(condition);
		return responseService.success(condition, list);
	}

	/** 권한관리 화면 : 등록 */
	   @GetMapping(value="/roleMng/roleInsert")
	   @ResponseBody
	   @ApiOperation(value = "권한관리 화면 / 등록", notes = "권한을 등록한다")
	   @ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	   @ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	   public String roleMngInsert( 
	         @ApiParam(value = "권한코드", required = true) @RequestParam(value = "roleId", required = true) String roleId,
	         @ApiParam(value = "권한명", required = true) @RequestParam(value = "roleNm", required = true) String roleNm,
	         @ApiParam(value = "권한설명", required = false) @RequestParam(value = "roleDesc", required = true) String roleDesc,
	         @ApiParam(value = "사용여부", required = false) @RequestParam(value = "useYn", required = true) String useYn
	         ) { 
	      
	      String code = "202";
	      
	      RoleDto dto = new RoleDto();
	      
	      if (StringUtils.isNotEmpty(roleId)) dto.setRoleId(roleId);
	      if (StringUtils.isNotEmpty(roleNm)) dto.setRoleNm(roleNm);
	      
	      dto.setRoleDesc(roleDesc);
	      dto.setUseYn(useYn);
	      
	      dto.setRegr("system"); //임시
	      
	      int cnt = roleService.roleMngInsert(dto);
	      if( cnt > 0) code = "200";
	      
	      return code;
	   } 
	   
	/** 권한관리 화면 : 수정 */
		@GetMapping(value="/roleMng/roleUpdate")
		@ResponseBody
		@ApiOperation(value = "권한관리 화면 / 수정", notes = "등록된 권한을 수정한다")
		@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
		@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
		public String roleMngUpdate( 
		         @ApiParam(value = "권한코드", required = true) @RequestParam(value = "roleId", required = true) String roleId,
		         @ApiParam(value = "권한명", required = true) @RequestParam(value = "roleNm", required = true) String roleNm,
		         @ApiParam(value = "권한설명", required = false) @RequestParam(value = "roleDesc", required = true) String roleDesc,
		         @ApiParam(value = "사용여부", required = false) @RequestParam(value = "useYn", required = true) String useYn
				) { 
			
			String code = "202";
			
			RoleDto dto = new RoleDto();
			
		    if (StringUtils.isNotEmpty(roleId)) dto.setRoleId(roleId);
		    if (StringUtils.isNotEmpty(roleNm)) dto.setRoleNm(roleNm);
		      
		    dto.setRoleDesc(roleDesc);
		    dto.setUseYn(useYn);
			
			dto.setUpdr("system"); //임시
			
			int cnt = roleService.roleMngUpdate(dto);
			if( cnt > 0) code = "200";
			
			return code;
		} 	
		
		
	/** 권한관리 화면 : 삭제 */
		@GetMapping(value="/roleMng/roleDelete")
		@ResponseBody
		@ApiOperation(value = "권한관리 화면 / 삭제", notes = "등록된 권한을 삭제한다(일괄삭제가능)")
		@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
		@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
		public String roleMngDelete( 
				@ApiParam(value = "메뉴ID", required = true) @RequestParam(value = "items", required = true) List<String> items
				) { 
			
			String code = "202";
			
			int cnt = 0;
			
			for(String roleId : items) {
				System.out.println("@@@@@@@@@@ "+ roleId);
				RoleDto dto = new RoleDto();
				dto.setRoleId(roleId);
				if( roleService.roleMngDelete(dto) > 0 ) cnt++;
			}
			
			if( cnt > 0) code = "200";
			
			return code;
		}
}
