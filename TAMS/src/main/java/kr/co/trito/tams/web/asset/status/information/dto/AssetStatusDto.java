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
public class AssetStatusDto {
	@ApiModelProperty(value="자산번호")
	private String asetNo;
	@ApiModelProperty(value="자산명")
	private String asetNm;
	@ApiModelProperty(value="공통코드(자산유형1)")
	private String asetType1;
	@ApiModelProperty(value="공통코드(자산유형1명)")
	private String asetType1Nm;
	@ApiModelProperty(value="공통코드(자산유형2)")
	private String asetType2;
	@ApiModelProperty(value="공통코드(자산유형2명)")
	private String asetType2Nm;
	@ApiModelProperty(value="공통코드(자산유형3)")
	private String asetType3;
	@ApiModelProperty(value="공통코드(자산유형3명)")
	private String asetType3Nm;
	@ApiModelProperty(value="제조사")
	private String mftco;
	@ApiModelProperty(value="모델")
	private String model;
	@ApiModelProperty(value="제품번호")
	private String sn;
	@ApiModelProperty(value="사업부코드")
	private String bizDeptCd;
	@ApiModelProperty(value="사업부명")
	private String bizDeptNm;
	@ApiModelProperty(value="부서코드")
	private String deptCd;
	@ApiModelProperty(value="부서명")
	private String deptNm;
	@ApiModelProperty(value="담장자")
	private String chrgr;
	@ApiModelProperty(value="공통코드(사업장)")
	private String bsplc;
	@ApiModelProperty(value="공통코드(건물)")
	private String buld;
	@ApiModelProperty(value="공통코드(층)")
	private String floor;
	@ApiModelProperty(value="위치")
	private String loc;
	@ApiModelProperty(value="비용부서코드")
	private String expDeptCd;
	@ApiModelProperty(value="비용계정")
	private String expAcct;
	@ApiModelProperty(value="내용년수")
	private String durYear;
	@ApiModelProperty(value="취득가")
	private String acqPrc;
	@ApiModelProperty(value="잔존가")
	private String svalPrc;
	@ApiModelProperty(value="감가비")
	private String dprcPrc;
	@ApiModelProperty(value="취득일자")
	private String acqDt;
	@ApiModelProperty(value="부외여부")
	private String asetOutBookYn;
	@ApiModelProperty(value="공통코드(자산상태)")
	private String asetStus;
	@ApiModelProperty(value="공통코드(자산상태명)")
	private String asetStusNm;
	@ApiModelProperty(value="수정자")
	private String updr;
	@ApiModelProperty(value="수정일자")
	private String upDt;
	@ApiModelProperty(value="등록자")
	private String regr;
	@ApiModelProperty(value="등록일자")
	private String regDt;		
}
