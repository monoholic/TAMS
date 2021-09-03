package kr.co.trito.tams.web.sample.dto;

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
//@ApiModel("Board Model")
public class BoardDto {
	@ApiModelProperty(value="게시글 번호")
	private String id;
	
	@ApiModelProperty(value="게시글 제목")
	private String title;
}
