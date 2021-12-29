package kr.co.trito.tams.web.asset.regist.outofbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.asset.regist.outofbook.dto.AssetMasDto;

@Mapper
public interface OutOfBookMapper {	
	public int selectOutOfBookCount(SearchCondition condition);	
	public List<AssetMasDto> selectOutOfBookList(SearchCondition condition);
}
