package kr.co.trito.tams.web.standard.invest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ApiModel("공통코드")
public class InvestDto {
	@ApiModelProperty(value="투자번호")
	private String invNo;
	@ApiModelProperty(value="투자명")
	private String invTtl;
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
	@ApiModelProperty(value="수량")
	private String qty;
	@ApiModelProperty(value="투자일자")
	private String invDt;
	@ApiModelProperty(value="부서")
	private String deptNm;
	@ApiModelProperty(value="담당자")
	private String invRegr;	
	@ApiModelProperty(value="회원명")
	private String userNm;	
	@ApiModelProperty(value="자산등록여부")
	private String poAsetYn;
	@ApiModelProperty(value="등록일자")
	private String regDt;
	@ApiModelProperty(value="등록자")
	private String regr;
	@ApiModelProperty(value="수정일자")
	private String upDt;
	@ApiModelProperty(value="수정자")
	private String updr;
}
