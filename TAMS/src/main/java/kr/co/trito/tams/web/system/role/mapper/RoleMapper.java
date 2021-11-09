package kr.co.trito.tams.web.system.role.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.system.role.dto.RoleDto;

@Mapper
public interface RoleMapper {
	public List<RoleDto> selectRoleList();
	
	public int selectCountRole(SearchCondition condtion);
	
	public List<RoleDto> selectRoleMngList(SearchCondition condtion);
	
	public int roleMngInsert(RoleDto dto);
	
	public int roleMngUpdate(RoleDto dto);
	
	public int roleMngDelete(RoleDto dto);
	
}
