package kr.co.trito.tams.web.sample.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.sample.controller.BoardController;
import kr.co.trito.tams.web.sample.dto.BoardDto;
import kr.co.trito.tams.web.sample.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	
	public int webUpdateBoard(BoardDto board){
		return mapper.webUpdateBoard(board);
	}
	
	public int webDeleteBoard(BoardDto board){
		return mapper.webDeleteBoard(board);
	}
	
	public int webInsertBoard(BoardDto board){
		return mapper.webInsertBoard(board);
	}
}