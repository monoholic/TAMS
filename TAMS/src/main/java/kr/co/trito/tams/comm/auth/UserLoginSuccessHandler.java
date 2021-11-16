package kr.co.trito.tams.comm.auth;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import kr.co.trito.tams.web.system.user.dto.UserMngDto;
import kr.co.trito.tams.web.system.user.service.UserMngService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Autowired
	UserMngService userMngService;
	
	@Override 
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException { 
		SecurityContextHolder.getContext().setAuthentication(authentication); 
		
		//인증 성공후 session에 정보 등록
		HttpSession session = request.getSession();
		UserDetails userInfo = (UserDetails)authentication.getPrincipal();
		
		UserMngDto user = userMngService.selectUserInfo(userInfo.getUsername());
		
		session.setAttribute("authentication", userInfo);
		
		List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
        //String strAuth = authorities.get(0).getAuthority();
        session.setAttribute("auth", authorities);
		
		session.setAttribute("userInfo", user);
		
		response.sendRedirect("/"); 
	}
}
