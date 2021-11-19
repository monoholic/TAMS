package kr.co.trito.tams.web.system.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.system.user.dto.UserMngDto;
import kr.co.trito.tams.web.system.user.dto.UserMngExcelDto;
import kr.co.trito.tams.web.system.user.mapper.UserMngMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserMngService {

	@Autowired
	UserMngMapper mapper;
	
	
	public int selectCountUser(SearchCondition condtion) {
		return mapper.selectCountUser(condtion);
	}
	
	
	public UserMngDto selectUserInfo(String userId){
		return mapper.selectUserInfo(userId);
	}	
	
	public List<UserMngDto> selectUserMngList(SearchCondition condition){
		return mapper.selectUserMngList(condition);
	}
	
	public List<UserMngExcelDto> selectUserMngListExcel(SearchCondition condition){
		return mapper.selectUserMngListExcel(condition);
	}	
	
	public int userMngInsert(UserMngDto dto) {
		return mapper.userMngInsert(dto);
	}
	
	public int userMngUpdate(UserMngDto dto) {
		return mapper.userMngUpdate(dto);
	}
	
	public int userMngDelete(UserMngDto dto) {
		return mapper.userMngDelete(dto);
	}
	
}