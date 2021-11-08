package kr.co.trito.tams.web.system.menu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.system.menu.dto.MenuDto;

@Mapper
public interface MenuMapper {
	public List<MenuDto> selectMenuList(String email);
	
	public int selectCountMenu(SearchCondition condtion);
	
	public List<MenuDto> selectMenuMgrList(SearchCondition condtion);
	
	public int menuMgrInsert(MenuDto dto);
	
	public int menuMgrUpdate(MenuDto dto);
	
	public int menuMgrDelete(MenuDto dto);
	
}
