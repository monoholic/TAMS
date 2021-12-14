package kr.co.trito.tams.web.common.dto;

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
@ApiModel("공통코드 조회 Dto")
public class ComCodeParamDto {
	@ApiModelProperty(value="공통코드 그룹 ID")
	private String codeGrpId;
	
	@ApiModelProperty(value="상위 공통코드 ID")
	private String upperCodeId;
	
	@ApiModelProperty(value="코드 레벨")
	private String codeLvl;
}
