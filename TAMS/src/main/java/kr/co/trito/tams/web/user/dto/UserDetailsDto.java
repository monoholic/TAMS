package kr.co.trito.tams.web.user.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Delegate;

@RequiredArgsConstructor
@Getter
@Setter
public class UserDetailsDto implements UserDetails {

	@Delegate
	private final UserDto userDto;
	private final Collection<GrantedAuthority> authorities;

	@Override 
	public String getPassword() { 
		return userDto.getUserPw(); 
	}
	
	@Override 
	public String getUsername() { 
		return userDto.getEmail() ;
	}

    /** 계정 만료 여부 (true : 만료 안됨, false : 만료)*/
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    /** 계정 잠김 여부 (true : 잠기지 않음, false : 잠김)*/    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }    

    /** 비밀번호 만료 여부 (true : 만료 안됨, false : 만료)*/
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**사용자 활성화 여부 (ture : 활성화, false : 비활성화)*/
    @Override
    public boolean isEnabled() {
        //이메일이 인증되어 있고 계정이 잠겨있지 않으면 true
        return true;
    }
	
}
