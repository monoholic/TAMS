package kr.co.trito.tams.web.user.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.trito.tams.web.user.dto.UserDto;
import kr.co.trito.tams.web.user.mapper.UserMapper;

@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;	
	
	public UserDto insertUser(UserDto userDto) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		userDto.setPassword(encoder.encode(userDto.getPassword()));
		userDto.setUseYn("Y");
		userDto.setUpdr("System");
		userDto.setRegr("System");
		userMapper.insertUser(userDto);
		
		if (userDto.getRoles() != null) {
			//register user-role
			for(String role : userDto.getRoles()) {
				Map<String, String> userRole = new HashMap<>();
				userRole.put("email", userDto.getEmail());
				userRole.put("roleId", role);
				userRole.put("useYn", "Y");
				userRole.put("updr", "System");
				userRole.put("regr", "System");				
				userMapper.insertUserRole(userRole);
			}
		} else {
			Map<String, String> userRole = new HashMap<>();
			userRole.put("email", userDto.getEmail());
			userRole.put("roleId", "1"); //user role
			userRole.put("useYn", "Y");
			userRole.put("updr", "System");
			userRole.put("regr", "System");				
			userMapper.insertUserRole(userRole);			
		}
		
		return userDto;
	}	
	
	public UserDto selectUserByEmail(String email) {
		UserDto user = userMapper.selectUserByEmail(email);		
		return user;
	}	
	
	public List<String> selectUserRoleByEmail(String email) {
		return userMapper.selectUserRoleByEmail(email);
	}	
}