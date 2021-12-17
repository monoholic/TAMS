package kr.co.trito.tams.web.asset.status.information.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@ApiModel("투자이력 정보")
public class AssetInvestDto {
	@ApiModelProperty(value="투자구분")
	private String newYn;		
	@ApiModelProperty(value="발생일자")
	private String invDt;
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
	@ApiModelProperty(value="투자자")
	private String invReqrNm;
	@ApiModelProperty(value="처리자")
	private String poChrgrNm;
}
