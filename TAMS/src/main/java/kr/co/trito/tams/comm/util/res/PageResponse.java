package kr.co.trito.tams.comm.util.res;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import kr.co.trito.tams.comm.util.search.SearchCondition;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PageResponse extends Response{
	
	@ApiModelProperty(value="조회 데이터 수")
	private int size;	
	@ApiModelProperty(value="마지막 페이지 수")	
	private int last_page;	
	@ApiModelProperty(value="조회 데이터")
	private List<?> data;
	@ApiModelProperty(value="조회 데이터2")
	private List<?> data2;
	@ApiModelProperty(value="검색조건")
	private SearchCondition condition;
	
	public PageResponse(SearchCondition condition, List<?> data) {
		this.condition = condition;
		this.last_page = condition.getLastPage();
		this.data = data;
		this.size =  data != null ? data.size() : 0;
	}
	
	public PageResponse(SearchCondition condition, List<?> data, List<?> data2) {
		this.condition = condition;
		this.data = data;
		this.data2 = data2;
		this.size =  data != null ? data.size() : 0;
	}
}
