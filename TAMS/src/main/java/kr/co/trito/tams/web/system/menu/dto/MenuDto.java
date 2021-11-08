package kr.co.trito.tams.web.system.menu.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

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
@ApiModel("메뉴 정보")
public class MenuDto {
	private String menuId;
	private String uppMenuId;
	private String menuNm;
	private String menuDesc;	
	private String url;	
	private String useYn;
	private String lvl;
	private String popupYn;
	private String sortOdr;
	private String regr;
	private String regDt;
	private String updr;
	private String upDt;
	private String chk;
}

