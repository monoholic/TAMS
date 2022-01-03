package kr.co.trito.tams.web.system.menu.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.system.menu.dto.MenuDto;

@Mapper
public interface MenuMapper {
	public List<MenuDto> selectMenuList(String role);
	
	public int selectCountMenu(SearchCondition condtion);
	
	public List<MenuDto> selectMenuMngList(SearchCondition condtion);
	
	public int menuMngInsert(Map<String, Object> data);
	
	public int menuMngUpdate(Map<String, Object> data);
	
	public int menuMngDelete(MenuDto dto);

}
