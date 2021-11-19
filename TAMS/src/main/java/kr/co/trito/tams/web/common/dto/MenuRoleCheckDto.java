package kr.co.trito.tams.web.common.dto;

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
@ApiModel("공통 Dto")
public class MenuRoleCheckDto {
	@ApiModelProperty(value="권한코드")
	private String roleId;
	@ApiModelProperty(value="메뉴ID")
	private String menuId;
	@ApiModelProperty(value="조회권한")
	private String inqrYn;
	@ApiModelProperty(value="수정권한")
	private String updYn;
	@ApiModelProperty(value="등록권한")
	private String regYn;
	@ApiModelProperty(value="삭제권한")
	private String delYn;
}

