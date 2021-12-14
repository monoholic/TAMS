package kr.co.trito.tams.web.asset.regist.invest.dto;

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
@ApiModel("등록자산목록 리스트 Dto")
public class AsetListDto{
	@ApiModelProperty(value="자산번호")
	private String asetNo;
	@ApiModelProperty(value="자산유형1")
	private String asetType1;
	@ApiModelProperty(value="자산유형2")
	private String asetType2;
	@ApiModelProperty(value="자산유형3")
	private String asetType3;
	@ApiModelProperty(value="메이커")
	private String mftco;
	@ApiModelProperty(value="모델")
	private String model;
	@ApiModelProperty(value="S/N")
	private String sn;
	@ApiModelProperty(value="사업부")
	private String uppDeptNm;
	@ApiModelProperty(value="부서")
	private String deptNm;
	@ApiModelProperty(value="담당자")
	private String chrgr;
	@ApiModelProperty(value="태깅여부")
	private String tagYn;
	@ApiModelProperty(value="등록상태")
	private String asetStus;
	
}
