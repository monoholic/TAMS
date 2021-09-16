package kr.co.trito.tams.comm.util.mail;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MailService {
	
	private JavaMailSender javaMailSender;
	private static final String FROM_ADDRESS = "kims197411@gmail.com";
	
	public void mailSend(MailDto mailDto) throws Exception {
		try {
			MailHandler mailHandler = new MailHandler(javaMailSender);
			mailHandler.setSubject(mailDto.getSubject());			
			mailHandler.setFrom(FROM_ADDRESS);
			mailHandler.setTo(mailDto.getTo()); //mail cc
			
			if(mailDto.getCc() != null)
				mailHandler.setCc(mailDto.getCc());
			
			//사이트에 맞는 메일폼 작성 필요
			String htmlContent = "<p2>" + mailDto.getContent() +"</p2>";
			mailHandler.setText(htmlContent, true);
			
			if(mailDto.getFile() != null) {
				for(MultipartFile file : mailDto.getFile()) {
					mailHandler.setAttach(file.getOriginalFilename(), file);
				}
			}			
			
			//이미지 첨부파일을 메일 본문에 삽입 (필요시 구현)
			//String htmlContent = "<p>" + mailDto.getContent() +"<p> <img src='cid:sample-img'>";
			//mailHandler.setInline("sample-img", mailDto.getFile());			
			
			mailHandler.send();			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
