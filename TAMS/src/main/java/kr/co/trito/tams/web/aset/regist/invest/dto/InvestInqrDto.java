package kr.co.trito.tams.web.aset.regist.invest.dto;

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
@ApiModel("공통코드")
public class InvestInqrDto{
	@ApiModelProperty(value="투자번호")
	private String invNo;
	@ApiModelProperty(value="투자명")
	private String invTtl;
	@ApiModelProperty(value="투자일자")
	private String invDt;
	@ApiModelProperty(value="PO번호")
	private String poNo;
	@ApiModelProperty(value="품명")
	private String mfgdNm;
	@ApiModelProperty(value="수량")
	private String qty;
	@ApiModelProperty(value="PO금액")
	private String poAmt;
	@ApiModelProperty(value="부서")
	private String deptNm;
	@ApiModelProperty(value="부서코드")
	private String deptCd;
	@ApiModelProperty(value="사업부")
	private String uppDeptNm;
	@ApiModelProperty(value="사업부코드")
	private String uppDeptCd;
	@ApiModelProperty(value="담당자")
	private String invReqr;	
	@ApiModelProperty(value="유저명")
	private String userNm;
	@ApiModelProperty(value="유저ID")
	private String userId;
	@ApiModelProperty(value="등록된 자산 수량")
	private String asetQty;
	@ApiModelProperty(value="미등록된 자산 수량")
	private String noAsetQty;
	@ApiModelProperty(value="투자일자(시작)")
	private String fromDate;
	@ApiModelProperty(value="투자일자(종료)")
	private String toDate;
	@ApiModelProperty(value="미등록자산여부")
	private String regYn;
	
}
