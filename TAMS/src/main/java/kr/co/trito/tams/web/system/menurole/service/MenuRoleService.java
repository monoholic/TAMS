package kr.co.trito.tams.web.system.menurole.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.system.menu.dto.MenuDto;
import kr.co.trito.tams.web.system.menurole.dto.MenuRoleDto;
import kr.co.trito.tams.web.system.menurole.dto.RoleGroupDto;
import kr.co.trito.tams.web.system.menurole.mapper.MenuRoleMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MenuRoleService {

	@Autowired
	MenuRoleMapper mapper;
	
	public List<RoleGroupDto> selectRoleGroup(){
		return mapper.selectRoleGroup();
	}
	
	public int selectCountMenu(SearchCondition condtion) {
		return mapper.selectCountMenu(condtion);
	}
	
	public List<MenuRoleDto> selectMenuRoleMngList(SearchCondition condition){
		return mapper.selectMenuRoleMngList(condition);
	}	
	
	public int menuRoleMngRoleSave(MenuRoleDto dto) {
		return mapper.menuRoleMngRoleSave(dto);
	}
	
	public int menuMngUpdate(MenuDto dto) {
		return mapper.menuMngUpdate(dto);
	}
	
}