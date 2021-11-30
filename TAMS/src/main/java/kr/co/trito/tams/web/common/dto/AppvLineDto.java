package kr.co.trito.tams.web.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel("결재 라인용 Dto")
public class AppvLineDto {
	
	@ApiModelProperty(value="결재라인ID")
	String appvLineId;
	@ApiModelProperty(value="결재ID")
	String appvId;
	@ApiModelProperty(value="결재자")
	String appvr;
	@ApiModelProperty(value="공통코드(결재종류)")
	String appvDiv;
	@ApiModelProperty(value="결재순서")
	String appvOrd;
	@ApiModelProperty(value="공통코드(결재상태)")
	String appvStus;
	@ApiModelProperty(value="결재 코멘트")
	String appvComt;
	@ApiModelProperty(value="결재 수신일자")
	String rcvDt;
	@ApiModelProperty(value="결재일자")
	String appvDt;
	@ApiModelProperty(value="수정자")
	String updr;
	@ApiModelProperty(value="수정일자")
	String upDt;
	@ApiModelProperty(value="등록자")
	String regr;
	@ApiModelProperty(value="등록일자")
	String regDt;

}
