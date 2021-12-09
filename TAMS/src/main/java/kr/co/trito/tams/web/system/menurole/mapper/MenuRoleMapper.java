package kr.co.trito.tams.web.system.menurole.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.system.menu.dto.MenuDto;
import kr.co.trito.tams.web.system.menurole.dto.MenuRoleDto;
import kr.co.trito.tams.web.system.menurole.dto.RoleGroupDto;

@Mapper
public interface MenuRoleMapper {
	public List<RoleGroupDto> selectRoleGroup();
	
	public int selectCountMenu(SearchCondition condtion);
	
	public List<MenuRoleDto> selectMenuRoleMngList(SearchCondition condtion);
	
	public int menuRoleMngRoleSave(MenuRoleDto dto);
	
	public int menuMngUpdate(MenuDto dto);
	
}
