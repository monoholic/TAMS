package kr.co.trito.tams.web.asset.update.reqmas.dto;

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
@ApiModel("자산 의뢰 마스터")
public class ReqMasExcelDto {
	@ApiModelProperty(value="의뢰번호")
	private String reqNo;
	@ApiModelProperty(value="공통코드명(의뢰구분)")
	private String reqTypeNm;
	@ApiModelProperty(value="의뢰명")
	private String reqNm;
	@ApiModelProperty(value="의뢰자 부서")
	private String reqtrDept;
	@ApiModelProperty(value="의뢰자")
	private String reqtr;
	@ApiModelProperty(value="의뢰일자")
	private String reqDt;
	@ApiModelProperty(value="공통코드명(의뢰상태)")	
	private String reqStusNm;
	@ApiModelProperty(value="자산 수")
	private String asetCnt;
}
