package kr.co.trito.tams.web.user.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel("사용자 정보")
public class UserInfo extends User {
	
	private static final long serialVersionUID = -3535780160311802626L;
	
	private UserDto dto;
	
	public UserInfo(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, true, true, true, true, authorities);
	}
	
	public UserInfo(String username, String password, Collection<? extends GrantedAuthority> authorities, UserDto dto) {
		super(username, password, true, true, true, true, authorities);
		this.dto = dto;
	}
}

