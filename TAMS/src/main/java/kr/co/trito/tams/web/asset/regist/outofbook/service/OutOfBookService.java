package kr.co.trito.tams.web.asset.regist.outofbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.asset.regist.outofbook.dto.OutOfBookDto;
import kr.co.trito.tams.web.asset.regist.outofbook.mapper.OutOfBookMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OutOfBookService {

	@Autowired
	OutOfBookMapper mapper;
	
	//부외자산등록 리스트 카운트
	public int selectCountOutOfBook(SearchCondition condition) {
		return mapper.selectCountOutOfBook(condition);
	}	
	
	//부외자산등록 리스트
	public List<OutOfBookDto> selectOutOfBookList(SearchCondition condition) {
		return mapper.selectOutOfBookList(condition);
	}	
}
