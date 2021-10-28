package kr.co.trito.tams.comm.auth;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import kr.co.trito.tams.comm.util.msg.Message;

public class UserLoginFailHandler implements AuthenticationFailureHandler {

	@Autowired
	Message message;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		
		if (exception instanceof AuthenticationServiceException) {
			request.setAttribute("loginFailMsg", message.getMessage("error.login.authentication"));
		
		} else if(exception instanceof BadCredentialsException) {
			request.setAttribute("loginFailMsg", message.getMessage("error.login.bad.credential"));
			
		} else if(exception instanceof LockedException) {
			request.setAttribute("loginFailMsg", message.getMessage("error.login.locked"));
			
		} else if(exception instanceof DisabledException) {
			request.setAttribute("loginFailMsg", message.getMessage("error.login.desabled"));
			
		} else if(exception instanceof AccountExpiredException) {
			request.setAttribute("loginFailMsg", message.getMessage("error.login.account.expired"));
			
		} else if(exception instanceof CredentialsExpiredException) {
			request.setAttribute("loginFailMsg", message.getMessage("error.login.credential.expired"));
		}
		
		// 로그인 페이지로 다시 포워딩
		RequestDispatcher dispatcher = request.getRequestDispatcher("/user/login");
		dispatcher.forward(request, response);

	}
}
