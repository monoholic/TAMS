package kr.co.trito.tams.web.system.role.dto;

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
@ApiModel("권한 정보")
public class RoleDto {
	private String roleId;
	private String roleNm;
	private String roleDesc;
	private String useYn;
	private String regr;
	private String regDt;
	private String updr;
	private String upDt;
}

