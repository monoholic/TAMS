package kr.co.trito.tams.web.asset.status.information.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@ApiModel("자산상태 정보")
public class AssetStatusExcelDto {
	@ApiModelProperty(value="자산번호")
	private String asetNo;
	@ApiModelProperty(value="자산명")
	private String asetNm;		
	@ApiModelProperty(value="공통코드(자산유형1명)")
	private String asetType1Nm;
	@ApiModelProperty(value="공통코드(자산유형2명)")
	private String asetType2Nm;
	@ApiModelProperty(value="공통코드(자산유형3명)")
	private String asetType3Nm;
	@ApiModelProperty(value="제조사")
	private String mftco;
	@ApiModelProperty(value="모델")
	private String model;
	@ApiModelProperty(value="제품번호")
	private String sn;
	@ApiModelProperty(value="사업부명")
	private String bizDeptNm;
	@ApiModelProperty(value="부서명")
	private String deptNm;
	@ApiModelProperty(value="담장자")
	private String chrgr;
	@ApiModelProperty(value="공통코드(자산상태명)")
	private String asetStusNm;
}
