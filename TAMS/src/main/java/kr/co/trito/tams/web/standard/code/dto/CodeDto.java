package kr.co.trito.tams.web.standard.code.dto;

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
@ApiModel("공통코드")
public class CodeDto {
	
	private String codeId;
	private String codeGrpId;
	private String codeNm;
	private String codeEngNm;
	private String uppCodeId;
	private String codeLvl;
	private String codeDesc;
	private String sortOdr;
	private String resv1;
	private String resv2;
	private String resv3;
	private String useYn;
	private String updr;
	private String upDt;
	private String regr;
	private String regDt;
}
