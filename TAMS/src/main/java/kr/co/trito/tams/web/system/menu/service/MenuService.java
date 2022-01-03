package kr.co.trito.tams.web.system.menu.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.system.menu.dto.MenuDto;
import kr.co.trito.tams.web.system.menu.mapper.MenuMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MenuService {

	@Autowired
	MenuMapper mapper;
	
	public List<MenuDto> selectMenuList(String role){
		return mapper.selectMenuList(role);
	}
	
	public int selectCountMenu(SearchCondition condtion) {
		return mapper.selectCountMenu(condtion);
	}
	
	public List<MenuDto> selectMenuMngList(SearchCondition condition){
		return mapper.selectMenuMngList(condition);
	}	
	
	public int menuMngInsert(Map<String, Object> data) {
		return mapper.menuMngInsert(data);
	}
	
	public int menuMngUpdate(Map<String, Object> data) {
		return mapper.menuMngUpdate(data);
	}
	
	public int menuMngDelete(MenuDto dto) {
		return mapper.menuMngDelete(dto);
	}
	
}