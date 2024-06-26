package kr.co.trito.tams.comm.util.file.excel;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ExcelReader {
	
	public <T> List<T> readFileToList(final MultipartFile multipartFile, final Function<Row, T> rowFunc) throws IOException, InvalidFormatException {
		Workbook workbook = readWorkbook(multipartFile);
		Sheet sheet = workbook.getSheetAt(0);
		int rowCount = sheet.getPhysicalNumberOfRows();
		return IntStream.range(0, rowCount)
				.mapToObj(rowIndex -> rowFunc.apply(sheet.getRow(rowIndex)))
				.collect(Collectors.toList());
	}
	
	private Workbook readWorkbook(MultipartFile multipartFile) throws IOException, InvalidFormatException {
		verifyFileExtension(multipartFile);
		return multipartFileToWorkbook(multipartFile);
	}	

	private void verifyFileExtension(MultipartFile multipartFile) throws InvalidFormatException {
		final String originalFilename = multipartFile.getOriginalFilename();
		
		if (!isExcelExtension(originalFilename)) {
			throw new InvalidFormatException("This file extension is not verify");
		}
	}
	
	private boolean isExcelExtension(String fileName) {
		return fileName.endsWith(ExcelConstant.XLS) || fileName.endsWith(ExcelConstant.XLSX);
	}	
	
	private boolean isExcelXls(String fileName) {
		return fileName.endsWith(ExcelConstant.XLS);
	}	
	
	private boolean isExcelXlsx(String fileName) {
		return fileName.endsWith(ExcelConstant.XLSX);
	}
	
	private Workbook multipartFileToWorkbook(MultipartFile multipartFile) throws IOException {
		if (isExcelXls(multipartFile.getOriginalFilename())) { 
			return new HSSFWorkbook(multipartFile.getInputStream());
		} else if (isExcelXlsx(multipartFile.getOriginalFilename())) { 
			return new XSSFWorkbook(multipartFile.getInputStream());
		}
		return null;
	}		
}
