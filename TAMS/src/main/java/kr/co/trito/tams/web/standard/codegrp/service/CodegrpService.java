package kr.co.trito.tams.web.standard.codegrp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.standard.codegrp.dto.CodegrpDto;
import kr.co.trito.tams.web.standard.codegrp.mapper.CodegrpMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CodegrpService {
	
	@Autowired
	CodegrpMapper mapper;
	
	public List<CodegrpDto> selectCodegrpList(SearchCondition condition) {
		return mapper.selectCodegrpList(condition);
	}
	
	public int selectCountCodegrp(SearchCondition condtion) {
		return mapper.selectCountCodegrp(condtion);
	}
	
	public int codegrpMngMerge(CodegrpDto dto){
		return mapper.codegrpMngMerge(dto);
	}
	
	public int codegrpMngDelete(CodegrpDto dto){
		return mapper.codegrpMngDelete(dto);
	}
	
}
