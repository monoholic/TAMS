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
@ApiModel("자산이력 정보")
public class AssetHistoryDto {
	@ApiModelProperty(value="의뢰유형")
	private String reqTypeNm;	
	@ApiModelProperty(value="의뢰일자")
	private String reqDt;
	@ApiModelProperty(value="의뢰번호")
	private String reqNo;	
	@ApiModelProperty(value="의뢰상태")
	private String reqStusNm;		
	@ApiModelProperty(value="의뢰자")
	private String reqtrNm;		
	@ApiModelProperty(value="자산상태")
	private String asetStusNm;		
	@ApiModelProperty(value="사업부")
	private String bizDeptNm;
	@ApiModelProperty(value="부서")
	private String deptNm;
	@ApiModelProperty(value="담당자")
	private String chrgrNm;
	@ApiModelProperty(value="자산유형1")
	private String asettype1Nm;
	@ApiModelProperty(value="자산유형2")
	private String asettype2Nm;
	@ApiModelProperty(value="자산유형3")
	private String asettype3Nm;	
}
