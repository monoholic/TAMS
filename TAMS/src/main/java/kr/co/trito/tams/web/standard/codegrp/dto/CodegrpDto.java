package kr.co.trito.tams.web.standard.codegrp.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ApiModel("공통코드 그룹")
public class CodegrpDto {
	@ApiModelProperty(value="코드 그룹 ID")
	private String codeGrpId;
	@ApiModelProperty(value="코드 그룹명")
	private String codeGrpNm;
	@ApiModelProperty(value="코드 그룹 영문명")
	private String codeGrpEngNm;
	@ApiModelProperty(value="코드 그룹 설명")
	private String codeGrpDesc;
	@ApiModelProperty(value="정렬순서")
	private String sortOdr;
	@ApiModelProperty(value="사용구분")
	private String useYn;
	@ApiModelProperty(value="수정자")
	private String updr;
	@ApiModelProperty(value="수정일자")
	private String upDt;
	@ApiModelProperty(value="등록자")
	private String regr;
	@ApiModelProperty(value="등록일자")
	private String regDt;
}
