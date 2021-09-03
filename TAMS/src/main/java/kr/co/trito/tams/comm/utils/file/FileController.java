package kr.co.trito.tams.comm.utils.file;

import java.io.IOException;
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

import kr.co.trito.tams.comm.utils.file.excel.ExcelConstant;
import kr.co.trito.tams.comm.utils.file.excel.ExcelReader;
import kr.co.trito.tams.comm.utils.file.excel.SampleDto;
import kr.co.trito.tams.comm.utils.res.GeneralResponse;
import kr.co.trito.tams.comm.utils.res.Response;
import kr.co.trito.tams.comm.utils.res.ResponseService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("file")
public class FileController {
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	ResponseService responseService;
	
	@Autowired
	private ExcelReader excelReader;
	
	@PostMapping("upload")
	@ResponseBody
	public ResponseEntity<? extends Response> upload(@RequestParam("files") MultipartFile[] files) {
		List<FileDto> list = Arrays.asList(files)
				.stream()
				.map(file -> upload(file))
				.collect(Collectors.toList());
		return responseService.success(new GeneralResponse<>(list));
	}
	
	@GetMapping("download/{fileName:.+}")
	@ResponseBody
	public ResponseEntity<Resource> download(@PathVariable String fileName, HttpServletRequest request){
		String contentType = null;
		Resource resource = fileService.load(fileName);
		
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (Exception e) {
			log.info("Could not determine file type.");
			contentType = "application/octet-stream";
		}
		
        return ResponseEntity.ok()
            	.contentType(MediaType.parseMediaType(contentType))
            	.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
            	.body(resource);		
	}
	
	@PostMapping("readExcel")
	@ResponseBody
	public List<SampleDto> readExcel(@RequestParam("file") MultipartFile multipartFile) throws IOException, InvalidFormatException {
		return excelReader.readFileToList(multipartFile, SampleDto::row);
	}	
	
	private FileDto upload(MultipartFile file) {
		String saveName = fileService.store(file);
		String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/file/download/")
				.path(saveName)
				.toUriString();
		
		return FileDto.builder()
				.fileName(saveName)
				.fileDownloadUri(downloadUri)
				.fileType(file.getContentType())
				.size(file.getSize())
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
	
}
