package kr.co.trito.tams.web.asset.regist.invest.dto;

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
@ApiModel("등록자산목록 : 자산유형특성정보 Dto")
public class AsetTypeInfoDto{
	@ApiModelProperty(value="컬럼1")
	private String code1;
	@ApiModelProperty(value="컬럼1")
	private String codeVal1;
	@ApiModelProperty(value="컬럼2")
	private String code2;
	@ApiModelProperty(value="컬럼2")
	private String codeVal2;
	@ApiModelProperty(value="컬럼3")
	private String code3;
	@ApiModelProperty(value="컬럼3")
	private String codeVal3;
	
}
