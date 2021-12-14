package kr.co.trito.tams.web.asset.status.information.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
import kr.co.trito.tams.web.asset.status.information.dto.AssetStatusDto;
import kr.co.trito.tams.web.asset.status.information.dto.AssetStatusExcelDto;
import kr.co.trito.tams.web.asset.status.information.service.AssetInformationService;
import kr.co.trito.tams.web.user.dto.UserDto;
import kr.co.trito.tams.web.user.dto.UserInfo;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/asset/status/information")
public class AssetInfomationController {

	@Autowired
	ResponseService responseService;
	
	@Autowired
	AssetInformationService assetInformationService;
	
	@PostMapping("assetStatus")
	public ModelAndView assetStatusView(Authentication authentication, HttpServletRequest request) {
	
		ModelAndView view = new ModelAndView();
		view.addObject("menuId", request.getParameter("menuId"));
		view.addObject("menuNm", request.getParameter("menuNm"));
		view.addObject("menuDesc", request.getParameter("menuDesc"));
		view.addObject("url", request.getParameter("url"));
		UserDto userDto = ((UserInfo) authentication.getPrincipal()).getDto();
		view.addObject("userDto", userDto);		
		view.setViewName("/content/asset/status/information/assetStatus");		
		
		return view;
	}	
	
	
	@GetMapping(value="/assetStatusList")
	@ResponseBody
	@ApiOperation(value = "자산현황 조회", notes = "자산현황 조회")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> assetStatusList(
				@ApiParam(value = "검색 조건 파리미터 Dto", required = true) @RequestParam Map<String, Object> params) { 

		SearchCondition condition = new SearchCondition(params.get("currentPage").toString(), params.get("numOfRows").toString(), params);			
		condition.pageSetup(assetInformationService.selectAssetStatusCount(condition));		
		List<AssetStatusDto> list = assetInformationService.selectAssetStatusList(condition);		
		return responseService.success(condition, list);
	}	
	
	
	@PostMapping(value="/excelDownload")
	@ApiOperation(value = "엑셀 다운로드", notes = "자산현황 리스트 엑셀 다운로드")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
			@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ModelAndView userMngListExcel(
			@ApiParam(value = "검색 조건 파리미터 Dto", required = true) @RequestParam Map<String, Object> params) { 
				
		SearchCondition condition = new SearchCondition(params.get("currentPage").toString(), params.get("numOfRows").toString(), params);			
		condition.pageSetup(assetInformationService.selectAssetStatusCount(condition));		
		List<AssetStatusExcelDto> list = assetInformationService.selectAssetStatusExcel(condition);				


		List<List<String>> rows = new ArrayList<List<String>>();
		
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> map = null;
		List<String> row = null;
		
		for(AssetStatusExcelDto dto : list){
			map = objectMapper.convertValue(dto, Map.class);
			row = new ArrayList<>(map.values());
			rows.add(row);
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ExcelConstant.FILE_NAME, "자산현황");
		paramMap.put(ExcelConstant.HEAD, 
				Arrays.asList("자산번호", "자산명", "자산유형1", "자산유형2", "자산유형3","메이커", "모델", "S/N", "사업부", "부서", "담당자", "자산상태"));				
		paramMap.put(ExcelConstant.BODY, rows);
		
		return new ModelAndView("excelXlsxView", paramMap) ;
	}	
}
