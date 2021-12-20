package kr.co.trito.tams.web.standard.code.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.standard.code.dto.CodeDto;

@Mapper
public interface CodeMapper {
	
	public List<CodeDto> selectCodeList(SearchCondition condition);
	
	public List<CodeDto> selectCodeGrpList(String email);
	
	public int selectCountCode(SearchCondition condition);
	
	public int codeMngMerge(Map<String, Object> data);
	
	public int codeMngDelete(CodeDto dto);
}
