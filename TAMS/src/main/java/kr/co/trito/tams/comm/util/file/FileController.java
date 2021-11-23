package kr.co.trito.tams.comm.util.file;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.util.StringUtils;

import kr.co.trito.tams.comm.util.file.excel.ExcelConstant;
import kr.co.trito.tams.comm.util.file.excel.ExcelDto;
import kr.co.trito.tams.comm.util.file.excel.ExcelReader;
import kr.co.trito.tams.comm.util.file.excel.InvDto;
import kr.co.trito.tams.comm.util.file.excel.SampleDto;
import kr.co.trito.tams.comm.util.res.GeneralResponse;
import kr.co.trito.tams.comm.util.res.Response;
import kr.co.trito.tams.comm.util.res.ResponseService;
import kr.co.trito.tams.web.user.dto.UserInfo;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/file")
public class FileController {
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	ResponseService responseService;
	
	@Autowired
	private ExcelReader excelReader;
	
	@PostMapping("/upload")
	@ResponseBody
	public ResponseEntity<? extends Response> upload(@RequestParam("files") MultipartFile[] files) {
		List<FileDto> list = Arrays.asList(files)
				.stream()
				.map(file -> upload(file))
				.collect(Collectors.toList());
		return responseService.success(new GeneralResponse<>(list));
	}
	
	@PostMapping("/download/{fileName:.+}/{orgFileName:.+}")
	@ResponseBody
	public ResponseEntity<Resource> download(@PathVariable(value="fileName") String fileName, @PathVariable(value="orgFileName") String orgFileName, HttpServletRequest request){
		String contentType = null;
		Resource resource = fileService.load(fileName); 
		
		try {
			log.error("@@@@ "+resource.getFile().getAbsolutePath());
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
			
			if( StringUtils.isEmpty(contentType) ) contentType = "application/octet-stream";
			
		} catch (Exception e) {
			log.info("Could not determine file type.");
			contentType = "application/octet-stream";
		}
		
		String originFileName = "";
		try{
			originFileName = URLDecoder.decode(orgFileName, "UTF-8");
		}catch(IOException ioe) {}
		log.error("@@@@ [orgFileName] "+originFileName);
		
        return ResponseEntity.ok()
            	.contentType(MediaType.parseMediaType(contentType))
            	.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + getFileNm(getBrowser(request), originFileName) + "\"")
            	.body(resource);		
	}
	
	@PostMapping("/download")
	@ResponseBody
	public ResponseEntity<Resource> download2(@RequestParam(value="fileName") String fileName, @RequestParam(value="orgFileName") String orgFileName, HttpServletRequest request){
		String contentType = null;
		Resource resource = fileService.load(fileName); 
		
		try {
			log.error("@@@@ "+resource.getFile().getAbsolutePath());
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
			
			if( StringUtils.isEmpty(contentType) ) contentType = "application/octet-stream";
			
		} catch (Exception e) {
			log.info("Could not determine file type.");
			contentType = "application/octet-stream";
		}
		
		String originFileName = "";
		try{
			originFileName = URLDecoder.decode(orgFileName, "UTF-8");
		}catch(IOException ioe) {}
		log.error("@@@@ [orgFileName] "+originFileName);
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + getFileNm(getBrowser(request), originFileName) + "\"")
				.body(resource);		
	}
	
	@PostMapping("/readExcel")
	@ResponseBody
	public List<ExcelDto> readExcel(@RequestParam("file") MultipartFile multipartFile, @RequestParam(value="gubun", required=false) String gubun, @AuthenticationPrincipal UserDetails userDetail) throws IOException, InvalidFormatException {
		
		ExcelDto dto = null;
		
		List<GrantedAuthority> auths = new ArrayList<>(userDetail.getAuthorities());
		log.error("@@@@@@@ : "+auths.get(0).getAuthority());			
		
		UserInfo userDto = (UserInfo)userDetail;
		String userId = userDto.getDto().getUserId();
		log.error("@@ user id : "+userId);
		
		if( StringUtils.isEmpty(gubun) ) gubun = "1";
		log.error("@@[readExcel] "+gubun);
		if( "1".equals(gubun) ) {
			dto = new SampleDto();
		} else if("2".equals(gubun) ) {
			dto = new InvDto();
		}
		
		dto.setRegr(userId);
			
		return excelReader.readFileToList(multipartFile, dto::row);
	}	
	
	public FileDto upload(MultipartFile file) {
		String orgFileName = org.springframework.util.StringUtils.cleanPath(file.getOriginalFilename());
		String saveName = fileService.store(file);
		
		String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/file/download/")
				.path(saveName)
				.path("/")
				.path(orgFileName)
				.toUriString();
		
		return FileDto.builder()
				.fileNm(saveName)
				.orgFileNm(orgFileName)
				.fileDownloadUri(downloadUri)
				.fileType(file.getContentType())
				.fileSize(file.getSize())
				.build();
	}	
	

	/* --------------------------  Excel Download Example ---------------------------- 
	 * 엑셀 다운로드 처리할 Controller 단에서 엑셀 헤더, 출력 데이터, 파일명 정보를
	 * Map에 생성하여 전달 사용
	  --------------------------------------------------------------------------------------------- */
	
	@GetMapping("excel-xls")
	public ModelAndView xlsView() {
		return new ModelAndView("excelXlsView", initExcelData());
	}

	@GetMapping("excel-xlsx")
	public ModelAndView xlsxView() {
		return new ModelAndView("excelXlsxView", initExcelData());
	}

	@GetMapping("excel-xlsx-streaming")
	public ModelAndView xlsxStreamingView() {
		return new ModelAndView("excelXlsxStreamingView", initExcelData());
	}

	private Map<String, Object> initExcelData() {
		Map<String, Object> map = new HashMap<>();
		map.put(ExcelConstant.FILE_NAME, "한글엑셀명");
		map.put(ExcelConstant.HEAD, Arrays.asList("ID", "NAME", "COMMENT"));
		map.put(ExcelConstant.BODY,
				Arrays.asList(
						Arrays.asList("A", "a", "가"),
						Arrays.asList("B", "b", "나"),
						Arrays.asList("C", "c", "다")
				));
		return map;
	}

	
	/* --------------------------  Excel Download Example -------------------------- */		
	
	public String getBrowser(HttpServletRequest req) {
		String userAgent = req.getHeader("User-Agent");
		if (userAgent.indexOf("MSIE") > -1 
				|| userAgent.indexOf("Trident") > -1  //IE11
				|| userAgent.indexOf("Edge") > -1) {
			return "MSIE";
		} else if (userAgent.indexOf("Chrome") > -1) {
			return "Chrome";
		} else if (userAgent.indexOf("Opera") > -1) {
			return "Opera";
		} else if (userAgent.indexOf("Safari") > -1) {
			return "Safari";
		} else if (userAgent.indexOf("Firefox") > -1) {
			return "Firefox";
		} else {
			return null;
		}
	}

	@SuppressWarnings("deprecation")
	public String getFileNm(String browser, String fileNm) {
		String reFileNm = null;
		try {
			if (browser.equals("MSIE") || browser.equals("Trident") || browser.equals("Edge")) {
				reFileNm = URLEncoder.encode(fileNm, "UTF-8").replaceAll("\\+", "%20");
			} else {
				if (browser.equals("Chrome")) {
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < fileNm.length(); i++) {
						char c = fileNm.charAt(i);
						if (c > '~') {
							sb.append(URLEncoder.encode(Character.toString(c), "UTF-8"));
						} else {
							sb.append(c);
						}
					}
					reFileNm = sb.toString();
				} else {
					reFileNm = new String(fileNm.getBytes("UTF-8"), "ISO-8859-1");
				}
				if (browser.equals("Safari") || browser.equals("Firefox"))
					reFileNm = URLDecoder.decode(reFileNm);
			}
		} catch (Exception e) {
		}
		return reFileNm;
	}

	
	
}
