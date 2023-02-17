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
	@ApiModelProperty(value="자산번호")
	private String asetNo;
	@ApiModelProperty(value="자산명")
	private String asetNm;
	@ApiModelProperty(value="자산유형1")
	private String asetType1;
	@ApiModelProperty(value="자산유형2")
	private String asetType2;
	@ApiModelProperty(value="자산유형3")
	private String asetType3;
	@ApiModelProperty(value="메이커")
	private String mftco;
	@ApiModelProperty(value="모델")
	private String model;
	@ApiModelProperty(value="S/N")
	private String sn;
	@ApiModelProperty(value="사업부코드")
	private String bizDeptCd;
	@ApiModelProperty(value="사업부명")
	private String bizDeptNm;
	@ApiModelProperty(value="부서")
	private String deptCd;
	@ApiModelProperty(value="부서명")
	private String deptNm;
	@ApiModelProperty(value="담당자")
	private String chrgr;
	@ApiModelProperty(value="실사방법")
	private String inspMtd;
	@ApiModelProperty(value="실사방법명")
	private String inspMtdNm;
	@ApiModelProperty(value="실사완료여부")
	private String inspCmplYn;
	@ApiModelProperty(value="특이사항")
	private String ptcl;
	@ApiModelProperty(value="비고")
	private String rmk;
}
