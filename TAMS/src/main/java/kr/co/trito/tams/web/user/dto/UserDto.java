package kr.co.trito.tams.web.user.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ApiModel("사용자 정보")
public class UserDto {
	private String email;
	private String name;
	private String password;	
	private String useYn;
	private String updr;
	private String regr;
	private List<String> roles;
}

