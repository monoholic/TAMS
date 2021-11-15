package kr.co.trito.tams.web.standard.code.service;

import java.util.List;

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
	
	public int codeMngInsert(CodeDto dto) {
		return mapper.codeMngInsert(dto);
	}
	
	public int codeMngUpdate(CodeDto dto) {
		return mapper.codeMngUpdate(dto);
	}
	
	public int codeMngDelete(CodeDto dto) {
		return mapper.codeMngDelete(dto);
	}
}
