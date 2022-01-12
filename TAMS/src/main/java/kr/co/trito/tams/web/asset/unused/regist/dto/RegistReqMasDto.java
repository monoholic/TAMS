package kr.co.trito.tams.web.asset.unused.regist.dto;

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
public class RegistReqMasDto {
	@ApiModelProperty(value="의뢰번호")
	private String reqNo;
	@ApiModelProperty(value="공통코드(의뢰구분)")
	private String reqType;
	@ApiModelProperty(value="공통코드명(의뢰구분)")
	private String reqTypeNm;
	@ApiModelProperty(value="의뢰명")
	private String reqNm;
	@ApiModelProperty(value="의뢰일자")
	private String reqDt;
	@ApiModelProperty(value="의뢰자")
	private String reqtr;
	@ApiModelProperty(value="의뢰자 부서")
	private String reqtrDept;
	@ApiModelProperty(value="결재문서 ID")
	private String appvDocId;
	@ApiModelProperty(value="의뢰사유")	
	private String reqRsn;
	@ApiModelProperty(value="공통코드(의뢰상태)")	
	private String reqStus;
	@ApiModelProperty(value="공통코드명(의뢰상태)")	
	private String reqStusNm;
	@ApiModelProperty(value="이동목적")
	private String movePupos;
	@ApiModelProperty(value="이동일자")
	private String moveDt;
	@ApiModelProperty(value="반출예정여부")
	private String tkoutScheYn;
	@ApiModelProperty(value="반출예정일자")
	private String tkoutScheDt;
	@ApiModelProperty(value="반출여부")
	private String tkoutYn;
	@ApiModelProperty(value="반출일자")
	private String tkoutDt;
	@ApiModelProperty(value="재반입여부")
	private String rtkinYn;
	@ApiModelProperty(value="재반입일자")
	private String rtKinDt;
	@ApiModelProperty(value="진행중단여부")
	private String prgsDcontYn;
	@ApiModelProperty(value="수정자")
	private String updr;
	@ApiModelProperty(value="수정일자")
	private String upDt;
	@ApiModelProperty(value="등록자")
	private String regr;
	@ApiModelProperty(value="등록일자")
	private String regDt;
	@ApiModelProperty(value="부서코드")
	private String deptCd;
	@ApiModelProperty(value="부서코드명")
	private String deptNm;
	@ApiModelProperty(value="상위 부서코드")
	private String uppDeptCd;
	@ApiModelProperty(value="상위 부서코드명")
	private String uppDeptNm;
	@ApiModelProperty(value="자산 수")
	private String asetCnt;
	@ApiModelProperty(value="rn")
	private String rn;
}
