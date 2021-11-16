package kr.co.trito.tams.web.system.user.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import kr.co.trito.tams.web.system.user.dto.UserMngDto;
import kr.co.trito.tams.web.system.user.service.UserMngService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/system/user")
public class UserMngController {
	@Autowired
	UserMngService userService;
	
	@Autowired
	ResponseService responseService;
	
	@Autowired
	BCryptPasswordEncoder encoder;
	
	/** 사용자 관리 화면 */
	@PostMapping("/usermng")
	public ModelAndView userMngView(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		view.addObject("menuId", request.getParameter("menuId"));
		view.addObject("menuNm", request.getParameter("menuNm"));
		view.addObject("menuDesc", request.getParameter("menuDesc"));
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
	
	
	/** 사죵자 관리 화면 : 등록 */
	@PostMapping(value="/usermng/userinsert")
	@ResponseBody
	@ApiOperation(value = "Web API Menu Mgr Insert", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public String userMngInsert( 
			@ApiParam(value = "사용자 정보", required = true) 
			@RequestBody(required = true) UserMngDto dto
			) { 
		
		String code = "202";
		
//		UserMngDto dto = new UserMngDto();
//		try {
//			JSONParser parser = new JSONParser(userInfo);
//			LinkedHashMap obj = parser.parseObject();
//			//System.out.println(obj.get("userId"));
//			dto.setUserId(obj.get("userId").toString());
//			dto.setUserNm(obj.get("userNm").toString());
		
			dto.setUserPw(encoder.encode(dto.getUserPw()));
			
//			dto.setDeptCd(obj.get("deptCd").toString());
//			dto.setEmail (obj.get("email").toString());
//			dto.setTelno (obj.get("telno").toString());
//			dto.setSex   (obj.get("sex").toString());
//			dto.setUseYn (obj.get("useYn").toString());
//		} catch(Exception e) {e.printStackTrace();}

		dto.setRegr("system"); //임시
		
		int cnt = userService.userMngInsert(dto);
		if( cnt > 0) code = "200";
		
		return code;
	} 
//	/** 사죵자 관리 화면 : 등록 */
//	@GetMapping(value="/usermng/userinsert")
//	@ResponseBody
//	@ApiOperation(value = "Web API Menu Mgr Insert", notes = "Web API Test")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
//			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
//	public String userMngInsert( 
//			@ApiParam(value = "사용자 정보", required = true) 
//			@RequestParam(value = "userInfo", required = true) 
//			String userInfo
//			) { 
//		
//		String code = "202";
//		
//		UserMngDto dto = new UserMngDto();
//		
//		try {
//			JSONParser parser = new JSONParser(userInfo);
//			LinkedHashMap obj = parser.parseObject();
//			//System.out.println(obj.get("userId"));
//			dto.setUserId(obj.get("userId").toString());
//			dto.setUserNm(obj.get("userNm").toString());
//			dto.setUserPw(obj.get("userPw").toString());
//			dto.setDeptCd(obj.get("deptCd").toString());
//			dto.setEmail (obj.get("email").toString());
//			dto.setTelno (obj.get("telno").toString());
//			dto.setSex   (obj.get("sex").toString());
//			dto.setUseYn (obj.get("useYn").toString());
//		} catch(Exception e) {e.printStackTrace();}
//		
//		dto.setRegr("system"); //임시
//		
//		int cnt = userService.userMngInsert(dto);
//		if( cnt > 0) code = "200";
//		
//		return code;
//	} 
	
	/** 사용자 관리 화면 : 수정 */
	@PostMapping(value="/usermng/userupdate")
	@ResponseBody
	@ApiOperation(value = "Web API User Mgr Update", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public String userMngUpdate( 
			@ApiParam(value = "사용자정보", required = true) @RequestBody(required = true) UserMngDto dto
			) { 
		
		String code = "202";
		
//		UserMngDto dto = new UserMngDto();
//		try {
//			JSONParser parser = new JSONParser(userInfo);
//			LinkedHashMap obj = parser.parseObject();
//			dto.setUserId(obj.get("userId").toString());
//			dto.setUserPw(obj.get("userPw").toString());
			dto.setUserPw(encoder.encode(dto.getUserPw()));
//			dto.setUserNm(obj.get("userNm").toString());
//			dto.setDeptCd(obj.get("deptCd").toString());
//			dto.setEmail (obj.get("email").toString());
//			dto.setTelno (obj.get("telno").toString());
//			dto.setSex   (obj.get("sex").toString());
//			dto.setUseYn (obj.get("useYn").toString());
//		} catch(Exception e) {e.printStackTrace();}

		dto.setUpdr("system");
		
		int cnt = userService.userMngUpdate(dto);
		if( cnt > 0) code = "200";
		
		return code;
	} 	
	
//	/** 사용자 관리 화면 : 수정 */
//	@PostMapping(value="/usermng/userupdate")
//	@ResponseBody
//	@ApiOperation(value = "Web API User Mgr Update", notes = "Web API Test")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
//			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
//	public String userMngUpdate( 
//			@ApiParam(value = "사용자정보", required = true) @RequestParam(value = "userInfo", required = true) String userInfo
//			) { 
//		
//		String code = "202";
//		
//		UserMngDto dto = new UserMngDto();
//		
//		try {
//			JSONParser parser = new JSONParser(userInfo);
//			LinkedHashMap obj = parser.parseObject();
//			dto.setUserId(obj.get("userId").toString());
//			dto.setUserPw(obj.get("userPw").toString());
//			dto.setUserNm(obj.get("userNm").toString());
//			dto.setDeptCd(obj.get("deptCd").toString());
//			dto.setEmail (obj.get("email").toString());
//			dto.setTelno (obj.get("telno").toString());
//			dto.setSex   (obj.get("sex").toString());
//			dto.setUseYn (obj.get("useYn").toString());
//		} catch(Exception e) {e.printStackTrace();}
//		
//		dto.setUpdr("system");
//		
//		int cnt = userService.userMngUpdate(dto);
//		if( cnt > 0) code = "200";
//		
//		return code;
//	} 	
	
	
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
//			System.out.println("@@@@@@@@@@ "+dto.getUserId());
//			UserMngDto dto = new UserMngDto();
//			dto.setUserId(userId);
			if( userService.userMngDelete(dto) > 0 ) cnt++;
		}
		
		if( cnt > 0) code = "200";
		
		return code;
	} 
	
//	/** 사용자관리 화면 : 삭제 */
//	@GetMapping(value="/usermng/userdelete")
//	@ResponseBody
//	@ApiOperation(value = "Web API User Mgr Delete", notes = "Web API Test")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
//			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
//	public String userMngDelete( 
//			@ApiParam(value = "User ID", required = true) @RequestParam(value = "items", required = true) List<String> items
//			) { 
//		
//		String code = "202";
//		
//		int cnt = 0;
//		
//		for(String userId : items) {
//			System.out.println("@@@@@@@@@@ "+userId);
//			UserMngDto dto = new UserMngDto();
//			dto.setUserId(userId);
//			if( userService.userMngDelete(dto) > 0 ) cnt++;
//		}
//		
//		if( cnt > 0) code = "200";
//		
//		return code;
//	} 	
	

}
