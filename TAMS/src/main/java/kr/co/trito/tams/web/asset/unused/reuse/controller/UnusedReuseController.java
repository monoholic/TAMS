package kr.co.trito.tams.web.asset.unused.reuse.controller;

import java.util.HashMap;
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
import kr.co.trito.tams.web.asset.unused.reuse.dto.ReuesReqMasDto;
import kr.co.trito.tams.web.asset.unused.reuse.dto.ReuseAsetReqDto;
import kr.co.trito.tams.web.asset.unused.reuse.service.UnusedReuseService;
import kr.co.trito.tams.web.common.service.CommonService;
import kr.co.trito.tams.web.user.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@RequestMapping("/asset/unused/reuse")
@Slf4j
public class UnusedReuseController {
	@Autowired
	UnusedReuseService reuseService; 
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	ResponseService responseService;
	
	/** 재활용의뢰 목록 화면 */
	@PostMapping("/list")
	public ModelAndView unusedReuseListView(HttpServletRequest request,
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
		
		view.setViewName("/content/asset/unused/reuse/reuseList");
		return view;
	}
	
	/** 재활용의뢰 목록 : 조회 */
	@GetMapping(value = "/unusedReuseList")
	@ResponseBody
	@ApiOperation(value = "재활용목록 화면 / 리스트", notes = "재활용목록 화면을 조회한다.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> unusedReuseList(
		@ApiParam(value = "필터 / 페이징 값", required = true) @RequestParam Map<String, Object> params,
		@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail) {
		
		UserInfo userInfo = (UserInfo)userDetail;
		String userId = userInfo.getDto().getUserId();
		
		params.put("userId", userId);
		
		SearchCondition condition = new SearchCondition(params.get("currentPage").toString(), params.get("numOfRows").toString(), params);
		condition.pageSetup(reuseService.selectCountUnusedReuseList(condition));
		List<ReuesReqMasDto> list = reuseService.selectUnusedReuseList(condition);
		return responseService.success(condition, list);
	}
	
	/** 재활용의뢰 화면 : 등록 */  
	@PostMapping(value="/unusedReuseInsert")
	@ResponseBody
	@ApiOperation(value = "재활용목록 화면 / 등록", notes = "재활용목록 화면에서 의뢰를 작성한다. (재활용의뢰)")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> unusedReuseInsert(
			@ApiParam(value = "자산의뢰 등록 데이터", required = true) @RequestBody Map<String, Object> items,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail) { 
		
		UserInfo userInfo = (UserInfo)userDetail;
		String userId = userInfo.getDto().getUserId();
		
		items.put("regr", userId);
		reuseService.unusedReuseListInsert(items); 
		
		return responseService.success(null);
	}	
	
	/** 재활용의뢰 목록 화면 : 삭제 */
	@PostMapping(value="/unusedReuseDelete")
	@ResponseBody
	@ApiOperation(value = "재활용목록 화면 / 삭제", notes = "재활용목록 화면에서 의뢰를 삭제한다. (재활용의뢰 삭제)")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> unusedReuseDelete( 
			@ApiParam(value = "의뢰 번호", required = true) @RequestBody List<ReuesReqMasDto> items) { 
		
		for(int i=0 ; i<items.size() ; i++) {
			String reqno = items.get(i).getReqNo();
			
			reuseService.unusedReuseListDelete(reqno);
			
			if(items.get(i).getAsetCnt() > 0) {
				reuseService.unusedReuseListAsetDelete(reqno);
			}	
		}
		
		return responseService.success(null);
	}
	
	/** 재활용의뢰작성 화면 */
	@PostMapping("/ReuseRegList")
	@ResponseBody
	public ModelAndView requestReuseView(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		String reqNo = request.getParameter("reqNo");
		view.addObject("reqNo", reqNo);
		view.setViewName("/content/asset/unused/reuse/reuseRegist");
		return view;
	}
	
	/** 재활용의뢰작성 화면 : 조회 */
	@GetMapping(value = "/unusedReuseRegist")
	@ResponseBody
	@ApiOperation(value = "재활용의뢰작성 화면 / 조회", notes = "재활용의뢰작성 화면을 조회한다.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> unusedReuseRegistView(
			@ApiParam(value = "필터 / 페이징 값", required = true) @RequestParam Map<String, Object> params) {
		
		SearchCondition condition = new SearchCondition(params.get("currentPage").toString(),params.get("numOfRows").toString(), params);
		condition.pageSetup(reuseService.selectCountAssetList(condition));

		List<ReuseAsetReqDto> list = reuseService.selectAssetList(condition);
		List<ReuesReqMasDto> list2 = reuseService.selectUnusedReuseRegist(condition);

		return responseService.success(condition, list, list2);
	}
	
	/** 재활용의뢰작성 화면 : 수정 */  
	@PostMapping(value="/unusedReuseRegUpdate")
	@ResponseBody
	@ApiOperation(value = "재활용의뢰작성 화면 : 수정", notes = "재활용의뢰작성 화면에서 의뢰 관련 정보를 수정한다.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> unusedReuseRegUpdate(
			@ApiParam(value = "자산수정의뢰작성 화면 수정 데이터", required = true) @RequestBody Map<String, Object> items,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail) { 
		
		UserInfo userInfo = (UserInfo)userDetail;
		String userId = userInfo.getDto().getUserId();
		
		List<Map<String, Object>> list = (List<Map<String, Object>>)items.get("asetList");
		
		items.put("regr", userId);
		reuseService.unusedReuseUpdate1(items);
		
		for(int i=0; i<list.size(); i++) {
			if (list.get(i).containsKey("reqNo"))
				reuseService.unusedReuseUpdate2(list.get(i));
			
			else
				(list.get(i)).put("reqNo", items.get("reqNo").toString());
			reuseService.unusedReuseUpdate2(list.get(i));
		}
		
		return responseService.success(null);
	}	
	
	/** 재활용의뢰작성 화면 : 삭제(화면 상단) */
	@PostMapping(value="/unusedReuseRegDelete1")
	@ResponseBody
	@ApiOperation(value = "재활용의뢰작성 화면 : 삭제", notes = "재활용의뢰작성 화면에서 의뢰 정보를 삭제한다.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> unusedReuseDelete1( 
			@ApiParam(value = "의뢰 번호", required = true) @RequestBody Map<String, Object> items) {
		
		String asetYn = items.get("asetYn").toString();
		if(asetYn.equals("Y")) {
			reuseService.unusedReuseDelete3(items);
		}
		
		reuseService.unusedReuseDelete1(items);
		
		return responseService.success(null);
	}
	
	/** 자산수정 의뢰작성 화면 : 삭제(화면 하단) */
	@PostMapping(value="/unusedReuseDelete2")
	@ResponseBody
	@ApiOperation(value = "자산수정 의뢰작성 화면 : 삭제", notes = "자산수정 의뢰작성 화면 : 삭제")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> unusedReuseDelete2( 
			@ApiParam(value = "의뢰 번호", required = true) @RequestBody Map<String, Object> items) { 
		ReuseAsetReqDto dto = new ReuseAsetReqDto();
		List<String> asetList = (List<String>) items.get("asetNoList");
		
		dto.setReqNo(items.get("reqNo").toString());
		
		for(String asetNo : asetList) {
			dto.setAsetNo(asetNo);
			reuseService.unusedReuseDelete2(dto);
		}
		
		return responseService.success(null);
	}
	
	/** 담당자 팝업 화면 */
	@GetMapping(value="reuseUserPopup")
	public ModelAndView reuseUserPopupView(HttpServletRequest request) {
		
		ModelAndView view = new ModelAndView();
		view.setViewName("/content/asset/unused/reuse/reuseUserPopup");
		
		return view;
	}
	
	/** 담당자 팝업 조회 */
	@GetMapping(value="reuseUserPopupList")
	@ResponseBody
	@ApiOperation(value = "Web API Common test", notes = "Web API Test")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response>  reuseUserPopupList(
			@ApiParam(value = "검색 조건(사용자명, 사용자ID)", required = false) @RequestParam(value="searchText",required = false) String searchText) {
		
		Map<String, Object> params = new HashMap<>();
		if (!StringUtils.isEmpty(searchText))
			params.put("searchText", searchText);
		
		SearchCondition condition = new SearchCondition("0","0",params);
		
		List<ReuseAsetReqDto> list = reuseService.selectReuseUserPopupList(condition);
		condition.pageSetup(list.size());
		
		return responseService.success(condition, list);
	}
	
	/** 재활용 반출증출력 목록 화면 */
	@PostMapping("/reuseCarryingOutList")
	@ResponseBody
	public ModelAndView unusedReuseCarryingOutListView(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		
		String reqNo = request.getParameter("reqNo");
		view.addObject("reqNo", reqNo);
		
		String asetNo = request.getParameter("asetNo");
		view.addObject("asetNo", asetNo);

		view.setViewName("/content/asset/unused/reuse/reuseCarryingOutList");
		return view;
	}
	
	/** 재활용 반출증출력 화면 : 조회 */
	@GetMapping(value = "/unusedReuseCarringOutList")
	@ResponseBody
	@ApiOperation(value = "재활용반출증출력 화면 / 조회", notes = "재활용반출증출력 화면을 조회한다.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> unusedReuseCarringOutListView(
			@ApiParam(value = "필터 / 페이징 값", required = true) @RequestParam Map<String, Object> params) {

		SearchCondition condition = new SearchCondition(params.get("currentPage").toString(),params.get("numOfRows").toString(), params);
		condition.pageSetup(reuseService.selectCountAssetList(condition));

		List<ReuseAsetReqDto> list = reuseService.selectAssetList(condition);
		List<ReuesReqMasDto> list2 = reuseService.selectUnusedReuseRegist(condition);

		return responseService.success(condition, list, list2);
	}
}
