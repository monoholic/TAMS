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
public class InvDto extends ExcelDto {

	/*
	 * 	엑셀 업로드 INVEST_MAST DTO Class
	 * */
	
    private String invNo;
    private String invDt;
    private String invRegr;
    private String invTtl;
    private String invQty;
    private String invAmt;
    private String actgYear;

//    protected InvestDto() {}
    
    @Override
    public  InvDto row(Row row) {
    	
    	InvDto inv = new InvDto();
    	inv.setInvNo 	(row.getCell(0).getStringCellValue());
    	inv.setInvDt 	(row.getCell(1).getStringCellValue());
    	inv.setInvRegr 	(row.getCell(2).getStringCellValue());
    	inv.setInvTtl 	(row.getCell(3).getStringCellValue());
    	inv.setInvQty 	(row.getCell(4).getStringCellValue());
    	inv.setInvAmt 	(row.getCell(5).getStringCellValue());
    	inv.setActgYear (row.getCell(6).getStringCellValue());
    	
        return inv;
    }
}