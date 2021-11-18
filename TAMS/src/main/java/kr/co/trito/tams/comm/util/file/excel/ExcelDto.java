package kr.co.trito.tams.comm.util.file.excel;

import org.apache.poi.ss.usermodel.Row;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class ExcelDto {
	
	private String regr;
	private String updr;
	private String regDt;
	private String upDt;

	public abstract ExcelDto row(Row row);
	
}
