package kr.co.trito.tams.comm.util.file;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "file")
@Component
@Setter
@Getter
public class FileStorageProperties {
    private String uploadDir;
}

