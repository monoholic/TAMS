package kr.co.trito.tams.web.asset.regist.outofbook.dto;

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
@ApiModel("자산마스터 Dto")
public class OutOfBookBatchDto extends ExcelDto {
	@ApiModelProperty(value="자산번호")
	private String asetNo;
	@ApiModelProperty(value="자산명")
	private String asetNm;
	@ApiModelProperty(value="자산유형1")
	private String asetType1;
	@ApiModelProperty(value="자산유형명1")
	private String asetTypeNm1;	
	@ApiModelProperty(value="자산유형2")	
	private String asetType2;
	@ApiModelProperty(value="자산유형명2")
	private String asetTypeNm2;		
	@ApiModelProperty(value="자산유형3")
	private String asetType3;
	@ApiModelProperty(value="자산유형명3")
	private String asetTypeNm3;			
	@ApiModelProperty(value="제조사")
	private String mftco;
	@ApiModelProperty(value="모델")
	private String model;
	@ApiModelProperty(value="S/N")
	private String sn;
	@ApiModelProperty(value="사업부코드")
	private String bizDeptCd;
	@ApiModelProperty(value="사업부명")
	private String bizDeptNm;
	@ApiModelProperty(value="부서코드")
	private String deptCd;
	@ApiModelProperty(value="부서명")
	private String deptNm;
	@ApiModelProperty(value="담당자")
	private String chrgr;
	@ApiModelProperty(value="담당자명")
	private String chrgrNm;
	@ApiModelProperty(value="사업장")
	private String bsplc;
	@ApiModelProperty(value="건물")
	private String buld;
	@ApiModelProperty(value="층")
	private String floor;	
	@ApiModelProperty(value="세부위치")
	private String loc;
	@ApiModelProperty(value="취득가")
	private String acqPrc;
	@ApiModelProperty(value="취득일자")
	private String acqDt;
	@ApiModelProperty(value="특성정보1")
	private String prop1;
	@ApiModelProperty(value="특성정보2")
	private String prop2;
	@ApiModelProperty(value="특성정보3")
	private String prop3;
	@ApiModelProperty(value="특성정보4")
	private String prop4;
	@ApiModelProperty(value="특성정보5")
	private String prop5;
	@ApiModelProperty(value="특성정보6")
	private String prop6;
	@ApiModelProperty(value="특성정보7")
	private String prop7;
	@ApiModelProperty(value="특성정보8")
	private String prop8;
	@ApiModelProperty(value="특성정보9")
	private String prop9;
	@ApiModelProperty(value="특성정보10")
	private String prop10;
	@ApiModelProperty(value="특성정보11")
	private String prop11;
	@ApiModelProperty(value="특성정보12")
	private String prop12;
	@ApiModelProperty(value="특성정보13")
	private String prop13;
	@ApiModelProperty(value="특성정보14")
	private String prop14;
	@ApiModelProperty(value="특성정보15")
	private String prop15;
	@ApiModelProperty(value="특성정보16")
	private String prop16;
	@ApiModelProperty(value="특성정보17")
	private String prop17;
	@ApiModelProperty(value="특성정보18")
	private String prop18;
	@ApiModelProperty(value="특성정보19")
	private String prop19;
	@ApiModelProperty(value="특성정보20")
	private String prop20;
	@ApiModelProperty(value="유효성검증")
	private String chkResult;
	@ApiModelProperty(value="Null Check")
	private String isNull;		
	
	
    @Override
    public  OutOfBookBatchDto row(Row row) {	
    	
    	OutOfBookBatchDto dto = null;
    	
    	if (row.getPhysicalNumberOfCells() > 0) {
	    	dto = OutOfBookBatchDto.builder()
	    			.asetNo(getCellValue(row.getCell(0)))
	    			.asetNm(getCellValue(row.getCell(1)))
	    			.asetType1(getCellValue(row.getCell(2)))
	    			.asetType2(getCellValue(row.getCell(3)))
	    			.asetType3(getCellValue(row.getCell(4)))
	    			.mftco(getCellValue(row.getCell(5)))
	    			.model(getCellValue(row.getCell(6)))
	    			.sn(getCellValue(row.getCell(7)))
	    			.bizDeptCd(getCellValue(row.getCell(8)))
	    			.deptCd(getCellValue(row.getCell(9)))
	    			.chrgr(getCellValue(row.getCell(10)))
	    			.bsplc(getCellValue(row.getCell(11)))
	    			.buld(getCellValue(row.getCell(12)))
	    			.floor(getCellValue(row.getCell(13)))
	    			.loc(getCellValue(row.getCell(14)))
	    			.acqPrc(getCellValue(row.getCell(15)))
	    			.acqDt(getCellValue(row.getCell(16)))
	    			.prop1(getCellValue(row.getCell(17)))
	    			.prop2(getCellValue(row.getCell(18)))
	    			.prop3(getCellValue(row.getCell(19)))
	    			.prop4(getCellValue(row.getCell(20)))
	    			.prop5(getCellValue(row.getCell(21)))
	    			.prop6(getCellValue(row.getCell(22)))
	    			.prop7(getCellValue(row.getCell(23)))
	    			.prop8(getCellValue(row.getCell(24)))
	    			.prop9(getCellValue(row.getCell(25)))
	    			.prop10(getCellValue(row.getCell(26)))
	    			.prop11(getCellValue(row.getCell(27)))
	    			.prop12(getCellValue(row.getCell(28)))
	    			.prop13(getCellValue(row.getCell(29)))
	    			.prop14(getCellValue(row.getCell(30)))
	    			.prop15(getCellValue(row.getCell(31)))
	    			.prop16(getCellValue(row.getCell(32)))
	    			.prop17(getCellValue(row.getCell(33)))
	    			.prop18(getCellValue(row.getCell(34)))
	    			.prop19(getCellValue(row.getCell(35)))
	    			.prop20(getCellValue(row.getCell(36)))
	    			.isNull("N")
	    			.build();
    	} else {
    		dto = OutOfBookBatchDto.builder().isNull("Y").build();
    	}
    	
    	return dto;
    }
	
    private String getCellValue(Cell cell) {
    	String returnValue = "";
    	if(cell != null) {
    		cell.setCellType(CellType.STRING);
    		returnValue = cell.getStringCellValue();
    	}    		
    	return returnValue;
    }
    
}
