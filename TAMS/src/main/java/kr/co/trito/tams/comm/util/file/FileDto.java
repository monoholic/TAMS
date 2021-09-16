package kr.co.trito.tams.comm.util.file;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
public class FileDto {
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;
}
