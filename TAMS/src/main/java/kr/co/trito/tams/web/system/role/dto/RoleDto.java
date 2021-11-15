package kr.co.trito.tams.web.system.role.dto;

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
@ApiModel("권한 정보")
public class RoleDto {
	@ApiModelProperty(value="권한코드")
	private String roleId;
	@ApiModelProperty(value="권한명")
	private String roleNm;
	@ApiModelProperty(value="권한설명")
	private String roleDesc;
	@ApiModelProperty(value="사용여부")
	private String useYn;
	@ApiModelProperty(value="등록자")
	private String regr;
	@ApiModelProperty(value="등록일자")
	private String regDt;
	@ApiModelProperty(value="수정자")
	private String updr;
	@ApiModelProperty(value="수정일자")
	private String upDt;
}

