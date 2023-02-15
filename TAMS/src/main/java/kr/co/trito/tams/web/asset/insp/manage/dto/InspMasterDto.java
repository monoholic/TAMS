package kr.co.trito.tams.web.asset.insp.manage.dto;

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
@ApiModel("실사마스터")
public class InspMasterDto {
	@ApiModelProperty(value="실사번호")
	private String inspNo;		
	@ApiModelProperty(value="실사명")
	private String inspNm;
	@ApiModelProperty(value="실사연도")
	private String inspYear;
	@ApiModelProperty(value="실사 시작일")
	private String inspStDt;	
	@ApiModelProperty(value="실사 종료일")
	private String inspEndDt;
	@ApiModelProperty(value="온라인 실사 기준일")
	private String onlineInspBaseDt;
	@ApiModelProperty(value="실사상태")
	private String inspStus;
	@ApiModelProperty(value="실사상태명")
	private String inspStusNm;
	@ApiModelProperty(value="수정자")
	private String updr;
	@ApiModelProperty(value="수정일자")
	private String upDt;
	@ApiModelProperty(value="등록자")
	private String regr;
	@ApiModelProperty(value="등록일자")
	private String regDt;
	@ApiModelProperty(value="실사진행률")
	private String inspProgressRate;
}
