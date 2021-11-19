package kr.co.trito.tams.comm.util.file.excel;

import org.apache.poi.ss.usermodel.Row;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel("투자정보 엑셀업로드용 Dto")
public abstract class ExcelDto {
	
	@ApiModelProperty(value="등록자")
	private String regr;
	@ApiModelProperty(value="등록일자")
	private String updr;
	@ApiModelProperty(value="수정자")
	private String regDt;
	@ApiModelProperty(value="수정일자")
	private String upDt;

	public abstract ExcelDto row(Row row);
	
}
