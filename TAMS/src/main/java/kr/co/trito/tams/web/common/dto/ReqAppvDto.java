package kr.co.trito.tams.web.common.dto;

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
@ApiModel("자산의뢰 결재 정보")
public class ReqAppvDto {
	@ApiModelProperty(value="자산의뢰 번호")
	private String reqNo;
	@ApiModelProperty(value="결재문서 ID")
	private String appvDocId;
	@ApiModelProperty(value="등록자")
	private String regr;
	@ApiModelProperty(value="등록일자")
	private String regDt;
}

