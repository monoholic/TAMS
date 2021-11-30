package kr.co.trito.tams.web.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel("결재 상신용 Dto")
public class AppvDto {
	
	@ApiModelProperty(value="결재ID")
	String appvId;
	@ApiModelProperty(value="결재상신 사용자 ID")
	String appvUserId;
	@ApiModelProperty(value="공통코드(결재문서 유형)")
	String appvType;
	@ApiModelProperty(value="갤재 문서 제목")
	String appvTtl;
	@ApiModelProperty(value="결재 내용")
	String appvCn;
	@ApiModelProperty(value="공통코드(문서상태)")
	String appvStus;
	@ApiModelProperty(value="공통코드(문서상태)")
	String appvStep;
	@ApiModelProperty(value="삭제구분")
	String delYn;
	@ApiModelProperty(value="수정자")
	String updr;
	@ApiModelProperty(value="수정일자")
	String upDt;
	@ApiModelProperty(value="등록자")
	String regr;
	@ApiModelProperty(value="등록일자")
	String regDt;

}
