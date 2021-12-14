package kr.co.trito.tams.web.standard.code.dto;

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
@ApiModel("공통코드")
public class CodeDto {
	@ApiModelProperty(value="공통코드 ID")
	private String codeId;
	@ApiModelProperty(value="공통코드 그룹 ID")
	private String codeGrpId;
	@ApiModelProperty(value="공통코드명")
	private String codeNm;
	@ApiModelProperty(value="공통코드 영문명")
	private String codeEngNm;
	@ApiModelProperty(value="상위 공통코드 ID")
	private String uppCodeId;
	@ApiModelProperty(value="코드 레벨")
	private String codeLvl;
	@ApiModelProperty(value="공통코드 설명")
	private String codeDesc;
	@ApiModelProperty(value="코드 정렬 순서")
	private String sortOdr;
	@ApiModelProperty(value="예비1")
	private String resv1;
	@ApiModelProperty(value="예비2")
	private String resv2;
	@ApiModelProperty(value="예비3")
	private String resv3;
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
