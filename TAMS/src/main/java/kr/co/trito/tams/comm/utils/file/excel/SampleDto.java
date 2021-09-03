package kr.co.trito.tams.comm.utils.file.excel;

import org.apache.poi.ss.usermodel.Row;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class SampleDto {

	/*
	 * 	엑셀 업로드 샘플 DTO Class
	 * */
	
    private String uniqueId;
    private String name;
    private String comment;

    protected SampleDto() {}
    
    public static SampleDto row(Row row) {
        return new SampleDto(row.getCell(0).getStringCellValue(), 
        		row.getCell(1).getStringCellValue(), 
        		row.getCell(2).getStringCellValue());
    }
}