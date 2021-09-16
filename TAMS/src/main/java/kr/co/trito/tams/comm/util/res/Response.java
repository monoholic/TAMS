package kr.co.trito.tams.comm.util.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel("Response Model")
public abstract class Response {
	@ApiModelProperty(value="조회 결과 메세지", example="정상적으로 조회되었습니다")
	protected String message;
	
	@ApiModelProperty(value="조회 결과 코드", example="200")
	protected String code;		
}

