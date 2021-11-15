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
@ApiModel("공통 Dto")
public class ComCodeDto {
	@ApiModelProperty(value="ID")
	private String codeId;
	@ApiModelProperty(value="코드네임")
	private String codeNm;
}

