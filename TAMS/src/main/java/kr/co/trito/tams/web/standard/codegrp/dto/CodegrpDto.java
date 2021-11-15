package kr.co.trito.tams.web.standard.codegrp.dto;

import io.swagger.annotations.ApiModel;
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
	
	private String codeGrpId;
	private String codeGrpNm;
	private String codeGrpEngNm;
	private String codeGrpDesc;
	private String sortOdr;
	private String useYn;
	private String updr;
	private String upDt;
	private String regr;
	private String regDt;
}
