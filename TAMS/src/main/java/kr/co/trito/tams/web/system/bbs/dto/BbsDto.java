package kr.co.trito.tams.web.system.bbs.dto;

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
@ApiModel("게시판 정보")
public class BbsDto {
	@ApiModelProperty(value="게시글 ID")
	private String bbsId;
	@ApiModelProperty(value="사용자 ID")
	private String userId;
	@ApiModelProperty(value="게시글 그룹(공통코드)")
	private String bbsGrpId;
	@ApiModelProperty(value="게시글 깊이")
	private String bbsDp;
	@ApiModelProperty(value="제목")
	private String bbsTtl;     
	@ApiModelProperty(value="내용")
	private String bbsCn;    
	@ApiModelProperty(value="조회수") 
	private String viewCnt;      
	@ApiModelProperty(value="삭제구분") 
	private String delYn;  
	@ApiModelProperty(value="수정자")
	private String updr;  
	@ApiModelProperty(value="수정일자")
	private String upDt;     
	@ApiModelProperty(value="등록자")
	private String regr;    
	@ApiModelProperty(value="등록일자")
	private String regDt;     
}