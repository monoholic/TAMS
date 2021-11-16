package kr.co.trito.tams.web.user.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import kr.co.trito.tams.comm.exception.UserNotFoundException;
import kr.co.trito.tams.comm.util.msg.Message;
import kr.co.trito.tams.web.user.dto.UserDto;
import kr.co.trito.tams.web.user.dto.UserInfo;
import kr.co.trito.tams.web.user.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserMapper userMapper;
	
	@Autowired
	Message message;

	@Override 
	public UserDetails loadUserByUsername(String email) { 
		UserDto user = userMapper.selectUserByEmail(email);
		
		 if(user == null) {			 
			 throw new UserNotFoundException(
					 new StringBuilder()
					 	.append(message.getMessage("error.login.authentication"))
					 	.append(" [")
					 	.append(email)
					 	.append("]")
					 	.toString()
					 );
		 } else { 
			 user.setRoles(userMapper.selectUserRoleByEmail(email));
		 }		
		 
		 return createUser(user);
	}
	
	private org.springframework.security.core.userdetails.User createUser(UserDto userDto) {
    	List<String> roles = userDto.getRoles();
		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		
		for(String role : roles) {
			grantedAuthorities.add(new GrantedAuthority() {
				@Override
				public String getAuthority() {
					return role;
				}
			});
		}
		
		return new UserInfo(userDto.getUserId(),
				userDto.getUserPw(),
				grantedAuthorities, userDto);
		
		/*
		 * return new
		 * org.springframework.security.core.userdetails.User(userDto.getUserId(),
		 * userDto.getUserPw(), grantedAuthorities);
		 */
		
    }
	
}
