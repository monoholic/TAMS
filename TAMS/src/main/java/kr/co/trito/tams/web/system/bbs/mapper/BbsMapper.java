package kr.co.trito.tams.web.system.bbs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.standard.code.dto.CodeDto;
import kr.co.trito.tams.web.system.bbs.dto.BbsDto;

@Mapper
public interface BbsMapper {
	public List<BbsDto> selectBbsMngList(SearchCondition condition);
	
	public List<BbsDto> selectBbsList(SearchCondition condition);
	
	public BbsDto selectBbsInfo(SearchCondition condtion);
	
	public int selectCountBbsMng(SearchCondition condition);
	
	public int selectCountBbs(SearchCondition condition);
	
	public List<CodeDto> selectBbsCodeList(String email);
	
	public int bbsMngInsert(BbsDto dto);
	
	public int bbsMngUpdate(BbsDto dto);
	
	public int bbsMngDelete(BbsDto dto);
	
	public int updateViewCnt(BbsDto dto);
}
