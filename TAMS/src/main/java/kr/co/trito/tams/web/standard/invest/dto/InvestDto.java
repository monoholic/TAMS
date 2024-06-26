package kr.co.trito.tams.web.standard.invest.dto;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jdk.internal.org.jline.utils.Log;
import kr.co.trito.tams.comm.util.file.excel.ExcelDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@ApiModel("공통코드")
@Slf4j
public class InvestDto extends ExcelDto {
	@ApiModelProperty(value="투자번호")
	private String invNo;
	@ApiModelProperty(value="투자명")
	private String invTtl;
	@ApiModelProperty(value="투자일자")
	private String invDt;
	@ApiModelProperty(value="투자수량")
	private String invQty;
	@ApiModelProperty(value="투자금액")
	private String invAmt;
	@ApiModelProperty(value="회계연도")
	private String actgYear;
	@ApiModelProperty(value="PO번호")
	private String poNo;
	@ApiModelProperty(value="품명")
	private String mfgdNm;
	@ApiModelProperty(value="PO수량")
	private String qty;
	@ApiModelProperty(value="PO금액")
	private String poAmt;
	@ApiModelProperty(value="부서")
	private String deptNm;
	@ApiModelProperty(value="업체")
	private String vendNm;
	@ApiModelProperty(value="요청일자")
	private String reqDt;
	@ApiModelProperty(value="납품일자")
	private String dlvDt;
	@ApiModelProperty(value="담당자")
	private String invReqr;	
	@ApiModelProperty(value="회원명")
	private String userNm;	
	@ApiModelProperty(value="자산등록여부")
	private String poAsetYn;
	@ApiModelProperty(value="유효성 검증")
	private String chkResult;
	@ApiModelProperty(value="유효성 상태")
	private String chkFlag;
	@ApiModelProperty(value="Null Check")
	private String isNull;	
	
	//@ApiModelProperty(value="등록일자")
	//private String regDt;
	//@ApiModelProperty(value="등록자")
	//private String regr;
	//@ApiModelProperty(value="수정일자")
	//private String upDt;
	//@ApiModelProperty(value="수정자")
	//private String updr;
	
    @Override
    public  InvestDto row(Row row) {
    	
    	InvestDto inv = new InvestDto();
    	
    	CellType Cell = null;
    	Cell cell = null;
    	
    	int numberOfCells = row.getPhysicalNumberOfCells();
    	
    	
    	if (numberOfCells > 0) {
	    	
	    	if(row.getCell(0) != null) 
	    		inv.setInvNo 	(row.getCell(0).getStringCellValue());
	    	
	    	if(row.getCell(1) != null)
	    		inv.setInvTtl 	(row.getCell(1).getStringCellValue());
	    	
	    	if(row.getCell(2) != null) {
	    		cell = row.getCell(2);
	    		cell.setCellType(Cell.STRING);
	    		inv.setInvDt	(cell.getStringCellValue());
	    	}
	    	if(row.getCell(3) != null) {
	    		cell = row.getCell(3);
	    		cell.setCellType(Cell.STRING);
	    		inv.setInvQty	(cell.getStringCellValue());
	    	}
	    	if(row.getCell(4) != null) {
	    		cell = row.getCell(4);
	    		cell.setCellType(Cell.STRING);
	    		inv.setInvAmt	(cell.getStringCellValue());
	    	}
	    	if(row.getCell(5) != null) {
	    		cell = row.getCell(5);
	    		cell.setCellType(Cell.STRING);
	    		inv.setActgYear	(cell.getStringCellValue());
	    	}
	    	if(row.getCell(6) != null) {
	    		cell = row.getCell(6);
	    		cell.setCellType(Cell.STRING);
	    		inv.setPoNo	(cell.getStringCellValue());    		
	    	}
			inv.setMfgdNm	(row.getCell(7).getStringCellValue());
			if(row.getCell(8) != null) {
	    		cell = row.getCell(8);
	    		cell.setCellType(Cell.STRING);
	    		inv.setQty	(cell.getStringCellValue()); 
			}
			if(row.getCell(9) != null) {
				cell = row.getCell(9);
				cell.setCellType(Cell.STRING);
				inv.setPoAmt	(cell.getStringCellValue()); 
			}
			if(row.getCell(10) != null) {
	    		cell = row.getCell(10);
	    		cell.setCellType(Cell.STRING);
	    		inv.setInvReqr	(cell.getStringCellValue()); 
			}
			if(row.getCell(11) != null) {
	    		cell = row.getCell(11);
	    		cell.setCellType(Cell.STRING);
	    		inv.setVendNm	(cell.getStringCellValue());			
			}
			if(row.getCell(12) != null)	{
	    		cell = row.getCell(12);
	    		cell.setCellType(Cell.STRING);
	    		inv.setReqDt	(cell.getStringCellValue());
			}
			if(row.getCell(13) != null)	{
	    		cell = row.getCell(13);
	    		cell.setCellType(Cell.STRING);
	    		inv.setDlvDt	(cell.getStringCellValue());			
			}
			
			inv.setIsNull("N");
    	}else {
    		inv.setIsNull("Y");
    	}
    	
		//inv.setInvRegrNm(row.getCell(9).getStringCellValue());
    	
        return inv;
    }
}
