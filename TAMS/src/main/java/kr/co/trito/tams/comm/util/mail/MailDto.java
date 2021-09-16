package kr.co.trito.tams.comm.util.mail;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MailDto {
	private String subject;
	private String[] to;	
	private String[] cc;
	private String content;
	private MultipartFile[] file;
}

