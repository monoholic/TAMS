package kr.co.trito.tams.web.system.bbs.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.standard.code.dto.CodeDto;
import kr.co.trito.tams.web.system.bbs.dto.BbsDto;
import kr.co.trito.tams.web.system.bbs.mapper.BbsMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BbsService {
	
	@Autowired
	BbsMapper mapper;
	
	public List<BbsDto> selectBbsMngList(SearchCondition condition) {
		return mapper.selectBbsMngList(condition);
	}
	
	public List<BbsDto> selectBbsList(SearchCondition condition) {
		return mapper.selectBbsList(condition);
	}
	
	public BbsDto selectbbsInfo(SearchCondition condition){
		return mapper.selectBbsInfo(condition);
	}
	
	public int selectCountBbsMng(SearchCondition condition) {
		return mapper.selectCountBbsMng(condition);
	}
	
	public int selectCountBbs(SearchCondition condition) {
		return mapper.selectCountBbs(condition);
	}
	
	public int bbsMngInsert(Map<String, Object> items) {
		return mapper.bbsMngInsert(items);
	}
	
	public int bbsMngUpdate(Map<String, Object> items) {
		return mapper.bbsMngUpdate(items);
	}
	
	public int bbsMngDelete(BbsDto dto) {
		return mapper.bbsMngDelete(dto);
	}
	
	public int updateViewCnt(BbsDto dto) {
		return mapper.updateViewCnt(dto);
	}
}
