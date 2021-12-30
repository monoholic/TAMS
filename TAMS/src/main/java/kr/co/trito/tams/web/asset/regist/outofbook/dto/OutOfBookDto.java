package kr.co.trito.tams.web.asset.regist.outofbook.dto;

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
@ApiModel("자산마스터 Dto")
public class OutOfBookDto {
	@ApiModelProperty(value="자산번호")
	private String asetNo;
	@ApiModelProperty(value="자산유형1")
	private String asetType1;
	@ApiModelProperty(value="자산유형명1")
	private String asetTypeNm1;	
	@ApiModelProperty(value="자산유형2")	
	private String asetType2;
	@ApiModelProperty(value="자산유형명2")
	private String asetTypeNm2;		
	@ApiModelProperty(value="자산유형3")
	private String asetType3;
	@ApiModelProperty(value="자산유형명3")
	private String asetTypeNm3;			
	@ApiModelProperty(value="제조사")
	private String mftco;
	@ApiModelProperty(value="모델")
	private String model;
	@ApiModelProperty(value="S/N")
	private String sn;
	@ApiModelProperty(value="사업부코드")
	private String bizDeptCd;
	@ApiModelProperty(value="사업부명")
	private String bizDeptNm;
	@ApiModelProperty(value="부서코드")
	private String deptCd;
	@ApiModelProperty(value="부서명")
	private String deptNm;
	@ApiModelProperty(value="담당자")
	private String chrgr;
	@ApiModelProperty(value="담당자명")
	private String chrgrNm;
	@ApiModelProperty(value="사업장")
	private String bsplc;
	@ApiModelProperty(value="자산상태")
	private String asetStus;		
	@ApiModelProperty(value="자산상태")
	private String asetStusNm;
	@ApiModelProperty(value="태그구분")
	private String tagYn;		
	@ApiModelProperty(value="등록일자")
	private String regDt;
}
