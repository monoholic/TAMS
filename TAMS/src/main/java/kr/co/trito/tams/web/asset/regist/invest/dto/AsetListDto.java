package kr.co.trito.tams.web.asset.regist.invest.dto;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.trito.tams.comm.util.file.excel.ExcelDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@ApiModel("등록자산목록 리스트 Dto")
public class AsetListDto {
	@ApiModelProperty(value="자산번호")
	private String asetNo;
	@ApiModelProperty(value="자산유형1")
	private String asetType1;
	@ApiModelProperty(value="자산유형2")
	private String asetType2;
	@ApiModelProperty(value="자산유형3")
	private String asetType3;
	@ApiModelProperty(value="메이커")
	private String mftco;
	@ApiModelProperty(value="모델")
	private String model;
	@ApiModelProperty(value="S/N")
	private String sn;
	@ApiModelProperty(value="사업부")
	private String uppDeptNm;
	@ApiModelProperty(value="부서")
	private String deptNm;
	@ApiModelProperty(value="담당자")
	private String chrgr;
	@ApiModelProperty(value="태깅여부")
	private String tagYn;
	@ApiModelProperty(value="등록상태")
	private String asetStus;
	
    public AsetListDto row(Row row) {
    	
    	AsetListDto reg = new AsetListDto();

    	CellType Cell = null;
    	Cell cell = null;
    	
    	if(row.getCell(0) != null) {
    		cell = row.getCell(0);
    		cell.setCellType(Cell.STRING);   
    		reg.setAsetNo(cell.getStringCellValue());
    	}
    	reg.setAsetType1 	(row.getCell(1).getStringCellValue());
    	reg.setAsetType2	(row.getCell(2).getStringCellValue());
    	reg.setAsetType3 	(row.getCell(3).getStringCellValue());
    	reg.setMftco 		(row.getCell(4).getStringCellValue());
    	if(row.getCell(5) != null) {
    		cell = row.getCell(5);
    		cell.setCellType(Cell.STRING);
    		reg.setModel	(cell.getStringCellValue());
    	}
    	if(row.getCell(6) != null) {
    		cell = row.getCell(6);
    		cell.setCellType(Cell.STRING);
    		reg.setSn		(cell.getStringCellValue());
    	}
		reg.setUppDeptNm	(row.getCell(7).getStringCellValue());
		reg.setDeptNm		(row.getCell(8).getStringCellValue());
		if(row.getCell(9) != null) {
			cell = row.getCell(9);
			cell.setCellType(Cell.STRING);
			reg.setChrgr	(cell.getStringCellValue()); 
		}
		if(row.getCell(10) != null) {
    		cell = row.getCell(10);
    		cell.setCellType(Cell.STRING);
    		reg.setTagYn	(cell.getStringCellValue()); 
		}
		if(row.getCell(11) != null) {
    		cell = row.getCell(11);
    		cell.setCellType(Cell.STRING);
    		reg.setAsetStus	(cell.getStringCellValue());			
		}
    	
        return reg;
    }	
}
