package kr.co.trito.tams.comm.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import kr.co.trito.tams.comm.util.msg.Message;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Autowired	
	Message message;
	
	@Override 
	public Authentication authenticate(Authentication authentication) throws AuthenticationException { 
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

		// AuthenticaionFilter에서 생성된 토큰으로부터 아이디와 비밀번호를 조회함 
		String userEmail = token.getName(); 
		String userPassword = (String) token.getCredentials();
		
		UserDetails userDetails = (UserDetails) userDetailsService.loadUserByUsername(userEmail);
	
		if (!passwordEncoder.matches(userPassword, userDetails.getPassword())) { 
			throw new BadCredentialsException(message.getMessage("error.login.bad.credential") + " [ " + userDetails.getUsername() + "]"); 			
		}
		
		return new UsernamePasswordAuthenticationToken(userDetails, userPassword, userDetails.getAuthorities());
	}
	
	@Override 
	public boolean supports(Class<?> authentication) { 
		return authentication.equals(UsernamePasswordAuthenticationToken.class); 
	}
}
