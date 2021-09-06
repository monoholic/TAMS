package kr.co.trito.tams.web.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.trito.tams.comm.utils.search.SearchCondition;
import kr.co.trito.tams.web.sample.dto.BoardDto;
import kr.co.trito.tams.web.sample.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {

	@Autowired
	BoardMapper mapper;
	
	public int webCountBoard(SearchCondition condtion) {
		return mapper.webCountBoard(condtion);
	}
	
	public List<BoardDto> webSelectBoard(SearchCondition condtion){
		return mapper.webSelectBoard(condtion);
	}	
}