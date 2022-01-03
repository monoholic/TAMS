package kr.co.trito.tams.web.system.user.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kr.co.trito.tams.comm.util.file.excel.ExcelConstant;
import kr.co.trito.tams.comm.util.res.Response;
import kr.co.trito.tams.comm.util.res.ResponseService;
import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.standard.code.dto.CodeDto;
import kr.co.trito.tams.web.system.user.dto.UserMngDto;
import kr.co.trito.tams.web.system.user.dto.UserMngExcelDto;
import kr.co.trito.tams.web.system.user.service.UserMngService;
import kr.co.trito.tams.web.user.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@RequestMapping("/system/user")
@Slf4j
public class UserMngController {
	@Autowired
	UserMngService userService;
	
	@Autowired
	ResponseService responseService;
	
	@Autowired
	BCryptPasswordEncoder encoder;
	
	/** 사용자 관리 화면 */
	@PostMapping("/usermng")
	public ModelAndView userMngView(@RequestParam(value="menuId"  , required=true) String menuId
			                      , @RequestParam(value="menuNm"  , required=true) String menuNm
			                      , @RequestParam(value="menuDesc", required=true) String menuDesc) {
		ModelAndView view = new ModelAndView();
		view.addObject("menuId"  , menuId);
		view.addObject("menuNm"  , menuNm);
		view.addObject("menuDesc", menuDesc);
		view.setViewName("/content/system/user/userMng");
		return view;
	} 	
	
	/** 사용자 관리 화면 : 조회 */
	@GetMapping(value="/usermng/userlist")
	@ResponseBody
	@ApiOperation(value = "Web API Menu Mgr test", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> userMngList(
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
		int total = userService.selectCountUser(condition);
		condition.pageSetup(total);
		
		List<UserMngDto> list = userService.selectUserMngList(condition);
		return responseService.success(condition, list);
	} 	
	
	/** 사용자 관리 화면 : 조회 - 엑셀다운 */
	@PostMapping(value="/usermng/userlistExcel")
	@ApiOperation(value = "Web API Menu Mgr test", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ModelAndView userMngListExcel(
			@ApiParam(value = "검색 조건(메뉴ID, 메뉴명)", required = false) @RequestParam(value = "searchText", required = false) String searchText
			) { 
		
		
		Map<String, Object> params = new HashMap<>();
		if (!StringUtils.isEmpty(searchText))
			params.put("searchText", searchText);
		
		
		SearchCondition condition = new SearchCondition("0", "0", params);
		int total = userService.selectCountUser(condition);
		condition.pageSetup(total);
		
		List<UserMngExcelDto> list = userService.selectUserMngListExcel(condition);
		
		return new ModelAndView("excelXlsxView",makeExcelData(list)) ;
	} 	
	
	/** 엑셀 다운로드용 데이터 생성 */
	private Map<String, Object> makeExcelData(List<UserMngExcelDto> list) {
		Map<String, Object> map = new HashMap<>();
		map.put(ExcelConstant.FILE_NAME, "사용자목록");
		map.put(ExcelConstant.HEAD, Arrays.asList("사용자ID", "사용자명", "부서코드", "부서명", "직급코드","직급명", "이메일", "전화번호", "성별", "사용여부", "등록자", "등록일", "수정자", "수정일"));
		
		ObjectMapper objectMapper = new ObjectMapper();
		List<List<String>> rows = new ArrayList<List<String>>();
		for( UserMngExcelDto user: list) {
			Map<String, String> m = objectMapper.convertValue(user, Map.class);
			List<String> l = new ArrayList<>(m.values());
			rows.add(l);
		}
		map.put(ExcelConstant.BODY, rows);
		return map;
	}	
	
	/** 사죵자 관리 화면 : 등록 */
	@PostMapping(value="/usermng/userinsert")
	@ResponseBody
	@ApiOperation(value = "Web API Menu Mgr Insert", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public String userMngInsert( 
			@ApiParam(value = "사용자 정보", required = true) @RequestBody(required = true) UserMngDto dto,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail
			) { 
		
		String code = "202";
		
		UserInfo userInfo = (UserInfo)userDetail;
		String userId = userInfo.getDto().getUserId();
		
		dto.setUserPw(encoder.encode(dto.getUserPw()));
		dto.setRegr(userId); //임시
		
		int cnt = userService.userMngInsert(dto);
		if( cnt > 0) code = "200";
		
		return code;
	} 
	
	/** 사용자 관리 화면 : 수정 */
	@PostMapping(value="/usermng/userupdate")
	@ResponseBody
	@ApiOperation(value = "Web API User Mgr Update", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public String userMngUpdate( 
			@ApiParam(value = "사용자정보", required = true) @RequestBody(required = true) UserMngDto dto,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail
			) { 
		
		String code = "202";
		
		UserInfo userInfo = (UserInfo)userDetail;
		String userId = userInfo.getDto().getUserId();
		
			if( !StringUtils.isEmpty(dto.getUserPw())) {
	            dto.setUserPw(encoder.encode(dto.getUserPw()));
	         }
		
		dto.setUpdr(userId);
			
		int cnt = userService.userMngUpdate(dto);
		if( cnt > 0) code = "200";
		
		return code;
	} 	
	
	/** 사용자관리 화면 : 삭제 */
	@PostMapping(value="/usermng/userdelete")
	@ResponseBody
	@ApiOperation(value = "Web API User Mgr Delete", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public String userMngDelete( 
			@ApiParam(value = "User ID", required = true) @RequestBody(required = true) List<UserMngDto> users
			) { 
		
		String code = "202";
		int cnt = 0;
		
		for(UserMngDto dto : users) {
			if( userService.userMngDelete(dto) > 0 ) cnt++;
		}
		
		if( cnt > 0) code = "200";
		return code;
	} 
	
	/** 사용자관리 화면 : 직급 리스트 출력 */
	@PostMapping("/codeClpstList")
	@ResponseBody
	public ResponseEntity<? extends Response> menulist(String str) { 
		
		List<CodeDto> list = userService.selectClpstList("");
		return responseService.success(list);
	}
}

