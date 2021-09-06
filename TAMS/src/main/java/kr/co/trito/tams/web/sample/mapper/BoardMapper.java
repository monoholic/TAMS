package kr.co.trito.tams.web.sample.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.trito.tams.comm.utils.search.SearchCondition;
import kr.co.trito.tams.web.sample.dto.BoardDto;

@Mapper
public interface BoardMapper {
	public int webCountBoard(SearchCondition condtion);
	public List<BoardDto> webSelectBoard(SearchCondition condtion);
	public int apiCountBoard(SearchCondition condtion);
	public List<BoardDto> apiSelectBoard(SearchCondition condtion);	
}
