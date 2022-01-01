package kr.co.trito.tams.web.system.bbs.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.json.JSONParser;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kr.co.trito.tams.comm.util.file.FileController;
import kr.co.trito.tams.comm.util.file.FileDto;
import kr.co.trito.tams.comm.util.res.Response;
import kr.co.trito.tams.comm.util.res.ResponseService;
import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.common.service.CommonService;
import kr.co.trito.tams.web.standard.code.dto.CodeDto;
import kr.co.trito.tams.web.standard.codegrp.dto.CodegrpDto;
import kr.co.trito.tams.web.system.bbs.dto.BbsDto;
import kr.co.trito.tams.web.system.bbs.service.BbsService;
import kr.co.trito.tams.web.user.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@Slf4j
@RequestMapping("/system/bbs")
public class BbsController {
	@Autowired
	ResponseService responseService;
	
	@Autowired
	FileController fileController;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	BbsService bbsService;

	/** 공지사항 관리 화면 */
	@PostMapping("/mng/list")
	public ModelAndView bbsMngView(HttpServletRequest request) {
		
		ModelAndView view = new ModelAndView();
		view.addObject("menuId", request.getParameter("menuId"));
		view.addObject("menuNm", request.getParameter("menuNm"));
		view.addObject("menuDesc", request.getParameter("menuDesc"));
		view.addObject("url", request.getParameter("url"));
		
		view.setViewName("/content/system/bbs/bbsMng");
		    
		return view;
	}
	
	/** 공지사항 화면 */
	@PostMapping("/list")
	public ModelAndView bbsMngList(HttpServletRequest request) {
		
		ModelAndView view = new ModelAndView();
		view.addObject("menuId", request.getParameter("menuId"));
		view.addObject("menuNm", request.getParameter("menuNm"));
		view.addObject("menuDesc", request.getParameter("menuDesc"));
		view.addObject("url", request.getParameter("url"));
		
		view.setViewName("/content/system/bbs/bbsList");
		    
		return view;
	}
	
	/** 공지사항 상세 화면 */
	@PostMapping("/detailBbs")
	public ModelAndView detailBbs(HttpServletRequest request) {
		
		ModelAndView view = new ModelAndView();

		BbsDto dto = new BbsDto();
		String bbsId = request.getParameter("bbsId");
		dto.setBbsId(bbsId);
		bbsService.updateViewCnt(dto);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("searchText", bbsId);
		
		SearchCondition condition = new SearchCondition("0","0", params);
		dto = bbsService.selectbbsInfo(condition);
		
		List<FileDto> files = commonService.selectFileList(condition);
		
		view.addObject("files", files);
		view.addObject("bbsDto", dto);
		
		view.setViewName("/content/system/bbs/detailBbs");
		return view;
	}
	
	/** 공지사항관리 화면 : 조회 */
	@GetMapping(value="/bbsMng/bbsMngList")
	@ResponseBody
	@ApiOperation(value = "공지사항관리 화면 : 조회", notes = "공지사항관리 화면 : 조회")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> bbsMngList(
			@ApiParam(value = "검색 조건 파리미터 Dto", required = true) @RequestParam Map<String, Object> params) { 

		SearchCondition condition = new SearchCondition(params.get("currentPage").toString(), params.get("numOfRows").toString(), params);
		condition.pageSetup(bbsService.selectCountBbsMng(condition));
		List<BbsDto> list = bbsService.selectBbsMngList(condition);
		return responseService.success(condition, list);
	}
	
	/** 공지사항관리 화면 : 등록 */  
	@PostMapping(value="/bbsMng/bbsInsert")
	@ResponseBody
	@ApiOperation(value = "공지사항관리 화면 : 등록", notes = "공지사항관리 화면 : 등록")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public String bbsMngInsert(
			@ApiParam(value = "파일정보", required = true)  MultipartFile[] files,
			@ApiParam(value = "공지사항관리 등록 데이터", required = true) @RequestParam Map<String, Object> items,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail) { 
		
		String code = "202";
		
		BbsDto dto = new BbsDto();
		UserInfo userInfo = (UserInfo)userDetail;
		String userId = userInfo.getDto().getUserId();
		
		items.put("regr", userId);
		items.put("userId", userId);
		
		int cnt = bbsService.bbsMngInsert(items);  
		
		if(files != null) {
			List<FileDto> list = Arrays.asList(files)
					.stream()
					.map(file -> fileController.upload(file))
					.collect(Collectors.toList());
			
			for(FileDto f : list) {
		    	 f.setRfncKey1(String.valueOf(dto.getBbsId()));
		    	 f.setUseYn("Y");
		    	 log.error(f.toString());
		    	 commonService.saveFiles(f);
		    }
		}
		
		if( cnt > 0 ) code = "200";
		
		return code;
	}
	
	/** 공지사항관리 화면 : 수정 */
	@PostMapping(value="/bbsMng/bbsUpdate")
	@ResponseBody
	@ApiOperation(value = "공지사항관리 화면 : 수정", notes = "공지사항관리 화면 : 수정")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public String bbsMngUpdate(
			@ApiParam(value = "파일정보", required = false) @RequestParam(value="files", required=false)MultipartFile[] files,
			@ApiParam(value = "삭제파일정보", required = false) @RequestParam(value="delFiles", required=false)String delFiles,
			@ApiParam(value = "공지사항관리 수정 데이터", required = true) @RequestParam Map<String, Object> items,
			@ApiParam(value = "사용자정보", required = true) @AuthenticationPrincipal UserDetails userDetail
			) { 
		String code = "202";
		
		UserInfo userInfo = (UserInfo)userDetail;
		String userId = userInfo.getDto().getUserId(); 
		
		items.put("updr", userId);
		
		if( files != null && files.length > 0 ) {
		      
		      //첨부파일 업로드
		      List<FileDto> list = Arrays.asList(files)
					  .stream()
					  .map(file -> fileController.upload(file))
					  .collect(Collectors.toList());
		      
		      //첨부파일 정보 저장
		      for( FileDto f : list) {
		    	  f.setRfncKey1(String.valueOf(items.get("bbsId").toString()));
		    	  f.setUseYn("Y");
		    	  log.error(f.toString());
		    	  commonService.saveFiles(f);
		      }
	      }
		
		//파일 삭제처리 - use_yn = 'N'
		if( delFiles != null && !"".equals(delFiles) ) {
		  
		    List<LinkedHashMap>  array = null;
		    
		    try {
		    	JSONParser parser = new JSONParser(delFiles);
		    	array = (List<LinkedHashMap> )parser.parse();
		    
		    for(int i=0;i<array.size();i++){
		    	LinkedHashMap map = array.get(i);
		    	log.error(map.get("id").toString());
		    	  
		    	FileDto dFile = new FileDto();
		    	dFile.setFileId(Long.parseLong(map.get("id").toString()));
  	  	  	    commonService.deleteFiles(dFile);
		    }
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
	    }  
			
		int cnt = bbsService.bbsMngUpdate(items);
		if( cnt > 0) code = "200";
		
		return code;
	}
	
	/** 공지사항관리 화면 : 삭제 */
	@GetMapping(value="/bbsMng/bbsDelete")
	@ResponseBody
	@ApiOperation(value = "공지사항관리 화면 : 삭제", notes = "공지사항관리 화면 : 삭제")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public String bbsMngDelete( 
			@ApiParam(value = "공지사항 ID", required = true) @RequestParam(value = "items", required = true) List<String> items
			) { 
		
		String code = "202";
		
		int cnt = 0;
		
		for(String bbsId : items) {
			BbsDto dto = new BbsDto();
			
			dto.setBbsId(bbsId);
			
			if( bbsService.bbsMngDelete(dto) > 0 ) 
				cnt++;
		}
		
		if(cnt > 0) code = "200";
		
		return code;
	}
	
	/** 공지사항 화면 : 조회 */
	@GetMapping(value="/bbsMng/bbsList")
	@ResponseBody
	@ApiOperation(value = "공지사항 화면 : 조회", notes = "공지사항 화면 : 조회")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로 수행 됨"),
	@ApiResponse(code = 500, message = "API 서버에 문제가 발생하였음") })
	public ResponseEntity<? extends Response> bbsList(
			@ApiParam(value = "검색 조건 파리미터 Dto", required = true) @RequestParam Map<String, Object> params) { 

		SearchCondition condition = new SearchCondition(params.get("currentPage").toString(), params.get("numOfRows").toString(), params);
		condition.pageSetup(bbsService.selectCountBbs(condition));
		List<BbsDto> list = bbsService.selectBbsList(condition);
		return responseService.success(condition, list);
	}
	
	
	@PostMapping("/bbsMng/bbsView")
	public ResponseEntity<? extends Response> bbsView(
			@ApiParam(value = "검색 조건(게시판ID)", required = true) @RequestBody(required = true) String searchText) {
		
		Map<String, Object> params = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		
		if (!StringUtils.isEmpty(searchText))
			params.put("searchText", searchText);
		
		SearchCondition condition = new SearchCondition("0", "0", params);
		BbsDto bbsInfo = bbsService.selectbbsInfo(condition);
		
		List<FileDto> files = commonService.selectFileList(condition);
		
		result.put("bbsInfo", bbsInfo);
		result.put("files", files);
		
		return responseService.success(result);
	}
}
