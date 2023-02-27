package kr.co.trito.tams.web.standard.tag.dto;

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
@ApiModel("태그발행")
public class IssueTagDto {
	@ApiModelProperty(value="자산번호")
	private String asetNo;		
	@ApiModelProperty(value="자산명")
	private String asetNm;		
	@ApiModelProperty(value="자산유형1")
	private String asetType1;		
	@ApiModelProperty(value="자산유형2")
	private String asetType2;		
	@ApiModelProperty(value="자산유형3")
	private String asetType3;		
	@ApiModelProperty(value="제조사")
	private String mftco;		
	@ApiModelProperty(value="모델")
	private String model;		
	@ApiModelProperty(value="S/N")
	private String sn;		
	@ApiModelProperty(value="사업부")
	private String bizDeptCd;		
	@ApiModelProperty(value="사업부명")
	private String bizDeptNm;		
	@ApiModelProperty(value="부서")
	private String deptCd;		
	@ApiModelProperty(value="부서명")
	private String deptNm;		
	@ApiModelProperty(value="담당자")
	private String chrgr;		
	@ApiModelProperty(value="태그ID")
	private String tagId;		
	@ApiModelProperty(value="태그여부")
	private String tagYn;		
	@ApiModelProperty(value="등록일자")
	private String regDt;		
	@ApiModelProperty(value="등록자")
	private String regr;		
	@ApiModelProperty(value="수정자")
	private String updr;		
	@ApiModelProperty(value="수정일자")
	private String upDt;		
	@ApiModelProperty(value="태그구분")
	private String tagDiv;		
	@ApiModelProperty(value="태그키")
	private String tagKey;		
	@ApiModelProperty(value="의뢰번호")
	private String reqNo;		
	@ApiModelProperty(value="태깅이력")
	private String tagHistory;		
}
