package kr.co.trito.tams.web.asset.unused.regist.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import kr.co.trito.tams.web.asset.unused.regist.dto.RegistAsetReqDto;
import kr.co.trito.tams.web.asset.unused.regist.dto.RegistReqMasDto;
import kr.co.trito.tams.web.asset.unused.regist.service.UnusedRegistService;
import kr.co.trito.tams.web.user.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@RequestMapping("/asset/unused/regist")
@Slf4j
public class UnusedRegistController {
	@Autowired
	UnusedRegistService registService; 
	
	@Autowired
	ResponseService responseService;
	
	/** 유휴의뢰 목록 화면 */
	@PostMapping("/list")
	public ModelAndView unusedRegistListView(HttpServletRequest request,
			@ApiParam(value = "필터 / 페이징 값", required = true) @RequestParam Map<String, Object> params) {
		ModelAndView view = new ModelAndView();
		if (params != null) {
			view.addObject("menuId", params.get("menuId").toString());
			view.addObject("menuNm", params.get("menuNm").toString());
			view.addObject("menuDesc", params.get("menuDesc").toString());
		} else {
			view.addObject("menuId", request.getParameter("menuId"));
			view.addObject("menuNm", request.getParameter("menuNm"));
			view.addObject("menuDesc", request.getParameter("menuDesc"));
		}
		
		view.addObject("loadParams", StringUtils.defaultIfBlank(request.getParameter("loadParams"), "N"));
		view.addObject("url", request.getParameter("url"));
		
		view.setViewName("/content/asset/unused/regist/registList");
		return view;
	}
	
	/** 유휴의뢰 목록 : 조회 */
	@GetMapping(value = "/unusedRegistList")
	@ResponseBody
	@ApiOperation(value = "유휴목록 화면 / 리스트", notes = "유휴목록 화면을 조회한다.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> unusedRegistList(
		@ApiParam(value = "필터 / 페이징 값", required = true) @RequestParam Map<String, Object> params,
		@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail) {
		
		UserInfo userInfo = (UserInfo)userDetail;
		String userId = userInfo.getDto().getUserId();
		
		params.put("userId", userId);
		
		SearchCondition condition = new SearchCondition(params.get("currentPage").toString(), params.get("numOfRows").toString(), params);
		condition.pageSetup(registService.selectCountUnusedRegistList(condition));
		List<RegistReqMasDto> list = registService.selectUnusedRegistList(condition);
		return responseService.success(condition, list);
	}
	
	/** 유휴의뢰 화면 : 등록 */  
	@PostMapping(value="/unusedRegistInsert")
	@ResponseBody
	@ApiOperation(value = "유휴목록 화면 / 등록", notes = "유휴목록 화면에서 의뢰를 작성한다. (유휴의뢰)")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> unusedRegistInsert(
			@ApiParam(value = "자산의뢰 등록 데이터", required = true) @RequestBody Map<String, Object> items,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail) { 
		
		UserInfo userInfo = (UserInfo)userDetail;
		String userId = userInfo.getDto().getUserId();
		
		items.put("regr", userId);
		registService.unusedRegistListInsert(items); 
		
		return responseService.success(null);
	}	
	
	/** 유휴의뢰 목록 화면 : 삭제 */
	@PostMapping(value="/unusedRegistDelete")
	@ResponseBody
	@ApiOperation(value = "유휴목록 화면 / 삭제", notes = "유휴목록 화면에서 의뢰를 삭제한다. (유휴의뢰 삭제)")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> unusedRegistDelete( 
			@ApiParam(value = "의뢰 번호", required = true) @RequestBody List<RegistReqMasDto> items) { 
		
		for(RegistReqMasDto reqNo : items) {
			registService.unusedRegistListDelete(reqNo);
		}
		
		return responseService.success(null);
	}
	
	/** 유휴의뢰작성 화면 */
	@PostMapping("/RegistRegList")
	@ResponseBody
	public ModelAndView requestRegistView(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		String reqNo = request.getParameter("reqNo");
		view.addObject("reqNo", reqNo);
		view.setViewName("/content/asset/unused/regist/registRegist");
		return view;
	}
	
	/** 유휴의뢰작성 화면 : 조회 */
	@GetMapping(value = "/unusedRegistRegist")
	@ResponseBody
	@ApiOperation(value = "유휴의뢰작성 화면 / 조회", notes = "유휴의뢰작성 화면을 조회한다.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> unusedRegistRegistView(
			@ApiParam(value = "필터 / 페이징 값", required = true) @RequestParam Map<String, Object> params) {
		
		SearchCondition condition = new SearchCondition(params.get("currentPage").toString(),params.get("numOfRows").toString(), params);
		condition.pageSetup(registService.selectCountAssetList(condition));

		List<RegistAsetReqDto> list = registService.selectAssetList(condition);
		List<RegistReqMasDto> list2 = registService.selectUnusedRegistRegist(condition);

		return responseService.success(condition, list, list2);
	}
	
	/** 유휴의뢰작성 화면 : 수정 */  
	@PostMapping(value="/unusedRegistRegUpdate")
	@ResponseBody
	@ApiOperation(value = "유휴의뢰작성 화면 : 수정", notes = "유휴의뢰작성 화면에서 의뢰 관련 정보를 수정한다.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> unusedRegistRegUpdate(
			@ApiParam(value = "자산수정의뢰작성 화면 수정 데이터", required = true) @RequestBody Map<String, Object> items,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail) { 
		
		UserInfo userInfo = (UserInfo)userDetail;
		String userId = userInfo.getDto().getUserId();
		
		List<Map<String, Object>> list = (List<Map<String, Object>>)items.get("asetList");
		
		items.put("regr", userId);
		registService.unusedRegistUpdate1(items);
		
		for(int i=0; i<list.size(); i++) {
			if (list.get(i).containsKey("reqNo"))
				registService.unusedRegistUpdate2(list.get(i));
			
			else
				(list.get(i)).put("reqNo", items.get("reqNo").toString());
			registService.unusedRegistUpdate2(list.get(i));
		}
		
		return responseService.success(null);
	}	
	
	/** 유휴의뢰작성 화면 : 삭제(화면 상단) */
	@PostMapping(value="/unusedRegistRegDelete1")
	@ResponseBody
	@ApiOperation(value = "유휴의뢰작성 화면 : 삭제", notes = "유휴의뢰작성 화면에서 의뢰 정보를 삭제한다.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> unusedRegisteDelete1( 
			@ApiParam(value = "의뢰 번호", required = true) @RequestBody Map<String, Object> items) {
		
		String asetYn = items.get("asetYn").toString();
		if(asetYn.equals("Y")) {
			registService.unusedRegistDelete3(items);
		}
		
		registService.unusedRegistDelete1(items);
		
		return responseService.success(null);
	}
	
	/** 자산수정 의뢰작성 화면 : 삭제(화면 하단) */
	@PostMapping(value="/unusedRegistDelete2")
	@ResponseBody
	@ApiOperation(value = "자산수정 의뢰작성 화면 : 삭제", notes = "자산수정 의뢰작성 화면 : 삭제")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> unusedRegisteDelete2( 
			@ApiParam(value = "의뢰 번호", required = true) @RequestBody Map<String, Object> items) { 
		RegistAsetReqDto dto = new RegistAsetReqDto();
		List<String> asetList = (List<String>) items.get("asetNoList");
		
		dto.setReqNo(items.get("reqNo").toString());
		
		for(String asetNo : asetList) {
			dto.setAsetNo(asetNo);
			registService.unusedRegistDelete2(dto);
		}
		
		return responseService.success(null);
	}
}
