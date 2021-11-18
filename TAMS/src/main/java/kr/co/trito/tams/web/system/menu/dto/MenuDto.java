package kr.co.trito.tams.web.system.menu.dto;

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
@ApiModel("메뉴 정보")
public class MenuDto {
	@ApiModelProperty(value="메뉴ID")
	private String menuId;
	@ApiModelProperty(value="상위 메뉴ID")
	private String uppMenuId;
	@ApiModelProperty(value="메뉴명")
	private String menuNm;
	@ApiModelProperty(value="메뉴설명")
	private String menuDesc;
	@ApiModelProperty(value="url")
	private String url;
	@ApiModelProperty(value="사용여부")
	private String useYn;
	@ApiModelProperty(value="레벨")
	private String lvl;
	@ApiModelProperty(value="팝업사용여부")
	private String popupYn;
	@ApiModelProperty(value="정렬")
	private String sortOdr;
	@ApiModelProperty(value="등록자")
	private String regr;
	@ApiModelProperty(value="등록일자")
	private String regDt;
	@ApiModelProperty(value="수정자")
	private String updr;
	@ApiModelProperty(value="수정일자")
	private String upDt;
	@ApiModelProperty(value="체크")
	private String chk;
	@ApiModelProperty(value="조회권한")
	private String inqrYn;
	@ApiModelProperty(value="수정권한")
	private String updYn;
	@ApiModelProperty(value="등록권한")
	private String regYn;
	@ApiModelProperty(value="삭제권한")
	private String delYn;
}

