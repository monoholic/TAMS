package kr.co.trito.tams.comm.auth;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class UserLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Override 
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException { 
		SecurityContextHolder.getContext().setAuthentication(authentication); 
		response.sendRedirect("/"); 
	}
}
