package kr.co.trito.tams.web.asset.regist.outofbook.dto;

import org.apache.commons.lang3.StringUtils;
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
	@ApiModelProperty(value="사업장")
	private String bsplcNm;	
	@ApiModelProperty(value="건물")
	private String buld;
	@ApiModelProperty(value="건물")
	private String buldNm;	
	@ApiModelProperty(value="층")
	private String floor;
	@ApiModelProperty(value="층")
	private String floorNm;		
	@ApiModelProperty(value="세부위치")
	private String loc;
	@ApiModelProperty(value="취득가")
	private String acqPrc;
	@ApiModelProperty(value="취득일자")
	private String acqDt;
	@ApiModelProperty(value="특성정보1")
	private String dtlInfo1;
	@ApiModelProperty(value="특성정보2")
	private String dtlInfo2;
	@ApiModelProperty(value="특성정보3")
	private String dtlInfo3;
	@ApiModelProperty(value="특성정보4")
	private String dtlInfo4;
	@ApiModelProperty(value="특성정보5")
	private String dtlInfo5;
	@ApiModelProperty(value="특성정보6")
	private String dtlInfo6;
	@ApiModelProperty(value="특성정보7")
	private String dtlInfo7;
	@ApiModelProperty(value="특성정보8")
	private String dtlInfo8;
	@ApiModelProperty(value="특성정보9")
	private String dtlInfo9;
	@ApiModelProperty(value="특성정보10")
	private String dtlInfo10;
	@ApiModelProperty(value="특성정보11")
	private String dtlInfo11;
	@ApiModelProperty(value="특성정보12")
	private String dtlInfo12;
	@ApiModelProperty(value="특성정보13")
	private String dtlInfo13;
	@ApiModelProperty(value="특성정보14")
	private String dtlInfo14;
	@ApiModelProperty(value="특성정보15")
	private String dtlInfo15;
	@ApiModelProperty(value="특성정보16")
	private String dtlInfo16;
	@ApiModelProperty(value="특성정보17")
	private String dtlInfo17;
	@ApiModelProperty(value="특성정보18")
	private String dtlInfo18;
	@ApiModelProperty(value="특성정보19")
	private String dtlInfo19;
	@ApiModelProperty(value="특성정보20")
	private String dtlInfo20;
	@ApiModelProperty(value="유효성검증")
	private String chkResult;
	@ApiModelProperty(value="자산번호 중복 카운트")
	private int asetNoCnt;			
	@ApiModelProperty(value="에러구분")
	private String isError;				
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
	    			.dtlInfo1(getCellValue(row.getCell(17)))
	    			.dtlInfo2(getCellValue(row.getCell(18)))
	    			.dtlInfo3(getCellValue(row.getCell(19)))
	    			.dtlInfo4(getCellValue(row.getCell(20)))
	    			.dtlInfo5(getCellValue(row.getCell(21)))
	    			.dtlInfo6(getCellValue(row.getCell(22)))
	    			.dtlInfo7(getCellValue(row.getCell(23)))
	    			.dtlInfo8(getCellValue(row.getCell(24)))
	    			.dtlInfo9(getCellValue(row.getCell(25)))
	    			.dtlInfo10(getCellValue(row.getCell(26)))
	    			.dtlInfo11(getCellValue(row.getCell(27)))
	    			.dtlInfo12(getCellValue(row.getCell(28)))
	    			.dtlInfo13(getCellValue(row.getCell(29)))
	    			.dtlInfo14(getCellValue(row.getCell(30)))
	    			.dtlInfo15(getCellValue(row.getCell(31)))
	    			.dtlInfo16(getCellValue(row.getCell(32)))
	    			.dtlInfo17(getCellValue(row.getCell(33)))
	    			.dtlInfo18(getCellValue(row.getCell(34)))
	    			.dtlInfo19(getCellValue(row.getCell(35)))
	    			.dtlInfo20(getCellValue(row.getCell(36)))
	    			.isNull("N")
	    			.build();
    	} else {
    		dto = OutOfBookBatchDto.builder().isNull("Y").build();
    	}
    	
    	return dto;
    }
	
    public void setDataVerification() {
    	StringBuffer sb = new StringBuffer();
    	if(StringUtils.isEmpty(this.getAsetNo())) {
    		sb.append("자산번호 오류").append(", ");
    	}
    	
    	if(this.getAsetNoCnt() > 0) {
    		sb.append("자산번호 중복 오류").append(", ");
    	}    	
    	
    	if(StringUtils.isEmpty(this.getAsetNm())) {
    		sb.append("자산명 오류").append(", ");
    	}  

    	if(StringUtils.isEmpty(this.getAsetType1()) || StringUtils.isEmpty(this.getAsetTypeNm1())) {
    		sb.append("자산유형1 오류").append(", ");
    	}   
    	
    	if(StringUtils.isEmpty(this.getAsetType2()) || StringUtils.isEmpty(this.getAsetTypeNm2())) {
    		sb.append("자산유형2 오류").append(", ");
    	}   
    	
    	if(StringUtils.isEmpty(this.getAsetType3()) || StringUtils.isEmpty(this.getAsetTypeNm3())) {
    		sb.append("자산유형3 오류").append(", ");
    	}
    	
    	if(StringUtils.isEmpty(this.getBizDeptCd()) || (StringUtils.isEmpty(this.getBizDeptNm()))) {
    		sb.append("사업부코드 오류").append(", ");
    	}       
    	
    	if(StringUtils.isEmpty(this.getDeptCd()) || (StringUtils.isEmpty(this.getDeptNm()))) {
    		sb.append("부서코드 오류").append(", ");
    	}       
    	      
    	if(StringUtils.isEmpty(this.getChrgr()) || (StringUtils.isEmpty(this.getChrgrNm()))) {
    		sb.append("담당자 오류").append(", ");
    	}           	
    	
    	
    	if(StringUtils.isEmpty(this.getAcqPrc())) {
    		sb.append("취득금액 오류").append(", ");
    	}     
    	
    	if(StringUtils.isEmpty(this.getAcqDt())) {
    		sb.append("취득일자 오류");
    	}         	
    	
    	this.setChkResult(sb.toString());
    	this.setIsError(sb.toString().length() > 0 ? "Y":"N");
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
