package kr.co.trito.tams.comm.utils.mail;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import kr.co.trito.tams.comm.utils.msg.Message;
import kr.co.trito.tams.comm.utils.res.Response;
import kr.co.trito.tams.comm.utils.res.ResponseService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor

public class MailController {
	
	private final ResponseService responseService;	
	private final Message message;	
	private final MailService mailService;
	
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
