package kr.co.trito.tams.web.standard.code.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.standard.code.dto.CodeDto;
import kr.co.trito.tams.web.standard.code.mapper.CodeMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CodeService {
	
	@Autowired
	CodeMapper mapper;
	
	public List<CodeDto> selectCodeList(SearchCondition condition) {
		return mapper.selectCodeList(condition);
	}
	
	public List<CodeDto> selectCodeGrpList(String email){
		return mapper.selectCodeGrpList(email);
	}
	
	public int selectCountCode(SearchCondition condition) {
		return mapper.selectCountCode(condition);
	}
	
	public int codeMngMerge(Map<String, Object> data) {
		return mapper.codeMngMerge(data);
	}
	
	public int codeMngDelete(CodeDto dto) {
		return mapper.codeMngDelete(dto);
	}
}
