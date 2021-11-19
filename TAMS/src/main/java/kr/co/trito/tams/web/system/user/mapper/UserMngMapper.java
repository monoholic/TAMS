package kr.co.trito.tams.web.system.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.system.user.dto.UserMngDto;
import kr.co.trito.tams.web.system.user.dto.UserMngExcelDto;

@Mapper
public interface UserMngMapper {
	
	public int selectCountUser(SearchCondition condtion);
	
	public UserMngDto selectUserInfo(String userId);
	
	public List<UserMngDto> selectUserMngList(SearchCondition condtion);
	
	public List<UserMngExcelDto> selectUserMngListExcel(SearchCondition condtion);
	
	public int userMngInsert(UserMngDto dto);
	
	public int userMngUpdate(UserMngDto dto);
	
	public int userMngDelete(UserMngDto dto);
	
}
