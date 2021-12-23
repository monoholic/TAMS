package kr.co.trito.tams.web.asset.change.modify.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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
import kr.co.trito.tams.web.asset.change.modify.dto.AsetReqDto;
import kr.co.trito.tams.web.asset.change.modify.dto.ReqMasDto;
import kr.co.trito.tams.web.asset.change.modify.dto.ReqMasExcelDto;
import kr.co.trito.tams.web.asset.change.modify.mapper.AssetModifyMapper;
import kr.co.trito.tams.web.asset.change.modify.service.AssetModifyService;
import kr.co.trito.tams.web.common.dto.AsetMasDto;
import kr.co.trito.tams.web.standard.code.dto.CodeDto;
import kr.co.trito.tams.web.standard.codegrp.dto.CodegrpDto;
import kr.co.trito.tams.web.system.user.dto.UserMngDto;
import kr.co.trito.tams.web.system.user.dto.UserMngExcelDto;
import kr.co.trito.tams.web.system.user.service.UserMngService;
import kr.co.trito.tams.web.user.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@RequestMapping("/asset/update/reqmas")
@Slf4j
public class AssetModifyController {
	@Autowired
	AssetModifyService reqmasService; 
	
	@Autowired
	ResponseService responseService;
	
	@Autowired
	BCryptPasswordEncoder encoder;
	
	/** 자산정보수정 화면 */
	@PostMapping("/requestListView")
	public ModelAndView requestListView(HttpServletRequest request,
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
		
		view.setViewName("/content/asset/change/modify/requestList");
		return view;
	}
	
	/** 자산수정 의뢰작성 화면 */
	@PostMapping("/requestRegistView")
	@ResponseBody
	public ModelAndView requestRegistView(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		String reqNo = request.getParameter("reqNo");
		
		view.addObject("reqNo", reqNo);
		view.setViewName("/content/asset/change/modify/requestRegist");
		return view;
	}
	
	/** 자산정보수정 화면 : 조회 */
	@GetMapping(value="/requestList")
	@ResponseBody
	@ApiOperation(value = "자산정보수정 화면 : 조회", notes = "자산정보수정 화면 : 조회")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> requestList(
			@ApiParam(value = "검색 조건 파리미터 Dto", required = true) @RequestParam Map<String, Object> params,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail) {
		
		UserInfo userInfo = (UserInfo)userDetail;
		String userId = userInfo.getDto().getUserId();
		
		params.put("userId", userId);
		
		SearchCondition condition = new SearchCondition(params.get("currentPage").toString(), params.get("numOfRows").toString(), params);
		condition.pageSetup(reqmasService.selectCountRequestList(condition));
		List<ReqMasDto> list = reqmasService.selectRequestList(condition);
		return responseService.success(condition, list);
	}
	
	/** 자산정보수정 화면 : 등록 */  
	@GetMapping(value="/requestListInsert")
	@ResponseBody
	@ApiOperation(value = "자산정보수정 화면 : 등록", notes = "자산정보수정 화면 : 등록")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> requestListInsert(
			@ApiParam(value = "자산의뢰 등록 데이터", required = true) @RequestParam Map<String, Object> items,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail
			) { 
		
		UserInfo userInfo = (UserInfo)userDetail;
		String userId = userInfo.getDto().getUserId();
		
		items.put("regr", userId);
		reqmasService.requestListInsert(items); 
		
		return responseService.success(null);
	}
	
	/** 자산정보수정 화면 : 삭제 */
	@GetMapping(value="/requestListDelete")
	@ResponseBody
	@ApiOperation(value = "자산정보수정 화면 : 삭제", notes = "자산정보수정 화면 : 삭제")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> requestListDelete( 
			@ApiParam(value = "의뢰 번호", required = true) @RequestParam(value = "items", required = true) List<String> items) { 
		
		ReqMasDto dto = new ReqMasDto();

		for(String reqNo : items) {
			dto.setReqNo(reqNo);
			reqmasService.requestListDelete(dto);
		}
		
		return responseService.success(null);
	}
	
	/** 자산정보수정 화면 : 조회 - 엑셀다운 */ 
	@PostMapping(value="/requestListExcel")
	@ApiOperation(value = "자산정보수정 화면 : 조회 - 엑셀다운", notes = "자산정보수정 화면 : 조회 - 엑셀다운")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ModelAndView reqMasListExcel(
			@ApiParam(value = "검색 조건 파리미터 Dto", required = true) @RequestParam Map<String, Object> params,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail) { 
		
		UserInfo userInfo = (UserInfo)userDetail;
		String userId = userInfo.getDto().getUserId();
		
		params.put("userId", userId);
		
		SearchCondition condition = new SearchCondition("0", "0", params);
		condition.pageSetup(reqmasService.selectCountRequestList(condition));
		List<ReqMasExcelDto> list = reqmasService.selectReqExcelList(condition);
		
		return new ModelAndView("excelXlsxView", makeExcelData(list)) ;
	} 	
	
	
	/** 엑셀 다운로드용 데이터 생성 */  
	private Map<String, Object> makeExcelData(List<ReqMasExcelDto> list) {
		Map<String, Object> map = new HashMap<>();
		map.put(ExcelConstant.FILE_NAME, "수정의뢰목록 리스트");
		map.put(ExcelConstant.HEAD, Arrays.asList("의뢰번호", "의뢰구분", "의뢰명", "의뢰자 부서", "의뢰자", "의뢰일자", "의뢰상태", "자산 수"));
		
		ObjectMapper objectMapper = new ObjectMapper();
		List<List<String>> rows = new ArrayList<List<String>>();
		
		for( ReqMasExcelDto req: list) {
			Map<String, String> m = objectMapper.convertValue(req, Map.class);
			List<String> l = new ArrayList<>(m.values());
			
			rows.add(l);
		}
		map.put(ExcelConstant.BODY, rows);
		return map;
	}
	
	/** 자산수정 의뢰작성 화면 : 조회 */
	@GetMapping(value="/requestRegist")
	@ResponseBody
	@ApiOperation(value = "자산수정 의뢰작성 화면 : 조회", notes = "자산수정 의뢰작성 화면 : 조회")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> requestRegist(
			@ApiParam(value = "검색 조건 파리미터 Dto", required = true) @RequestParam Map<String, Object> params,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail) {
		
		UserInfo userInfo = (UserInfo)userDetail;
		String userId = userInfo.getDto().getUserId();

		params.put("userId", userId);
		
		SearchCondition condition = new SearchCondition(params.get("currentPage").toString(), params.get("numOfRows").toString(), params);
		condition.pageSetup(reqmasService.selectCountAsetReqList(condition));
		List<ReqMasDto> list = reqmasService.selectRequestRegist(condition);
		List<AsetReqDto> list2 = reqmasService.selectAsetReqList(condition);
		
		return responseService.success(condition, list, list2);
	}
	
	/** 자산수정 의뢰작성 화면 : 수정 */  
	@PostMapping(value="/requestRegUpdate")
	@ResponseBody
	@ApiOperation(value = "자산수정 의뢰작성 화면 : 수정", notes = "자산수정 의뢰작성 화면 : 수정")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> requestRegUpdate(
			@ApiParam(value = "자산수정의뢰작성 화면 수정 데이터", required = true) @RequestBody Map<String, Object> items,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail) { 
		
		UserInfo userInfo = (UserInfo)userDetail;
		String userId = userInfo.getDto().getUserId();

		List<Map<String, Object>> list = (List<Map<String, Object>>)items.get("asetList");
		
		items.put("regr", userId);
		reqmasService.requestRegUpdate1(items);
		
		for(int i=0; i<list.size(); i++) {
			if (list.get(i).containsKey("reqNo"))
				reqmasService.requestRegUpdate2(list.get(i));
			
			else
				(list.get(i)).put("reqNo", items.get("reqNo").toString());
				reqmasService.requestRegUpdate2(list.get(i));
		}
		
		return responseService.success(null);
	}

	
	/** 자산수정 의뢰작성 화면 : 삭제(화면 상단) */
	@PostMapping(value="/requestRegDelete")
	@ResponseBody
	@ApiOperation(value = "자산수정 의뢰작성 화면 : 삭제", notes = "자산수정 의뢰작성 화면 : 삭제")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> requestRegDelete( 
			@ApiParam(value = "의뢰 번호", required = true) @RequestBody Map<String, Object> items) { 
		
		reqmasService.requestRegDelete(items);
		
		return responseService.success(null);
	}
	
	/** 자산수정 의뢰작성 화면 : 삭제(화면 하단) */
	@PostMapping(value="/requestRegDelete2")
	@ResponseBody
	@ApiOperation(value = "자산수정 의뢰작성 화면 : 삭제", notes = "자산수정 의뢰작성 화면 : 삭제")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> requestRegDelete2( 
			@ApiParam(value = "의뢰 번호", required = true) @RequestBody Map<String, Object> items) { 
		// System.out.println(items.get("asetNoList"));
		
		AsetReqDto dto = new AsetReqDto();
		List<String> asetList = (List<String>) items.get("asetNoList");
		
		dto.setReqNo(items.get("reqNo").toString());
		
		for(String asetNo : asetList) {
			dto.setAsetNo(asetNo);
			reqmasService.requestRegDelete2(dto);
		}
		
		return responseService.success(null);
	}
}

