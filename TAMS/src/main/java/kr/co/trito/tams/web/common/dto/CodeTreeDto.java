package kr.co.trito.tams.web.common.dto;

import io.swagger.annotations.ApiModel;
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
@ApiModel("공통코드(트리용) 정보")
public class CodeTreeDto {
	private String id;
	private String parent;
	private String text;
	private String depth;	
}

