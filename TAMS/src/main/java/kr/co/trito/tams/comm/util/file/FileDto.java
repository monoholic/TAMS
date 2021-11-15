package kr.co.trito.tams.comm.util.file;

import kr.co.trito.tams.web.sample.dto.BoardDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class FileDto {
    
	private long fileId;
    private String rfncKey1;
    private String fileNm;
    private String orgFileNm;
    private long fileSize;
    private int sortOdr;
    private int dwldCnt;
    private String useYn;
    private String regr;
    private String regDt;
    private String updr;
    private String upDt;
    
    private String fileDownloadUri;
    private String fileType;
}
