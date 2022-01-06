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
@ApiModel("메뉴권한 정보")
public class MenuRoleDto {
	@ApiModelProperty(value="체크박스")
	private String chk;
	@ApiModelProperty(value="메뉴ID")
	private String menuId;
	@ApiModelProperty(value="메뉴명")
	private String menuNm;
	@ApiModelProperty(value="메뉴사용권한여부")
	private String menuYn;
	@ApiModelProperty(value="조회권한여부")
	private String inqrYn;
	@ApiModelProperty(value="생성권한여부")
	private String regYn;
	@ApiModelProperty(value="수정권한여부")
	private String updYn;
	@ApiModelProperty(value="삭제권한여부")
	private String delYn;
	@ApiModelProperty(value="등록자")
	private String regr;
	@ApiModelProperty(value="등록일자")
	private String regDt;
	@ApiModelProperty(value="수정자")
	private String updr;
	@ApiModelProperty(value="수정일자")
	private String upDt;
	@ApiModelProperty(value="그룹ID")
	private String groupId;
	@ApiModelProperty(value="Row Number")
	private String rn;	
}

