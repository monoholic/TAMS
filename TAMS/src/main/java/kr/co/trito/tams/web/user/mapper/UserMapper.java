package kr.co.trito.tams.web.user.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.trito.tams.web.user.dto.UserDto;

@Mapper
public interface UserMapper {
	public void insertUser(UserDto userDto);
	public void insertUserRole(Map<String, String> userRole);
	public UserDto selectUserByEmail(String email);
	public List<String> selectUserRoleByEmail(String email);
}
