package kr.co.trito.tams.comm.utils.res;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import kr.co.trito.tams.comm.utils.SearchCondition;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PageResponse extends Response{
	
	@ApiModelProperty(value="조회 데이터 수")
	private int size;
	@ApiModelProperty(value="조회 데이터")
	private List<?> data;
	@ApiModelProperty(value="검색조건")
	private SearchCondition condition;
	
	public PageResponse(SearchCondition condition, List<?> data) {
		this.condition = condition;
		this.data = data;
		this.size =  data != null ? data.size() : 0;
	}
}
