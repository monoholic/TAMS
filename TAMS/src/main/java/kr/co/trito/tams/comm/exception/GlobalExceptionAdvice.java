package kr.co.trito.tams.comm.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import kr.co.trito.tams.comm.utils.Message;
import kr.co.trito.tams.comm.utils.res.Response;
import kr.co.trito.tams.comm.utils.res.ResponseService;

@RestControllerAdvice
public class GlobalExceptionAdvice {

	@Autowired
	Message message;
	
	@Autowired
	ResponseService responseService;
	
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handlerRuntimeException(Exception e) {
        return responseService.error();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Response> illegalArgumentException(IllegalArgumentException e) {
        return responseService.error(HttpStatus.BAD_REQUEST, 
        		message.getMessage("info.http.status.bad.request"), e.getMessage());
    }
    
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Response> usernameNotFoundException(UsernameNotFoundException e) {
        return responseService.error(HttpStatus.UNAUTHORIZED, 
        		message.getMessage("info.http.status.unauthorized"), message.getMessage("error.login.bad.credential"));
    }    
}