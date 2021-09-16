package kr.co.trito.tams.comm.util.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
//@ApiModel(description = "Open Api Response Model")
public class ApiResponse<T> extends Response {	
	@ApiModelProperty(value="전체 조회수", example="4900")
	private String totalCount;	
	
	@ApiModelProperty(value="조회 페이지 번호", example="1")
	private String currentPage;
	
	@ApiModelProperty(value="페이지별 조회수", example="10")
	private String numOfRows;
	
	@ApiModelProperty(value="조회 데이터")
	private T data;
	
	public ApiResponse() {}
	
	public ApiResponse(T data, int totalCount, int currentPage, int numOfRows) {
		this.totalCount = Integer.toString(totalCount) ;
		this.currentPage = Integer.toString(currentPage);
		this.numOfRows = Integer.toString(numOfRows);
		this.data = data;
	}
}
