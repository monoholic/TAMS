package kr.co.trito.tams.web.asset.change.move.controller;

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

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kr.co.trito.tams.comm.util.res.Response;
import kr.co.trito.tams.comm.util.res.ResponseService;
import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.asset.change.modify.controller.AssetModifyController;
import kr.co.trito.tams.web.asset.change.modify.dto.AsetReqDto;
import kr.co.trito.tams.web.asset.change.modify.dto.ReqMasDto;
import kr.co.trito.tams.web.asset.change.modify.service.AssetModifyService;
import kr.co.trito.tams.web.asset.change.move.service.AssetMoveService;
import kr.co.trito.tams.web.asset.regist.invest.dto.AsetListDto;
import kr.co.trito.tams.web.asset.regist.invest.dto.InvestRegistDto;
import kr.co.trito.tams.web.common.service.CommonService;
import kr.co.trito.tams.web.user.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@RequestMapping("/asset/change/move")
@Slf4j
public class AssetMoveController {
	@Autowired
	AssetMoveService moveService; 
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	ResponseService responseService;
	
	/** 이동의뢰 목록 화면 */
	@PostMapping("/list")
	public ModelAndView assetMoveListView(HttpServletRequest request,
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
		
		view.setViewName("/content/asset/change/move/moveList");
		return view;
	}
	
	/** 이동의뢰 목록 : 조회 */
	@GetMapping(value = "/assetMoveList")
	@ResponseBody
	@ApiOperation(value = "이동목록 화면 / 리스트", notes = "이동목록 화면을 조회한다.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> assetMoveList(
		@ApiParam(value = "필터 / 페이징 값", required = true) @RequestParam Map<String, Object> params,
		@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail) {
		
		UserInfo userInfo = (UserInfo)userDetail;
		String userId = userInfo.getDto().getUserId();
		
		params.put("userId", userId);
		
		SearchCondition condition = new SearchCondition(params.get("currentPage").toString(), params.get("numOfRows").toString(), params);
		condition.pageSetup(moveService.selectCountAssetMovetList(condition));
		List<ReqMasDto> list = moveService.selectAssetMoveList(condition);
		return responseService.success(condition, list);
	}
	
	/** 이동의뢰 화면 : 등록 */  
	@PostMapping(value="/assetMoveInsert")
	@ResponseBody
	@ApiOperation(value = "이동목록 화면 / 등록", notes = "이동목록 화면에서 의뢰를 작성한다. (이동의뢰)")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> assetMoveInsert(
			@ApiParam(value = "자산의뢰 등록 데이터", required = true) @RequestBody Map<String, Object> items,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail) { 
		
		UserInfo userInfo = (UserInfo)userDetail;
		String userId = userInfo.getDto().getUserId();
		
		items.put("regr", userId);
		moveService.assetMoveListInsert(items); 
		
		return responseService.success(null);
	}
	
	/** 이동목록 화면 : 삭제 */
	@PostMapping(value="/assetMoveDelete")
	@ResponseBody
	@ApiOperation(value = "이동목록 화면 / 삭제", notes = "이동목록 화면에서 의뢰를 삭제한다. (이동의뢰 삭제)")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> assetMoveDelete( 
			@ApiParam(value = "의뢰 번호", required = true) @RequestBody List<ReqMasDto> items) { 
		
		for(ReqMasDto reqNo : items) {
			moveService.assetMoveListDelete(reqNo);
		}
		
		return responseService.success(null);
	}
	
	/** 이동의뢰작성 화면 : 조회 */
	@PostMapping("/moveRegList")
	@ResponseBody
	public ModelAndView requestRegistView(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		String reqNo = request.getParameter("reqNo");
		
		view.addObject("reqNo", reqNo);
		view.setViewName("/content/asset/change/move/moveRegist");
		return view;
	}
	
	/** 이동의뢰작성 화면 : 조회 */
	@GetMapping(value = "/assetMoveRegist")
	@ResponseBody
	@ApiOperation(value = "이동의뢰작성 화면 / 조회", notes = "이동의뢰작성 화면을 조회한다.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> AssetMoveRegistView(
			@ApiParam(value = "필터 / 페이징 값", required = true) @RequestParam Map<String, Object> params) {
		SearchCondition condition = new SearchCondition(params.get("currentPage").toString(),
				params.get("numOfRows").toString(), params);
		condition.pageSetup(moveService.selectCountAssetList(condition));

		List<AsetReqDto> list = moveService.selectAssetList(condition);
		List<ReqMasDto> list2 = moveService.selectAssetMoveRegist(condition);

		return responseService.success(condition, list, list2);
	}
	
	/** 이동의뢰작성 화면 : 수정 */  
	@PostMapping(value="/assetMoveRegUpdate")
	@ResponseBody
	@ApiOperation(value = "이동의뢰작성 화면 : 수정", notes = "이동의뢰작성 화면에서 의뢰 관련 정보를 수정한다.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> assetMoveRegUpdate(
			@ApiParam(value = "자산수정의뢰작성 화면 수정 데이터", required = true) @RequestBody Map<String, Object> items,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail) { 
		
		UserInfo userInfo = (UserInfo)userDetail;
		String userId = userInfo.getDto().getUserId();
		
		List<Map<String, Object>> list = (List<Map<String, Object>>)items.get("asetList");
		
		items.put("regr", userId);
		moveService.assetMoveUpdate1(items);
		
		for(int i=0; i<list.size(); i++) {
			if (list.get(i).containsKey("reqNo"))
				moveService.assetMoveUpdate2(list.get(i));
			
			else
				(list.get(i)).put("reqNo", items.get("reqNo").toString());
				moveService.assetMoveUpdate2(list.get(i));
		}
		
		return responseService.success(null);
	}
	
	/** 이동의뢰작성 화면 : 삭제(화면 상단) */
	@PostMapping(value="/assetMoveRegDelete1")
	@ResponseBody
	@ApiOperation(value = "이동의뢰작성 화면 : 삭제", notes = "이동의뢰작성 화면에서 의뢰 정보를 삭제한다.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> assetMoveDelete1( 
			@ApiParam(value = "의뢰 번호", required = true) @RequestBody Map<String, Object> items) { 
		
		moveService.assetMoveDelete1(items);
		
		return responseService.success(null);
	}
	
	/** 자산수정 의뢰작성 화면 : 삭제(화면 하단) */
	@PostMapping(value="/assetMoveDelete2")
	@ResponseBody
	@ApiOperation(value = "자산수정 의뢰작성 화면 : 삭제", notes = "자산수정 의뢰작성 화면 : 삭제")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> assetMoveDelete2( 
			@ApiParam(value = "의뢰 번호", required = true) @RequestBody Map<String, Object> items) { 
		AsetReqDto dto = new AsetReqDto();
		List<String> asetList = (List<String>) items.get("asetNoList");
		
		dto.setReqNo(items.get("reqNo").toString());
		
		for(String asetNo : asetList) {
			dto.setAsetNo(asetNo);
			moveService.assetMoveDelete2(dto);
		}
		
		return responseService.success(null);
	}
}
