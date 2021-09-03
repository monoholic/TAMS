package kr.co.trito.tams.comm.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;

@Configuration
public class Message {
	@Autowired
	private MessageSource messageSource;
	
	public String getMessage(String code) {
		return this.getMessage(code, null);
	}
		
	public String getMessage(String code, String[] args) {
		return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
	}
}
