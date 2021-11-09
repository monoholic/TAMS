package kr.co.trito.tams.web.system.role.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.system.menu.dto.MenuDto;
import kr.co.trito.tams.web.system.role.dto.RoleDto;
import kr.co.trito.tams.web.system.role.mapper.RoleMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RoleService {

	@Autowired
	RoleMapper mapper;
	
	public List<RoleDto> selectRoleList(){
		return mapper.selectRoleList();
	}
	
	public int selectCountRole(SearchCondition condtion) {
		return mapper.selectCountRole(condtion);
	}
	
	public List<RoleDto> selectRoleMngList(SearchCondition condition){
		return mapper.selectRoleMngList(condition);
	}	
	
	public int roleMngInsert(RoleDto dto) {
		return mapper.roleMngInsert(dto);
	}
	
	public int roleMngUpdate(RoleDto dto) {
		return mapper.roleMngUpdate(dto);
	}
	
	public int roleMngDelete(RoleDto dto) {
		return mapper.roleMngDelete(dto);
	}
	
}