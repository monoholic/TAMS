package kr.co.trito.tams.web.system.menu.service;

import java.util.List;

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
	
	
	public List<MenuDto> selectMenuList(String email){
		return mapper.selectMenuList(email);
	}
	
	
	public int selectCountMenu(SearchCondition condtion) {
		return mapper.selectCountMenu(condtion);
	}
	
	
	public List<MenuDto> selectMenuMgrList(SearchCondition condition){
		return mapper.selectMenuMgrList(condition);
	}	
	
	public int selectMenuMgrInsert(MenuDto dto) {
		return mapper.menuMgrInsert(dto);
	}
	
	public int selectMenuMgrUpdate(MenuDto dto) {
		return mapper.menuMgrUpdate(dto);
	}
	
	public int selectMenuMgrDelete(MenuDto dto) {
		return mapper.menuMgrDelete(dto);
	}
	
}