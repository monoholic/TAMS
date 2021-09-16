package kr.co.trito.tams.comm.util.mail;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import kr.co.trito.tams.comm.util.msg.Message;
import kr.co.trito.tams.comm.util.res.Response;
import kr.co.trito.tams.comm.util.res.ResponseService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor

public class MailController {
	
	private final ResponseService responseService;	
	private final Message message;	
	private final MailService mailService;
	
	/*
	 * form-data 전송
	 * subject : 제목
	 * to : 수신 (array)
	 * cc : 참조 (array)
	 * content : 본문내용 (java 단에서 html 포맷으로 변경 처리)
	 * file : 첨부파일 (array)
	 * */
	@PostMapping("/mail")
	public ResponseEntity<? extends Response> mailSend(MailDto mailDto) {
		try {
			mailService.mailSend(mailDto);			
		} catch (Exception e) {
			return responseService.error(message.getMessage("info.http.status.internal.server.error"),
					message.getMessage("info.mail.status.error.msg"));
		}		
		
		return responseService.success(message.getMessage("info.http.status.ok"),
				message.getMessage("info.mail.status.ok.msg"));		
	}
}
