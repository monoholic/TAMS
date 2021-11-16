package kr.co.trito.tams.comm.util.file;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import kr.co.trito.tams.comm.exception.FileHandleException;
import kr.co.trito.tams.comm.util.msg.Message;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileService {
	
	@Autowired
	Message msg;

	private Path fileLocation = null;	
	
	@Autowired
	public FileService(FileStorageProperties fileStorageProperties) {
		log.error("@@@@ "+fileStorageProperties.getUploadDir());
        this.fileLocation = Paths.get(fileStorageProperties.getUploadDir()) .toAbsolutePath().normalize();
        try {
        	Files.createDirectories(this.fileLocation);
		} catch (Exception e) {
			throw new FileHandleException(msg.getMessage("error.file.create.directory"));
		}
	}
	
	public String store(MultipartFile file) {
		//UUID 생성
		UUID uuid = UUID.randomUUID();
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		String savedName = uuid.toString();
		try {
			if (fileName.contains(".."))
				throw new FileHandleException(msg.getMessage("error.file.name.format", new String[] {fileName}));
			
//			Path targetPath = this.fileLocation.resolve(fileName);
			Path targetPath = this.fileLocation.resolve(savedName);
			
			Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
			
		} catch (Exception e) {
			throw new FileHandleException(msg.getMessage("error.file.upload.fail", new String[] {fileName}));
		}
		
		return savedName;
//		return fileName;
	}
		
	public Resource load(String fileName) {
		try {
    		Path filePath = this.fileLocation.resolve(fileName).normalize();
    		log.error("@@ "+filePath);
    		org.springframework.core.io.Resource resource = new UrlResource(filePath.toUri());
    		
    		if (resource.exists()) {
    			return resource; 
    		} else {
    			throw new FileHandleException(msg.getMessage("error.file.not.found", new String[] {fileName}));
    		}
		} catch (Exception e) {
			throw new FileHandleException(msg.getMessage("error.file.not.found", new String[] {fileName}));
		}
	}
	
}
