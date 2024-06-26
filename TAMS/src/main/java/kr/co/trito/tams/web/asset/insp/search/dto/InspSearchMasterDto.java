package kr.co.trito.tams.web.asset.insp.search.dto;

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
@ApiModel("실사조회마스터")
public class InspSearchMasterDto {
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
	@ApiModelProperty(value="자산유형1")
	private String asetType1Nm;
	@ApiModelProperty(value="자산유형2")
	private String asetType2Nm;
	@ApiModelProperty(value="자산유형3")
	private String asetType3Nm;
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
	@ApiModelProperty(value="코드")
	private String codeId;
	@ApiModelProperty(value="코드명")
	private String codeNm;
	@ApiModelProperty(value="비고")
	private String rmk;
	@ApiModelProperty(value="상위부서코드")
	private String uppDeptCd;
	@ApiModelProperty(value="상위부서명")
	private String uppDeptNm;
	@ApiModelProperty(value="실사담당자")
	private String depart_head;
	@ApiModelProperty(value="부서장")
	private String dpldr;
	@ApiModelProperty(value="실사자1")
	private String insptr1;
	@ApiModelProperty(value="실사자2")
	private String insptr2;
	@ApiModelProperty(value="실사자3")
	private String insptr3;
	@ApiModelProperty(value="실사자4")
	private String insptr4;
	@ApiModelProperty(value="실사자5")
	private String insptr5;
	@ApiModelProperty(value="실사자대상자")
	private String inspTarget;
	@ApiModelProperty(value="실사자대상자산수")
	private String inspTargetCnt;
	@ApiModelProperty(value="실사자완료자산수")
	private String inspCmplCnt;
	@ApiModelProperty(value="결제여부")
	private String paymentYn;
}
