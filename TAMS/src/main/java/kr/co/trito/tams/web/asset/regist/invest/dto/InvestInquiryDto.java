package kr.co.trito.tams.web.asset.regist.invest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("투자정보조회 필터 Dto")
public class InvestInquiryDto{
	@ApiModelProperty(value="투자번호")
	private String invNo;
	@ApiModelProperty(value="투자명")
	private String invTtl;
	@ApiModelProperty(value="PO번호")
	private String poNo;
	@ApiModelProperty(value="품명")
	private String mfgdNm;
	@ApiModelProperty(value="수량")
	private String qty;
	@ApiModelProperty(value="사업부")
	private String uppDeptNm;
	@ApiModelProperty(value="부서")
	private String deptNm;
	@ApiModelProperty(value="담당자")
	private String invReqr;
	@ApiModelProperty(value="등록된 자산 수량")
	private String asetQty;
	@ApiModelProperty(value="미등록된 자산 수량")
	private String noAsetQty;
}