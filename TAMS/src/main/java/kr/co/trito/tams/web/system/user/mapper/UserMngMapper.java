package kr.co.trito.tams.web.system.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.system.user.dto.UserMngDto;

@Mapper
public interface UserMngMapper {
	
	public int selectCountUser(SearchCondition condtion);
	
	public List<UserMngDto> selectUserMngList(SearchCondition condtion);
	
	public int userMngInsert(UserMngDto dto);
	
	public int userMngUpdate(UserMngDto dto);
	
	public int userMngDelete(UserMngDto dto);
	
}
