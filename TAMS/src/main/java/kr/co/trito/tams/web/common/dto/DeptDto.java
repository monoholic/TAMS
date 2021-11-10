package kr.co.trito.tams.web.common.dto;

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
@ApiModel("부서 정보")
public class DeptDto {
	private String deptCd;
	private String deptNm;
	private String uppDeptCd;
	private String loc;
	private String deptChrgrId;
	private String sortOdr;	
	private String useYn;	
	private String regr;
	private String regdt;
	private String updr;
	private String updt;
	private String chk;
}

