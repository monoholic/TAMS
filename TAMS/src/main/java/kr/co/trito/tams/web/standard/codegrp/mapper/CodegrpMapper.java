package kr.co.trito.tams.web.standard.codegrp.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.standard.codegrp.dto.CodegrpDto;

@Mapper
public interface CodegrpMapper {
	
	public List<CodegrpDto> selectCodegrpList(SearchCondition condtion);
	
	public int selectCountCodegrp(SearchCondition condtion);
	
	public int codegrpMngMerge(Map<String, Object> data);
	
	public int codegrpMngDelete(CodegrpDto dto);
	
}
