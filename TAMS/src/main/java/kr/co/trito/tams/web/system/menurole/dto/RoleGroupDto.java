package kr.co.trito.tams.web.system.menurole.dto;

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
@ApiModel("권한 그룹")
public class RoleGroupDto {
	@ApiModelProperty(value="권한그룹ID")
	private String groupId;
	@ApiModelProperty(value="권한그룹명")
	private String groupNm;
}

