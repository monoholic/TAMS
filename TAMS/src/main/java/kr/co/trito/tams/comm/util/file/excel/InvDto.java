package kr.co.trito.tams.comm.util.file.excel;

import org.apache.poi.ss.usermodel.Row;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ApiModel("투자정보 엑셀업로드용 Dto")
public class InvDto extends ExcelDto {

	/*
	 * 	투자정보 엑셀 업로드 DTO Class
	 * */
	@ApiModelProperty(value="투자 번호")
    private String invNo;
	@ApiModelProperty(value="투자명")
	private String invTtl;
	@ApiModelProperty(value="구매요청번호")
	private String poNo;
	@ApiModelProperty(value="제품명")
	private String mfgdNm;
	@ApiModelProperty(value="수량")
	private String qty;
	@ApiModelProperty(value="투자 일자")
    private String invDt;
	@ApiModelProperty(value="투자 담당자")
    private String invRegr;
	@ApiModelProperty(value="투자 담당자명")
	private String invRegrNm;
	@ApiModelProperty(value="사업부")
	private String groupNm;
	@ApiModelProperty(value="부서")
	private String deptNm;
	@ApiModelProperty(value="유효성 검증")
	private String chkResult;

	//@ApiModelProperty(value="회계연도")
	//private String actgYear;
	//@ApiModelProperty(value="투자 금액")
	//private String invAmt;
	
    
    @Override
    public  InvDto row(Row row) {
    	
    	InvDto inv = new InvDto();
    	inv.setInvNo 	(row.getCell(0).getStringCellValue());
    	inv.setInvTtl 	(row.getCell(1).getStringCellValue());
    	inv.setPoNo		(row.getCell(2).getStringCellValue());
    	inv.setMfgdNm	(row.getCell(3).getStringCellValue());
    	inv.setQty	 	(row.getCell(4).getStringCellValue());
    	inv.setInvDt 	(row.getCell(5).getStringCellValue());
    	inv.setInvRegr 	(row.getCell(8).getStringCellValue());
    	inv.setInvRegrNm(row.getCell(9).getStringCellValue());
    	
        return inv;
    }
}