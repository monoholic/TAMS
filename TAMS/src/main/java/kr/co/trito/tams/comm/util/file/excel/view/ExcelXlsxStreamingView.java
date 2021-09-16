package kr.co.trito.tams.comm.util.file.excel.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxStreamingView;

import kr.co.trito.tams.comm.util.file.excel.ExcelWriter;

@Component("excelXlsxStreamingView")
public class ExcelXlsxStreamingView extends AbstractXlsxStreamingView{
	@Override
	protected void buildExcelDocument(Map<String, Object> model, 
			Workbook workbook, HttpServletRequest request, HttpServletResponse response) {
		new ExcelWriter(workbook, model, request, response).create();
	}
}
