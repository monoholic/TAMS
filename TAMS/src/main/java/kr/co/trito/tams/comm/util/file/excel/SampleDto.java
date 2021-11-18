package kr.co.trito.tams.comm.util.file.excel;

import org.apache.poi.ss.usermodel.Row;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SampleDto extends ExcelDto {

	/*
	 * 	엑셀 업로드 샘플 DTO Class
	 * */
	
    private String uniqueId;
    private String name;
    private String comment;

//    protected SampleDto() {}
    
    @Override
    public  SampleDto row(Row row) {
    	
//        return new SampleDto(row.getCell(0).getStringCellValue(), 
//        		row.getCell(1).getStringCellValue(), 
//        		row.getCell(2).getStringCellValue());
    	SampleDto dto = new SampleDto();
    	dto.setUniqueId	(row.getCell(0).getStringCellValue());
    	dto.setName		(row.getCell(1).getStringCellValue());
    	dto.setComment	(row.getCell(2).getStringCellValue());
    	return dto;
    	
    }
}